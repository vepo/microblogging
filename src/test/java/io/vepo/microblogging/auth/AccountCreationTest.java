package io.vepo.microblogging.auth;

import static io.vepo.microblogging.user.UserActions.userCreator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.time.LocalDateTime;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.vepo.microblogging.infra.TestContainerPostgreResource;
import io.vepo.microblogging.user.User;

@QuarkusTest
@DisplayName("Account Creation")
@QuarkusTestResource(value = TestContainerPostgreResource.class)
public class AccountCreationTest {

    @TestHTTPResource
    @TestHTTPEndpoint(AuthenticationResource.class)
    URL authUrl;

    @Test
    @DisplayName("It should allow creating users")
    void createUserTest() {
            var beforeCreation = LocalDateTime.now();
            var createUserResponse = userCreator(authUrl).create("user-1", "user-1@microblogging.com", "123456")
                                                         .response();

            assertEquals(201, createUserResponse.statusCode(), "User creation endpoint should return 201 for success");
            var createdUser = createUserResponse.as(User.class);
            assertEquals("user-1", createdUser.getHandle(), "It should create an user with the requested handle!");
            assertEquals("user-1@microblogging.com", createdUser.getEmail(), "It should create an user with the requested email!");
            assertTrue(beforeCreation.isBefore(createdUser.getCreatedAt()), "The user should have a creation date.");
    }

    @Test
    @DisplayName("It should avoid creating users with duplicated handle/email")
    void avoidDuplicatedHandleTest() {
            userCreator(authUrl).create("user-1", "user-1@microblogging.com", "123456")
                                .successful();
            var failedHandleResponse = userCreator(authUrl).create("user-2", "user-1@microblogging.com", "789456")
                                                           .response();
            var failedEmailResponse = userCreator(authUrl).create("user-1", "user-2@microblogging.com", "789456")
                                                          .response();
            assertEquals(Status.CONFLICT.getStatusCode(), failedHandleResponse.statusCode(), "It should return Conflict");
            assertEquals(Status.CONFLICT.getStatusCode(), failedEmailResponse.statusCode(), "It should return Conflict");
    }
}
