package io.maxilog.web.rest;


import io.maxilog.service.NotificationService;
import io.maxilog.service.dto.NotificationDTO;
import io.maxilog.service.dto.Pageable;
import io.maxilog.web.rest.Util.ResponseUtil;
import io.maxilog.web.rest.errors.BadRequestException;
import io.quarkus.panache.common.Page;
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
public class NotificationResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationResource.class);

    private final NotificationService notificationservice;

    @Inject
    public NotificationResource(NotificationService notificationservice) {
        this.notificationservice = notificationservice;
    }


    @GET
    @Path("/notifications")
    public Response findAll(@BeanParam Pageable page) {
        LOGGER.debug("REST request to get all notifications");
        return Response.ok().entity(notificationservice.findAll(page)).build();
    }


    @GET
    @Path("/notifications/me")
    public Response findMyData(@BeanParam Pageable page) {
        LOGGER.debug("REST request to my data");
        return Response.ok(notificationservice.findMyNotifications(page)).build();
    }

    @GET
    @Path("/notifications/{id}")
    public Response findById(@PathParam("id") String id) {
        LOGGER.debug("REST request to get notification by id: {}", id);
        return Response.ok().entity(notificationservice.findOne(id)).build();
    }


    @POST
    @Path("/notifications")
    public Response create(NotificationDTO notificationDTO) throws URISyntaxException {
        LOGGER.debug("REST request to save Notification : {}", notificationDTO);
        if (notificationDTO.getId() != null) {
            throw new BadRequestException("A new notification cannot already have an ID");
        }

        NotificationDTO result = notificationservice.save(notificationDTO);
        return Response.created(new URI("/api/notifications/" + result.getId())).entity(result)
                .build();
    }


    @POST
    @Path("/notifications/notify")
    public Response notify(NotificationDTO notificationDTO) throws URISyntaxException {
        LOGGER.debug("REST request to save Notification : {}", notificationDTO);
        if (notificationDTO.getId() != null) {
            throw new BadRequestException("A new notification cannot already have an ID");
        }
        notificationservice.notify(notificationDTO);
        return Response.ok().build();
    }

    @DELETE
    @Path("/notifications/{id}")
    public Response delete(@PathParam("id") String id) {
        LOGGER.debug("REST request to delete Notification : {}", id);
        notificationservice.delete(id);
        return Response.ok().build();
    }


}