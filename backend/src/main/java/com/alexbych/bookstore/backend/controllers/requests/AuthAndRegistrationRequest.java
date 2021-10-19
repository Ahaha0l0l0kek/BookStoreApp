package com.alexbych.bookstore.backend.controllers.requests;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class AuthAndRegistrationRequest {

    @NotNull
    private String login;

    @NotNull
    private String password;
}
