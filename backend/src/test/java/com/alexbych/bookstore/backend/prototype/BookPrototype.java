package com.alexbych.bookstore.backend.prototype;

import com.alexbych.bookstore.backend.dto.BookDTO;
import com.alexbych.bookstore.model.Book;

import java.time.LocalDate;

public class BookPrototype {

    public static Book aBook(){
         Book book = new Book();
        book.setId(1);
        book.setName("TestName");
        book.setAuthor("TestAuthor");
        book.setPrice(450);
        book.setDescription("TestDescription");
        book.setDateOfIssue(LocalDate.of(1988, 1, 11));
        book.setReceiptDate(LocalDate.of(2020, 3, 12));
        book.setAvailable(true);
        book.setStock(1);
        return book;
    }

    public static BookDTO aBookDTO(){
        return BookDTO.builder()
                .id(1)
                .name("TestName")
                .author("TestAuthor")
                .price(450)
                .description("TestDescription")
                .dateOfIssue(LocalDate.of(1988, 1, 11))
                .receiptDate(LocalDate.of(2020, 3, 12))
                .isAvailable(true)
                .stock(1).build();
    }
}
