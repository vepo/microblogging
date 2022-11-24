package io.vepo.microblogging.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@DisplayName("Model")
public class ModelTest {
    @Test
    void modelTest() {
        EqualsVerifier.forClass(User.class)
                .usingGetClass()
                .suppress(Warning.SURROGATE_KEY).verify();
        ToStringVerifier.forClass(User.class)
                .withClassName(NameStyle.SIMPLE_NAME)
                .verify();
    }
}
