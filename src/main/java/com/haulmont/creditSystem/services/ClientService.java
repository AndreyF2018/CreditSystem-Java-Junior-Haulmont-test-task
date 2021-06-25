package com.haulmont.creditSystem.services;

import com.haulmont.creditSystem.dao.ClientDao;
import com.haulmont.creditSystem.dao.IDao;
import com.haulmont.creditSystem.models.Client;

import java.util.List;
import java.util.UUID;

public class ClientService implements IService<Client> {

    private IDao<Client> clientDao;

    public  ClientService(){
        clientDao = new ClientDao();
    }
    @Override
    public Client findById(UUID id) {

        return clientDao.findById(id);
    }

    @Override
    public void create (Client client) {

        clientDao.create(client);
    }

    @Override
    public void delete(Client client) {

        clientDao.delete(client);
    }

    @Override
    public void update(Client client) {
        clientDao.update(client);
    }

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }
}
