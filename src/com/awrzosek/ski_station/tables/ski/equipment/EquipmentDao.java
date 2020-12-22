package com.awrzosek.ski_station.tables.ski.equipment;

import com.awrzosek.ski_station.basic.BasicDao;

import java.util.List;
import java.util.Optional;

public class EquipmentDao implements BasicDao<Equipment> {
    @Override
    public Optional<Equipment> get(Long id)
    {
        return Optional.empty();
    }

    @Override
    public List<Equipment> getAll()
    {
        return null;
    }

    @Override
    public boolean add(Equipment equipment)
    {
        return true;
    }

    @Override
    public boolean update(Equipment equipment)
    {
        return true;
    }

    @Override
    public boolean delete(Equipment equipment)
    {
        return true;
    }
}
