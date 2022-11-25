package io.vepo.microblogging.user;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vepo.microblogging.post.Page;
import io.vepo.microblogging.post.Post;
import io.vepo.microblogging.post.Posts;

@Path("/user")
@ApplicationScoped
public class UserEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(UserEndpoint.class);

    @Inject
    Users users;

    @Inject
    Posts posts;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateUserRequest request) throws URISyntaxException {
        logger.info("Creating user: request={}", request);
        var user = users.create(new User(request.handle(), request.email(), request.password()));
        return Response.created(new URI(String.format("/post/%d", user.getId())))
                .entity(user)
                .build();
    }

    @GET
    @Path("{userId: [0-9]+}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile findProfile(Long userId) {
        return users.findUserById(userId)
                    .map(user -> new UserProfile(user.getId(), user.getHandle(), user.getCreatedAt()))
                    .orElseThrow(() -> new NotFoundException(String.format("User does not exists! userId=%d", userId)));
    }

    @GET
    @Path("{userHandle}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile findProfile(String userHandle) {
        return users.findUserByHandle(userHandle)
                    .map(user -> new UserProfile(user.getId(), user.getHandle(), user.getCreatedAt()))
                    .orElseThrow(() -> new NotFoundException(String.format("User does not exists! userHandle=%s", userHandle)));
    }

    @GET
    @PermitAll
    @Path("{userId: [0-9]+}/stream")
    @Produces(MediaType.APPLICATION_JSON)
    public Page<Post> postStreamByUserId(@PathParam("userId") Long userId,
                                         @DefaultValue("0") @QueryParam("page") int page,
                                         @DefaultValue("10") @QueryParam("pageSize") int pageSize) {
        logger.info("Retrieving Stream: userId={} page={} pageSize={}", userId, page, pageSize);
        return users.findUserById(userId)
                    .map(user -> new Page<>(page * pageSize, posts.listByAuthorId(user.getId(), page * pageSize, pageSize)))
                    .orElseThrow(() -> new NotFoundException(String.format("User does not exist! userId=%d", userId)));
    }

    @GET
    @PermitAll
    @Path("{userHandle}/stream")
    @Produces(MediaType.APPLICATION_JSON)
    public Page<Post> postStreamByUserHandle(@PathParam("userHandle") String userHandle,
                                             @DefaultValue("0") @QueryParam("page") int page,
                                             @DefaultValue("10") @QueryParam("pageSize") int pageSize) {
        logger.info("Retrieving Stream: page={} pageSize={}", page, pageSize);
        return users.findUserByHandle(userHandle)
                    .map(user -> new Page<>(page * pageSize, posts.listByAuthorId(user.getId(), page * pageSize, pageSize)))
                    .orElseThrow(() -> new NotFoundException(String.format("User does not exist! userHandle=%s", userHandle)));
    }


}
