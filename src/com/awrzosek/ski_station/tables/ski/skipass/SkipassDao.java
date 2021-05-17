package com.awrzosek.ski_station.tables.ski.skipass;

import com.awrzosek.ski_station.tables.basic.BasicDao;
import com.awrzosek.ski_station.tables.person.client.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.ski.skipass.SkipassConsts.*;

public class SkipassDao extends BasicDao<Skipass> {
	public SkipassDao(Connection connection)
	{
		super(connection);
	}

	@Override
	public Optional<Skipass> get(Long id) throws SQLException
	{
		String query = "select * from " + TAB_NAME + " where " + FLD_ID + " = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, id);

			try (ResultSet result = preparedStatement.executeQuery())
			{
				if (result.next())
					return Optional.of(processForSelect(result));
			}
		}
		return Optional.empty();
	}

	@Override
	public List<Skipass> getAll() throws SQLException
	{
		List<Skipass> skipasses = new ArrayList<>();
		try (Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				skipasses.add(processForSelect(result));
		}

		return skipasses;
	}

	@Override
	public void add(Skipass skipass) throws SQLException
	{
		//@formatter:off
		String query =
				"insert into " + TAB_NAME +
						" ( " + FLD_CLIENT_ID + ", " +
						FLD_SKIPASS_TYPE_ID + ", " +
						FLD_RENTED + ", " +
						FLD_ACTIVE + ", " +
						FLD_DATE_FROM + ", " +
						FLD_DATE_TO + ") " +
						"values (?, ?, ?, ?, ?, ?);";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
		{
			processForAdding(skipass, preparedStatement);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next())
				skipass.setId(resultSet.getLong(1));
		}
	}

	@Override
	public void update(Skipass skipass) throws SQLException
	{
		//@formatter:off
		String query =
				"update " + TAB_NAME +
						" set " + FLD_CLIENT_ID + " = ?, " +
						FLD_SKIPASS_TYPE_ID + " = ?, " +
						FLD_RENTED + " = ?, " +
						FLD_ACTIVE + " = ?, " +
						FLD_DATE_FROM + " = ?, " +
						FLD_DATE_TO + " = ? " +
						"where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(skipass, preparedStatement);
			preparedStatement.setLong(7, skipass.getId());
			preparedStatement.execute();
		}
	}

	@Override
	public void delete(Skipass skipass) throws SQLException
	{
		//@formatter:off
		String query =
				"delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, skipass.getId());
			preparedStatement.execute();
		}
	}

	@Override
	public boolean checkTableIfEmpty() throws SQLException
	{
		String query = "select * from " + TAB_NAME + " limit 1";
		return getByQuery(query).isEmpty();
	}

	public List<Skipass> getNotRented(int numberOfSkipasses) throws SQLException
	{
		//@formatter:off
		String query =
				"select * from " + TAB_NAME +
				" where coalesce(" + FLD_RENTED + ", 0) = 0" +
				" limit " + numberOfSkipasses;
		//@formatter:on

		return listByQuery(query);
	}

	public List<Skipass> listByClient(Client client) throws SQLException
	{
		//@formatter:off
		String query =
				"select * from " + TAB_NAME +
				" where " + FLD_CLIENT_ID + " = " + client.getId();
		//@formatter:on

		return listByQuery(query);
	}

	@Override
	protected Skipass processForSelect(ResultSet result) throws SQLException
	{
		LocalDate dateFrom = result.getDate(FLD_DATE_FROM) != null ? result.getDate(FLD_DATE_FROM).toLocalDate() :
				null;
		LocalDate dateTo = result.getDate(FLD_DATE_TO) != null ? result.getDate(FLD_DATE_TO).toLocalDate() :
				null;

		return new Skipass(result.getLong(FLD_ID), result.getLong(FLD_CLIENT_ID),
				result.getLong(FLD_SKIPASS_TYPE_ID),
				result.getBoolean(FLD_RENTED), result.getBoolean(FLD_ACTIVE), dateFrom, dateTo);
	}

	@Override
	protected void processForAdding(Skipass skipass, PreparedStatement preparedStatement) throws SQLException
	{
		setLongNullsafe(1, skipass.getClientId(), preparedStatement);
		setLongNullsafe(2, skipass.getSkipassTypeId(), preparedStatement);
		preparedStatement.setBoolean(3, skipass.isRented());
		preparedStatement.setBoolean(4, skipass.isActive());
		setDateNullsafe(5, skipass.getDateFrom(), preparedStatement);
		setDateNullsafe(6, skipass.getDateTo(), preparedStatement);
	}

}
