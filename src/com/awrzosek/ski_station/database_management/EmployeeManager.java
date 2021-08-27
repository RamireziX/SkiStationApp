package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.tables.person.employee.Employee;
import com.awrzosek.ski_station.tables.person.employee.EmployeeConsts;
import com.awrzosek.ski_station.tables.person.employee.EmployeeDao;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeManager {
	private EmployeeDao employeeDao;

	//TODO jak starczy czasu to encrypting password - najpierw w gui potem w database najlepiej
	public EmployeeManager(Connection connection)
	{
		this.employeeDao = new EmployeeDao(connection);
	}

	//TODO przetestować czy rzeczywiście działa jak nieunikalny login
	public boolean add(Employee employee) throws SQLException
	{
		try
		{
			employeeDao.add(employee);
			return true;
		} catch (SQLException e)
		{
			if (e.getMessage().contains(EmployeeConsts.TAB_NAME + "." + EmployeeConsts.FLD_LOGIN))
				return false;
			else
				throw e;
		}
	}

	//TODO metoda copiego paste'a
	public boolean edit(Employee employee) throws SQLException
	{
		try
		{
			employeeDao.update(employee);
			return true;
		} catch (SQLException e)
		{
			if (e.getMessage().contains(EmployeeConsts.TAB_NAME + "." + EmployeeConsts.FLD_LOGIN))
				return false;
			else
				throw e;
		}
	}

	//TODO sprawdzanie czy nie jest aktualnie zalogowany
	public boolean delete(Employee employee) throws SQLException
	{
		employeeDao.delete(employee);
		return true;
	}

	public EmployeeDao getEmployeeDao()
	{
		return employeeDao;
	}
}
