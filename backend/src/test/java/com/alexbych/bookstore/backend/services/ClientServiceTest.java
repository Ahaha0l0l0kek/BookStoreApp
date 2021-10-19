package com.alexbych.bookstore.backend.services;

import com.alexbych.bookstore.backend.converters.IClientConverter;
import com.alexbych.bookstore.backend.dto.ClientDTO;
import com.alexbych.bookstore.backend.interfaces.IClientRepository;
import com.alexbych.bookstore.backend.prototype.ClientPrototype;
import com.alexbych.bookstore.model.Client;
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
public class ClientServiceTest {

    @MockBean
    private IClientRepository clientRepository;

    @MockBean
    private IClientConverter clientConverter;

    @Autowired
    private ClientService clientService;

    @Test
    public void getAllClients(){
        Client aClient = ClientPrototype.aClient();
        List<ClientDTO> expected = new ArrayList<>();
        expected.add(ClientPrototype.aClientDTO());
        when(clientRepository.getAllClients()).thenReturn(new ArrayList<>(List.of(aClient)));
        when(clientConverter.fromClientToClientDto(aClient)).thenReturn(ClientPrototype.aClientDTO());
        List<ClientDTO> allClients = this.clientService.getAllClients();
        assertThat(expected).isEqualTo(allClients);
    }

    @Test
    public void getClientById(){
        Client aClient = ClientPrototype.aClient();
        ClientDTO expected = ClientPrototype.aClientDTO();
        when(clientRepository.getClientById(anyLong())).thenReturn(aClient);
        when(clientConverter.fromClientToClientDto(aClient)).thenReturn(ClientPrototype.aClientDTO());
        ClientDTO actual = this.clientService.getClientById(anyLong());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void createClient(){
        Client aClient = ClientPrototype.aClient();
        ClientDTO expected = ClientPrototype.aClientDTO();
        when(clientRepository.createClient(any())).thenReturn(aClient);
        when(clientConverter.fromClientToClientDto(aClient)).thenReturn(ClientPrototype.aClientDTO());
        ClientDTO actual = this.clientService.createClient(ClientPrototype.aClientDTO());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateClient(){
        Client aClient = ClientPrototype.aClient();
        ClientDTO expected = ClientPrototype.aClientDTO();
        when(clientRepository.updateClient(any())).thenReturn(aClient);
        when(clientConverter.fromClientToClientDto(aClient)).thenReturn(ClientPrototype.aClientDTO());
        ClientDTO actual = this.clientService.updateClient(ClientPrototype.aClientDTO());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void deleteClient(){
        clientService.deleteClient(ClientPrototype.aClient().getId());
        verify(clientRepository, times(1)).deleteClient(ClientPrototype.aClient().getId());
    }
}
