package com.haulmont.creditSystem.dao;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class ClientDao implements IDao<Client>{
    @Override
    public Client findById(UUID id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }

    @Override
    public void create(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(client);
        transaction.commit();
    }

    @Override
    public void update(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(client);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(client);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = (List<Client>)HibernateUtil.getSessionFactory().openSession().createQuery("from Client").list();
        return clients;
    }
}
