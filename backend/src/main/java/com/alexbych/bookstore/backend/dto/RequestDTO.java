package com.alexbych.bookstore.backend.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class RequestDTO {

    private long id;
    private long bookId;
    private int quantity;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfRequest;

    private boolean isCompleted;
}
