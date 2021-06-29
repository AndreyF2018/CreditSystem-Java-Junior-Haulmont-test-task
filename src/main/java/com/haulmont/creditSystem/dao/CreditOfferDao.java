package com.haulmont.creditSystem.dao;

import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.UUID;

public class CreditOfferDao extends BankDao<CreditOffer>{
    @Override
    public CreditOffer findById(UUID id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CreditOffer offer = session.get(CreditOffer.class, id);
        session.close();
        return offer;
    }

    @Override
    public List<CreditOffer> findAll() {
        List<CreditOffer> creditOffers = (List<CreditOffer>)HibernateUtil.getSessionFactory().openSession().createQuery("from CreditOffer").list();
        return creditOffers;
    }
}
