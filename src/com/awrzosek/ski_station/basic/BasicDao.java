package com.awrzosek.ski_station.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BasicDao<T> {
    Optional<T> get(Long id);
    List<T> getAll();
    void add(T t);
    void update(T t);
    void delete(T t);

    default Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(BasicConsts.DB_URL);
    }
}
