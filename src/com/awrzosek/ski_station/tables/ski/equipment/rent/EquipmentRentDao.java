package com.awrzosek.ski_station.tables.ski.equipment.rent;

import com.awrzosek.ski_station.basic.BasicDao;

import java.util.List;
import java.util.Optional;

public class EquipmentRentDao implements BasicDao<EquipmentRent> {
    @Override
    public Optional<EquipmentRent> get(Long id)
    {
        return Optional.empty();
    }

    @Override
    public List<EquipmentRent> getAll()
    {
        return null;
    }

    @Override
    public void add(EquipmentRent equipmentRent)
    {

    }

    @Override
    public void update(EquipmentRent equipmentRent)
    {

    }

    @Override
    public void delete(EquipmentRent equipmentRent)
    {

    }
}
