package io.vepo.microblogging.post;

import static io.restassured.RestAssured.given;

import java.net.URL;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class PostActions {
    public static class PostLister {
        private final URL postUrl;

        public PostLister(URL postUrl) {
            this.postUrl = postUrl;
        }

        public PostListing list() {
            return new PostListing(postUrl, given().when()
                    .get(postUrl)
                    .thenReturn());
        }

        public PagedPostListing list(int page, int pageSize) {
            return new PagedPostListing(postUrl, given().queryParam("page", page)
                                                        .queryParam("pageSize", pageSize)
                                                        .get(postUrl.toExternalForm() + "/stream")
                                                        .thenReturn());
        }

    }

    public static class PostListing {
        private final URL postUrl;
        private Response response;

        public PostListing(URL postUrl, Response response) {
            this.postUrl = postUrl;
            this.response = response;
        }

        public Response response() {
            return this.response;
        }

    }

    public static class PagedPostListing {
        private final URL postUrl;
        private Response response;

        public PagedPostListing(URL postUrl, Response response) {
            this.postUrl = postUrl;
            this.response = response;
        }

        public Response response() {
            return this.response;
        }

    }

    public static class PostCreated {
        private final URL postUrl;
        private final Header authorizationHeader;
        private final Response response;

        public PostCreated(URL postUrl, Header authorizationHeader, Response response) {
            this.postUrl = postUrl;
            this.authorizationHeader = authorizationHeader;
            this.response = response;
        }

        public Response response() {
            return response;
        }
    }

    public static class PostCreator {
        private final URL postUrl;
        private final Header authorizationHeader;

        public PostCreator(URL postUrl, Header authorizationHeader) {
            this.postUrl = postUrl;
            this.authorizationHeader = authorizationHeader;
        }

        public PostCreated create(CreatePostRequest request) {
            return new PostCreated(postUrl, authorizationHeader, given().contentType(ContentType.JSON)
                                                                        .header(authorizationHeader)
                                                                        .body(request)
                                                                        .when()
                                                                        .post(postUrl)
                                                                        .thenReturn());
        }

        public Stream<PostCreated> create(int postCount, IntFunction<CreatePostRequest> requestSupplier) {
            return IntStream.range(0, postCount)
                            .mapToObj(requestSupplier)
                            .map(this::create);

        }

    }

    public static class PostCleaner {

        private final URL postUrl;

        public PostCleaner(URL postUrl) {
            this.postUrl = postUrl;
        }

        public PostDeleted delete(Long postId) {
            return new PostDeleted(given().when()
                                          .delete(postUrl.toExternalForm() + "/{postId}", Map.of("postId", postId))
                                          .thenReturn());
        }
    }

    public static class PostDeleted {

        private final Response response;

        public PostDeleted(Response response) {
            this.response = response;
        }

        public Response response() {
            return response;
        }

    }

    public static class PostViewer {

        private final URL postUrl;

        public PostViewer(URL postUrl) {
            this.postUrl = postUrl;
        }

        public PostViewed view(Long postId) {
            return new PostViewed(given().when()
                                         .get(postUrl.toExternalForm() + "/{postId}", Map.of("postId", postId))
                                         .thenReturn());
        }

    }

    public static class PostViewed {

        private final Response response;

        public PostViewed(Response response) {
            this.response = response;
        }

        public Response response() {
            return response;
        }
    }

    public static PostLister postLister(URL postUrl) {
        return new PostLister(postUrl);
    }

    public static PostCreator postCreator(URL postUrl, Header authorizationHeader) {
        return new PostCreator(postUrl, authorizationHeader);
    }

    public static PostCleaner postCleaner(URL postUrl) {
        return new PostCleaner(postUrl);
    }

    public static PostViewer postViewer(URL postUrl) {
        return new PostViewer(postUrl);
    }

}
