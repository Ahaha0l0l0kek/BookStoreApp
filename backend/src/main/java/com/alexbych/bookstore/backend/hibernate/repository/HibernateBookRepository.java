package com.alexbych.bookstore.backend.hibernate.repository;

import com.alexbych.bookstore.backend.interfaces.IBookRepository;
import com.alexbych.bookstore.backend.hibernate.HibernateSessionFactory;
import com.alexbych.bookstore.model.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class HibernateBookRepository implements IBookRepository {


    @Override
    public List<Book> getAllBooks() {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From Book").list();
    }

    @Override
    public Book getBookById(long bookId) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Book.class, bookId);
    }

    @Override
    public Book createBook(Book book) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(book);
            tx1.commit();
        } catch (HibernateException e) {

        }
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(book);
            tx1.commit();
        }
        return book;
    }

    @Override
    public void deleteBook(long bookId) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            Book book = session.load(Book.class, bookId);
            session.delete(book);
            tx1.commit();
        }
    }
}
