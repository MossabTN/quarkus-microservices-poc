package io.maxilog.client;

import io.maxilog.service.dto.CustomerDTO;
import io.maxilog.service.dto.ProductDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative()
@Priority(1)
@ApplicationScoped
@RestClient
public class MockProductClient implements ProductClient {

    @Override
    public ProductDTO getProdcutById(long id) {
        return new ProductDTO(id, "product1");
    }
}