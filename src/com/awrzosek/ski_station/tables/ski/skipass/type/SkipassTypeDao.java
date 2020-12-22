package com.awrzosek.ski_station.tables.ski.skipass.type;

import com.awrzosek.ski_station.basic.BasicDao;

import java.util.List;
import java.util.Optional;

public class SkipassTypeDao implements BasicDao<SkipassType> {
    @Override
    public Optional<SkipassType> get(Long id)
    {
        return Optional.empty();
    }

    @Override
    public List<SkipassType> getAll()
    {
        return null;
    }

    @Override
    public boolean add(SkipassType skipassType)
    {
        return true;
    }

    @Override
    public boolean update(SkipassType skipassType)
    {
        return true;
    }

    @Override
    public boolean delete(SkipassType skipassType)
    {
        return true;
    }
}
