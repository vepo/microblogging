package io.vepo.microblogging.image;

import java.security.InvalidParameterException;
import java.util.stream.Stream;

public enum ImageFormat {
    JPEG;

    public static ImageFormat parse(String format) {
        return Stream.of(ImageFormat.values())
                     .filter(v -> v.toString().compareToIgnoreCase(format) == 0).findFirst()
                     .orElseThrow(() -> new InvalidParameterException(String.format("Invalid image format! format=%s",
                                                                                    format)));
    }
}
