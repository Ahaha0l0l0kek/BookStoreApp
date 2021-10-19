package com.alexbych.bookstore.backend.prototype;

import com.alexbych.bookstore.backend.dto.OrderDTO;
import com.alexbych.bookstore.model.Order;
import com.alexbych.bookstore.model.OrderStatus;

import java.time.LocalDate;

public class OrderPrototype {

    public static Order aOrder(){
        Order order = new Order();
        order.setId(1);
        order.setTotalPrice(900);
        order.setBookId(1);
        order.setClientId(1);
        order.setDateOfOrder(LocalDate.of(2020, 3, 12));
        order.setOrderStatus("NEW");
        order.setQuantity(2);
        return order;
    }

    public static OrderDTO aOrderDTO(){
        return OrderDTO.builder()
                .id(1)
                .totalPrice(900)
                .bookId(1)
                .clientId(1)
                .dateOfOrder(LocalDate.of(2020, 3, 12))
                .orderStatus(OrderStatus.NEW)
                .quantity(2).build();
    }
}
