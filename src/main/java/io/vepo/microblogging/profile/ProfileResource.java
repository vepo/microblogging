package io.vepo.microblogging.profile;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.vepo.microblogging.user.Users;

@Path("/profile")
@ApplicationScoped
public class ProfileResource {

    @Inject
    Users users;

    @GET
    @Path("{handle}")
    public Profile findProfile(@PathParam("handle") String handle) {
        return users.findUserByHandle(handle)
                .map(user -> new Profile(user.getId(), user.getHandle(),
                        new ProfileImages(
                                user.getCover().map(cover -> new Image("/api/image/" + cover.getId())).orElse(null),
                                user.getAvatar().map(cover -> new Image("/api/image/" + cover.getId())).orElse(null)),
                        user.getCreatedAt()))
                .orElseThrow(() -> new NotFoundException(String.format("User do not exists! handle=%s", handle)));
    }
}
