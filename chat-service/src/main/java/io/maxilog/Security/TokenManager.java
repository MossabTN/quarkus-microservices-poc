package io.maxilog.Security;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;
import java.time.LocalDateTime;

@Singleton
public class TokenManager {

    private ResteasyClient client;
    private AccessTokenResponse currentToken;
    private long expirationTime;
    private long minTokenValidity = 30L;
    private TokenService tokenService;

    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    String url;

    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;


    @PostConstruct
    public void init() {
        client = new ResteasyClientBuilderImpl().disableTrustManager().build();
        ResteasyWebTarget target = client.target(url);

        tokenService = target
                .proxyBuilder(TokenService.class)
                .classloader(TokenService.class.getClassLoader())
                .build();
    }

    public synchronized AccessTokenResponse getAccessToken() {
        if (currentToken == null) {
            grantToken();
        } else if (tokenExpired()) {
            refreshToken();
        }
        return currentToken;
    }

    private AccessTokenResponse grantToken() {
        Form form = (new Form())
                .param("grant_type", "client_credentials")
                .param("client_id", clientId)
                .param("client_secret", clientSecret);
        int requestTime = LocalDateTime.now().getSecond();
        synchronized(TokenManager.class) {
            currentToken = tokenService.grantToken(form.asMap());
            expirationTime = (long)requestTime + currentToken.getExpiresIn();
        }
        return currentToken;
    }

    private synchronized AccessTokenResponse refreshToken() {
        Form form = (new Form())
                .param("grant_type", "refresh_token")
                .param("refresh_token", currentToken.getRefreshToken());
        try {
            int requestTime =  LocalDateTime.now().getSecond();
            currentToken = tokenService.refreshToken(form.asMap());
            expirationTime = (long)requestTime + currentToken.getExpiresIn();
            return currentToken;
        } catch (BadRequestException e) {
            return grantToken();
        }
    }

    private synchronized boolean tokenExpired() {
        return  LocalDateTime.now().getSecond() + minTokenValidity >= expirationTime;
    }


    @Produces({"application/json"})
    @Consumes({"application/x-www-form-urlencoded"})
    private interface TokenService {
        @POST
        @Path("/protocol/openid-connect/token")
        AccessTokenResponse grantToken(MultivaluedMap<String, String> var2);

        @POST
        @Path("/protocol/openid-connect/token")
        AccessTokenResponse refreshToken(MultivaluedMap<String, String> var2);
    }

}
