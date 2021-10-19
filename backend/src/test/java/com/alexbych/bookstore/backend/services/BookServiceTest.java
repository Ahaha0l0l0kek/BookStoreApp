package com.alexbych.bookstore.backend.services;

import com.alexbych.bookstore.backend.converters.IBookConverter;
import com.alexbych.bookstore.backend.converters.IRequestConverter;
import com.alexbych.bookstore.backend.dto.BookDTO;
import com.alexbych.bookstore.backend.dto.RequestDTO;
import com.alexbych.bookstore.backend.interfaces.IBookRepository;
import com.alexbych.bookstore.backend.interfaces.IOrderRepository;
import com.alexbych.bookstore.backend.interfaces.IRequestRepository;
import com.alexbych.bookstore.backend.prototype.BookPrototype;
import com.alexbych.bookstore.backend.prototype.OrderPrototype;
import com.alexbych.bookstore.backend.prototype.RequestPrototype;
import com.alexbych.bookstore.model.Book;
import com.alexbych.bookstore.model.Order;
import com.alexbych.bookstore.model.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @MockBean
    private IBookRepository bookRepository;

    @MockBean
    private IOrderRepository orderRepository;

    @MockBean
    private IRequestRepository requestRepository;

    @MockBean
    private IBookConverter bookConverter;

    @Autowired
    private BookService bookService;

    @MockBean
    private IRequestConverter requestConverter;

    @Test
    public void getAllBooks(){
        Book aBook = BookPrototype.aBook();
        List<BookDTO> expected = new ArrayList<>();
        expected.add(BookPrototype.aBookDTO());
        when(bookRepository.getAllBooks()).thenReturn(new ArrayList<>(List.of(aBook)));
        when(bookConverter.fromBookToBookDto(aBook)).thenReturn(BookPrototype.aBookDTO());
        List<BookDTO> allBooks = this.bookService.getAllBooks();
        assertThat(allBooks).isEqualTo(expected);
    }


    @Test
    public void getBookById(){
        Book aBook = BookPrototype.aBook();
        BookDTO expected = BookPrototype.aBookDTO();
        when(bookRepository.getBookById(anyLong())).thenReturn(aBook);
        when(bookConverter.fromBookToBookDto(aBook)).thenReturn(BookPrototype.aBookDTO());
        BookDTO actual = this.bookService.getBookById(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void createBook(){
        Book aBook = BookPrototype.aBook();
        BookDTO expected = BookPrototype.aBookDTO();
        when(bookRepository.createBook(any())).thenReturn(aBook);
        when(bookConverter.fromBookToBookDto(aBook)).thenReturn(BookPrototype.aBookDTO());
        BookDTO actual = bookService.createBook(BookPrototype.aBookDTO());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateBook(){
        Book aBook = BookPrototype.aBook();
        BookDTO expected = BookPrototype.aBookDTO();
        when(bookRepository.updateBook(any())).thenReturn(aBook);
        when(bookConverter.fromBookToBookDto(aBook)).thenReturn(BookPrototype.aBookDTO());
        BookDTO actual = bookService.updateBook(BookPrototype.aBookDTO());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void deleteBook(){
        bookService.deleteBook(BookPrototype.aBook().getId());
        verify(bookRepository, times(1)).deleteBook(BookPrototype.aBook().getId());
    }

    @Test
    public void notAvailable(){
        Book aBook = BookPrototype.aBook();
        Book notAvailableBook = BookPrototype.aBook();
        notAvailableBook.setAvailable(false);
        when(bookRepository.getBookById(anyLong())).thenReturn(aBook);
        when(bookRepository.updateBook(aBook)).thenReturn(notAvailableBook);
        assertThat(bookService.notAvailable(aBook.getId())).isTrue();
    }

    @Test
    public void getAllOrdersOfBook(){
        Order aOrder = OrderPrototype.aOrder();
        List<Order> expected = new ArrayList<>();
        expected.add(aOrder);
        when(orderRepository.getAllOrders()).thenReturn(new ArrayList<>(List.of(aOrder)));
        List<Order> actual = bookService.getAllOrdersOfBook(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void bookToAvailable(){
        Book notAvailableBook = BookPrototype.aBook();
        notAvailableBook.setAvailable(false);
        Book aBook = BookPrototype.aBook();
        when(bookRepository.getBookById(anyLong())).thenReturn(notAvailableBook);
        when(bookRepository.updateBook(notAvailableBook)).thenReturn(aBook);
        assertThat(bookService.bookToAvailable(notAvailableBook.getId(), anyInt())).isTrue();
    }

    @Test
    public void getStaleBooks(){
        Book aBook = BookPrototype.aBook();
        List<BookDTO> expected = new ArrayList<>();
        expected.add(BookPrototype.aBookDTO());
        when(bookRepository.getAllBooks()).thenReturn(new ArrayList<>(List.of(aBook)));
        when(bookConverter.fromBookToBookDto(aBook)).thenReturn(BookPrototype.aBookDTO());
        List<BookDTO> allBooks = this.bookService.getAllBooks();
        assertThat(allBooks).isEqualTo(expected);
    }

    @Test
    public void getAllRequestsOfBook(){
        Request aRequest = RequestPrototype.aRequest();
        RequestDTO aRequestDTO = RequestPrototype.aRequestDTO();
        List<RequestDTO> expected = new ArrayList<>();
        expected.add(aRequestDTO);
        when(requestRepository.getAllRequests()).thenReturn(new ArrayList<>(List.of(aRequest)));
        when(requestConverter.fromRequestToRequestDto(aRequest)).thenReturn(RequestPrototype.aRequestDTO());
        List<RequestDTO> actual = bookService.getAllRequestsOfBook(1);
        assertThat(actual).isEqualTo(expected);
    }
}
