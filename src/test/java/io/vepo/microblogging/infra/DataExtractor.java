package io.vepo.microblogging.infra;

import io.restassured.common.mapper.TypeRef;
import io.vepo.microblogging.post.Page;
import io.vepo.microblogging.post.Post;

public class DataExtractor {

    public static TypeRef<Page<Post>> postPage() {
        return new TypeRef<Page<Post>>() {
        };
    }
}
