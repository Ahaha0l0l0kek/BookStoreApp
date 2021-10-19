package com.alexbych.bookstore.backend.services;

import com.alexbych.bookstore.backend.converters.IClientConverter;
import com.alexbych.bookstore.backend.dto.ClientDTO;
import com.alexbych.bookstore.backend.interfaces.IClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService {

    private final IClientRepository clientRepository;
    private final IClientConverter clientConverter;

    public List<ClientDTO> getAllClients() {
        return clientRepository.getAllClients().stream().map(clientConverter::fromClientToClientDto).collect(Collectors.toList());
    }

    public ClientDTO getClientById(long userId) {
        return clientConverter.fromClientToClientDto(clientRepository.getClientById(userId));
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
        clientRepository.createClient(clientConverter.fromClientDtoToClient(clientDTO));
        return clientDTO;
    }

    public ClientDTO updateClient(ClientDTO clientDTO) {
        clientRepository.updateClient(clientConverter.fromClientDtoToClient(clientDTO));
        return clientDTO;
    }

    public void deleteClient(long clientId) {
        clientRepository.deleteClient(clientId);
    }
}
