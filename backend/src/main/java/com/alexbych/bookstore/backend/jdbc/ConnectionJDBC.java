package com.alexbych.bookstore.backend.jdbc;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class ConnectionJDBC {

    @Value("${db.host}")
    private String host;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @SneakyThrows
    @Bean
    public Connection connection() {
        return DriverManager.getConnection(host, username, password);
    }
}
