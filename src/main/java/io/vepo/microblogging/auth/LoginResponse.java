package io.vepo.microblogging.auth;

public record LoginResponse(long id, String handle, String username, String accessToken) {

}
