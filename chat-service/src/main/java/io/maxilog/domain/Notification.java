package io.maxilog.domain;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

@MongoEntity(collection="notification")
public class Notification extends PanacheMongoEntity {

    private String from;

    private String to;

    private String payload;

    private boolean seen;


    public Notification() { }

    public Notification(String from, String to, String payload, boolean seen) {
        this.from = from;
        this.to = to;
        this.payload = payload;
        this.seen = seen;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    /*QUERY*/

    public static List<Notification> findAllByTo(String username, Page page){
        return find("to", Sort.descending("id"), username).page(page).list();
    }

    public static List<Notification> findAll(Page page){
        return findAll(Sort.descending("id")).page(page).list();
    }

    /*QUERY*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification notification = (Notification) o;
        return Objects.equals(getId(), notification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", payload='" + payload + '\'' +
                ", seen='" + seen + '\'' +
                '}';
    }
}
