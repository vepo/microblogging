package io.vepo.microblogging.auth;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.security.PermitAll;
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

import io.vepo.microblogging.infra.ErrorInformation;
import io.vepo.microblogging.infra.JwtUtils;
import io.vepo.microblogging.user.CreateUserRequest;
import io.vepo.microblogging.user.Credentials;
import io.vepo.microblogging.user.User;
import io.vepo.microblogging.user.Users;

@Path("/auth")
@ApplicationScoped
public class AuthenticationResource {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationResource.class);

    @Inject
    Users users;

    @Inject
    JwtUtils jwtUtils;

    @POST
    @Path("login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {
        return users.findByHandleAndPassword(credentials.handle(), credentials.password())
                .map(user -> Response.ok(
                        new LoginResponse(user.getId(), user.getHandle(), user.getEmail(), jwtUtils.generate(user))))
                .orElseGet(() -> Response.status(401).entity(new ErrorInformation("Credentials are not valid!")))
                .build();
    }

    @POST
    @Path("register")
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
}
