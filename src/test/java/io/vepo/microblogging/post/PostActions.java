package io.vepo.microblogging.post;

import static io.restassured.RestAssured.given;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class PostActions {
    private static final Logger logger = LoggerFactory.getLogger(PostActions.class);
    
    public static class PostLister {
        private final URL postUrl;

        public PostLister(URL postUrl) {
            this.postUrl = postUrl;
        }

        public PostListing list() {
            return new PostListing(given().when()
                                          .get(postUrl)
                                          .thenReturn());
        }

        public PagedPostListing list(int page, int pageSize) {
            return new PagedPostListing(given().queryParam("page", page)
                                               .queryParam("pageSize", pageSize)
                                               .get(postUrl.toExternalForm() + "/stream")
                                               .thenReturn());
        }

    }

    public static class PostListerByUser {
        private final URL userUrl;
        private final UserId userId;
        private final UserHandle userHandle;

        public PostListerByUser(URL userUrl, UserId userId, UserHandle userHandle) {
            this.userUrl = userUrl;
            this.userId = userId;
            this.userHandle = userHandle;
        }

        public PagedPostListing list(int page, int pageSize) {
            logger.info("Retrieving stream for user... url={}", retrieveUrl());
            return new PagedPostListing(given().queryParam("page", page)
                                               .queryParam("pageSize", pageSize)
                                               .get(retrieveUrl())
                                               .thenReturn());
        }

        private String retrieveUrl() {
            if (Objects.nonNull(userId)) {
                return userUrl.toExternalForm() + "/" + userId.id() + "/stream";
            } else {
                return userUrl.toExternalForm() + "/" + userHandle.userHandle() + "/stream";
            }
        }

    }

    public static class PostListing {
        private Response response;

        public PostListing(Response response) {
            this.response = response;
        }

        public Response response() {
            return this.response;
        }

    }

    public static class PagedPostListing {
        private Response response;

        public PagedPostListing(Response response) {
            this.response = response;
        }

        public Response response() {
            return this.response;
        }

    }

    public static class PostCreated {
        private final Response response;

        public PostCreated(Response response) {
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
            return new PostCreated(given().contentType(ContentType.JSON)
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

    public static record UserId(Long id) {}

    public static record UserHandle(String userHandle) {}

    public static PostLister postLister(URL postUrl) {
        return new PostLister(postUrl);
    }

    public static PostListerByUser postLister(URL userUrl, UserId userId) {
        return new PostListerByUser(userUrl, userId, null);
    }

    public static PostListerByUser postLister(URL userUrl, UserHandle userHandle) {
        return new PostListerByUser(userUrl, null, userHandle);
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

    public static UserId byUserId(Long id) {
        return new UserId(id);
    }

    public static UserHandle byUserHandle(String handle) {
        return new UserHandle(handle);
    }
}
