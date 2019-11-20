package io.maxilog.client;


import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@Produces({"application/json"})
@Consumes({"application/json"})
@RegisterRestClient(configKey = "users")
@RegisterClientHeaders(OAuth2InterceptedConfiguration.class)
@SuppressWarnings("all")
public interface UserClient {



}
