package io.maxilog.config;

import io.maxilog.Security.TokenManager;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.hibernate.search.mapper.orm.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLifecycleBean.class);
    private final EntityManager entityManager;
    private final TokenManager tokenManager;

    @Inject
    public AppLifecycleBean(EntityManager entityManager, TokenManager tokenManager) {
        this.entityManager = entityManager;
        this.tokenManager = tokenManager;
    }

    @Transactional
    void onStart(@Observes StartupEvent ev) throws InterruptedException {
        LOGGER.info("The application is starting...");
        /*if ((long)entityManager.createQuery("SELECT COUNT(o) FROM Order o").getSingleResult() > 0) {
            Search.session(entityManager)
                    .massIndexer()
                    .startAndWait();
        }*/
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}