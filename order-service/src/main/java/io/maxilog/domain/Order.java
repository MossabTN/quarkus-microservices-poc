package io.maxilog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.maxilog.domain.enumeration.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Cacheable
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "shipped")
    private LocalDateTime shipped;

    @JsonIgnoreProperties("order")
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(unique = true)
    private Payment payment;

    @Embedded
    private Address shipmentAddress;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;

    @NotNull
    @Column(name = "customer", nullable = false)
    private String customer;

    public Order() {}

    public Order(@NotNull BigDecimal totalPrice, @NotNull OrderStatus status, @NotNull String customer) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.customer = customer;
    }

    public Order(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Order(BigDecimal totalPrice, OrderStatus status,
                 LocalDateTime shipped, Payment payment, Address shipmentAddress,
                 Set<OrderItem> orderItems, String customer) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.shipped = shipped;
        this.payment = payment;
        this.shipmentAddress = shipmentAddress;
        this.orderItems = orderItems;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getShipped() {
        return shipped;
    }

    public void setShipped(LocalDateTime shipped) {
        this.shipped = shipped;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Address getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(Address shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order user = (Order) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", shipped=" + shipped +
                ", payment=" + payment +
                ", shipmentAddress=" + shipmentAddress +
                ", orderItems=" + orderItems +
                ", customer=" + customer +
                '}';
    }
}
