package com.alexbych.bookstore.backend.hibernate.repository;

import com.alexbych.bookstore.backend.interfaces.IUserRepository;
import com.alexbych.bookstore.backend.hibernate.HibernateSessionFactory;
import com.alexbych.bookstore.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class HibernateUserRepository implements IUserRepository {
    @Override
    public User findByLogin(String login) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From User where login=?1", User.class).setParameter(1, login).getSingleResult();
    }

    @Override
    public User save(User user) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
        } catch (HibernateException e) {

        }
        return user;
    }

    @Override
    public User update(User user) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(user);
            tx1.commit();
        } catch (HibernateException e) {

        }
        return user;
    }
}
