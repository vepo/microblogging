package io.vepo.microblogging.user;

public record CreateUserRequest(String handle, String email, String password) {

}
