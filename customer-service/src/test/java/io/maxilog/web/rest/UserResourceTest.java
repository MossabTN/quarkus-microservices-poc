package io.maxilog.web.rest;

import io.maxilog.domain.User;
import io.maxilog.repository.UserRepository;
import com.google.common.collect.Iterators;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class UserResourceTest {


    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "FN";

    private static final String DEFAULT_LAST_NAME = "LN";

    @Inject
    UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        user = createEntity();
    }


    public static User createEntity() {
        return new User(DEFAULT_USERNAME, DEFAULT_EMAIL, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME);
    }

    @Test
    public void createUser() {

        int databaseSizeBeforeCreate = Iterators.size(userRepository.findAll().iterator());

        given()
                .contentType(JSON)
                .body(this.user)
                .when().post("/api/users")
                .then()
                .statusCode(201);

        // Validate the User in the database
        List<User> userList = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        Assertions.assertEquals(userList.size(), databaseSizeBeforeCreate + 1);
        User user = userList.get(userList.size() - 1);
        Assertions.assertEquals(user.getEmail(), DEFAULT_EMAIL);
        Assertions.assertEquals(user.getUsername(), DEFAULT_USERNAME);
    }

    @Test
    public void createUserWithExistingId() {

        int databaseSizeBeforeCreate = Iterators.size(userRepository.findAll().iterator());
        user.setId(10L);

        given()
                .contentType(JSON)
                .body(user)
                .when().post("/api/users")
                .then()
                .statusCode(400);

        // Validate the User in the database
        List<User> userList = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        Assertions.assertEquals(userList.size(), databaseSizeBeforeCreate);
    }

    @Test
    public void getAllUsers() {
        // Initialize the database
        userRepository.save(user);

        given()
                .when().get("/api/users")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("content[0].email", equalTo(DEFAULT_EMAIL))
                .body("content[0].username",equalTo(DEFAULT_USERNAME));
    }

    @Test
    public void getAllUsersByFullName() {
        // Initialize the database
        userRepository.save(user);

        given()
                .when().get("/api/users/search?name="+DEFAULT_FIRST_NAME)
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("email", hasItem(DEFAULT_EMAIL))
                .body("username",hasItem(DEFAULT_USERNAME));
    }

    @Test
    public void getAllKeycloakUsers() {
        // Initialize the database
        userRepository.save(user);

        given()
                .when().get("/api/users/keycloak")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("email", hasItem(DEFAULT_EMAIL))
                .body("username",hasItem(DEFAULT_USERNAME));
    }

    @Test
    public void getUserById() {
        // Initialize the database
        userRepository.save(user);

        given()
                .when().get("/api/users/{id}", user.getId())
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("email", equalTo(DEFAULT_EMAIL))
                .body("username",equalTo(DEFAULT_USERNAME));

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
        userRepository.save(user);
        int databaseSizeBeforeCreate = Iterators.size(userRepository.findAll().iterator());

        user.setUsername(UPDATED_USERNAME);
        user.setEmail(UPDATED_EMAIL);

        given()
                .contentType(JSON)
                .body(user)
                .when().put("/api/users")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("email", equalTo(UPDATED_EMAIL))
                .body("username",equalTo(UPDATED_USERNAME));

        // Validate the User in the database
        List<User> userList = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        Assertions.assertEquals(userList.size(), databaseSizeBeforeCreate);
    }


    @Test
    public void deleteUser() {

        // Initialize the database
        userRepository.save(user);
        int databaseSizeBeforeDelete = Iterators.size(userRepository.findAll().iterator());

        given()
                .when().delete("/api/users/"+ user.getId())
                .then()
                .statusCode(200);

        // Validate the User in the database
        List<User> userList = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        Assertions.assertEquals(userList.size(), databaseSizeBeforeDelete - 1);
    }

}
