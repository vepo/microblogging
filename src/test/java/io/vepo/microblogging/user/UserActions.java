package io.vepo.microblogging.user;

import static io.restassured.RestAssured.given;

import java.net.URL;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class UserActions {

    public interface Authenticator {
        AuthenticatedUserResponse authenticate();
    }

    public static class UserCreator {
        private final URL authUrl;

        private UserCreator(final URL authUrl) {
            this.authUrl = authUrl;
        }

        public UserCreated create(final UserInfo user) {
            logger.info("Trying to create user... info={}", user);
            return new UserCreated(authUrl, user, given().contentType(ContentType.JSON)
                    .body(new CreateUserRequest(user.handle(), user.email(), user.password()))
                    .post(authUrl + "/register")
                    .then());
        }

        public UserCreated create(final String handle, final String email, final String password) {
            return create(new UserInfo(handle, email, password));
        }

        public UserCreated create() {
            return create(DEFAULT_HANDLE, DEFAULT_EMAIL, DEFAULT_PASSWORD);
        }
    }

    public static class GivenCredentials implements Authenticator {
        private final URL authUrl;
        private final UserInfo userInfo;

        private GivenCredentials(final URL authUrl, final UserInfo userInfo) {
            this.authUrl = authUrl;
            this.userInfo = userInfo;
        }

        @Override
        public AuthenticatedUserResponse authenticate() {
            logger.info("Trying to authenticate user... info={}", userInfo);
            return new AuthenticatedUserResponse(given().contentType(ContentType.JSON)
                    .body(new Credentials(userInfo.handle(), userInfo.password()))
                    .post(authUrl + "/login")
                    .then());
        }

    }

    public static class UserCreated implements Authenticator {
        private final URL authUrl;
        private final UserInfo userInfo;
        private final ValidatableResponse httpResponse;

        public UserCreated(final URL authUrl, final UserInfo userInfo, final ValidatableResponse httpResponse) {
            this.authUrl = authUrl;
            this.userInfo = userInfo;
            this.httpResponse = httpResponse;
        }

        public UserCreated successful() {
            this.httpResponse.statusCode(201);
            return this;
        }

        @Override
        public AuthenticatedUserResponse authenticate() {
            logger.info("Trying to authenticate user... info={}", userInfo);
            return new AuthenticatedUserResponse(given().contentType(ContentType.JSON)
                    .body(new Credentials(userInfo.handle(), userInfo.password()))
                    .post(authUrl+"/login")
                    .then());
        }

        public UserCreated conflict() {
            this.httpResponse.statusCode(409);
            return this;
        }

        public Response response() {
            return httpResponse.extract()
                    .response();
        }
    }

    public static class AuthenticatedUserResponse {

        private final ValidatableResponse httpResponse;

        public AuthenticatedUserResponse(final ValidatableResponse httpResponse) {
            this.httpResponse = httpResponse;
        }

        public AuthenticatedUserResponse successful() {
            this.httpResponse.statusCode(200);
            return this;
        }

        public String jwtToken() {
            return this.httpResponse.extract()
                    .as(LoginResponse.class).accessToken();
        }

        public Header authorizationHeader() {
            return new Header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken());
        }

        public AuthenticatedUserResponse unauthorized() {
            this.httpResponse.statusCode(401);
            return this;
        }

        public Response response() {
            return this.httpResponse.extract().response();
        }

    }

    public static record UserInfo(String handle, String email, String password) {
    }

    public static class UserProfileViewer {

        private final URL userUrl;

        public UserProfileViewer(URL userUrl) {
            this.userUrl = userUrl;
        }

        public UserProfileViewed view(Long userId) {
            return new UserProfileViewed(given().get(userUrl.toExternalForm() + "/{userId}", Map.of("userId", userId))
                                                .thenReturn());
        }

        public UserProfileViewed view(String userHandle) {
            return new UserProfileViewed(given().get(userUrl.toExternalForm() + "/{userHandle}", Map.of("userHandle", userHandle))
                                                .thenReturn());
        }
    }

    public static class UserProfileViewed {

        private final Response response;

        public UserProfileViewed(Response response) {
            this.response = response;
        }

        public Response response() {
            return response;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(UserActions.class);

    public static final String DEFAULT_HANDLE = "user";

    public static final String DEFAULT_EMAIL = "user@microblogging.com";

    public static final String DEFAULT_PASSWORD = "123456";

    public static UserCreator userCreator(final URL authUrl) {
        return new UserCreator(authUrl);
    }

    public static Authenticator givenAuthenticator(final URL authUrl) {
        return givenAuthenticator(authUrl, new UserInfo(DEFAULT_HANDLE, DEFAULT_EMAIL, DEFAULT_PASSWORD));
    }


    public static Authenticator givenAuthenticator(final URL authUrl, final UserInfo userInfo) {
        return new GivenCredentials(authUrl, userInfo);
    }

    public static UserInfo withUserInfo(final String handle, final String email, final String password) {
        return new UserInfo(handle, email, password);
    }

    public static UserProfileViewer userProfileViewer(URL userUrl) {
        return new UserProfileViewer(userUrl);
    }

}
