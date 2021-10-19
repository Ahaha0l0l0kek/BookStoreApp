package com.alexbych.bookstore.backend.hibernate.repository;

import com.alexbych.bookstore.backend.interfaces.IClientRepository;
import com.alexbych.bookstore.backend.hibernate.HibernateSessionFactory;
import com.alexbych.bookstore.model.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class HibernateClientRepository implements IClientRepository {
    @Override
    public List<Client> getAllClients() {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From Client").list();
    }

    @Override
    public Client getClientById(long userId) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Client.class, userId);
    }

    @Override
    public Client createClient(Client client) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(client);
            tx1.commit();
        }
        return client;
    }

    @Override
    public Client updateClient(Client client) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(client);
            tx1.commit();
        }
        return client;
    }

    @Override
    public void deleteClient(long clientId) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            Client client = session.load(Client.class, clientId);
            session.delete(client);
            tx1.commit();
        }
    }
}
