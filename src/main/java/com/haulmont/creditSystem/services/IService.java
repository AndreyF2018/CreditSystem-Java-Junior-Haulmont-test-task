package com.haulmont.creditSystem.services;

import java.util.List;
import java.util.UUID;

public interface IService<T> {
    T findById(UUID id);
    void create(T object);
    void update(T object);
    void delete(T object);
    List<T> findAll();
}
