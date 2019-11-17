package io.maxilog.client;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative()
@Priority(1)
@ApplicationScoped
@RestClient
public class MockUserClient implements UserClient {


}