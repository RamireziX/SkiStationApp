package com.awrzosek.ski_station.tables.basic;

import com.awrzosek.ski_station.basic.BasicConsts;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public abstract class BasicDao<T> {
	public abstract Optional<T> get(Long id) throws SQLException;

	public abstract List<T> getAll() throws SQLException;

	public abstract boolean add(T t) throws SQLException;

	public abstract boolean update(T t) throws SQLException;

	public abstract boolean delete(T t) throws SQLException;

	protected abstract T processForSelect(ResultSet result) throws SQLException;

	protected abstract void processForAdding(T t, PreparedStatement preparedStatement) throws SQLException;

	protected Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(BasicConsts.DB_URL);
	}
}
