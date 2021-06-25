package com.haulmont.creditSystem.dao;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class CreditDao implements IDao<Credit> {
    @Override
    public Credit findById(UUID id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Credit credit = session.get(Credit.class, id);
        session.close();
        return credit;
    }

    @Override
    public void create(Credit credit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(credit);
        transaction.commit();
    }

    @Override
    public void update(Credit credit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(credit);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Credit credit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(credit);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Credit> findAll() {
        List<Credit> credits = (List<Credit>)HibernateUtil.getSessionFactory().openSession().createQuery("from Credit").list();
        return credits;
    }
}
