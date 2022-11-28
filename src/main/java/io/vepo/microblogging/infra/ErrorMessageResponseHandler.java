package io.vepo.microblogging.infra;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ErrorMessageResponseHandler implements ExceptionMapper<WebApplicationException> {
    private static Logger logger = LoggerFactory.getLogger(ErrorMessageResponseHandler.class);

    @Override
    public Response toResponse(WebApplicationException exception) {
        logger.info("Exception throwed! Creating request...", exception);
        return Response.status(exception.getResponse().getStatus())
                .entity(new ErrorInformation(exception.getResponse().getStatusInfo().toString()))
                .build();
    }

}
