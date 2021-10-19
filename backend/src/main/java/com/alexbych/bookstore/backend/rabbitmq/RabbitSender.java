package com.alexbych.bookstore.backend.rabbitmq;

import com.alexbych.bookstore.backend.services.BookService;
import com.alexbych.bookstore.backend.services.ClientService;
import com.alexbych.bookstore.backend.services.OrderService;
import com.alexbych.bookstore.backend.services.RequestService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    private final BookService bookService;
    private final OrderService orderService;
    private final RequestService requestService;
    private final ClientService clientService;


    @Scheduled(fixedDelay = 30000)
    public void send() {
        rabbitTemplate.convertAndSend(exchange, routingkey, "books{ \"books\" : " + new Gson().toJson(bookService.getAllBooks()) + "}");
        rabbitTemplate.convertAndSend(exchange, routingkey, "orders{ \"orders\" : " + new Gson().toJson(orderService.getAllOrders()) + "}");
        rabbitTemplate.convertAndSend(exchange, routingkey, "requests{ \"requests\" : " + new Gson().toJson(requestService.getAllRequests()) + "}");
        rabbitTemplate.convertAndSend(exchange, routingkey, "clients{ \"clients\" : " + new Gson().toJson(clientService.getAllClients()) + "}");
    }
}
