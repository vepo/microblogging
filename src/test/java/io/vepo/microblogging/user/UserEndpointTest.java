package io.vepo.microblogging.user;

import java.net.URL;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.vepo.microblogging.infra.CustomResource;

@QuarkusTest
@DisplayName("User Endpoint")
@QuarkusTestResource(value = CustomResource.class)
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
        given().contentType(ContentType.JSON)
                .body(new Credentials("user-1", "123456"))
                .post(loginUrl)
                .then()
                .statusCode(403);

        var start = LocalDateTime.now();
        var createUserResponse = given().contentType(ContentType.JSON)
                .body(new CreateUserRequest("user-1", "user-1@microblogging.com", "123456"))
                .post(userUrl)
                .andReturn();
        assertEquals(201, createUserResponse.statusCode());
        User createdUser = createUserResponse.as(User.class);
        assertEquals("user-1", createdUser.getHandle());
        assertEquals("user-1@microblogging.com", createdUser.getEmail());
        assertTrue(start.isBefore(createdUser.getCreatedAt()));

        given().contentType(ContentType.JSON)
                .body(new Credentials("user-1", "123456"))
                .post(loginUrl)
                .then()
                .statusCode(200);
    }
}
