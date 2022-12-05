package io.vepo.microblogging.user;

import static io.vepo.microblogging.user.UserActions.userCreator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.vepo.microblogging.auth.AuthenticationResource;
import io.vepo.microblogging.infra.TestContainerPostgreResource;
import static io.restassured.RestAssured.given;

@QuarkusTest
@DisplayName("Logged User")
@QuarkusTestResource(value = TestContainerPostgreResource.class)
public class MeResourceTest {
    @TestHTTPResource
    @TestHTTPEndpoint(MeResource.class)
    URL meUrl;

    @TestHTTPResource
    @TestHTTPEndpoint(AuthenticationResource.class)
    URL authUrl;

    @Test
    @DisplayName("It should allow create an avatar for logged user")
    void createUserAvatarTest() throws IOException, URISyntaxException {
        var authHeader = userCreator(authUrl).create("user-1", "user-1@microblogging.com", "123456").authenticate()
                                             .authorizationHeader();
        given().header(authHeader)
               .body(new UpdateImageRequest(Files.readString(Paths.get(MeResourceTest.class.getResource("image.data")
                                                                                           .toURI().getPath()))))
               .contentType(ContentType.JSON)
               .post(meUrl.toExternalForm() + "/avatar")
               .then()
               .statusCode(Status.OK.getStatusCode());
    }

    @Test
    @DisplayName("It should allow create an avatar for logged user")
    void updateUserAvatarTest() throws IOException, URISyntaxException {
        var authHeader = userCreator(authUrl).create("user-1", "user-1@microblogging.com", "123456").authenticate()
                                             .authorizationHeader();
        given().header(authHeader)
               .body(new UpdateImageRequest(Files.readString(Paths.get(MeResourceTest.class.getResource("image.data")
                                                                                           .toURI().getPath()))))
               .contentType(ContentType.JSON)
               .post(meUrl.toExternalForm() + "/avatar")
               .then()
               .statusCode(Status.OK.getStatusCode());
    }
}
