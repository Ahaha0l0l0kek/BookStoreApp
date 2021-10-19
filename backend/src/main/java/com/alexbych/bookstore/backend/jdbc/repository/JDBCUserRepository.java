package com.alexbych.bookstore.backend.jdbc.repository;

import com.alexbych.bookstore.backend.interfaces.IUserRepository;
import com.alexbych.bookstore.model.Role;
import com.alexbych.bookstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class JDBCUserRepository implements IUserRepository {

    @Autowired
    private Connection connection;

    private static final Logger logger = LogManager.getLogger(JDBCUserRepository.class);

    @Override
    public User findByLogin(String login) {
        ResultSet resultSet;
        User user = new User();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM users WHERE login = " + login);
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole((Role) resultSet.getObject("role_id"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    @Override
    public User save(User user) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users(login, password, role_id)" +
                " VALUES(?, ?, ?)")) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(2, user.getRole().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE clients SET login=?, password=?, role_id=? WHERE id=?")) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(2, user.getRole().getId());

            statement.setInt(2, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }
}
