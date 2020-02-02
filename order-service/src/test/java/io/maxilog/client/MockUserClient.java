package io.maxilog.client;

import io.maxilog.service.dto.CustomerDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative()
@Priority(1)
@ApplicationScoped
@RestClient
public class MockUserClient implements CustomerClient {


    @Override
    public CustomerDTO getUserByUsername(String username) {
        return new CustomerDTO(username, "email", "FN","LN");
    }
}