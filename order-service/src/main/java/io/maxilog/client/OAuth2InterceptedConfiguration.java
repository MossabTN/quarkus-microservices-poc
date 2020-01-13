package io.maxilog.client;

import io.maxilog.Security.TokenManager;
import org.eclipse.microprofile.rest.client.ext.DefaultClientHeadersFactoryImpl;

import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

@Provider
public class OAuth2InterceptedConfiguration extends DefaultClientHeadersFactoryImpl {

    private TokenManager tokenManager;

    public OAuth2InterceptedConfiguration() {
        this.tokenManager = CDI.current().select(TokenManager.class).get();
    }

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> multivaluedMap, MultivaluedMap<String, String> multivaluedMap1) {

        MultivaluedMap<String, String> propagatedHeaders = super.update(multivaluedMap, multivaluedMap1);
        if (!multivaluedMap1.containsKey("Authorization")) {
            propagatedHeaders.put("Authorization", Collections.singletonList("Bearer "+tokenManager.getAccessToken().getToken()));
        }
        return propagatedHeaders;
    }
}
