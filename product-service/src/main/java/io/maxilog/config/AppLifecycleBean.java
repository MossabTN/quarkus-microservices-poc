package io.maxilog.config;

import io.maxilog.Security.TokenManager;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import org.eclipse.microprofile.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLifecycleBean.class);
    private final TokenManager tokenManager;
    private final Config config;

    @Inject
    public AppLifecycleBean(TokenManager tokenManager, Config config) {
        this.config = config;
        this.tokenManager = tokenManager;
    }

    @Transactional
    void onStart(@Observes StartupEvent ev) throws UnknownHostException {
        String protocol = "http";
        if (config.getOptionalValue("quarkus.http.ssl.certificate.key-store-file", String.class).isPresent()) {
            protocol = "https";
        }

        LOGGER.info("\n----------------------------------------------------------\n\t" +
                    "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t" +
                    "External: \t{}://{}:{}\n\t" +
                    "Profile(s): \t{}\n----------------------------------------------------------\n----------------------------------------------------------",
            config.getValue("maxilog.application.name", String.class),
            protocol, config.getValue("quarkus.http.port", String.class), protocol,
            InetAddress.getLocalHost().getHostAddress(),config.getValue("quarkus.http.port", String.class), ProfileManager.getActiveProfile());

    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}