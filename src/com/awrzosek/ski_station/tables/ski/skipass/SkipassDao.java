package com.awrzosek.ski_station.tables.ski.skipass;

import com.awrzosek.ski_station.tables.basic.BasicDao;

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
	public boolean add(Skipass skipass) throws SQLException
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

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(skipass, preparedStatement);
			return preparedStatement.execute();
		}
	}

	@Override
	public boolean update(Skipass skipass) throws SQLException
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
			return preparedStatement.execute();
		}
	}

	@Override
	public boolean delete(Skipass skipass) throws SQLException
	{
		//@formatter:off
		String query =
				"delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, skipass.getId());
			return preparedStatement.execute();
		}
	}

	@Override
	protected Skipass processForSelect(ResultSet result) throws SQLException
	{
		LocalDate dateFrom = result.getDate(FLD_DATE_FROM).toLocalDate();
		LocalDate dateTo = result.getDate(FLD_DATE_TO).toLocalDate();

		return new Skipass(result.getLong(FLD_ID), result.getLong(FLD_CLIENT_ID), result.getLong(FLD_SKIPASS_TYPE_ID),
				result.getBoolean(FLD_RENTED), result.getBoolean(FLD_ACTIVE), dateFrom, dateTo);
	}

	@Override
	protected void processForAdding(Skipass skipass, PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setLong(1, skipass.getClientId());
		preparedStatement.setLong(2, skipass.getSkipassTypeId());
		preparedStatement.setBoolean(3, skipass.isRented());
		preparedStatement.setBoolean(4, skipass.isActive());
		preparedStatement.setDate(5, Date.valueOf(skipass.getDateFrom()));
		preparedStatement.setDate(6, Date.valueOf(skipass.getDateTo()));
	}
}
