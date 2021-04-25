package com.awrzosek.ski_station.tables.basic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BasicDao<T> {
	protected Connection connection;

	protected BasicDao(Connection connection)
	{
		this.connection = connection;
	}

	public abstract Optional<T> get(Long id) throws SQLException;

	public abstract List<T> getAll() throws SQLException;

	public abstract boolean add(T t) throws SQLException;

	public abstract boolean update(T t) throws SQLException;

	public abstract boolean delete(T t) throws SQLException;

	//TODO zobaczyć czy na pewno działa (ale powinno)
	public Optional<T> selectByQuery(String query) throws SQLException
	{
		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			try (ResultSet result = preparedStatement.executeQuery())
			{
				if (result.next())
					return Optional.of(processForSelect(result));
			}
		}
		return Optional.empty();
	}

	public List<T> listByQuery(String query) throws SQLException
	{
		List<T> resultList = new ArrayList<>();
		try (Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery(query))
		{
			while (result.next())
				resultList.add(processForSelect(result));
		}
		return resultList;
	}

	protected abstract T processForSelect(ResultSet result) throws SQLException;

	protected abstract void processForAdding(T t, PreparedStatement preparedStatement) throws SQLException;
}
