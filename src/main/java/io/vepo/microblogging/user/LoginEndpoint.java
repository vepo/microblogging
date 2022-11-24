package io.vepo.microblogging.user;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.vepo.microblogging.infra.JwtUtils;

@Path("/login")
@ApplicationScoped
public class LoginEndpoint {

    @Inject
    Users users;

    @Inject
    JwtUtils jwtUtils;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(Credentials credentials) {
        return users.findByHandleAndPassword(credentials.handle(), credentials.password())
                .map(user -> Response.ok(jwtUtils.generate(user)))
                .orElseGet(() -> Response.status(403)).build();
    }
}
