package com.alexbych.bookstore.backend.hibernate.repository;

import com.alexbych.bookstore.backend.interfaces.IRequestRepository;
import com.alexbych.bookstore.backend.hibernate.HibernateSessionFactory;
import com.alexbych.bookstore.model.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class HibernateRequestRepository implements IRequestRepository {
    @Override
    public List<Request> getAllRequests() {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From Request").list();
    }

    @Override
    public Request getRequestById(long requestId) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Request.class, requestId);
    }

    @Override
    public Request createRequest(Request request) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(request);
            tx1.commit();
        }
        return request;
    }

    @Override
    public Request updateRequest(Request request) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(request);
            tx1.commit();
        }
        return request;
    }

    @Override
    public void deleteRequest(long requestId) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            Request request = session.load(Request.class, requestId);
            session.delete(request);
            tx1.commit();
        }
    }
}
