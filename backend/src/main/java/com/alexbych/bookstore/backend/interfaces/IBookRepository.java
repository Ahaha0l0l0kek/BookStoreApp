package com.alexbych.bookstore.backend.interfaces;

import com.alexbych.bookstore.model.Book;

import java.util.List;

public interface IBookRepository {
    List<Book> getAllBooks();

    Book getBookById(long bookId);

    Book createBook(Book book);

    Book updateBook(Book book);

    void deleteBook(long bookId);
}
