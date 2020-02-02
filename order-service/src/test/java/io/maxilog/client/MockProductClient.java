package io.maxilog.client;

import io.maxilog.service.dto.ProductDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Alternative()
@Priority(1)
@ApplicationScoped
@RestClient
public class MockProductClient implements ProductClient {

    @Override
    public ProductDTO getProductById(long id) {
        return new ProductDTO(id, "product1");
    }

    @Override
    public List<ProductDTO> getProductByIds(List<Long> ids) {
        return ids
                .stream()
                .map(aLong -> new ProductDTO(aLong, "product"+aLong, BigDecimal.TEN, 7))
                .collect(Collectors.toList());
    }
}