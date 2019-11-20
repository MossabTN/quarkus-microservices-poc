package io.maxilog.web.rest;

import com.google.common.collect.Iterators;
import io.maxilog.domain.Category;
import io.maxilog.repository.CategoryRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class CategoryResourceTest {


    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    CategoryRepository categoryRepository;


    private Category category;

    @BeforeEach
    void setUp(){
        categoryRepository.deleteAll();
        category = createEntity();
    }


    public static Category createEntity() {
        return new Category(DEFAULT_NAME, DEFAULT_DESCRIPTION);
    }

    @Test
    public void createCategory() {

        int databaseSizeBeforeCreate = Iterators.size(categoryRepository.findAll().iterator());

        given()
                .contentType(JSON)
                .body(this.category)
                .when().post("/api/categories")
                .then()
                .statusCode(201);

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        Assertions.assertEquals(categoryList.size(), databaseSizeBeforeCreate + 1);
        Category category = categoryList.get(categoryList.size() - 1);
        Assertions.assertEquals(category.getName(), DEFAULT_NAME);
    }

    @Test
    public void createCategoryWithExistingId() {

        int databaseSizeBeforeCreate = categoryRepository.findAll().size();
        category.setId(10L);

        given()
                .contentType(JSON)
                .body(category)
                .when().post("/api/categories")
                .then()
                .statusCode(400);

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        Assertions.assertEquals(categoryList.size(), databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCategories() {
        // Initialize the database
        categoryRepository.save(category);

        given()
                .when().get("/api/categories")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("content[0].name", equalTo(DEFAULT_NAME));
    }

    @Test
    public void getCategoryById() {
        // Initialize the database
        categoryRepository.save(category);

        given()
                .when().get("/api/categories/{id}", category.getId())
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("name", equalTo(DEFAULT_NAME));

    }

    @Test
    public void getNotExistingCategoryById() {

        given()
                .when().get("/api/categories/{id}", "notExistingId")
                .then()
                .statusCode(404);
    }

    @Test
    public void updateCategory() {

        // Initialize the database
        categoryRepository.save(category);
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        category.setName(DEFAULT_NAME);

        given()
                .contentType(JSON)
                .body(category)
                .when().put("/api/categories")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("name", equalTo(DEFAULT_NAME));

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        Assertions.assertEquals(categoryList.size(), databaseSizeBeforeCreate);
    }


    @Test
    public void deleteCategory() {

        // Initialize the database
        categoryRepository.save(category);
        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        given()
                .when().delete("/api/categories/"+ category.getId())
                .then()
                .statusCode(200);

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        Assertions.assertEquals(categoryList.size(), databaseSizeBeforeDelete - 1);
    }

}
