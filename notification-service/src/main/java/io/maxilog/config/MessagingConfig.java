package io.maxilog.config;

import io.maxilog.service.NotificationService;
import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.quarkus.vertx.http.runtime.security.QuarkusHttpUser;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.impl.HttpStatusException;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class MessagingConfig {

    private static final String ACCESS_TOKEN = "access-token";

    private final IdentityProviderManager identityProviderManager;
    private final NotificationService notificationService;
    private final Vertx vertx;

    @Inject
    public MessagingConfig(IdentityProviderManager identityProviderManager, NotificationService notificationService, Vertx vertx) {
        this.identityProviderManager = identityProviderManager;
        this.notificationService = notificationService;
        this.vertx = vertx;
    }

    public void init(@Observes Router router) {

        router.route("/ws/chat/*").handler(routingContext -> {
            String token = routingContext.request().getParam(ACCESS_TOKEN);
            if(token == null || token.isEmpty()) {
                routingContext.response().setStatusCode(401).end();
                return;
            }
            identityProviderManager.authenticate(new TokenAuthenticationRequest(new AccessTokenCredential(token)))
                .thenAccept((securityIdentity) -> {
                    routingContext.setUser(new QuarkusHttpUser(securityIdentity));
                    routingContext.next();
                })
                .exceptionally(e -> {
                    Future.failedFuture(new HttpStatusException(401));
                    return null;
                });
        });
        router.route("/ws/chat/*").handler(eventBusHandler());
        router.route().handler(StaticHandler.create().setCachingEnabled(false));
    }

    private SockJSHandler eventBusHandler() {
        BridgeOptions options = new BridgeOptions()
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("out"))
                .addInboundPermitted(new PermittedOptions().setAddressRegex("in"));

        EventBus eventBus = vertx.eventBus();
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        sockJSHandler.bridge(options, event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                notificationService.onOpen(event,eventBus);
            }

            if (event.type() == BridgeEventType.SEND) {
                notificationService.onMessage(event,eventBus);
            }

            if (event.type() == BridgeEventType.SOCKET_CLOSED) {
                notificationService.onClose(event,eventBus);
            }

            event.complete(true);
        });
        return sockJSHandler;
    }
}