package com.haulmont.creditSystem.services;

import com.haulmont.creditSystem.dao.ClientDao;
import com.haulmont.creditSystem.dao.CreditDao;
import com.haulmont.creditSystem.dao.IDao;
import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.models.Credit;

import java.util.List;
import java.util.UUID;

public class CreditService implements IService<Credit>{

    private IDao<Credit> creditIDao;

    public  CreditService(){
        creditIDao = new CreditDao();
    }
    @Override
    public Credit findById(UUID id) {

        return creditIDao.findById(id);
    }

    @Override
    public void create (Credit credit) {

        creditIDao.create(credit);
    }

    @Override
    public void delete(Credit credit) {

        creditIDao.delete(credit);
    }

    @Override
    public void update(Credit credit) {
        creditIDao.update(credit);
    }

    @Override
    public List<Credit> findAll() {
        return creditIDao.findAll();
    }
}
