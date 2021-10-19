package com.alexbych.bookstore.backend.services;

import com.alexbych.bookstore.backend.converters.IOrderConverter;
import com.alexbych.bookstore.backend.dto.OrderDTO;
import com.alexbych.bookstore.backend.interfaces.IBookRepository;
import com.alexbych.bookstore.backend.interfaces.IClientRepository;
import com.alexbych.bookstore.backend.interfaces.IOrderRepository;
import com.alexbych.bookstore.model.Book;
import com.alexbych.bookstore.model.Client;
import com.alexbych.bookstore.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final IOrderRepository orderRepository;
    private final IBookRepository bookRepository;
    private final IClientRepository clientRepository;
    private final IOrderConverter orderConverter;

    public List<OrderDTO> getAllOrders() {
        return orderRepository.getAllOrders().stream().map(orderConverter::fromOrderToOrderDto).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(long orderId) {
        return orderConverter.fromOrderToOrderDto(orderRepository.getOrderById(orderId));
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        orderRepository.createOrder(orderConverter.fromOrderDtoToOrder(orderDTO));
        return orderDTO;
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        orderRepository.updateOrder(orderConverter.fromOrderDtoToOrder(orderDTO));
        return orderDTO;
    }

    public void deleteOrder(long orderId) {
        orderRepository.deleteOrder(orderId);
    }

    public boolean changeOrderStatus(long id, String status) {
        Order order = orderRepository.getOrderById(id);
        if (!order.getOrderStatus().equals(status)) {
            order.setOrderStatus(status);
            orderRepository.updateOrder(order);
            return true;
        } else return false;
    }

    public List<OrderDTO> fulfilledOrders(LocalDate firstDate, LocalDate lastDate) {
        List<OrderDTO> orders = orderRepository.getAllOrders().stream().map(orderConverter::fromOrderToOrderDto).collect(Collectors.toList());
        return orders.stream().filter(s -> s.getDateOfOrder().isBefore(lastDate))
                        .filter(s -> s.getDateOfOrder().isAfter(firstDate))
                        .collect(Collectors.toList());
    }

    public int countOfFulfilled(LocalDate firstDate, LocalDate lastDate) {
        List<OrderDTO> orders = fulfilledOrders(firstDate, lastDate);
        return orders.size();
    }

    public float totalPriceInTime(LocalDate firstDate, LocalDate lastDate) {
        List<Order> orders = orderRepository.getAllOrders();
        return (float) (orders != null ? orders.stream().filter(s -> s.getDateOfOrder().isBefore(lastDate))
                .filter(s -> s.getDateOfOrder().isAfter(firstDate)).mapToDouble(Order::getTotalPrice).sum() : 0);
    }

    public boolean orderCompleted(long bookId) {
        List<Book> books = bookRepository.getAllBooks();
            long id = Objects.requireNonNull(books.stream().filter(s -> s.getId() == bookId).findFirst().orElse(null)).getId();
        Order order = orderRepository.getOrderById(id);
        if (!order.getOrderStatus().equals("COMPLETED")) {
            order.setOrderStatus("COMPLETED");
            orderRepository.updateOrder(order);
            return true;
        } else return false;
    }

    public String detailsOfOrder(long id) {
        Order order = orderRepository.getOrderById(id);
        List<Book> books = bookRepository.getAllBooks();
        List<Client> clients = clientRepository.getAllClients();
        String name = Objects.requireNonNull(books != null ? books.stream()
                .filter(s -> s.getId() == order.getBookId())
                .findFirst().orElse(null) : null).getName();
        String author = Objects.requireNonNull(books.stream().filter(s -> s.getId() == order.getBookId())
                .findFirst().orElse(null)).getAuthor();

        String clientName = Objects.requireNonNull(clients != null ? clients.stream()
                .filter(s -> s.getId() == order.getClientId())
                .findFirst().orElse(null) : null).getName();
        String phoneNumber = Objects.requireNonNull(clients.stream().filter(s -> s.getId() == order.getClientId())
                .findFirst().orElse(null)).getPhoneNumber();

        return id + " " + clientName + " " + phoneNumber + " "
                + name + " " + author;
    }
}
