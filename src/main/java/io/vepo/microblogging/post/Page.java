package io.vepo.microblogging.post;

import java.util.List;

public record Page<T>(int offset, List<T> items) {

}
