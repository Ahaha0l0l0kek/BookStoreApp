package com.alexbych.bookstore.backend.converters;

import com.alexbych.bookstore.backend.dto.ClientDTO;
import com.alexbych.bookstore.model.Client;
import org.mapstruct.Mapper;

@Mapper
public interface IClientConverter {
    Client fromClientDtoToClient(ClientDTO clientDTO);
    ClientDTO fromClientToClientDto(Client client);
}
