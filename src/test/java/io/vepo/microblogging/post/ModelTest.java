package io.vepo.microblogging.post;

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
        EqualsVerifier.forClass(Post.class)
                .usingGetClass()
                .suppress(Warning.SURROGATE_KEY).verify();
        ToStringVerifier.forClass(Post.class)
                .withClassName(NameStyle.SIMPLE_NAME)
                .verify();
    }
}
