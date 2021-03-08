package com.awrzosek.ski_station.tables.ski.equipment.rent;

import com.awrzosek.ski_station.tables.basic.BasicDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentConsts.*;

//TODO - finish
public class EquipmentRentDao extends BasicDao<EquipmentRent> {
	@Override
	public Optional<EquipmentRent> get(Long id)
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
	public List<EquipmentRent> getAll()
	{
		List<EquipmentRent> equipmentRents = new ArrayList<>();
		try (Connection connection = getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				equipmentRents.add(processForSelect(result));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return equipmentRents;
	}

	@Override
	public boolean add(EquipmentRent equipmentRent)
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

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(equipmentRent, preparedStatement);
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(EquipmentRent equipmentRent)
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

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(equipmentRent, preparedStatement);
			preparedStatement.setLong(6, equipmentRent.getId());
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(EquipmentRent equipmentRent)
	{
		//@formatter:off
		String query =
				"delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
		//@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, equipmentRent.getId());
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
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
