package io.maxilog.web.rest;


import io.maxilog.service.OrderService;
import io.maxilog.service.dto.OrderDTO;
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
public class OrderResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    private final OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }


    @GET
    @Path("/orders")
    public Response findAll(@BeanParam PageableImpl pageable) {
        LOGGER.debug("REST request to get all Users");
        return Response.ok().entity(orderService.findAll(pageable)).build();
    }

    @GET
    @Path("/orders/me")
    public Response findMyData() {
        LOGGER.debug("REST request to my data");
        return Response.ok(orderService.findMyOrders()).build();
    }

    @GET
    @Path("/orders/{id}")
    public Response findById(@PathParam("id") long id) {
        LOGGER.debug("REST request to get User : {}", id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderService.findOne(id)));
    }

    //@RolesAllowed({"admin"})
    @POST
    @Path("/orders")
    public Response create(OrderDTO orderDTO) throws URISyntaxException {
        LOGGER.debug("REST request to save User : {}", orderDTO);
        if (orderDTO.getId() != null) {
            throw new BadRequestException("A new user cannot already have an ID");
        }

        OrderDTO result = orderService.save(orderDTO);
        return Response.created(new URI("/api/orders/" + result.getId())).entity(result)
                .build();
    }

/*
    @PUT
    @Path("/orders")
    public Response update(OrderDTO orderDTO) {
        LOGGER.debug("REST request to update User : {}", orderDTO);
        if (orderDTO.getId() == null) {
            throw new BadRequestException("Updated user must have an ID");
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderService.save(orderDTO)));
    }
*/

    @DELETE
    @Path("/orders/{id}")
    public Response delete(@PathParam("id") long id) {
        LOGGER.debug("REST request to delete User : {}", id);
        orderService.delete(id);
        return Response.ok().build();
    }


}