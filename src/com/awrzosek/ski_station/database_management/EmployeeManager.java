package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.tables.person.employee.Employee;
import com.awrzosek.ski_station.tables.person.employee.EmployeeConsts;
import com.awrzosek.ski_station.tables.person.employee.EmployeeDao;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeManager {
	private EmployeeDao employeeDao;

	//TODO jak starczy czasu to encrypting password - w database
	public EmployeeManager(Connection connection)
	{
		this.employeeDao = new EmployeeDao(connection);
	}

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

	public boolean delete(Employee employee, Employee loggedInEmployee) throws SQLException
	{
		if (employee.getId().compareTo(loggedInEmployee.getId()) == 0)
			return false;

		employeeDao.delete(employee);

		return true;
	}

	public Employee authenticateLogin(String login, String password) throws SQLException
	{
		Employee employee = employeeDao.findByLogin(login).orElse(null);

		if (employee == null || (!employee.getPasswd().equals(password)))
			return null;

		return employee;
	}

	public EmployeeDao getEmployeeDao()
	{
		return employeeDao;
	}
}
