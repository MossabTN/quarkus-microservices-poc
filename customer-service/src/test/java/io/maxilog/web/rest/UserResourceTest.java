package io.maxilog.web.rest;

import io.maxilog.service.dto.UserDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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


    private UserDTO user;

    @BeforeEach
    void setUp(){
        user = createEntity();
    }


    public static UserDTO createEntity() {
        return new UserDTO(DEFAULT_USERNAME, DEFAULT_EMAIL, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME);
    }

    @Test
    public void createUser() {

        given()
                .contentType(JSON)
                .body(this.user)
                .when().post("/api/users")
                .then()
                .statusCode(201);

    }

    @Test
    public void getAllUsers() {
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

        given()
                .when().get("/api/users/username/"+DEFAULT_USERNAME)
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("email", equalTo(DEFAULT_EMAIL))
                .body("username",equalTo(DEFAULT_USERNAME));
    }

    @Test
    public void getUserById() {

        given()
                .when().get("/api/users/notExistingId")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("email", equalTo(DEFAULT_EMAIL))
                .body("username",equalTo(DEFAULT_USERNAME));

    }

    @Test
    public void updateUser() {

        user.setId("notExisting");
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

    }


    @Test
    public void deleteUser() {

        given()
                .when().delete("/api/users/"+ user.getId())
                .then()
                .statusCode(200);
    }

}
