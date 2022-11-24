package io.vepo.microblogging.infra;

import static io.restassured.RestAssured.given;

import java.net.URL;

import javax.ws.rs.core.HttpHeaders;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import io.vepo.microblogging.user.CreateUserRequest;
import io.vepo.microblogging.user.Credentials;

public class UserHelper {

    public static final String DEFAULT_HANDLE = "user";
    public static final String DEFAULT_EMAIL = "user@microblogging.com";
    public static final String DEFAULT_PASSWORD = "123456";

    private static record UserInfo(String handle, String email, String password) {
    }

    public static class UserCreator {
        private final URL userUrl;
        private final URL loginUrl;

        private UserCreator(URL userUrl, URL loginUrl) {
            this.userUrl = userUrl;
            this.loginUrl = loginUrl;
        }

        public UserCreatorResponse create(String handle, String email, String password) {
            return new UserCreatorResponse(loginUrl, new UserInfo(handle, email, password),
                    given().contentType(ContentType.JSON)
                            .body(new CreateUserRequest(handle, email, password))
                            .post(userUrl)
                            .then());
        }

        public UserCreatorResponse create() {
            return create(DEFAULT_HANDLE, DEFAULT_EMAIL, DEFAULT_PASSWORD);
        }
    }

    public static class UserCreatorResponse {
        private final URL loginUrl;
        private final UserInfo userInfo;
        private final ValidatableResponse httpResponse;

        public UserCreatorResponse(URL loginUrl, UserInfo userInfo, ValidatableResponse httpResponse) {
            this.loginUrl = loginUrl;
            this.userInfo = userInfo;
            this.httpResponse = httpResponse;
        }

        public UserCreatorResponse successful() {
            this.httpResponse.statusCode(201);
            return this;
        }

        public AuthenticatedUserResponse authenticate() {
            return new AuthenticatedUserResponse(given().contentType(ContentType.JSON)
                    .body(new Credentials(userInfo.handle(), userInfo.password()))
                    .post(loginUrl)
                    .then());
        }
    }

    public static class AuthenticatedUserResponse {

        private final ValidatableResponse httpResponse;

        public AuthenticatedUserResponse(ValidatableResponse httpResponse) {
            this.httpResponse = httpResponse;
        }

        public AuthenticatedUserResponse successful() {
            this.httpResponse.statusCode(200);
            return this;
        }

        public String jwtToken() {
            return this.httpResponse.extract()
                    .asString();
        }

        public Header authorizationHeader() {
            return new Header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken());
        }

    }

    public static UserCreator givenUserCreator(URL userUrl, URL loginUrl) {
        return new UserCreator(userUrl, loginUrl);
    }

}
