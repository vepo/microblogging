package io.vepo.microblogging.infra;

import java.security.PrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.vepo.microblogging.user.User;

@ApplicationScoped
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Inject
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Inject
    PrivateKey privateKey;

    @ConfigProperty(name = "mp.jwt.expiration.time.in.minutes", defaultValue = "1000")
    int expirationTimeInMinutes;

    public String generate(User user) {
        logger.info("Generating token for user! user={}", user);
        try {
            var now = Date.from(Instant.now());
            var jwtClaims = new JWTClaimsSet.Builder().issuer(issuer)
                    .subject(user.getId().toString())
                    .expirationTime(Date.from(Instant.now().plus(expirationTimeInMinutes, ChronoUnit.MINUTES)))
                    .notBeforeTime(now)
                    .issueTime(now)
                    .claim("authTime", now)
                    .jwtID(UUID.randomUUID().toString())
                    .claim("nickname", user.getHandle())
                    .claim("email", user.getEmail())
                    .claim("groups", new String[] { "USER" })
                    .build();

            var signer = new RSASSASigner(privateKey);
            var jwtHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();

            var signedJWT = new SignedJWT(jwtHeader, jwtClaims);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
