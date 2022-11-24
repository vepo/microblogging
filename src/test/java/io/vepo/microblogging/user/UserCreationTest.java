package io.vepo.microblogging.user;

import static io.vepo.microblogging.user.UserActions.givenUserCreator;
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

@QuarkusTest
@DisplayName("User Creation")
@QuarkusTestResource(value = TestContainerPostgreResource.class)
class UserCreationTest {

        @TestHTTPResource
        @TestHTTPEndpoint(UserEndpoint.class)
        URL userUrl;

        @TestHTTPResource
        @TestHTTPEndpoint(LoginEndpoint.class)
        URL loginUrl;

        @Test
        @DisplayName("It should allow creating users")
        void createUserTest() {
                var beforeCreation = LocalDateTime.now();
                var createUserResponse = givenUserCreator(userUrl, loginUrl).create("user-1", "user-1@microblogging.com", "123456")
                                                                            .response();

                assertEquals(201, createUserResponse.statusCode(), "User creation endpoint should return 201 for success");
                User createdUser = createUserResponse.as(User.class);
                assertEquals("user-1", createdUser.getHandle(), "It should create an user with the requested handle!");
                assertEquals("user-1@microblogging.com", createdUser.getEmail(), "It should create an user with the requested email!");
                assertTrue(beforeCreation.isBefore(createdUser.getCreatedAt()), "The user should have a creation date.");
        }

        @Test
        @DisplayName("It should avoid creating users with duplicated handle/email")
        void avoidDuplicatedHandleTest() {
                givenUserCreator(userUrl, loginUrl).create("user-1", "user-1@microblogging.com", "123456")
                                .successful();
                var failedHandleResponse = givenUserCreator(userUrl, loginUrl).create("user-2", "user-1@microblogging.com", "789456")
                                                                              .response();
                var failedEmailResponse = givenUserCreator(userUrl, loginUrl).create("user-1", "user-2@microblogging.com", "789456")
                                                                             .response();
                assertEquals(Status.CONFLICT.getStatusCode(), failedHandleResponse.statusCode(), "It should return Conflict");
                assertEquals(Status.CONFLICT.getStatusCode(), failedEmailResponse.statusCode(), "It should return Conflict");
        }
}
