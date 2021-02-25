package com.awrzosek.ski_station.tables.ski.skipass.type;

import com.awrzosek.ski_station.basic.BasicDao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeConsts.*;

public class SkipassTypeDao implements BasicDao<SkipassType> {
	@Override
	public Optional<SkipassType> get(Long id)
	{
		String query = "select * from " + TAB_NAME + " where " + FLD_ID + " = ?";
		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, id);
			try (ResultSet result = preparedStatement.executeQuery())
			{
				if (result.next())
					return Optional.of(processSkipassTypeForSelect(result));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public List<SkipassType> getAll()
	{
		List<SkipassType> skipassTypes = new ArrayList<>();
		try (Connection connection = getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				skipassTypes.add(processSkipassTypeForSelect(result));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return skipassTypes;
	}

	@Override
	public boolean add(SkipassType skipassType)
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
			processSkipassTypeForAdding(skipassType, preparedStatement);
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(SkipassType skipassType)
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
			processSkipassTypeForAdding(skipassType, preparedStatement);
			preparedStatement.setLong(4, skipassType.getId());
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(SkipassType skipassType)
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
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private SkipassType processSkipassTypeForSelect(ResultSet result) throws SQLException
	{
		DiscountType discountType = DiscountType.valueOf(result.getString(FLD_DISCOUNT_TYPE));

		return new SkipassType(result.getLong(FLD_ID), result.getInt(FLD_DURATION), discountType,
				BigDecimal.valueOf(result.getDouble(FLD_PRICE)));
	}

	private void processSkipassTypeForAdding(SkipassType skipassType, PreparedStatement preparedStatement)
			throws SQLException
	{
		preparedStatement.setString(1, String.valueOf(skipassType.getDuration()));
		preparedStatement.setString(2, skipassType.getDiscountType().name());
		preparedStatement.setString(3, skipassType.getPrice().setScale(2, RoundingMode.HALF_UP).toString());
	}
}
