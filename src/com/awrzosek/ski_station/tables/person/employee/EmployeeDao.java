package com.awrzosek.ski_station.tables.person.employee;

import com.awrzosek.ski_station.tables.basic.BasicDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.person.employee.EmployeeConsts.*;

public class EmployeeDao extends BasicDao<Employee> {
	@Override
	public Optional<Employee> get(Long id)
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
	public List<Employee> getAll()
	{
		List<Employee> employees = new ArrayList<>();
		try (Connection connection = getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
		{
			while (result.next())
				employees.add(processForSelect(result));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return employees;
	}

	@Override
	public boolean add(Employee employee)
	{
		//@formatter:off
        String query =
                "insert into " + TAB_NAME +
                        " ( " + FLD_FIRST_NAME + ", " +
                        FLD_SECOND_NAME + ", " +
                        FLD_SURNAME + ", " +
                        FLD_DATE_OF_BIRTH + ", " +
                        FLD_PESEL + ", " +
                        FLD_PERSONAL_ID_NUMBER + ", " +
                        FLD_PHONE + ", " +
                        FLD_E_MAIL + ", " +
                        FLD_BUILDING_NR + ", " +
                        FLD_APT_NR + ", " +
                        FLD_STREET_NR + ", " +
                        FLD_STREET_NAME + ", " +
                        FLD_CITY + ", " +
                        FLD_VOIVODESHIP + ", " +
                        FLD_ZIPCODE + ", " +
                        FLD_LOGIN + ", " +
                        FLD_PASSWD + ", " +
                        FLD_ACCOUNT_NR + ", " +
                        FLD_BANK_NAME + ") " +
                "values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        //@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(employee, preparedStatement);
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Employee employee)
	{
		//@formatter:off
        String query =
                "update " + TAB_NAME +
                " set " + FLD_FIRST_NAME + " = ?, " +
                        FLD_SECOND_NAME + " = ?, " +
                        FLD_SURNAME + " = ?, " +
                        FLD_DATE_OF_BIRTH + " = ?, " +
                        FLD_PESEL + " = ?, " +
                        FLD_PERSONAL_ID_NUMBER + " = ?, " +
                        FLD_PHONE + " = ?, " +
                        FLD_E_MAIL + " = ?, " +
                        FLD_BUILDING_NR + " = ?, " +
                        FLD_APT_NR + " = ?, " +
                        FLD_STREET_NR + " = ?, " +
                        FLD_STREET_NAME + " = ?, " +
                        FLD_CITY + " = ?, " +
                        FLD_VOIVODESHIP + " = ?, " +
                        FLD_ZIPCODE + " = ?, " +
                        FLD_LOGIN + " = ?, " +
                        FLD_PASSWD + " = ?, " +
                        FLD_ACCOUNT_NR + " = ?, " +
                        FLD_BANK_NAME + " = ? " +
                "where " + FLD_ID + " = ?";
        //@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			processForAdding(employee, preparedStatement);
			preparedStatement.setLong(20, employee.getId());
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Employee employee)
	{
		//@formatter:off
        String query =
                "delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
        //@formatter:on

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setLong(1, employee.getId());
			return preparedStatement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	protected Employee processForSelect(ResultSet result) throws SQLException
	{
		LocalDate dateOfBirth = result.getDate(FLD_DATE_OF_BIRTH).toLocalDate();

		return new Employee(result.getLong(FLD_ID), result.getString(FLD_FIRST_NAME),
				result.getString(FLD_SECOND_NAME), result.getString(FLD_SURNAME), dateOfBirth,
				result.getString(FLD_PESEL), result.getString(FLD_PERSONAL_ID_NUMBER), result.getString(FLD_PHONE),
				result.getString(FLD_E_MAIL), result.getString(FLD_BUILDING_NR), result.getString(FLD_APT_NR),
				result.getString(FLD_STREET_NR), result.getString(FLD_STREET_NAME), result.getString(FLD_CITY),
				result.getString(FLD_VOIVODESHIP), result.getString(FLD_ZIPCODE), result.getString(FLD_LOGIN),
				result.getString(FLD_PASSWD), result.getString(FLD_ACCOUNT_NR), result.getString(FLD_BANK_NAME));
	}

	protected void processForAdding(Employee employee, PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, employee.getFirstName());
		preparedStatement.setString(2, employee.getSecondName());
		preparedStatement.setString(3, employee.getSurname());
		preparedStatement.setDate(4, Date.valueOf(employee.getDateOfBirth()));
		preparedStatement.setString(5, employee.getPesel());
		preparedStatement.setString(6, employee.getPersonalIdNumber());
		preparedStatement.setString(7, employee.getPhone());
		preparedStatement.setString(8, employee.getEMail());
		preparedStatement.setString(9, employee.getBuildingNr());
		preparedStatement.setString(10, employee.getAptNr());
		preparedStatement.setString(11, employee.getStreetNr());
		preparedStatement.setString(12, employee.getStreetName());
		preparedStatement.setString(13, employee.getCity());
		preparedStatement.setString(14, employee.getVoivodeship());
		preparedStatement.setString(15, employee.getZipcode());
		preparedStatement.setString(16, employee.getLogin());
		preparedStatement.setString(17, employee.getPasswd());
		preparedStatement.setString(18, employee.getAccountNr());
		preparedStatement.setString(19, employee.getBankName());
	}
}
