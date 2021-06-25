package com.haulmont.creditSystem.dao;

import java.util.List;
import java.util.UUID;

public interface IDao<T> {
    T findById(UUID id);
    void create(T object);
    void update(T object);
    void delete(T object);
    List<T> findAll();
}
