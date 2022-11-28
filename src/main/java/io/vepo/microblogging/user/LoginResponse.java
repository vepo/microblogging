package io.vepo.microblogging.user;

public record LoginResponse(long id, String handle, String username, String accessToken) {

}
