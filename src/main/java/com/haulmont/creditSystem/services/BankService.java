package com.haulmont.creditSystem.services;

import com.haulmont.creditSystem.dao.BankDao;
import com.haulmont.creditSystem.models.Client;

import java.util.List;
import java.util.UUID;

public interface BankService<T> {
    T findById(UUID id);
    void create(T object);
    void update(T object);
    void delete(T object);
     List<T> findAll();
}
