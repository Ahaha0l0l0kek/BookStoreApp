package com.alexbych.bookstore.backend.hibernate.repository;

import com.alexbych.bookstore.backend.interfaces.IRoleRepository;
import com.alexbych.bookstore.backend.hibernate.HibernateSessionFactory;
import com.alexbych.bookstore.model.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class HibernateRoleRepository implements IRoleRepository {
    @Override
    public Role findByName(String name) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From Role where name=?1", Role.class).setParameter(1, name).getSingleResult();
    }
}
