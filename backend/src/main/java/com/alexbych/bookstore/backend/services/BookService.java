package com.alexbych.bookstore.backend.services;

import com.alexbych.bookstore.backend.converters.IBookConverter;
import com.alexbych.bookstore.backend.converters.IRequestConverter;
import com.alexbych.bookstore.backend.dto.BookDTO;
import com.alexbych.bookstore.backend.dto.RequestDTO;
import com.alexbych.bookstore.backend.interfaces.IBookRepository;
import com.alexbych.bookstore.backend.interfaces.IOrderRepository;
import com.alexbych.bookstore.backend.interfaces.IRequestRepository;
import com.alexbych.bookstore.model.Book;
import com.alexbych.bookstore.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final IBookRepository bookRepository;
    private final IOrderRepository orderRepository;
    private final IRequestRepository requestRepository;
    private final IBookConverter bookConverter;
    private final IRequestConverter requestConverter;

    @Value("${months}")
    private long months;

    public List<BookDTO> getAllBooks() {
        return bookRepository.getAllBooks().stream().map(bookConverter::fromBookToBookDto).collect(Collectors.toList());
    }

    public BookDTO getBookById(long bookId) {
        return bookConverter.fromBookToBookDto(bookRepository.getBookById(bookId));
    }

    public BookDTO createBook(BookDTO bookDTO) {
        bookRepository.createBook(bookConverter.fromBookDtoToBook(bookDTO));
        return bookDTO;
    }

    public BookDTO updateBook(BookDTO bookDTO) {
        bookRepository.updateBook(bookConverter.fromBookDtoToBook(bookDTO));
        return bookDTO;
    }

    public void deleteBook(long bookId) {
        bookRepository.deleteBook(bookId);
    }

    public boolean notAvailable(long id) {
        Book book = bookRepository.getBookById(id);
        if (book.isAvailable()) {
            book.setAvailable(false);
            bookRepository.updateBook(book);
            return true;
        } else return false;
    }

    public List<Order> getAllOrdersOfBook(long id) {
        List<Order> orders = orderRepository.getAllOrders();
        return orders != null ? orders.stream().filter(s -> s.getBookId() == id).collect(Collectors.toList()) : null;
    }

    public boolean bookToAvailable(long id, int quantity) {
        Book book = bookRepository.getBookById(id);
        if (!book.isAvailable()) {
            book.setAvailable(true);
            book.setStock(quantity);
            bookRepository.updateBook(book);
            return true;
        } else return false;
    }

    public List<BookDTO> getStaleBooks() {
        LocalDate month = LocalDate.now()
                .minusMonths(months);
        List<BookDTO> books = bookRepository.getAllBooks().stream().map(bookConverter::fromBookToBookDto).collect(Collectors.toList());
        return books.stream().filter(s -> s.getReceiptDate()
                        .isBefore(month))
                .collect(Collectors.toList());
    }

    public List<RequestDTO> getAllRequestsOfBook(long id) {
        List<RequestDTO> request = requestRepository.getAllRequests().stream().map(requestConverter::fromRequestToRequestDto).collect(Collectors.toList());
        return request.stream().filter(s -> s.getBookId() == id).collect(Collectors.toList());
    }
}
