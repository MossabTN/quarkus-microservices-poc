package io.maxilog.config;

import io.quarkus.arc.config.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ConfigProperties(prefix = "maxilog")
public interface ApplicationPropertyConfiguration {

    @ConfigProperty(name = "application.name")
    String applicationName();

}