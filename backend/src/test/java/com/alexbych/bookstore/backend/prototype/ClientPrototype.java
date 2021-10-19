package com.alexbych.bookstore.backend.prototype;

import com.alexbych.bookstore.backend.dto.ClientDTO;
import com.alexbych.bookstore.model.Client;

public class ClientPrototype {

    public static Client aClient(){
        Client client = new Client();
        client.setId(1);
        client.setName("TestNameClient");
        client.setPhoneNumber("0000000000");
        return client;
    }

    public static ClientDTO aClientDTO(){
        return ClientDTO.builder()
                .id(1)
                .name("TestNameClient")
                .phoneNumber("0000000000").build();
    }
}
