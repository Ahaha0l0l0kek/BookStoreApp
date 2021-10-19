package com.alexbych.bookstore.backend.controllers;

import com.alexbych.bookstore.backend.config.jwt.JwtProvider;
import com.alexbych.bookstore.backend.controllers.requests.AuthAndRegistrationRequest;
import com.alexbych.bookstore.backend.controllers.responses.AuthResponse;
import com.alexbych.bookstore.backend.services.UserService;
import com.alexbych.bookstore.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody AuthAndRegistrationRequest request) {
        User u = new User();
        u.setPassword(request.getPassword());
        u.setLogin(request.getLogin());
        return new ResponseEntity<>(userService.saveUser(u), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthAndRegistrationRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }

    @PatchMapping("/setAdmin")
    public ResponseEntity<Void> setAdmin(@RequestParam String login) {
        userService.update(login);
        return ResponseEntity.ok().build();
    }
}
