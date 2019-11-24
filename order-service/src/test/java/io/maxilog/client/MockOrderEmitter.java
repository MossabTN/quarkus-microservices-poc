package io.maxilog.client;

import io.maxilog.order.OrderAvro;
import io.maxilog.service.dto.CustomerDTO;
import io.smallrye.reactive.messaging.annotations.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative()
@Priority(1)
@ApplicationScoped
public class MockOrderEmitter implements Emitter<OrderAvro> {



    @Override
    public Emitter<OrderAvro> send(OrderAvro msg) {
        return null;
    }

    @Override
    public void complete() {

    }

    @Override
    public void error(Exception e) {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isRequested() {
        return false;
    }
}