package io.maxilog.service.dto;

import io.maxilog.domain.enumeration.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal totalPrice;

    private OrderStatus status;

    private LocalDateTime shipped;

    private PaymentDTO payment;

    private AddressDTO shipmentAddress;

    private Set<OrderItemDTO> orderItems;

    private CustomerDTO customer;

    public OrderDTO() {}

    public OrderDTO(BigDecimal totalPrice, OrderStatus status,
                    LocalDateTime shipped, PaymentDTO payment, AddressDTO shipmentAddress,
                    Set<OrderItemDTO> orderItems, CustomerDTO customer) {
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

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    public AddressDTO getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(AddressDTO shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public Set<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;
        OrderDTO user = (OrderDTO) o;
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
