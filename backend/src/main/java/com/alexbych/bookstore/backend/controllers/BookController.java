package com.alexbych.bookstore.backend.controllers;

import com.alexbych.bookstore.backend.dto.BookDTO;
import com.alexbych.bookstore.backend.services.BookService;
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

@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final OrderService orderService;

    @GetMapping("/findAll")
    public ResponseEntity<Object> findAllBooks() {
        return new ResponseEntity<>(
                bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO request) {
        return new ResponseEntity<>(bookService.createBook(request), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateBook(@RequestBody BookDTO request) {
        bookService.updateBook(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PatchMapping("/toNotAvailable/{id}")
    public ResponseEntity<Void> notAvailable(@PathVariable long id) {
        bookService.notAvailable(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Object> getAllOrdersOfBook(@PathVariable long id) {
        return new ResponseEntity<>(
                bookService.getAllOrdersOfBook(id), HttpStatus.OK);
    }

    @PatchMapping("/bookToAvailable/{id}")
    public ResponseEntity<Void> bookToAvailable(@PathVariable long id, @RequestParam int quantity) {
        bookService.bookToAvailable(id, quantity);
        orderService.orderCompleted(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stale")
    public ResponseEntity<Object> getStaleBooks() {
        return new ResponseEntity<>(
                bookService.getStaleBooks(), HttpStatus.OK);
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<Object> getAllRequestsOfBook(@PathVariable long id) {
        return new ResponseEntity<>(
                bookService.getAllRequestsOfBook(id), HttpStatus.OK);
    }
}
