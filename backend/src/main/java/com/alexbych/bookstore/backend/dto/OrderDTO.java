package com.alexbych.bookstore.backend.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.alexbych.bookstore.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class OrderDTO {

    private long id;
    private float totalPrice;
    private long bookId;
    private long clientId;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfOrder;

    private OrderStatus orderStatus;
    private int quantity;

    public void setOrderStatus(String orderStatus) {
        switch (orderStatus) {
            case "NEW" -> this.orderStatus = OrderStatus.NEW;
            case "COMPLETED" -> this.orderStatus = OrderStatus.COMPLETED;
            case "CANCELED" -> this.orderStatus = OrderStatus.CANCELED;
        }
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
