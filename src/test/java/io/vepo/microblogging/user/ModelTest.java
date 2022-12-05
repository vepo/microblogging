package io.vepo.microblogging.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;

import io.vepo.microblogging.image.Image;
import io.vepo.microblogging.image.ImageFormat;
import io.vepo.microblogging.image.ImageType;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@DisplayName("Model")
public class ModelTest {
    @Test
    void modelTest() {
        var image = new Image(ImageType.AVATAR, ImageFormat.JPEG, new byte[] {
            0,
            0,
            0 });
        image.setId(1l);
        EqualsVerifier.forClass(User.class)
                      .usingGetClass()
                      .suppress(Warning.SURROGATE_KEY)
                      .verify();
        ToStringVerifier.forClass(User.class)
                        .withClassName(NameStyle.SIMPLE_NAME)
                        .withValueProvider(Image.class, x -> image)
                        .verify();
    }
}
