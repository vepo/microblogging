package io.vepo.microblogging.infra;

import java.util.Scanner;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExeptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        String text = new Scanner(this.getClass().getResourceAsStream("/META-INF/resources/index.html"), "UTF-8")
                .useDelimiter("\\A").next();
        return Response.status(404).entity(text).build();
    }
}