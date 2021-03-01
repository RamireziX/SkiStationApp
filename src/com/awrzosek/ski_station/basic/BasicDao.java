package com.awrzosek.ski_station.basic;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public abstract class BasicDao<T> {
	public abstract Optional<T> get(Long id);

	public abstract List<T> getAll();

	public abstract boolean add(T t);

	public abstract boolean update(T t);

	public abstract boolean delete(T t);

	protected abstract T processForSelect(ResultSet result) throws SQLException;

	protected abstract void processForAdding(T t, PreparedStatement preparedStatement) throws SQLException;

	protected Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(BasicConsts.DB_URL);
	}
}
