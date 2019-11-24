package io.maxilog.service.dto;

import org.bson.types.ObjectId;

public class NotificationDTO {

    private ObjectId id;
    private String from;
    private String to;
    private String payload;
    private boolean seen;

    public NotificationDTO() {
    }

    public NotificationDTO(ObjectId id, String from, String to, String payload, boolean seen) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.payload = payload;
        this.seen = seen;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
