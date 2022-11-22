package io.vepo.microblogging.post;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/post")
@ApplicationScoped
public class PostEndpoint {

    @Inject
    Posts posts;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> allPosts() {
        return posts.list();
    }

    @GET
    @Path("stream")
    @Produces(MediaType.APPLICATION_JSON)
    public Page<Post> stream(@DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("pageSize") int pageSize) {
        return new Page<>(page * pageSize, posts.list(page * pageSize, pageSize));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPost(CreatePostRequest newPost) throws URISyntaxException {
        var post = posts.createPost(new Post(newPost.content()));
        return Response.created(new URI(String.format("/post/%d", post.getId()))).entity(post).build();
    }

    @DELETE
    @Path("{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post deletePost(@PathParam("postId") Long postId) {
        return posts.delete(postId);
    }

    @GET
    @Path("{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPost(@PathParam("postId") Long postId) {
        return posts.find(postId);
    }
}
