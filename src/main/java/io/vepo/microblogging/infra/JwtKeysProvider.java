package io.vepo.microblogging.infra;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class JwtKeysProvider {
    @Produces
    public RSAPublicKey generatePublicKey() {
        try {
            return (RSAPublicKey) decodePublicKey(new String(
                    JwtKeysProvider.class.getResourceAsStream("/META-INF/resources/publicKey.pem").readAllBytes(),
                    "UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Produces
    public PrivateKey generatePrivateKey() {
        try {
            return readPrivateKey("/privateKey.pem");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read a PEM encoded private key from the classpath
     *
     * @param pemResName - key file resource name
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        return decodePrivateKey(
                new String(JwtKeysProvider.class.getResourceAsStream(pemResName).readAllBytes(), "UTF-8"));
    }

    /**
     * Decode a PEM encoded private key string to an RSA PrivateKey
     *
     * @param pemEncoded - PEM string for private key
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(toEncodedBytes(pemEncoded)));
    }

    /**
     * Decode a PEM encoded public key string to an RSA PublicKey
     *
     * @param pemEncoded - PEM string for private key
     * @return PublicKey
     * @throws Exception on decode failure
     */
    public static PublicKey decodePublicKey(String pemEncoded) throws Exception {
        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(toEncodedBytes(pemEncoded)));
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        return Base64.getDecoder().decode(removeBeginEnd(pemEncoded));
    }

    private static String removeBeginEnd(String pem) {
        return pem.replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)----", "")
                .replaceAll("\r\n", "")
                .replaceAll("\n", "")
                .trim();
    }
}
