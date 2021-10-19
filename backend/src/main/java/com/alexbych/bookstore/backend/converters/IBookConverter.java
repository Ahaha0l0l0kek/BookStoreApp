package com.alexbych.bookstore.backend.converters;

import com.alexbych.bookstore.backend.dto.BookDTO;
import com.alexbych.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper
public interface IBookConverter {
     Book fromBookDtoToBook(BookDTO bookDTO);
     BookDTO fromBookToBookDto(Book book);
}
