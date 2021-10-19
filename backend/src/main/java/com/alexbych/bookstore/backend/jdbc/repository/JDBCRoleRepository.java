package com.alexbych.bookstore.backend.jdbc.repository;

import com.alexbych.bookstore.backend.interfaces.IRoleRepository;
import com.alexbych.bookstore.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class JDBCRoleRepository implements IRoleRepository {

    @Autowired
    private Connection connection;

    private static final Logger logger = LogManager.getLogger(JDBCRoleRepository.class);

    @Override
    public Role findByName(String name) {
        ResultSet resultSet;
        Role role = new Role();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM roles WHERE name = " + name);
            while (resultSet.next()) {
                role.setId(resultSet.getInt("id"));
                role.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return role;
    }
}
