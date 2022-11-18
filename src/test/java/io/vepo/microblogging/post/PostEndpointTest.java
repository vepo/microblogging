package io.vepo.microblogging.post;

import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class PostEndpointTest {

    @Test
    @Order(1)
    @DisplayName("Listing all Posts")
    public void testPostList() {
        var response = given().when()
                .get("/post")
                .thenReturn();
        assertEquals(response.statusCode(), 200);
        var body = response.jsonPath();
        assertEquals(body.getList(".", Post.class), emptyList());
    }

    @Test
    @Order(2)
    @DisplayName("Create Post")
    public void createPost() {
        var uuid = UUID.randomUUID().toString();
        given().contentType(ContentType.JSON)
                .body(new CreatePostRequest("Test" + uuid))
                .when()
                .post("/post")
                .then()
                .statusCode(201)
                .header("Location", matchesPattern(".*/post/[0-9]+"));
        var response = given().when()
                .get("/post")
                .thenReturn();
        assertEquals(response.statusCode(), 200);
        var body = response.jsonPath()
                .getList(".", Post.class);
        assertEquals(1, body.size());
        assertEquals("Test" + uuid, body.get(0).getContent());
        assertTrue(body.get(0).getId() > 0);
    }

    @Test
    @Order(3)
    @DisplayName("Delete Post")
    public void deletePost() {
        var uuid = UUID.randomUUID().toString();
        var post = given().contentType(ContentType.JSON)
                .body(new CreatePostRequest("Test" + uuid))
                .when()
                .post("/post")
                .then()
                .statusCode(201)
                .header("Location", matchesPattern(".*/post/[0-9]+")).extract().as(Post.class);
        given().when()
                .delete("/post/" + post.getId())
                .then().statusCode(200);
        var response = given().when()
                .get("/post")
                .thenReturn();

    }

}
