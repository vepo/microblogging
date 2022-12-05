package io.vepo.microblogging.user;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/user")
@ApplicationScoped
public class UserResource {
    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Inject
    Users users;

    //@Inject
    //Posts posts;

    //@GET
    //@Path("{userId: [0-9]+}")
    //@PermitAll
    //@Produces(MediaType.APPLICATION_JSON)
    //public UserProfile findProfile(Long userId) {
    //    return users.findUserById(userId)
    //                .map(user -> new UserProfile(user.getId(), user.getHandle(), user.getCreatedAt()))
    //                .orElseThrow(() -> new NotFoundException(String.format("User does not exists! userId=%d", userId)));
    //}
//
    //@GET
    //@Path("{userHandle}")
    //@PermitAll
    //@Produces(MediaType.APPLICATION_JSON)
    //public UserProfile findProfile(String userHandle) {
    //    return users.findUserByHandle(userHandle)
    //                .map(user -> new UserProfile(user.getId(), user.getHandle(), user.getCreatedAt()))
    //                .orElseThrow(() -> new NotFoundException(String.format("User does not exists! userHandle=%s", userHandle)));
    //}
//
    //@GET
    //@PermitAll
    //@Path("{userId: [0-9]+}/stream")
    //@Produces(MediaType.APPLICATION_JSON)
    //public Page<Post> postStreamByUserId(@PathParam("userId") Long userId,
    //                                     @DefaultValue("0") @QueryParam("page") int page,
    //                                     @DefaultValue("10") @QueryParam("pageSize") int pageSize) {
    //    logger.info("Retrieving Stream: userId={} page={} pageSize={}", userId, page, pageSize);
    //    return users.findUserById(userId)
    //                .map(user -> new Page<>(page * pageSize, posts.listByAuthorId(user.getId(), page * pageSize, pageSize)))
    //                .orElseThrow(() -> new NotFoundException(String.format("User does not exist! userId=%d", userId)));
    //}
//
    //@GET
    //@PermitAll
    //@Path("{userHandle}/stream")
    //@Produces(MediaType.APPLICATION_JSON)
    //public Page<Post> postStreamByUserHandle(@PathParam("userHandle") String userHandle,
    //                                         @DefaultValue("0") @QueryParam("page") int page,
    //                                         @DefaultValue("10") @QueryParam("pageSize") int pageSize) {
    //    logger.info("Retrieving Stream: page={} pageSize={}", page, pageSize);
    //    return users.findUserByHandle(userHandle)
    //                .map(user -> new Page<>(page * pageSize, posts.listByAuthorId(user.getId(), page * pageSize, pageSize)))
    //                .orElseThrow(() -> new NotFoundException(String.format("User does not exist! userHandle=%s", userHandle)));
    //}
}
