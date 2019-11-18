package io.maxilog.web.rest.errors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ForbiddenException extends WebApplicationException {

     public ForbiddenException(String message) {
         super(Response.status(Response.Status.FORBIDDEN)
             .entity(new ErrorVM(message)).type(MediaType.APPLICATION_JSON_TYPE).build());
     }
}