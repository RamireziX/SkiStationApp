package com.awrzosek.ski_station.tables.ski.skipass.map;

import com.awrzosek.ski_station.tables.basic.BasicDao;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMapConsts.*;

public class SkipassSkipassTypeMapDao extends BasicDao<SkipassSkipassTypeMap> {

	public SkipassSkipassTypeMapDao(Connection connection)
	{
		super(connection);
	}

	@Override
	public Optional<SkipassSkipassTypeMap> get(Long id) throws SQLException
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
	public List<SkipassSkipassTypeMap> getAll() throws SQLException
	{
		List<SkipassSkipassTypeMap> skipassSkipassTypeMaps = new ArrayList<>();
		try (Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				skipassSkipassTypeMaps.add(processForSelect(result));
		}

		return skipassSkipassTypeMaps;
	}

	@Override
	public void add(SkipassSkipassTypeMap skipassSkipassTypeMap) throws SQLException
	{
		//@formatter:off
		String query =
				"insert into " + TAB_NAME +
						" ( " +
							FLD_SKIPASS_ID + ", " +
							FLD_SKIPASS_TYPE_ID + ", " +
							FLD_DURATION + ", " +
							FLD_PRICE +
						") " +
						"values " +
						"(?, ?, ?, ?);";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
		{
			processForAdding(skipassSkipassTypeMap, preparedStatement);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next())
				skipassSkipassTypeMap.setId(resultSet.getLong(1));
		}

	}

	@Override
	public void update(SkipassSkipassTypeMap skipassSkipassTypeMap) throws SQLException
	{
		//@formatter:off
		String query =
				"update " + TAB_NAME +
						" set " +
						FLD_SKIPASS_ID + " = ?, " +
						FLD_SKIPASS_TYPE_ID + " = ?, " +
						FLD_DURATION + " = ?, " +
						FLD_PRICE + " = ? " +
						"where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(skipassSkipassTypeMap, preparedStatement);
			preparedStatement.setLong(5, skipassSkipassTypeMap.getId());
			preparedStatement.execute();
		}

	}

	@Override
	public void delete(SkipassSkipassTypeMap skipassSkipassTypeMap) throws SQLException
	{
		//@formatter:off
		String query =
				"delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, skipassSkipassTypeMap.getId());
			preparedStatement.execute();
		}
	}

	@Override
	public boolean checkTableIfEmpty() throws SQLException
	{
		String query = "select * from " + TAB_NAME + " limit 1";

		return getByQuery(query).isEmpty();
	}

	public Optional<SkipassSkipassTypeMap> getBySkipass(Skipass skipass) throws SQLException
	{
		//@formatter:off
		String query =
				"select * from " + TAB_NAME +
						" where " + FLD_SKIPASS_ID + " = " + skipass.getId();
		//@formatter:on

		return getByQuery(query);
	}

	@Override
	protected SkipassSkipassTypeMap processForSelect(ResultSet result) throws SQLException
	{
		Duration duration = Duration.valueOf(result.getInt(FLD_DURATION));

		return new SkipassSkipassTypeMap(result.getLong(FLD_ID), result.getLong(FLD_SKIPASS_ID),
				result.getLong(FLD_SKIPASS_TYPE_ID), duration, result.getBigDecimal(FLD_PRICE));
	}

	@Override
	protected void processForAdding(SkipassSkipassTypeMap skipassSkipassTypeMap, PreparedStatement preparedStatement)
			throws SQLException
	{
		preparedStatement.setLong(1, skipassSkipassTypeMap.getSkipassId());
		preparedStatement.setLong(2, skipassSkipassTypeMap.getSkipassTypeId());
		preparedStatement.setInt(3, skipassSkipassTypeMap.getDuration().getDays());
		preparedStatement.setBigDecimal(4, skipassSkipassTypeMap.getPrice());
	}
}
