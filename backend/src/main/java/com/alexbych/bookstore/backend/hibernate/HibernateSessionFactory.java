package com.alexbych.bookstore.backend.hibernate;

import com.alexbych.bookstore.model.Book;
import com.alexbych.bookstore.model.Client;
import com.alexbych.bookstore.model.Order;
import com.alexbych.bookstore.model.Request;
import com.alexbych.bookstore.model.Role;
import com.alexbych.bookstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public final class HibernateSessionFactory {
    private static SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger(HibernateSessionFactory.class);

    private HibernateSessionFactory() { }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Book.class);
                configuration.addAnnotatedClass(Client.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(Request.class);
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return sessionFactory;
    }
}
