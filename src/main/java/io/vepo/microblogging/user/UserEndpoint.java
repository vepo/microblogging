package io.vepo.microblogging.user;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/user")
@ApplicationScoped
public class UserEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(UserEndpoint.class);

    @Inject
    Users users;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateUserRequest request) throws URISyntaxException {
        logger.info("Creating user: request={}", request);
        var user = users.create(new User(request.handle(), request.email(), request.password()));
        return Response.created(new URI(String.format("/post/%d", user.getId())))
                .entity(user)
                .build();
    }
}
