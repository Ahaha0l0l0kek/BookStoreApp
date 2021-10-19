package com.alexbych.bookstore.backend.hibernate.repository;

import com.alexbych.bookstore.backend.interfaces.IOrderRepository;
import com.alexbych.bookstore.backend.hibernate.HibernateSessionFactory;
import com.alexbych.bookstore.model.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class HibernateOrderRepository implements IOrderRepository {
    @Override
    public List<Order> getAllOrders() {
        return HibernateSessionFactory.getSessionFactory()
                .openSession().createQuery("From Order").list();
    }

    @Override
    public Order getOrderById(long orderId) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Order.class, orderId);
    }

    @Override
    public Order createOrder(Order order) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(order);
            tx1.commit();
        }
        return order;
    }

    @Override
    public Order updateOrder(Order order) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(order);
            tx1.commit();
        }
        return order;
    }

    @Override
    public void deleteOrder(long orderId) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            Order order = session.load(Order.class, orderId);
            session.delete(order);
            tx1.commit();
        }
    }
}
