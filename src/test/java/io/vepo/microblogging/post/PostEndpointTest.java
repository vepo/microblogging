package io.vepo.microblogging.post;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class PostEndpointTest {

    @Test
    @Order(1)
    @DisplayName("Listing no Posts")
    public void testPostList() {
        var response = given().when()
                .get("/post")
                .thenReturn();
        assertEquals(response.statusCode(), 200);
        var body = response.jsonPath();
        assertEquals(body.getList(".", Post.class), Collections.emptyList());
    }

    @Test
    @Order(2)
    @DisplayName("Listing no Posts")
    public void createPost() {
        given().contentType(ContentType.JSON).body(new CreatePostRequest("Test")).when().post("/post").then()
                .statusCode(201).header("Location", Matchers.matchesPattern(".*/post/[0-9]+"));
        var response = given().when()
                .get("/post")
                .thenReturn();
        assertEquals(response.statusCode(), 200);
        var body = response.jsonPath().getList(".", Post.class);
        assertEquals(1, body.size());
        assertEquals("Test", body.get(0).getContent());
    }

}
