package io.vepo.microblogging.user;

import static io.vepo.microblogging.user.UserActions.DEFAULT_EMAIL;
import static io.vepo.microblogging.user.UserActions.DEFAULT_HANDLE;
import static io.vepo.microblogging.user.UserActions.DEFAULT_PASSWORD;
import static io.vepo.microblogging.user.UserActions.givenAuthenticator;
import static io.vepo.microblogging.user.UserActions.userCreator;
import static io.vepo.microblogging.user.UserActions.withUserInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.vepo.microblogging.infra.TestContainerPostgreResource;

@QuarkusTest
@DisplayName("Login")
@QuarkusTestResource(value = TestContainerPostgreResource.class)
public class AuthenticationResourceTest {
    @TestHTTPResource
    @TestHTTPEndpoint(UserEndpoint.class)
    URL userUrl;

    @TestHTTPResource
    @TestHTTPEndpoint(AuthenticationResource.class)
    URL authUrl;

    @Test
    @DisplayName("It should not allow login if the user doesn't exists")
    public void noUserLoginTest() {
        var response = givenAuthenticator(authUrl).authenticate().response();
        assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.statusCode(), "It should not authorize!");
    }

    @Test
    @DisplayName("It should not allow login if the user doesn't exists")
    public void wrongPasswordLoginTest() {
        userCreator(authUrl).create().successful();
        var response = givenAuthenticator(authUrl, withUserInfo(DEFAULT_HANDLE, DEFAULT_EMAIL, DEFAULT_PASSWORD + 'X'))
                .authenticate()
                .response();
        assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.statusCode(), "It should not authorize if the password is wrong!");
    }
}
