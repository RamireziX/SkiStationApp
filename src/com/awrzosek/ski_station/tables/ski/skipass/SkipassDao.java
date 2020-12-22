package com.awrzosek.ski_station.tables.ski.skipass;

import com.awrzosek.ski_station.basic.BasicDao;

import java.util.List;
import java.util.Optional;

public class SkipassDao implements BasicDao<Skipass> {
    @Override
    public Optional<Skipass> get(Long id)
    {
        return Optional.empty();
    }

    @Override
    public List<Skipass> getAll()
    {
        return null;
    }

    @Override
    public boolean add(Skipass skipass)
    {
        return true;
    }

    @Override
    public boolean update(Skipass skipass)
    {
        return true;
    }

    @Override
    public boolean delete(Skipass skipass)
    {
        return true;
    }
}
