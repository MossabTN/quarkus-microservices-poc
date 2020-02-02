package io.maxilog.repository.impl;

import io.maxilog.domain.Notification;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class NotificationRepository implements PanacheMongoRepository<Notification> {

    public List<Notification> findAllByTo(String username, Page page){
        return find("to", Sort.descending("id"), username).page(page).list();
    }

    public List<Notification> findAll(Page page){
        return findAll(Sort.descending("id")).page(page).list();
    }


}