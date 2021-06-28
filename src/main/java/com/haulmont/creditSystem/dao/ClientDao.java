package com.haulmont.creditSystem.dao;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.UUID;

public class ClientDao extends BankDao<Client> {
    @Override
    public Client findById(UUID id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }

    @Override
    public List<Client> findAll() {
        //Session session = HibernateUtil.getSessionFactory().openSession();
        List<Client> clients = (List<Client>)HibernateUtil.getSessionFactory().openSession().createQuery("from Client").list();
        //List<Client> clients = session.createQuery("from Client").list();
        //session.close();
        return clients;
    }
}
