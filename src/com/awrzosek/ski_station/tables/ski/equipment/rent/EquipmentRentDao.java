package com.awrzosek.ski_station.tables.ski.equipment.rent;

import com.awrzosek.ski_station.basic.BasicDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
		return null;
	}

	@Override
	public boolean add(EquipmentRent equipmentRent)
	{
		return true;
	}

	@Override
	public boolean update(EquipmentRent equipmentRent)
	{
		return true;
	}

	@Override
	public boolean delete(EquipmentRent equipmentRent)
	{
		return true;
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
//		preparedStatement.setString(1, client.getFirstName());
//		preparedStatement.setString(2, client.getSecondName());
//		preparedStatement.setString(3, client.getSurname());
//		preparedStatement.setDate(4, Date.valueOf(client.getDateOfBirth()));
//		preparedStatement.setString(5, client.getPesel());
//		preparedStatement.setString(6, client.getPersonalIdNumber());
//		preparedStatement.setDate(7, Date.valueOf(client.getDateEntered()));
//		preparedStatement.setString(8, client.getPhone());
//		preparedStatement.setString(9, client.getEMail());
	}
}
