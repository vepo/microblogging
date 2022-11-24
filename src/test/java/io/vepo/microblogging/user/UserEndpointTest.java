package io.vepo.microblogging.user;

import static io.vepo.microblogging.infra.UserHelper.givenAuthenticator;
import static io.vepo.microblogging.infra.UserHelper.givenUserCreator;
import static io.vepo.microblogging.infra.UserHelper.withUserInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.vepo.microblogging.infra.TestContainerPostgreResource;

@QuarkusTest
@DisplayName("User Endpoint")
@QuarkusTestResource(value = TestContainerPostgreResource.class)
@TestMethodOrder(OrderAnnotation.class)
class UserEndpointTest {

        @TestHTTPResource
        @TestHTTPEndpoint(UserEndpoint.class)
        URL userUrl;

        @TestHTTPResource
        @TestHTTPEndpoint(LoginEndpoint.class)
        URL loginUrl;

        @Test
        @DisplayName("Create User")
        void createUserTest() {
                givenAuthenticator(loginUrl).authenticate()
                                .unauthorized();
                var start = LocalDateTime.now();
                var createUserResponse = givenUserCreator(userUrl, loginUrl)
                                .create("user-1", "user-1@microblogging.com", "123456")
                                .response();

                assertEquals(201, createUserResponse.statusCode());
                User createdUser = createUserResponse.as(User.class);
                assertEquals("user-1", createdUser.getHandle());
                assertEquals("user-1@microblogging.com", createdUser.getEmail());
                assertTrue(start.isBefore(createdUser.getCreatedAt()));

                givenAuthenticator(loginUrl, withUserInfo("user-1", "user-1@microblogging.com", "123456"))
                                .authenticate()
                                .successful();
        }

        @Test
        @DisplayName("Avoid duplicated handle")
        void avoidDuplicatedHandleTest() {
                givenUserCreator(userUrl, loginUrl).create("user-1", "user-1@microblogging.com", "123456")
                                .successful();
                givenUserCreator(userUrl, loginUrl).create("user-2", "user-1@microblogging.com", "789456")
                                .conflict();
                givenUserCreator(userUrl, loginUrl).create("user-1", "user-2@microblogging.com", "789456")
                                .conflict();
        }
}
