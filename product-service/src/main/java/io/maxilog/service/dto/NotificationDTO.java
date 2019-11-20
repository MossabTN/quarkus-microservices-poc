package io.maxilog.service.dto;

public class NotificationDTO {

    private String id;
    private String body;
    private String date;
    private From from;
    private String link;
    private Boolean seen;
    private String type;

    public NotificationDTO() {
    }

    public NotificationDTO(String body) {
        this.body = body;
        this.from = new From("John", "Doe");
        this.type = "news";
    }

    public NotificationDTO(String id, String body, String date, From from, String link, Boolean seen, String type) {
        this.id = id;
        this.body = body;
        this.date = date;
        this.from = from;
        this.link = link;
        this.seen = seen;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    static class From {

        private String id;
        private String firstName;
        private String lastName;
        private String profilePicture;

        public From() {
        }

        public From(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

    }
}
