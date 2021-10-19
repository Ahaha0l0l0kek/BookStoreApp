package com.alexbych.bookstore.backend.interfaces;

import com.alexbych.bookstore.model.Role;

public interface IRoleRepository {
    Role findByName(String name);
}
