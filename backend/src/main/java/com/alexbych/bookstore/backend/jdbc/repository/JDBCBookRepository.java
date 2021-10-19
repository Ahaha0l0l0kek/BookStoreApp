package com.alexbych.bookstore.backend.jdbc.repository;

import com.alexbych.bookstore.backend.interfaces.IBookRepository;
import com.alexbych.bookstore.model.Book;
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
public class JDBCBookRepository implements IBookRepository {

    @Autowired
    private Connection connection;

    private static final Logger logger = LogManager.getLogger(JDBCBookRepository.class);

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM books");
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setName(resultSet.getString("name"));
                book.setAuthor(resultSet.getString("author"));
                book.setPrice(resultSet.getFloat("price"));
                book.setDescription(resultSet.getString("description"));
                book.setDateOfIssue(resultSet.getDate("date_of_issue").toLocalDate());
                book.setReceiptDate(resultSet.getDate("receipt_date").toLocalDate());
                book.setAvailable(resultSet.getBoolean("availability"));
                book.setStock(resultSet.getInt("stock"));
                books.add(book);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return books;
    }

    @Override
    public Book getBookById(long bookId) {
        ResultSet resultSet;
        Book book = new Book();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM books WHERE id = " + bookId);

            while (resultSet.next()) {
                book.setId(resultSet.getLong("id"));
                book.setName(resultSet.getString("name"));
                book.setAuthor(resultSet.getString("author"));
                book.setPrice(resultSet.getFloat("price"));
                book.setDescription(resultSet.getString("description"));
                book.setDateOfIssue(resultSet.getDate("date_of_issue").toLocalDate());
                book.setReceiptDate(resultSet.getDate("receipt_date").toLocalDate());
                book.setAvailable(resultSet.getBoolean("availability"));
                book.setStock(resultSet.getInt("stock"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return book;
    }

    @Override
    public Book createBook(Book book) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO books(name, author, price," +
                " description, date_of_issue, availability, stock, receipt_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setFloat(3, book.getPrice());
            statement.setString(4, book.getDescription());
            statement.setObject(5, book.getDateOfIssue());
            statement.setBoolean(6, book.isAvailable());
            statement.setInt(7, book.getStock());
            statement.setObject(8, book.getReceiptDate());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE books SET name=?, author=?, price=?," +
                " description=?, receipt_date=?, date_of_issue=?, availability=?, stock=? WHERE id=?")) {

            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setFloat(3, book.getPrice());
            statement.setString(4, book.getDescription());
            statement.setObject(5, book.getDateOfIssue());
            statement.setObject(6, book.getReceiptDate());
            statement.setBoolean(7, book.isAvailable());
            statement.setInt(8, book.getStock());
            statement.setLong(9, book.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        return book;
    }

    @Override
    public void deleteBook(long bookId) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE id=?")) {

            statement.setLong(1, bookId);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
