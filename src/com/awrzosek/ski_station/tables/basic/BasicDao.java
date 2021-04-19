package com.awrzosek.ski_station.tables.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class BasicDao<T> {
	protected Connection connection;

	public BasicDao(Connection connection)
	{
		this.connection = connection;
	}

	public abstract Optional<T> get(Long id) throws SQLException;

	public abstract List<T> getAll() throws SQLException;

	public abstract boolean add(T t) throws SQLException;

	public abstract boolean update(T t) throws SQLException;

	public abstract boolean delete(T t) throws SQLException;

	protected abstract T processForSelect(ResultSet result) throws SQLException;

	protected abstract void processForAdding(T t, PreparedStatement preparedStatement) throws SQLException;
}
