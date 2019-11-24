package io.maxilog.web.rest;

import com.google.common.collect.Iterators;
import io.maxilog.domain.Category;
import io.maxilog.domain.Product;
import io.maxilog.domain.enumeration.ProductStatus;
import io.maxilog.repository.CategoryRepository;
import io.maxilog.repository.ProductRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class ProductResourceTest {


    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = BigDecimal.TEN;
    private static final BigDecimal UPDATED_PRICE = BigDecimal.ONE;

    private static final int DEFAULT_QUANTITY = 10;
    private static final int UPDATED_QUANTITY = 2;

    private static final ProductStatus DEFAULT_STATUS = ProductStatus.AVAILABLE;
    private static final ProductStatus UPDATED_STATUS = ProductStatus.DISCONTINUED;

    private static Category DEFAULT_CATEGORY = new Category("AAAAAAAAAA");
    private static Category UPDATED_CATEGORY = new Category("BBBBBBBBBB");

    @Inject
    ProductRepository productRepository;

    @Inject
    CategoryRepository categoryRepository;


    private Product product;

    @BeforeEach
    void setUp(){
        productRepository.deleteAll();
        DEFAULT_CATEGORY = categoryRepository.save(DEFAULT_CATEGORY);
        product = createEntity();
    }


    public static Product createEntity() {
        return new Product(DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_PRICE,
                DEFAULT_QUANTITY, DEFAULT_STATUS, DEFAULT_CATEGORY);
    }

    @Test
    public void createProduct() {

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        given()
                .contentType(JSON)
                .body(this.product)
                .when().post("/api/products")
                .then()
                .statusCode(201);

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        Assertions.assertEquals(productList.size(), databaseSizeBeforeCreate + 1);
        Product product = productList.get(productList.size() - 1);
        Assertions.assertEquals(product.getName(), DEFAULT_NAME);
    }

    @Test
    public void createProductWithExistingId() {

        int databaseSizeBeforeCreate = productRepository.findAll().size();
        product.setId(10L);

        given()
                .contentType(JSON)
                .body(product)
                .when().post("/api/products")
                .then()
                .statusCode(400);

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        Assertions.assertEquals(productList.size(), databaseSizeBeforeCreate);
    }

    @Test
    public void getAllProducts() {
        // Initialize the database
        productRepository.save(product);

        given()
                .when().get("/api/products")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("content[0].name", equalTo(DEFAULT_NAME));
    }

    //@Test
    public void getAllProductsByFullName() {
        // Initialize the database
        productRepository.save(product);

        given()
                .when().get("/api/products/search?name="+DEFAULT_NAME)
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("name", hasItem(DEFAULT_NAME));
    }

    @Test
    public void getProductById() {
        // Initialize the database
        productRepository.save(product);

        given()
                .when().get("/api/products/{id}", product.getId())
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("name", equalTo(DEFAULT_NAME));

    }

    @Test
    public void getNotExistingProductById() {

        given()
                .when().get("/api/products/{id}", "notExistingId")
                .then()
                .statusCode(404);

    }

    @Test
    public void updateProduct() {

        // Initialize the database
        productRepository.save(product);
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        product.setName(DEFAULT_NAME);

        given()
                .contentType(JSON)
                .body(product)
                .when().put("/api/products")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("name", equalTo(DEFAULT_NAME));

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        Assertions.assertEquals(productList.size(), databaseSizeBeforeCreate);
    }


    @Test
    public void deleteProduct() {

        // Initialize the database
        productRepository.save(product);
        int databaseSizeBeforeDelete = productRepository.findAll().size();

        given()
                .when().delete("/api/products/"+ product.getId())
                .then()
                .statusCode(200);

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        Assertions.assertEquals(productList.size(), databaseSizeBeforeDelete - 1);
    }

}
