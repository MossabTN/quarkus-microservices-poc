package io.maxilog.client;


import io.maxilog.service.dto.CustomerDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;

@Path("/users/")
@Produces({"application/json"})
@Consumes({"application/json"})
@RegisterRestClient(configKey = "customer")
@RegisterClientHeaders(OAuth2InterceptedConfiguration.class)
@SuppressWarnings("all")
public interface CustomerClient {


    @GET
    @Path("/username/{username}")
    CustomerDTO getUserByUsername(@PathParam("username") String username);

}
