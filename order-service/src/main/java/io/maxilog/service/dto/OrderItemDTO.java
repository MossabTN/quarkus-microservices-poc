package io.maxilog.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class OrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long quantity;

    private ProductDTO product;

    public OrderItemDTO() {}

    public OrderItemDTO(Long quantity, ProductDTO product) {
        this.quantity = quantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemDTO)) return false;
        OrderItemDTO orderItem = (OrderItemDTO) o;
        return Objects.equals(getId(), orderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }
}