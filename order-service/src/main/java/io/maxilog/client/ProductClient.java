package io.maxilog.client;


import io.maxilog.service.dto.ProductDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import java.util.List;

@Path("/")
@Produces({"application/json"})
@Consumes({"application/json"})
@RegisterRestClient(configKey = "product")
@RegisterClientHeaders(OAuth2InterceptedConfiguration.class)
@SuppressWarnings("all")
public interface ProductClient {


    @GET
    @Path("/products/{id}")
    ProductDTO getProductById(@PathParam("id") long id);

    @GET
    @Path("/products/by-ids")
    List<ProductDTO> getProductByIds(List<Long> ids);

}
