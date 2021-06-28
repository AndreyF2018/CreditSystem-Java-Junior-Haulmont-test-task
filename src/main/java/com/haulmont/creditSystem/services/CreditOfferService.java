package com.haulmont.creditSystem.services;

import com.haulmont.creditSystem.dao.BankDao;
import com.haulmont.creditSystem.dao.CreditOfferDao;
import com.haulmont.creditSystem.models.CreditOffer;

import java.util.List;
import java.util.UUID;

public class CreditOfferService implements BankService<CreditOffer>{

    private BankDao<CreditOffer> offerDao;

    public CreditOfferService(){
        offerDao = new CreditOfferDao();
    }
    @Override
    public CreditOffer findById(UUID id) {
        return offerDao.findById(id);
    }

    @Override
    public void create(CreditOffer creditOffer) {
        offerDao.create(creditOffer);
    }

    @Override
    public void update(CreditOffer creditOffer) {
        offerDao.update(creditOffer);
    }

    @Override
    public void delete(CreditOffer creditOffer) {
        offerDao.delete(creditOffer);
    }

    @Override
    public List<CreditOffer> findAll() {
        return offerDao.findAll();
    }
}
