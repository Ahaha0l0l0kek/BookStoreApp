package com.alexbych.bookstore.backend.interfaces;

import com.alexbych.bookstore.model.User;

public interface IUserRepository {
    User findByLogin(String login);
    User save(User user);
    User update(User user);
}
