package io.maxilog.Security;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Singleton
public class TokenManager {

    private AccessTokenResponse currentToken;
    private long expirationTime;
    private long minTokenValidity = 30L;

    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    String url;

    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;

    @Inject
    @RestClient
    private TokenService tokenService;

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
        long requestTime =  LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        synchronized(TokenManager.class) {
            currentToken = tokenService.grantToken(form.asMap());
            expirationTime = requestTime + currentToken.getExpiresIn();
        }
        return currentToken;
    }

    private synchronized AccessTokenResponse refreshToken() {
        Form form = (new Form())
                .param("grant_type", "refresh_token")
                .param("refresh_token", currentToken.getRefreshToken());
        try {
            long requestTime =  LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
            currentToken = tokenService.refreshToken(form.asMap());
            expirationTime = requestTime + currentToken.getExpiresIn();
            return currentToken;
        } catch (BadRequestException e) {
            return grantToken();
        }
    }

    private synchronized boolean tokenExpired() {
        return  LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() + minTokenValidity >= expirationTime;
    }


    @RegisterRestClient(configKey = "keycloak-token")
    @Produces({"application/json"})
    @Consumes({"application/x-www-form-urlencoded"})
    public interface TokenService {
        @POST
        @Path("/protocol/openid-connect/token")
        AccessTokenResponse grantToken(MultivaluedMap<String, String> var2);

        @POST
        @Path("/protocol/openid-connect/token")
        AccessTokenResponse refreshToken(MultivaluedMap<String, String> var2);
    }

}
