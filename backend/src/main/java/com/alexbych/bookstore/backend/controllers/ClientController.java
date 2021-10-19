package com.alexbych.bookstore.backend.controllers;

import com.alexbych.bookstore.backend.dto.ClientDTO;
import com.alexbych.bookstore.backend.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/findAll")
    public ResponseEntity<Object> getAllClients() {
        return new ResponseEntity<>(
                clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable long id) {
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO request) {
        return new ResponseEntity<>(clientService.createClient(request), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateClient(@RequestBody ClientDTO request) {
        clientService.updateClient(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
