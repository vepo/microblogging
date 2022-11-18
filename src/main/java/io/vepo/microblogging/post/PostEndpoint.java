package io.vepo.microblogging.post;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/post")
@ApplicationScoped
public class PostEndpoint {

    @Inject
    Posts posts;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> allPosts() {
        return posts.list();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPost(CreatePostRequest newPost) throws URISyntaxException {
        var post = posts.createPost(new Post(newPost.content()));
        return Response.created(new URI(String.format("/post/%d", post.getId()))).entity(post).build();
    }
}
