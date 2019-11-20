package io.maxilog.service.dto;

import io.maxilog.domain.enumeration.PaymentStatus;

import java.io.Serializable;
import java.util.Objects;

public class PaymentDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String paypalPaymentId;

    private PaymentStatus status;

    private OrderDTO order;

    public PaymentDTO() {}

    public PaymentDTO(String paypalPaymentId, PaymentStatus status, OrderDTO order) {
        this.paypalPaymentId = paypalPaymentId;
        this.status = status;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaypalPaymentId() {
        return paypalPaymentId;
    }

    public void setPaypalPaymentId(String paypalPaymentId) {
        this.paypalPaymentId = paypalPaymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentDTO)) return false;
        PaymentDTO payment = (PaymentDTO) o;
        return Objects.equals(getId(), payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paypalPaymentId='" + paypalPaymentId + '\'' +
                ", status=" + status +
                ", order=" + order +
                '}';
    }
}