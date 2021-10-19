package com.alexbych.bookstore.backend.jdbc.repository;

import com.alexbych.bookstore.backend.interfaces.IRequestRepository;
import com.alexbych.bookstore.model.Request;
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
public class JDBCRequestRepository implements IRequestRepository {

    @Autowired
    private Connection connection;

    private static final Logger logger = LogManager.getLogger(JDBCRequestRepository.class);

    @Override
    public List<Request> getAllRequests() {
        List<Request> requests = new ArrayList<>();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM requests");
            while (resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getLong("id"));
                request.setBookId(resultSet.getLong("book_id"));
                request.setQuantity(resultSet.getInt("quantity"));
                request.setDateOfRequest(resultSet.getDate("date_of_request").toLocalDate());
                request.setCompleted(resultSet.getBoolean("is_completed"));
                requests.add(request);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return requests;
    }

    @Override
    public Request getRequestById(long requestId) {
        ResultSet resultSet;
        Request request = new Request();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM requests WHERE id = " + requestId);
            while (resultSet.next()) {
                request.setId(resultSet.getLong("id"));
                request.setBookId(resultSet.getLong("book_id"));
                request.setQuantity(resultSet.getInt("quantity"));
                request.setDateOfRequest(resultSet.getDate("date_of_request").toLocalDate());
                request.setCompleted(resultSet.getBoolean("is_completed"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return request;
    }

    @Override
    public Request createRequest(Request request) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO requests(book_id, quantity," +
                " date_of_request, is_completed) VALUES(?, ?, ?, false)")) {

            statement.setLong(1, request.getBookId());
            statement.setInt(2, request.getQuantity());
            statement.setObject(3, request.getDateOfRequest());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return request;
    }

    @Override
    public Request updateRequest(Request request)  {
        try (PreparedStatement statement = connection
                .prepareStatement("UPDATE requests SET book_id=?, quantity=?," +
                        " date_of_request=?, is_completed=false WHERE id=?")) {

            statement.setLong(1, request.getBookId());
            statement.setInt(2, request.getQuantity());
            statement.setObject(3, request.getDateOfRequest());
            statement.setLong(4, request.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return request;
    }

    @Override
    public void deleteRequest(long requestId) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM requests WHERE id=?")) {

            statement.setLong(1, requestId);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
