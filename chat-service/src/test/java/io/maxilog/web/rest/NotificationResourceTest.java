package io.maxilog.web.rest;

import io.maxilog.domain.Notification;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
class NotificationResourceTest {


    private static final String DEFAULT_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_TO = "AAAAAAAAAA";
    private static final String UPDATED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_PAYLOAD = "BBBBBBBBBB";

    private static final boolean DEFAULT_SEEN = false;
    private static final boolean UPDATED_SEEN = true;

    private Notification notification;

    @BeforeEach
    void setUp(){
        Notification.deleteAll();
        notification = createEntity();
    }


    public static Notification createEntity() {
        return new Notification(DEFAULT_FROM, DEFAULT_TO, DEFAULT_PAYLOAD, DEFAULT_SEEN);
    }

    @Test
    public void createUser() {

        long databaseSizeBeforeCreate = Notification.count();

        given()
                .contentType(JSON)
                .body(this.notification)
                .when().post("/api/users")
                .then()
                .statusCode(201);

        // Validate the Notification in the database
        List<Notification> notificationList = Notification.listAll();
        Assertions.assertEquals(notificationList.size(), databaseSizeBeforeCreate + 1);
        Notification notification = notificationList.get(notificationList.size() - 1);
        Assertions.assertEquals(notification.getFrom(), DEFAULT_FROM);
        Assertions.assertEquals(notification.getTo(), DEFAULT_TO);
        Assertions.assertEquals(notification.getPayload(), DEFAULT_PAYLOAD);
        Assertions.assertEquals(notification.isSeen(), DEFAULT_SEEN);
    }

    @Test
    public void createUserWithExistingId() {

        Notification.persist(notification);
        long databaseSizeBeforeCreate = Notification.count();

        notification.setId("newid");

        given()
                .contentType(JSON)
                .body(notification)
                .when().post("/api/users")
                .then()
                .statusCode(400);

        // Validate the Notification in the database
        Assertions.assertEquals(Notification.count(), databaseSizeBeforeCreate);
    }

    @Test
    public void getAllUsers() {
        // Initialize the database
        Notification.persist(notification);

        given()
                .when().get("/api/notifications")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("content[0].from", equalTo(DEFAULT_FROM))
                .body("content[0].to", equalTo(DEFAULT_TO))
                .body("content[0].payload", equalTo(DEFAULT_PAYLOAD))
                .body("content[0].seen",equalTo(DEFAULT_SEEN));
    }

    @Test
    public void getUserById() {
        // Initialize the database
        Notification.persist(notification);

        given()
                .when().get("/api/users/{id}", notification.getId())
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("content[0].from", equalTo(DEFAULT_FROM))
                .body("content[0].to", equalTo(DEFAULT_TO))
                .body("content[0].payload", equalTo(DEFAULT_PAYLOAD))
                .body("content[0].seen",equalTo(DEFAULT_SEEN));

    }

    @Test
    public void getNotExistingUserById() {

        given()
                .when().get("/api/users/{id}", "notExistingId")
                .then()
                .statusCode(404);

    }

    @Test
    public void updateUser() {

        // Initialize the database
        Notification.persist(notification);
        long databaseSizeBeforeCreate = Notification.count();

        notification.setFrom(UPDATED_FROM);
        notification.setTo(UPDATED_TO);
        notification.setPayload(UPDATED_PAYLOAD);
        notification.setSeen(UPDATED_SEEN);

        given()
                .contentType(JSON)
                .body(notification)
                .when().put("/api/users")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("content[0].from", equalTo(UPDATED_FROM))
                .body("content[0].to", equalTo(UPDATED_TO))
                .body("content[0].payload", equalTo(UPDATED_PAYLOAD))
                .body("content[0].seen",equalTo(UPDATED_SEEN));

        // Validate the Notification in the database
        Assertions.assertEquals(Notification.count(), databaseSizeBeforeCreate);
    }


    @Test
    public void deleteUser() {

        // Initialize the database
        Notification.persist(notification);
        long databaseSizeBeforeDelete = Notification.count();

        given()
                .when().delete("/api/users/"+ notification.getId())
                .then()
                .statusCode(200);

        // Validate the Notification in the database
        Assertions.assertEquals(Notification.count(), databaseSizeBeforeDelete - 1);
    }

}
