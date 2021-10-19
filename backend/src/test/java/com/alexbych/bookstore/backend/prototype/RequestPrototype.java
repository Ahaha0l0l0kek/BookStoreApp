package com.alexbych.bookstore.backend.prototype;

import com.alexbych.bookstore.backend.dto.RequestDTO;
import com.alexbych.bookstore.model.Request;

import java.time.LocalDate;

public class RequestPrototype {

    public static Request aRequest(){
        Request request = new Request();
        request.setId(1);
        request.setBookId(1);
        request.setQuantity(1);
        request.setDateOfRequest(LocalDate.of(2020, 3, 12));
        request.setCompleted(false);
        return request;
    }

    public static RequestDTO aRequestDTO(){
        return RequestDTO.builder()
                .id(1)
                .bookId(1)
                .quantity(1)
                .dateOfRequest(LocalDate.of(2020, 3, 12))
                .isCompleted(false).build();
    }
}
