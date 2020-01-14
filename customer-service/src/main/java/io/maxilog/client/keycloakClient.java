package io.maxilog.client;


import io.maxilog.domain.RoleRepresentation;
import io.maxilog.domain.UserRepresentation;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces({"application/json"})
@Consumes({"application/json"})
@RegisterRestClient(configKey = "keycloak")
@RegisterClientHeaders(OAuth2InterceptedConfiguration.class)
@SuppressWarnings("all")
public interface keycloakClient {

    @GET
    @Path("/users")
    List<UserRepresentation> getUsers();

    @GET
    @Path("/users")
    List<UserRepresentation> getUsersPageable(@QueryParam("first") int page, @QueryParam("max") int size);

    @GET
    @Path("/users/count")
    int countUsers();

    @GET
    @Path("/users")
    List<UserRepresentation> getUsersByEmail(@QueryParam("email") String email);

    @GET
    @Path("/users")
    List<UserRepresentation> getUsersByUsername(@QueryParam("username") String username);

    @GET
    @Path("/users/{id}")
    UserRepresentation getUserById(@PathParam("id") String id);

    @POST
    @Path("/users")
    Response createUser(UserRepresentation userRepresentation);

    @PUT
    @Path("/users/{id}")
    Response updateUser(@PathParam("id") String id, UserRepresentation userRepresentation);

    @DELETE
    @Path("/users/{id}")
    Response deleteUser(@PathParam("id") String id);

    @GET
    @Path("/roles")
    List<RoleRepresentation> getRoles();

    @GET
    @Path("/roles/{role-name}")
    RoleRepresentation getRoleByName(@PathParam("role-name") String roleName);

    @POST
    @Path("/roles")
    Response createRole(RoleRepresentation roleRepresentation);

    @DELETE
    @Path("/roles/{role-name}")
    Response deleteRole(@PathParam("role-name") String roleName);

    @GET
    @Path("/users/{id}/role-mappings/realm/available")
    List<RoleRepresentation> getRealmRolesByUser(@PathParam("id") String id);

    @POST
    @Path("/users/{id}/role-mappings/realm")
    Response createUserRole(@PathParam("id") String id, List<RoleRepresentation> roles);

    /*@PUT
    @Path("/users/{id}/reset-password")
    Response resetUserPassword(@PathParam("id") String id, CredentialRepresentation credentialRepresentation);*/


}
