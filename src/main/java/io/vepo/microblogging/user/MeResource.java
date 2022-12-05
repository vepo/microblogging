package io.vepo.microblogging.user;

import java.util.Base64;
import java.util.regex.Pattern;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vepo.microblogging.image.Image;
import io.vepo.microblogging.image.ImageFormat;
import io.vepo.microblogging.image.ImageType;

@Path("/user")
@RequestScoped
public class MeResource {
    private static final Logger logger = LoggerFactory.getLogger(MeResource.class);

    @Inject
    Users users;

    @Inject
    @Claim(standard = Claims.sub)
    String subject;

    private static final String IMAGE_REGEX = "^data:image/([a-z]+);base64,(.*)$";
    private static final Pattern IMAGE_PATTERN = Pattern.compile(IMAGE_REGEX);

    @POST
    @Path("avatar")
    @RolesAllowed({
        "USER" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAvatar(UpdateImageRequest request) {
        // Starting with "data:image/jpeg;base64,"
        var matcher = IMAGE_PATTERN.matcher(request.data());
        if (!matcher.matches()) {
            throw new BadRequestException(String.format("Invalid data content! It should match \"%s\"", IMAGE_REGEX));
        }

        logger.info("Update avatar! request={}", request);

        return users.updateAvatar(Long.parseLong(subject),
                                  new Image(ImageType.AVATAR, ImageFormat.parse(matcher.group(1)),
                                            Base64.getDecoder().decode(matcher.group(2))))
                    .map(user -> Response.ok(user.getAvatar().get()))
                    .orElseGet(() -> Response.notModified())
                    .build();
    }

}
