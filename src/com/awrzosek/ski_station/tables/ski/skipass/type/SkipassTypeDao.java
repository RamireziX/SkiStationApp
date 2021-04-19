package com.awrzosek.ski_station.tables.ski.skipass.type;

import com.awrzosek.ski_station.tables.basic.BasicDao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeConsts.*;

public class SkipassTypeDao extends BasicDao<SkipassType> {
	@Override
	public Optional<SkipassType> get(Long id) throws SQLException
	{
		String query = "select * from " + TAB_NAME + " where " + FLD_ID + " = ?";
		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
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
		try (Connection connection = getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				skipassTypes.add(processForSelect(result));
		}

		return skipassTypes;
	}

	@Override
	public boolean add(SkipassType skipassType) throws SQLException
	{
		//@formatter:off
		String query =
				"insert into " + TAB_NAME +
						" ( " +
						FLD_DURATION + ", " +
						FLD_DISCOUNT_TYPE + ", " +
						FLD_PRICE + ") " +
						"values " +
						"(?, ?, ?);";
		//@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(skipassType, preparedStatement);
			return preparedStatement.execute();
		}
	}

	@Override
	public boolean update(SkipassType skipassType) throws SQLException
	{
		//@formatter:off
		String query =
				"update " + TAB_NAME +
						" set " + FLD_DURATION + " = ?, " +
						FLD_DISCOUNT_TYPE + " = ?, " +
						FLD_PRICE + " = ? " +
						"where " + FLD_ID + " = ?";
		//@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(skipassType, preparedStatement);
			preparedStatement.setLong(4, skipassType.getId());
			return preparedStatement.execute();
		}
	}

	@Override
	public boolean delete(SkipassType skipassType) throws SQLException
	{
		//@formatter:off
		String query =
				"delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
		//@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, skipassType.getId());
			return preparedStatement.execute();
		}
	}

	protected SkipassType processForSelect(ResultSet result) throws SQLException
	{
		DiscountType discountType = DiscountType.valueOf(result.getString(FLD_DISCOUNT_TYPE));

		return new SkipassType(result.getLong(FLD_ID), result.getInt(FLD_DURATION), discountType,
				BigDecimal.valueOf(result.getDouble(FLD_PRICE)));
	}

	protected void processForAdding(SkipassType skipassType, PreparedStatement preparedStatement)
			throws SQLException
	{
		preparedStatement.setInt(1, skipassType.getDuration());
		preparedStatement.setString(2, skipassType.getDiscountType().name());
		preparedStatement.setBigDecimal(3, skipassType.getPrice());
	}
}
