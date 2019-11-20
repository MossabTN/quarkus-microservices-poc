package io.maxilog.utils;

import io.quarkus.test.Mock;
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.Collection;

@Alternative()
@Priority(1)
@ApplicationScoped
@Mock
public class MockJsonWebToken extends JWTCallerPrincipal {

    public MockJsonWebToken() {
        super("", "");
    }

    @Override
    public String getName() {
        return "username";
    }

    @Override
    protected Collection<String> doGetClaimNames() {
        return null;
    }

    @Override
    protected Object getClaimValue(String s) {
        return null;
    }
}