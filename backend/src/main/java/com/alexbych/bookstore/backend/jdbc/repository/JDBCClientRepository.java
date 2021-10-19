package com.alexbych.bookstore.backend.jdbc.repository;

import com.alexbych.bookstore.backend.interfaces.IClientRepository;
import com.alexbych.bookstore.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCClientRepository implements IClientRepository {

    @Autowired
    private Connection connection;

    private static final Logger logger = LogManager.getLogger(JDBCClientRepository.class);

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM clients");
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                client.setPhoneNumber(resultSet.getString("phone_number"));
                clients.add(client);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return clients;
    }

    @Override
    public Client getClientById(long userId) {
        ResultSet resultSet;
        Client client = new Client();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM clients WHERE id = " + userId);
            while (resultSet.next()) {
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                client.setPhoneNumber(resultSet.getString("phone_number"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return client;
    }

    @Override
    public Client createClient(Client client) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO clients(name, phone_number)" +
                " VALUES(?, ?)")) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getPhoneNumber());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return client;
    }

    @Override
    public Client updateClient(Client client) {
        try (PreparedStatement statement = connection.
                prepareStatement("UPDATE clients SET name=?, phone_number=? WHERE id=?")) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getPhoneNumber());
            statement.setLong(3, client.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return client;
    }

    @Override
    public void deleteClient(long clientId) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM clients WHERE id=?")) {

            statement.setLong(1, clientId);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
