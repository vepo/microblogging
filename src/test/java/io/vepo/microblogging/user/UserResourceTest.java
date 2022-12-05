package io.vepo.microblogging.user;

import static io.vepo.microblogging.infra.DataExtractor.postPage;
import static io.vepo.microblogging.post.PostActions.byUserHandle;
import static io.vepo.microblogging.post.PostActions.byUserId;
import static io.vepo.microblogging.post.PostActions.postCreator;
import static io.vepo.microblogging.post.PostActions.postLister;
import static io.vepo.microblogging.user.UserActions.userCreator;
import static io.vepo.microblogging.user.UserActions.userProfileViewer;
import static io.vepo.microblogging.validators.Validators.lenght;
import static io.vepo.microblogging.validators.Validators.offset;
import static io.vepo.microblogging.validators.Validators.statusCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.vepo.microblogging.auth.AuthenticationResource;
import io.vepo.microblogging.infra.TestContainerPostgreResource;
import io.vepo.microblogging.post.CreatePostRequest;
import io.vepo.microblogging.post.PostEndpoint;
import io.vepo.microblogging.user.UserActions.UserCreated;
import io.vepo.microblogging.user.UserActions.UserProfileViewer;

@Disabled
@QuarkusTest
@DisplayName("User Creation")
@QuarkusTestResource(value = TestContainerPostgreResource.class)
class UserResourceTest {

        private static final Logger logger = LoggerFactory.getLogger(UserResourceTest.class);

        @TestHTTPResource
        @TestHTTPEndpoint(UserResource.class)
        URL userUrl;

        @TestHTTPResource
        @TestHTTPEndpoint(AuthenticationResource.class)
        URL authUrl;       

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
