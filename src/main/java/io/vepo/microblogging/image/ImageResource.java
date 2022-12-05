package io.vepo.microblogging.image;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/image")
@ApplicationScoped
public class ImageResource {
    @Inject
    Images images;

    @GET
    @Path("{imageId}")
    public Response serveImage(@PathParam("imageId") Long imageId) {
        return images.find(imageId)
                     .map(image -> Response.ok(image.getData())
                                           .header("Content-Type",
                                                   "image/" + image.getFormat().toString().toLowerCase()))
                     .orElseGet(() -> Response.status(Status.NOT_FOUND))
                     .build();
    }
}
