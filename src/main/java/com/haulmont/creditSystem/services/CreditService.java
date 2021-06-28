package com.haulmont.creditSystem.services;

import com.haulmont.creditSystem.dao.CreditDao;
import com.haulmont.creditSystem.dao.BankDao;
import com.haulmont.creditSystem.models.Credit;

import java.util.List;
import java.util.UUID;

public class CreditService implements BankService<Credit> {

    private BankDao<Credit> creditDao;

    public  CreditService(){
        creditDao = new CreditDao();
    }
    @Override
    public Credit findById(UUID id) {

        return creditDao.findById(id);
    }

    @Override
    public void create (Credit credit) {

        creditDao.create(credit);
    }

    @Override
    public void delete(Credit credit) {

        creditDao.delete(credit);
    }

    @Override
    public void update(Credit credit) {
        creditDao.update(credit);
    }

    @Override
    public List<Credit> findAll() {
        return creditDao.findAll();
    }
}
