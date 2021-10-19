package com.alexbych.bookstore.backend.jdbc.repository;

import com.alexbych.bookstore.backend.interfaces.IOrderRepository;
import com.alexbych.bookstore.model.Order;
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
public class JDBCOrderRepository implements IOrderRepository {

    @Autowired
    private Connection connection;

    private static final Logger logger = LogManager.getLogger(JDBCOrderRepository.class);

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM orders");
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setTotalPrice(resultSet.getFloat("total_price"));
                order.setBookId(resultSet.getLong("book_id"));
                order.setClientId(resultSet.getLong("client_id"));
                order.setDateOfOrder(resultSet.getDate("date_of_order").toLocalDate());
                order.setOrderStatus(resultSet.getString("order_status"));
                order.setQuantity(resultSet.getInt("quantity"));
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return orders;
    }

    @Override
    public Order getOrderById(long orderId) {
        ResultSet resultSet;
        Order order = new Order();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM orders WHERE id = " + orderId);
            while (resultSet.next()) {
                order.setId(resultSet.getLong("id"));
                order.setTotalPrice(resultSet.getFloat("total_price"));
                order.setBookId(resultSet.getLong("book_id"));
                order.setClientId(resultSet.getLong("client_id"));
                order.setDateOfOrder(resultSet.getDate("date_of_order").toLocalDate());
                order.setOrderStatus(resultSet.getString("order_status"));
                order.setQuantity(resultSet.getInt("quantity"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return order;
    }

    @Override
    public Order createOrder(Order order) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO orders(total_price, book_id," +
                " client_id, date_of_order, order_status, quantity) VALUES(?, ?, ?, ?, ?, ?)")) {

            statement.setFloat(1, order.getTotalPrice());
            statement.setLong(2, order.getBookId());
            statement.setLong(3, order.getClientId());
            statement.setObject(4, order.getDateOfOrder());
            statement.setString(5, order.getOrderStatus());
            statement.setInt(6, order.getQuantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return order;
    }

    @Override
    public Order updateOrder(Order order) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE orders SET total_price=?, book_id=?," +
                " client_id=?, date_of_order=?, order_status=?, quantity=? WHERE id=?")) {

            statement.setFloat(1, order.getTotalPrice());
            statement.setLong(2, order.getBookId());
            statement.setLong(3, order.getClientId());
            statement.setObject(4, order.getDateOfOrder());
            statement.setString(5, order.getOrderStatus());
            statement.setInt(6, order.getQuantity());
            statement.setLong(7, order.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return order;
    }

    @Override
    public void deleteOrder(long orderId) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id=?")) {

            statement.setLong(1, orderId);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
