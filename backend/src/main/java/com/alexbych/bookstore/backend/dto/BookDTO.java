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
public class BookDTO {

    private long id;
    private String name;
    private String author;
    private float price;
    private String description;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfIssue;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate receiptDate;

    private boolean isAvailable;
    private int stock;
}
