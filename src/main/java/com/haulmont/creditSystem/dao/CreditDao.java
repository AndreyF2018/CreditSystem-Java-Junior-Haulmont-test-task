package com.haulmont.creditSystem.dao;

import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class CreditDao extends BankDao<Credit> {
    @Override
    public Credit findById(UUID id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Credit credit = session.get(Credit.class, id);
        session.close();
        return credit;
    }

    @Override
    public List<Credit> findAll() {
        //Session session = HibernateUtil.getSessionFactory().openSession();
        List<Credit> credits = (List<Credit>)HibernateUtil.getSessionFactory().openSession().createQuery("from Credit").list();
        //List<Credit> credits = session.createQuery("from Credit").list();
        //session.close();
        return credits;
    }
}
