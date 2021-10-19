package com.alexbych.bookstore.backend.interfaces;

import com.alexbych.bookstore.model.Order;

import java.util.List;

public interface IOrderRepository {
    List<Order> getAllOrders();

    Order getOrderById(long orderId);

    Order createOrder(Order order);

    Order updateOrder(Order order);

    void deleteOrder(long orderId);
}
