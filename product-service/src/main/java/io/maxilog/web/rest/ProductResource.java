package io.maxilog.web.rest;


import io.maxilog.service.ProductService;
import io.maxilog.service.dto.PageableImpl;
import io.maxilog.service.dto.ProductDTO;
import io.maxilog.web.rest.Util.ResponseUtil;
import io.maxilog.web.rest.errors.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService productService;

    @Inject
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }
    
    @GET
    @Path("/products")
    public Response findAll(@BeanParam PageableImpl pageable) {
        LOGGER.debug("REST request to get all Products");
        return Response.ok().entity(productService.findAll(pageable)).build();
    }

    @GET
    @Path("/products/search")
    public Page<ProductDTO> findAllByProductName(@QueryParam("name") String name, @BeanParam PageableImpl pageable) {
        LOGGER.debug("REST request to get all Products by name {}", name);
        return productService.findAllByName(name, pageable);
    }

    @GET
    @Path("/products/{id}")
    public Response findById(@PathParam("id") long id) {
        LOGGER.debug("REST request to get Product : {}", id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productService.findOne(id)));
    }

    //@RolesAllowed({"admin"})
    @POST
    @Path("/products")
    public Response create(ProductDTO productDTO) throws URISyntaxException {
        LOGGER.debug("REST request to save Product : {}", productDTO);
        if (productDTO.getId() != null) {
            throw new BadRequestException("A new product cannot already have an ID");
        }

        ProductDTO result = productService.save(productDTO);
        return Response.created(new URI("/api/products/" + result.getId())).entity(result)
                .build();
    }

    @PUT
    @Path("/products")
    public Response update(ProductDTO productDTO) {
        LOGGER.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            throw new BadRequestException("Updated product must have an ID");
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productService.save(productDTO)));
    }
    
    @DELETE
    @Path("/products/{id}")
    public Response delete(@PathParam("id") long id) {
        LOGGER.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return Response.ok().build();
    }


}