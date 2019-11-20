package io.maxilog.web.rest;


import io.maxilog.service.CategoryService;
import io.maxilog.service.dto.CategoryDTO;
import io.maxilog.service.dto.PageableImpl;
import io.maxilog.web.rest.Util.ResponseUtil;
import io.maxilog.web.rest.errors.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class CategoryResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryResource.class);

    private final CategoryService categoryService;

    @Inject
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GET
    @Path("/categories")
    public Response findAll(@BeanParam PageableImpl pageable) {
        LOGGER.debug("REST request to get all Categories");
        return Response.ok().entity(categoryService.findAll(pageable)).build();
    }

    @GET
    @Path("/categories/search")
    public List<CategoryDTO> findAllByCategoryName(@QueryParam("name") String fullName) {
        LOGGER.debug("REST request to get all Categories by fullName {}", fullName);
        return categoryService.findAllByName(fullName);
    }

    @GET
    @Path("/categories/{id}")
    public Response findById(@PathParam("id") long id) {
        LOGGER.debug("REST request to get Category : {}", id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categoryService.findOne(id)));
    }

    //@RolesAllowed({"admin"})
    @POST
    @Path("/categories")
    public Response create(CategoryDTO categoryDTO) throws URISyntaxException {
        LOGGER.debug("REST request to save Category : {}", categoryDTO);
        if (categoryDTO.getId() != null) {
            throw new BadRequestException("A new category cannot already have an ID");
        }

        CategoryDTO result = categoryService.save(categoryDTO);
        return Response.created(new URI("/api/categories/" + result.getId())).entity(result)
                .build();
    }

    @PUT
    @Path("/categories")
    public Response update(CategoryDTO categoryDTO) {
        LOGGER.debug("REST request to update Category : {}", categoryDTO);
        if (categoryDTO.getId() == null) {
            throw new BadRequestException("Updated category must have an ID");
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categoryService.save(categoryDTO)));
    }
    
    @DELETE
    @Path("/categories/{id}")
    public Response delete(@PathParam("id") long id) {
        LOGGER.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return Response.ok().build();
    }


}