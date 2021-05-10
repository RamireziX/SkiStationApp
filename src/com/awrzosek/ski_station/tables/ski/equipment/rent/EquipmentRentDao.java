package com.awrzosek.ski_station.tables.ski.equipment.rent;

import com.awrzosek.ski_station.tables.basic.BasicDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentConsts.*;

public class EquipmentRentDao extends BasicDao<EquipmentRent> {
	public EquipmentRentDao(Connection connection)
	{
		super(connection);
	}

	@Override
	public Optional<EquipmentRent> get(Long id) throws SQLException
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
	public List<EquipmentRent> getAll() throws SQLException
	{
		List<EquipmentRent> equipmentRents = new ArrayList<>();
		try (Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				equipmentRents.add(processForSelect(result));
		}

		return equipmentRents;
	}

	@Override
	public void add(EquipmentRent equipmentRent) throws SQLException
	{
		//@formatter:off
		String query =
				"insert into " + TAB_NAME +
						" ( " +
						FLD_CLIENT_ID + ", " +
						FLD_EQUIPMENT_ID + ", " +
						FLD_RENT_DATE + ", " +
						FLD_RETURN_DATE + ", " +
						FLD_RENT_TYPE + ") " +
						"values " +
						"(?, ?, ?, ?, ?);";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
		{
			processForAdding(equipmentRent, preparedStatement);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next())
				equipmentRent.setId(resultSet.getLong(1));
		}
	}

	@Override
	public void update(EquipmentRent equipmentRent) throws SQLException
	{
		//@formatter:off
		String query =
				"update " + TAB_NAME +
						" set " + FLD_CLIENT_ID + " = ?, " +
						FLD_EQUIPMENT_ID + " = ?, " +
						FLD_RENT_DATE + " = ?, " +
						FLD_RETURN_DATE + " = ?, " +
						FLD_RENT_TYPE + " = ? " +
						"where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(equipmentRent, preparedStatement);
			preparedStatement.setLong(6, equipmentRent.getId());
			preparedStatement.execute();
		}
	}

	@Override
	public void delete(EquipmentRent equipmentRent) throws SQLException
	{
		//@formatter:off
		String query =
				"delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
		//@formatter:on

		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, equipmentRent.getId());
			preparedStatement.execute();
		}
	}

	protected EquipmentRent processForSelect(ResultSet result) throws SQLException
	{
		RentType rentType = RentType.valueOf(result.getString(FLD_RENT_TYPE));
		LocalDate rentDate = result.getDate(FLD_RENT_DATE).toLocalDate();
		LocalDate returnDate = result.getDate(FLD_RETURN_DATE).toLocalDate();

		return new EquipmentRent(result.getLong(FLD_ID), result.getLong(FLD_CLIENT_ID),
				result.getLong(FLD_EQUIPMENT_ID),
				rentDate, returnDate, rentType);
	}

	protected void processForAdding(EquipmentRent equipmentRent, PreparedStatement preparedStatement)
			throws SQLException
	{
		preparedStatement.setLong(1, equipmentRent.getClientId());
		preparedStatement.setLong(2, equipmentRent.getEquipmentId());
		preparedStatement.setDate(3, Date.valueOf(equipmentRent.getRentDate()));
		preparedStatement.setDate(4, Date.valueOf(equipmentRent.getReturnDate()));
		preparedStatement.setString(5, equipmentRent.getRentType().name());
	}
}
