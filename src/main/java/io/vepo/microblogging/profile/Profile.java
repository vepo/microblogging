package io.vepo.microblogging.profile;

import java.time.LocalDateTime;

public record Profile(Long userId, String handle, ProfileImages images, LocalDateTime createdAt) {

}
