package io.maxilog.client;

import io.maxilog.domain.RoleRepresentation;
import io.maxilog.domain.UserRepresentation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Alternative()
@Priority(1)
@ApplicationScoped
@RestClient
public class MockKeycloakClient implements keycloakClient {

    @Override
    public List<UserRepresentation> getUsers() {
        return Collections.singletonList(new UserRepresentation("AAAAAAAAAA","FN","LN","AAAAAAAAAA"));
    }

    @Override
    public List<UserRepresentation> getUsersPageable(int page, int size) {
        return Collections.singletonList(new UserRepresentation("AAAAAAAAAA","FN","LN","AAAAAAAAAA"));
    }

    @Override
    public int countUsers() {
        return 1;
    }

    @Override
    public List<UserRepresentation> getUsersByEmail(String email) {
        return Collections.singletonList(new UserRepresentation("AAAAAAAAAA","FN","LN",email));

    }

    @Override
    public List<UserRepresentation> getUsersByUsername(String username) {
        return Collections.singletonList(new UserRepresentation(username,"FN","LN","AAAAAAAAAA"));

    }

    @Override
    public UserRepresentation getUserById(String id) {
        return new UserRepresentation("AAAAAAAAAA","FN","LN","AAAAAAAAAA");
    }

    @Override
    public Response createUser(UserRepresentation userRepresentation) {
        return null;
    }

    @Override
    public Response updateUser(String id, UserRepresentation userRepresentation) {
        return null;
    }

    @Override
    public Response deleteUser(String id) {
        return null;
    }

    @Override
    public List<RoleRepresentation> getRoles() {
        return null;
    }

    @Override
    public RoleRepresentation getRoleByName(String roleName) {
        return null;
    }

    @Override
    public Response createRole(RoleRepresentation roleRepresentation) {
        return null;
    }

    @Override
    public Response deleteRole(String roleName) {
        return null;
    }

    @Override
    public List<RoleRepresentation> getRealmRolesByUser(String id) {
        return null;
    }

    @Override
    public Response createUserRole(String id, List<RoleRepresentation> roles) {
        return null;
    }
}