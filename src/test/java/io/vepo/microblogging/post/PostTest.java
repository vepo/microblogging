package io.vepo.microblogging.post;

import static io.vepo.microblogging.infra.DataExtractor.postPage;
import static io.vepo.microblogging.post.PostActions.postCleaner;
import static io.vepo.microblogging.post.PostActions.postCreator;
import static io.vepo.microblogging.post.PostActions.postLister;
import static io.vepo.microblogging.post.PostActions.postViewer;
import static io.vepo.microblogging.user.UserActions.userCreator;
import static io.vepo.microblogging.validators.Validators.lenght;
import static io.vepo.microblogging.validators.Validators.offset;
import static io.vepo.microblogging.validators.Validators.statusCode;
import static java.util.UUID.randomUUID;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.IntFunction;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.vepo.microblogging.infra.TestContainerPostgreResource;
import io.vepo.microblogging.post.PostActions.PostCreated;
import io.vepo.microblogging.user.AuthenticationResource;
import io.vepo.microblogging.user.UserEndpoint;

@QuarkusTest
@DisplayName("Post")
@QuarkusTestResource(value = TestContainerPostgreResource.class)
@TestMethodOrder(OrderAnnotation.class)
class PostTest {

        @TestHTTPEndpoint(PostEndpoint.class)
        @TestHTTPResource
        URL postUrl;

        @TestHTTPResource
        @TestHTTPEndpoint(UserEndpoint.class)
        URL userUrl;

        @TestHTTPResource
        @TestHTTPEndpoint(AuthenticationResource.class)
        URL authUrl;

        @Test
        @DisplayName("It should allow list all posts")
        void listAllPostsTests() {
                var response = postLister(postUrl).list()
                                                  .response();
                assertEquals(200, response.statusCode(), "It allow listing");
                assertArrayEquals(array(), response.as(Post[].class), "It should return an empty list if there is no post");
        }

        @Test
        @DisplayName("It should allow create a post for logged User")
        void createPostTest() throws MalformedURLException {
                var authorizationHeader = userCreator(authUrl).create()
                                                              .successful()
                                                              .authenticate()
                                                              .successful()
                                                              .authorizationHeader();
                var uuid = randomUUID().toString();
                var response = postCreator(postUrl, authorizationHeader).create(new CreatePostRequest("POST-" + uuid))
                                                                        .response();
                assertEquals(201, response.statusCode(), "It should return 201 on success!");
                Post createdPost = response.as(Post.class);
                assertEquals(new URL(postUrl.toExternalForm() + "/" + createdPost.getId()).toExternalForm(), response.header("Location"), "The location header should match the created id!");
                var listingResponse = postLister(postUrl).list()
                                                         .response();
                assertArrayEquals(array(createdPost), listingResponse.as(Post[].class), "It should contain one post.");
        }

        @Test
        @DisplayName("It should allow paginated listing")
        void paginatedListPostTest() throws MalformedURLException {
                IntFunction<String> postNaming = index -> "POST-" + index;
                var authorizationHeader = userCreator(authUrl).create()
                                                              .successful()
                                                              .authenticate()
                                                              .successful()
                                                              .authorizationHeader();
                postCreator(postUrl, authorizationHeader).create(40, index-> new CreatePostRequest(postNaming.apply(index)))
                                                         .map(PostCreated::response)
                                                         .allMatch(resp -> resp.statusCode() == 201);
                var postLister = postLister(postUrl);
                // validate 1st page
                var firstPageResponse = postLister.list(0, 15)
                                                  .response();
                assertThat(firstPageResponse).has(statusCode(200))
                                             .extracting(resp -> resp.body().as(postPage()))
                                             .has(offset(0))
                                             .has(lenght(15));
                var firstPage = firstPageResponse.body().as(postPage());
                assertThat(firstPage.items()).map(Post::getContent)
                                             .isEqualTo(range(0, 15).mapToObj(postNaming)
                                                                                                 .toList());
                // validate 2nd page
                var secondPageResponse = postLister.list(1, 15)
                                                   .response();
                assertThat(secondPageResponse).has(statusCode(200))
                                              .extracting(resp -> resp.body().as(postPage()))
                                              .has(offset(15))
                                              .has(lenght(15));

                var secondPage = secondPageResponse.body().as(postPage());
                assertThat(secondPage.items()).map(Post::getContent)
                                              .isEqualTo(range(15, 30).mapToObj(postNaming)
                                                                                                   .toList());
                // validate 3rd page
                var thirdPageResponse = postLister.list(2, 15)
                                                   .response();
                assertThat(thirdPageResponse).has(statusCode(200))
                                             .extracting(resp -> resp.body().as(postPage()))
                                             .has(offset(30))
                                             .has(lenght(10));

                var thirdPage = thirdPageResponse.body().as(postPage());
                assertThat(thirdPage.items()).map(Post::getContent)
                                             .isEqualTo(range(30, 40).mapToObj(postNaming)
                                                                                                  .toList());
                // validate 4rd page
                var fourthPageResponse = postLister.list(3, 15)
                                                   .response();
                assertThat(fourthPageResponse).has(statusCode(200))
                                              .extracting(resp -> resp.body().as(postPage()))
                                              .has(offset(45))
                                              .has(lenght(0));
        }

        @Test
        @DisplayName("Delete Post")
        void deletePostTest() {
                var authorizationHeader = userCreator(authUrl).create()
                                                              .successful()
                                                              .authenticate()
                                                              .successful()
                                                              .authorizationHeader();
                var uuid = randomUUID().toString();
                var createResponse = postCreator(postUrl, authorizationHeader).create(new CreatePostRequest("POST-" + uuid))
                                                                              .response();
                var deleteResponse = postCleaner(postUrl).delete(createResponse.as(Post.class).getId());

                assertEquals(Status.OK.getStatusCode(), deleteResponse.response().statusCode(), "It should return OK for delete.");
                var response = postLister(postUrl).list()
                                                  .response();
                assertArrayEquals(array(), response.as(Post[].class), "It should return an empty list as there is no post");

        }

        @Test
        @DisplayName("Access Post")
        void accessPostTest() {
                var authorizationHeader = userCreator(authUrl).create()
                                                              .successful()
                                                              .authenticate()
                                                              .successful()
                                                              .authorizationHeader();
                var uuid = randomUUID().toString();
                var createResponse = postCreator(postUrl, authorizationHeader).create(new CreatePostRequest("POST-" + uuid))
                                                                              .response();
                var viewResponse = postViewer(postUrl).view(createResponse.as(Post.class).getId())
                                                      .response();
                assertEquals(Status.OK.getStatusCode(), viewResponse.statusCode(), "It should return OK.");
                assertEquals("POST-" + uuid, viewResponse.body().as(Post.class).getContent(), "It should contain the same content.");
        }
}
