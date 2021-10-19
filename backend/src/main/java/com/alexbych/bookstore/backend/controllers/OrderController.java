package com.alexbych.bookstore.backend.controllers;

import com.alexbych.bookstore.backend.dto.OrderDTO;
import com.alexbych.bookstore.backend.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/findAll")
    public ResponseEntity<Object> getAllOrders() {
        return new ResponseEntity<>(
                orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateOrder(@RequestBody OrderDTO request) {
        orderService.updateOrder(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PatchMapping("/changeOrderStatusToCanceled/{id}")
    public ResponseEntity<Void> changeOrderStatusToCanceled(@PathVariable long id) {
        orderService.changeOrderStatus(id, "CANCELED");
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/changeOrderStatus/{id}")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable long id, @RequestParam String status) {
        orderService.changeOrderStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fulfilled")
    public ResponseEntity<Object> fulfilledOrders(@RequestParam LocalDate firstDate, @RequestParam LocalDate lastDate) {
        return new ResponseEntity<>(
                orderService.fulfilledOrders(firstDate, lastDate), HttpStatus.OK);
    }

    @GetMapping("/countOfFulfilled")
    public int countOfFulfilled(@RequestParam LocalDate firstDate, @RequestParam LocalDate lastDate) {
        return orderService.countOfFulfilled(firstDate, lastDate);
    }

    @GetMapping("/totalPriceInTime")
    public float totalPriceInTime(@RequestParam LocalDate firstDate, @RequestParam LocalDate lastDate) {
        return orderService.totalPriceInTime(firstDate, lastDate);
    }

    @GetMapping("/details/{id}")
    public String detailsOfOrder(@PathVariable long id) {
        return orderService.detailsOfOrder(id);
    }
}
