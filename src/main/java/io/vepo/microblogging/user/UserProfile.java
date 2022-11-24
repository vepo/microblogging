package io.vepo.microblogging.user;

import java.time.LocalDateTime;

public record UserProfile(long id, String handle, LocalDateTime since) {}
