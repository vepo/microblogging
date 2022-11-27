package io.vepo.microblogging.user;

import static io.vepo.microblogging.infra.DataExtractor.postPage;
import static io.vepo.microblogging.post.PostActions.byUserId;
import static io.vepo.microblogging.post.PostActions.byUserHandle;
import static io.vepo.microblogging.post.PostActions.postCreator;
import static io.vepo.microblogging.post.PostActions.postLister;
import static io.vepo.microblogging.user.UserActions.userCreator;
import static io.vepo.microblogging.user.UserActions.userProfileViewer;
import static io.vepo.microblogging.validators.Validators.lenght;
import static io.vepo.microblogging.validators.Validators.offset;
import static io.vepo.microblogging.validators.Validators.statusCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.time.LocalDateTime;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.vepo.microblogging.infra.TestContainerPostgreResource;
import io.vepo.microblogging.post.CreatePostRequest;
import io.vepo.microblogging.post.PostEndpoint;
import io.vepo.microblogging.user.UserActions.UserCreated;
import io.vepo.microblogging.user.UserActions.UserProfileViewer;

@QuarkusTest
@DisplayName("User Creation")
@QuarkusTestResource(value = TestContainerPostgreResource.class)
class UserTest {

        private static final Logger logger = LoggerFactory.getLogger(UserTest.class);

        @TestHTTPResource
        @TestHTTPEndpoint(UserEndpoint.class)
        URL userUrl;

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
                User createdUser = createUserResponse.as(User.class);
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

        @TestHTTPResource
        @TestHTTPEndpoint(PostEndpoint.class)
        URL postUrl;

        @Test
        @DisplayName("It should allow view user profile")
        void userProfileTest() {
                UserCreated createUser = userCreator(authUrl).create("user-1", "user-1@microblogging.com", "123456");
                var createUserResponse = createUser.response();
                UserProfileViewer userProfileViewer = userProfileViewer(userUrl);
                User user = createUserResponse.as(User.class);
                logger.info("User created! user={}", user);
                var viewByIdResponse = userProfileViewer.view(user.getId())
                                                        .response();
                assertEquals(Status.OK.getStatusCode(), viewByIdResponse.statusCode(), "It should return OK");
                assertEquals("user-1", viewByIdResponse.as(UserProfile.class).handle(), "It should have the correct handle!");
                var viewByHandleResponse = userProfileViewer.view(user.getHandle())
                                                            .response();
                assertEquals(Status.OK.getStatusCode(), viewByHandleResponse.statusCode(), "It should return OK");
                assertEquals("user-1", viewByHandleResponse.as(UserProfile.class).handle(), "It should have the correct handle!");

                postCreator(postUrl, createUser.authenticate().authorizationHeader()).create(50, index -> new CreatePostRequest("POST-" + index))
                                                                                     .forEach(post -> assertEquals(Status.CREATED.getStatusCode(), post.response().statusCode(),"It should return OK!"));
                var streamByUserId = postLister(userUrl, byUserId(user.getId())).list(0, 15).response();
                assertThat(streamByUserId).has(statusCode(200))
                                          .extracting(resp -> resp.body().as(postPage()))
                                          .has(offset(0))
                                          .has(lenght(15));
                var streamByUserHandle = postLister(userUrl, byUserHandle(user.getHandle())).list(1, 15).response();
                assertThat(streamByUserHandle).has(statusCode(200))
                                              .extracting(resp -> resp.body().as(postPage()))
                                              .has(offset(15))
                                              .has(lenght(15));
        }
}
