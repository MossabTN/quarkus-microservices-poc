package io.maxilog.service.dto;

public class NotificationDTO {

    private String id;
    private String from;
    private String to;
    private String payload;
    private boolean seen;

    public NotificationDTO() {
    }

    public NotificationDTO(String id, String from, String to, String payload, boolean seen) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.payload = payload;
        this.seen = seen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
