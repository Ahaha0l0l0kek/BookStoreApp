package com.alexbych.bookstore.backend.controllers;

import com.alexbych.bookstore.backend.dto.RequestDTO;
import com.alexbych.bookstore.backend.services.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/findAll")
    public ResponseEntity<Object> getAllRequests() {
        return new ResponseEntity<>(
                requestService.getAllRequests(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<RequestDTO> getRequestById(@PathVariable long id) {
        return new ResponseEntity<>(requestService.getRequestById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO request) {
        return new ResponseEntity<>(requestService.createRequest(request), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateRequest(@RequestBody RequestDTO request) {
        requestService.updateRequest(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteRequest(@PathVariable long id) {
        requestService.deleteRequest(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PatchMapping("/changeStatusToCompleted/{id}")
    public void changeStatusToCompleted(@PathVariable long id) {
        requestService.changeStatusToCompleted(id);
    }
}
