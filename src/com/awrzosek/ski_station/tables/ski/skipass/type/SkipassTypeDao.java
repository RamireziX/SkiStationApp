package com.awrzosek.ski_station.tables.ski.skipass.type;

import com.awrzosek.ski_station.tables.basic.BasicDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeConsts.*;

public class SkipassTypeDao extends BasicDao<SkipassType> {
	public SkipassTypeDao(Connection connection)
	{
		super(connection);
	}

	@Override
	public Optional<SkipassType> get(Long id) throws SQLException
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
	public List<SkipassType> getAll() throws SQLException
	{
		List<SkipassType> skipassTypes = new ArrayList<>();
		try (Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				skipassTypes.add(processForSelect(result));
		}

		return skipassTypes;
	}

	@Override
	public void add(SkipassType skipassType) throws SQLException
	{
		//@formatter:off
		String query =
				"insert into " + TAB_NAME +
						" ( " +
						FLD_DISCOUNT_TYPE +
						") " +
						"values " +
						"(?);";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
		{
			processForAdding(skipassType, preparedStatement);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next())
				skipassType.setId(resultSet.getLong(1));
		}
	}

	@Override
	public void update(SkipassType skipassType) throws SQLException
	{
		//@formatter:off
		String query =
				"update " + TAB_NAME +
						" set " +
						FLD_DISCOUNT_TYPE + " = ? " +
						"where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(skipassType, preparedStatement);
			preparedStatement.setLong(2, skipassType.getId());
			preparedStatement.execute();
		}
	}

	@Override
	public void delete(SkipassType skipassType) throws SQLException
	{
		//@formatter:off
		String query =
				"delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, skipassType.getId());
			preparedStatement.execute();
		}
	}

	@Override
	public boolean checkTableIfEmpty() throws SQLException
	{
		String query = "select * from " + TAB_NAME + " limit 1";
		return getByQuery(query).isEmpty();
	}

	protected SkipassType processForSelect(ResultSet result) throws SQLException
	{
		DiscountType discountType = DiscountType.valueOf(result.getString(FLD_DISCOUNT_TYPE));

		return new SkipassType(result.getLong(FLD_ID), discountType);
	}

	protected void processForAdding(SkipassType skipassType, PreparedStatement preparedStatement)
			throws SQLException
	{
		preparedStatement.setString(1, skipassType.getDiscountType().name());
	}
}
