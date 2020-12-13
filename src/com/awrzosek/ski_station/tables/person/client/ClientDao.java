package com.awrzosek.ski_station.tables.person.client;

import com.awrzosek.ski_station.basic.BasicDao;

import java.util.List;
import java.util.Optional;

public class ClientDao implements BasicDao<Client> {
    @Override
    public Optional<Client> get(Long id)
    {
        return Optional.empty();
    }

    @Override
    public List<Client> getAll()
    {
        return null;
    }

    @Override
    public void add(Client client)
    {

    }

    @Override
    public void update(Client client)
    {

    }

    @Override
    public void delete(Client client)
    {

    }
}
