package io.vepo.microblogging.infra;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class PasswordCrypt {
    @Inject
    @ConfigProperty(name = "password.iterations")
    int passwordIterations;

    @Inject
    @ConfigProperty(name = "password.key.length")
    int passwordKeyLength;

    @Inject
    @ConfigProperty(name = "password.algorithm")
    String algorithm;

    @Inject
    @ConfigProperty(name = "password.salt")
    String salt;

    public String hashPassword(String password) {
        char[] chars = password.toCharArray();
        byte[] bytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, bytes, passwordIterations, passwordKeyLength);
        Arrays.fill(chars, Character.MIN_VALUE);

        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(algorithm);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(securePassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        } finally {
            spec.clearPassword();
        }
    }
}
