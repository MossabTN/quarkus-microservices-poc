package io.maxilog.config;

import io.maxilog.Security.TokenManager;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLifecycleBean.class);
    private final TokenManager tokenManager;

    @Inject
    public AppLifecycleBean(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    void onStart(@Observes StartupEvent ev) throws InterruptedException {
        LOGGER.info("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}