package io.vepo.microblogging.post;

import static io.restassured.RestAssured.given;
import static io.vepo.microblogging.infra.UserHelper.givenUserCreator;
import static java.util.UUID.randomUUID;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.VerboseCondition.verboseCondition;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.vepo.microblogging.infra.TestContainerPostgreResource;
import io.vepo.microblogging.user.LoginEndpoint;
import io.vepo.microblogging.user.UserEndpoint;

@QuarkusTest
@DisplayName("Post Endpoint")
@QuarkusTestResource(value = TestContainerPostgreResource.class)
@TestMethodOrder(OrderAnnotation.class)
class PostEndpointTest {

        @TestHTTPEndpoint(PostEndpoint.class)
        @TestHTTPResource
        URL postUrl;

        @TestHTTPResource
        @TestHTTPEndpoint(UserEndpoint.class)
        URL userUrl;

        @TestHTTPResource
        @TestHTTPEndpoint(LoginEndpoint.class)
        URL loginUrl;

        @Test
        @DisplayName("Listing all posts")
        void testPostListTest() {
                var response = given().when()
                                .get(postUrl)
                                .thenReturn();
                assertEquals(200, response.statusCode());
                var body = response.jsonPath();
                assertThat(body.getList(".", Post.class)).isEmpty();
        }

        @Test
        @DisplayName("Listing Posts with pagination")
        void listingPostsTest() throws MalformedURLException {
                var authorizationHeader = givenUserCreator(userUrl, loginUrl)
                                .create()
                                .successful()
                                .authenticate()
                                .successful()
                                .authorizationHeader();
                range(0, 40)
                                .forEachOrdered(index -> {
                                        given().contentType(ContentType.JSON)
                                                        .header(authorizationHeader)
                                                        .body(new CreatePostRequest("POST-" + index))
                                                        .when()
                                                        .post(postUrl)
                                                        .then()
                                                        .statusCode(201)
                                                        .header("Location", matchesPattern(".*/post/[0-9]+"));
                                });
                var firstPage = given().queryParam("page", 0)
                                .queryParam("pageSize", 15)
                                .get(postUrl.toExternalForm() + "/stream")
                                .thenReturn();
                assertThat(firstPage).has(verboseCondition(response -> response.statusCode() == 200,
                                "Status code should be 200", r -> String.format(", but was %d", r.statusCode())))
                                .extracting(resp -> resp.body().as(new TypeRef<Page<Post>>() {
                                }))
                                .has(verboseCondition(page -> page.offset() == 0, "First page should have offset zero",
                                                p -> String.format(", but hsa offset=%s", p.offset())))
                                .has(verboseCondition(page -> page.items().size() == 15, "Items should have lenght 15",
                                                p -> String.format(", but has %d", p.items().size())));
                var fPage = firstPage.body().as(new TypeRef<Page<Post>>() {
                });
                assertThat(fPage.items()).map(Post::getContent)
                                .isEqualTo(range(0, 15)
                                                .mapToObj(index -> "POST-" + index).toList());
                var secondPage = given().queryParam("page", 1)
                                .queryParam("pageSize", 15)
                                .get(postUrl.toExternalForm() + "/stream")
                                .thenReturn();
                assertThat(secondPage).has(verboseCondition(response -> response.statusCode() == 200,
                                "Status code should be 200", r -> String.format(", but was %d", r.statusCode())))
                                .extracting(resp -> resp.body().as(new TypeRef<Page<Post>>() {
                                }))
                                .has(verboseCondition(p -> p.offset() == 15, "Second page should have offset zero",
                                                p -> String.format(", but has offset=%s", p.offset())))
                                .has(verboseCondition(p -> p.items().size() == 15, "Items should have lenght 15",
                                                p -> String.format(", but has %d", p.items().size())));

                var sPage = secondPage.body().as(new TypeRef<Page<Post>>() {
                });
                assertThat(sPage.items()).map(Post::getContent)
                                .isEqualTo(range(15, 30)
                                                .mapToObj(index -> "POST-" + index).toList());

                var thirdPage = given().queryParam("page", 2)
                                .queryParam("pageSize", 15)
                                .get(postUrl.toExternalForm() + "/stream")
                                .thenReturn();
                assertThat(thirdPage).has(verboseCondition(response -> response.statusCode() == 200,
                                "Status code should be 200", r -> String.format(", but was %d", r.statusCode())))
                                .extracting(resp -> resp.body().as(new TypeRef<Page<Post>>() {
                                }))
                                .has(verboseCondition(p -> p.offset() == 30, "Third page should have offset zero",
                                                p -> String.format(", but has offset=%s", p.offset())))
                                .has(verboseCondition(p -> p.items().size() == 10, "Items should have lenght 15",
                                                p -> String.format(", but has %d", p.items().size())));

                var tPage = thirdPage.body().as(new TypeRef<Page<Post>>() {
                });
                assertThat(tPage.items()).map(Post::getContent)
                                .isEqualTo(range(30, 40)
                                                .mapToObj(index -> "POST-" + index).toList());

                var lastPage = given().queryParam("page", 3)
                                .queryParam("pageSize", 15)
                                .get(postUrl.toExternalForm() + "/stream")
                                .thenReturn();
                assertThat(lastPage).has(verboseCondition(response -> response.statusCode() == 200,
                                "Status code should be 200", r -> String.format(", but was %d", r.statusCode())))
                                .extracting(resp -> resp.body().as(new TypeRef<Page<Post>>() {
                                }))
                                .has(verboseCondition(p -> p.offset() == 45, "Last page should have offset zero",
                                                p -> String.format(", but has offset=%s", p.offset())))
                                .has(verboseCondition(p -> p.items().size() == 0, "Items should have lenght 0",
                                                p -> String.format(", but has %d", p.items().size())));
        }

        @Test
        @DisplayName("Create Post")
        void createPostTest() {
                var authorizationHeader = givenUserCreator(userUrl, loginUrl)
                                .create()
                                .successful()
                                .authenticate()
                                .successful()
                                .authorizationHeader();
                var uuid = randomUUID().toString();
                given().contentType(ContentType.JSON)
                                .header(authorizationHeader)
                                .body(new CreatePostRequest("Test" + uuid))
                                .when()
                                .post(postUrl)
                                .then()
                                .statusCode(201)
                                .header("Location", matchesPattern(".*/post/[0-9]+"));
                var response = given().when()
                                .get(postUrl)
                                .thenReturn();
                assertEquals(200, response.statusCode());
                var body = response.jsonPath()
                                .getList(".", Post.class);
                assertThat(body).hasSize(1)
                                .anyMatch(p -> p.getContent().equals("Test" + uuid))
                                .allMatch(p -> p.getId() > 0l);
        }

        @Test
        @DisplayName("Delete Post")
        void deletePostTest() {
                var authorizationHeader = givenUserCreator(userUrl, loginUrl)
                                .create()
                                .successful()
                                .authenticate()
                                .successful()
                                .authorizationHeader();
                var uuid = randomUUID().toString();
                var post = given().contentType(ContentType.JSON)
                                .header(authorizationHeader)
                                .body(new CreatePostRequest("Test" + uuid))
                                .when()
                                .post(postUrl)
                                .then()
                                .statusCode(201)
                                .header("Location", matchesPattern(".*/post/[0-9]+")).extract().as(Post.class);
                given().when()
                                .delete(postUrl.toExternalForm() + "/{postId}", Map.of("postId", post.getId()))
                                .then().statusCode(200);
                var response = given().when()
                                .get(postUrl)
                                .thenReturn();
                var body = response.jsonPath()
                                .getList(".", Post.class);
                assertThat(body).isEmpty();

        }

        @Test
        @DisplayName("Access Post")
        void accessPostTest() {
                var authorizationHeader = givenUserCreator(userUrl, loginUrl)
                                .create()
                                .successful()
                                .authenticate()
                                .successful()
                                .authorizationHeader();
                var uuid = randomUUID().toString();
                var post = given().contentType(ContentType.JSON)
                                .header(authorizationHeader)
                                .body(new CreatePostRequest("Test" + uuid))
                                .when()
                                .post(postUrl)
                                .then()
                                .statusCode(201)
                                .header("Location", matchesPattern(".*/post/[0-9]+"))
                                .extract()
                                .as(Post.class);
                given().when().get(postUrl.toExternalForm() + "/{postId}", Map.of("postId", post.getId()))
                                .then()
                                .statusCode(200)
                                .body("content", Matchers.is("Test" + uuid));

        }
}
