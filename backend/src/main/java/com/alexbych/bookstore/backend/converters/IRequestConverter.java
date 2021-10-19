package com.alexbych.bookstore.backend.converters;

import com.alexbych.bookstore.backend.dto.RequestDTO;
import com.alexbych.bookstore.model.Request;
import org.mapstruct.Mapper;

@Mapper
public interface IRequestConverter {
    Request fromRequestDtoToRequest(RequestDTO requestDTO);
    RequestDTO fromRequestToRequestDto(Request request);
}
