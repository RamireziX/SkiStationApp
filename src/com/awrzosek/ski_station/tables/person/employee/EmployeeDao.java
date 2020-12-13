package com.awrzosek.ski_station.tables.person.employee;

import com.awrzosek.ski_station.basic.BasicDao;

import java.util.List;
import java.util.Optional;

public class EmployeeDao implements BasicDao<Employee> {
    @Override
    public Optional<Employee> get(Long id)
    {
        return Optional.empty();
    }

    @Override
    public List<Employee> getAll()
    {
        return null;
    }

    @Override
    public void add(Employee employee)
    {

    }

    @Override
    public void update(Employee employee)
    {

    }

    @Override
    public void delete(Employee employee)
    {

    }
}
