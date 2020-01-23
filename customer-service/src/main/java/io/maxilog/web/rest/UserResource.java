package io.maxilog.web.rest;


import io.maxilog.service.UserService;
import io.maxilog.service.dto.PageableImpl;
import io.maxilog.service.dto.UserDTO;
import io.maxilog.web.rest.Util.ResponseUtil;
import io.maxilog.web.rest.errors.BadRequestException;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }


    @GET
    @Path("/users")
    @Timed
    public Response findAll(@BeanParam PageableImpl pageable) {
        LOGGER.info("REST request to get all Users");
        return Response.ok().entity(userService.findPage(pageable)).build();
    }

    @GET
    @Path("/users/me")
    @Timed
    public Response findMyData() {
        LOGGER.info("REST request to my data");
        return Response.ok(userService.findMyData()).build();
    }

    @GET
    @Path("/users/{id}")
    @Timed
    public Response findById(@PathParam("id") String id) {
        LOGGER.info("REST request to get User : {}", id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userService.findOne(id)));
    }

    @GET
    @Path("/users/username/{username}")
    @Timed
    public Response findByUsername(@PathParam("username") String username) {
        LOGGER.info("REST request to get User by username: {}", username);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userService.findUsername(username)));
    }

    //@RolesAllowed({"admin"})
    @POST
    @Path("/users")
    @Timed
    public Response create(UserDTO userDTO) throws URISyntaxException {
        LOGGER.info("REST request to save User : {}", userDTO);
        if (userDTO.getId() != null) {
            throw new BadRequestException("A new user cannot already have an ID");
        }

        UserDTO result = userService.save(userDTO);
        return Response.created(new URI("/api/users/" + result.getId())).entity(result)
                .build();
    }

    @PUT
    @Path("/users")
    @Timed
    public Response update(UserDTO userDTO) {
        LOGGER.info("REST request to update User : {}", userDTO);
        if (userDTO.getId() == null) {
            throw new BadRequestException("Updated user must have an ID");
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userService.update(userDTO)));
    }


    @DELETE
    @Path("/users/{id}")
    @Timed
    public Response delete(@PathParam("id") String id) {
        LOGGER.info("REST request to delete User : {}", id);
        userService.delete(id);
        return Response.ok().build();
    }


}