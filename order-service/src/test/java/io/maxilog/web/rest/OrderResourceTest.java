package io.maxilog.web.rest;

import com.google.common.collect.Iterators;
import io.maxilog.domain.Order;
import io.maxilog.domain.enumeration.OrderStatus;
import io.maxilog.repository.OrderRepository;
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
class OrderResourceTest {


    private static final BigDecimal DEFAULT_TOTAL_PRICE = BigDecimal.valueOf(100);
    private static final BigDecimal UPDATED_TOTAL_PRICE = BigDecimal.valueOf(150);

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.SHIPPED;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.DELIVERED;

    @Inject
    OrderRepository orderRepository;


    private Order order;

    @BeforeEach
    void setUp(){
        orderRepository.deleteAll();
        order = createEntity();
    }


    public static Order createEntity() {
        return new Order(DEFAULT_TOTAL_PRICE, DEFAULT_STATUS);
    }

    @Test
    public void createOrder() {

        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        given()
                .contentType(JSON)
                .body(this.order)
                .when().post("/api/orders")
                .then()
                .statusCode(201);

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        Assertions.assertEquals(orderList.size(), databaseSizeBeforeCreate + 1);
        Order order = orderList.get(orderList.size() - 1);
        Assertions.assertEquals(order.getTotalPrice(), DEFAULT_TOTAL_PRICE);
    }

    @Test
    public void createOrderWithExistingId() {

        int databaseSizeBeforeCreate = orderRepository.findAll().size();
        order.setId(10L);

        given()
                .contentType(JSON)
                .body(order)
                .when().post("/api/orders")
                .then()
                .statusCode(400);

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        Assertions.assertEquals(orderList.size(), databaseSizeBeforeCreate);
    }

    @Test
    public void getAllOrders() {
        // Initialize the database
        orderRepository.save(order);

        given()
                .when().get("/api/orders")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("content[0].total_price", equalTo(DEFAULT_TOTAL_PRICE));
    }

    @Test
    public void getOrderById() {
        // Initialize the database
        orderRepository.save(order);

        given()
                .when().get("/api/orders/{id}", order.getId())
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("total_price", equalTo(DEFAULT_TOTAL_PRICE));

    }

    @Test
    public void getNotExistingOrderById() {

        given()
                .when().get("/api/orders/{id}", "notExistingId")
                .then()
                .statusCode(404);

    }

    @Test
    public void updateOrder() {

        // Initialize the database
        orderRepository.save(order);
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        order.setTotalPrice(UPDATED_TOTAL_PRICE);

        given()
                .contentType(JSON)
                .body(order)
                .when().put("/api/orders")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("total_price", equalTo(UPDATED_TOTAL_PRICE));

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        Assertions.assertEquals(orderList.size(), databaseSizeBeforeCreate);
    }


    @Test
    public void deleteOrder() {

        // Initialize the database
        orderRepository.save(order);
        int databaseSizeBeforeDelete = orderRepository.findAll().size();


        given()
                .when().delete("/api/orders/"+ order.getId())
                .then()
                .statusCode(200);

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        Assertions.assertEquals(orderList.size(), databaseSizeBeforeDelete - 1);
    }

}
