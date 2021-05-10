package com.awrzosek.ski_station.tables.ski.equipment;

import com.awrzosek.ski_station.tables.basic.BasicDao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.ski.equipment.EquipmentConsts.*;

public class EquipmentDao extends BasicDao<Equipment> {
	public EquipmentDao(Connection connection)
	{
		super(connection);
	}

	@Override
	public Optional<Equipment> get(Long id) throws SQLException
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
	public List<Equipment> getAll() throws SQLException
	{
		List<Equipment> equipments = new ArrayList<>();
		try (Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				equipments.add(processForSelect(result));
		}

		return equipments;
	}

	@Override
	public void add(Equipment equipment) throws SQLException
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

		try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
		{
			processForAdding(equipment, preparedStatement);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next())
				equipment.setId(resultSet.getLong(1));
		}
	}

	@Override
	public void update(Equipment equipment) throws SQLException
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

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(equipment, preparedStatement);
			preparedStatement.setLong(6, equipment.getId());
			preparedStatement.execute();
		}
	}

	@Override
	public void delete(Equipment equipment) throws SQLException
	{
		//@formatter:off
		String query =
				"delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, equipment.getId());
			preparedStatement.execute();
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
