package com.alexbych.bookstore.backend.interfaces;

import com.alexbych.bookstore.model.Client;

import java.util.List;

public interface IClientRepository {
    List<Client> getAllClients();

    Client getClientById(long userId);

    Client createClient(Client client);

    Client updateClient(Client client);

    void deleteClient(long clientId);
}
