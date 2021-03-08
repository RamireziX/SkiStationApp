package com.awrzosek.ski_station.tables.ski.equipment;

import com.awrzosek.ski_station.basic.BasicDao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.ski.equipment.EquipmentConsts.*;

public class EquipmentDao extends BasicDao<Equipment> {
	@Override
	public Optional<Equipment> get(Long id)
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
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public List<Equipment> getAll()
	{
		List<Equipment> equipments = new ArrayList<>();
		try (Connection connection = getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				equipments.add(processForSelect(result));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return equipments;
	}

	@Override
	public boolean add(Equipment equipment)
	{
		//@formatter:off
		String query =
				"insert into " + TAB_NAME +
						" ( " +
						FLD_NAME + ", " +
						FLD_SERIAL_NUMBER + ", " +
						FLD_TYPE + ", " +
						FLD_RENT_PRICE + ", " +
						FLD_CONDITION + ") " +
						"values " +
						"(?, ?, ?, ?, ?);";
		//@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(equipment, preparedStatement);
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Equipment equipment)
	{
		//@formatter:off
		String query =
				"update " + TAB_NAME +
						" set " + FLD_NAME + " = ?, " +
						FLD_SERIAL_NUMBER + " = ?, " +
						FLD_TYPE + " = ?, " +
						FLD_RENT_PRICE + " = ?, " +
						FLD_CONDITION + " = ? " +
						"where " + FLD_ID + " = ?";
		//@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(equipment, preparedStatement);
			preparedStatement.setLong(6, equipment.getId());
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Equipment equipment)
	{
		//@formatter:off
		String query =
				"delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
		//@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, equipment.getId());
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	protected Equipment processForSelect(ResultSet result) throws SQLException
	{
		EquipmentType type = EquipmentType.valueOf(result.getString(FLD_TYPE));
		Condition condition = Condition.valueOf(result.getString(FLD_CONDITION));

		return new Equipment(result.getLong(FLD_ID), result.getString(FLD_NAME), result.getString(FLD_SERIAL_NUMBER),
				type, BigDecimal.valueOf(result.getDouble(FLD_RENT_PRICE)), condition);
	}

	protected void processForAdding(Equipment equipment, PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, equipment.getName());
		preparedStatement.setString(2, equipment.getSerialNumber());
		preparedStatement.setString(3, equipment.getType().name());
		preparedStatement.setBigDecimal(4, equipment.getRentPrice());
		preparedStatement.setString(5, equipment.getCondition().name());
	}
}
