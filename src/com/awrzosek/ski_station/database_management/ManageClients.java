package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;

import java.sql.Connection;
import java.sql.SQLException;

public class ManageClients {
	private Connection connection;

	public ManageClients(Connection connection)
	{
		this.connection = connection;
	}

	public boolean addClient(Client client, Equipment equipment, SkipassType skipassType)
	{
		//TODO zaimplementować i przetestować
		ClientDao clientDao = new ClientDao(connection);
		try
		{
			clientDao.add(client);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public boolean removeClient(Client client)
	{
		return false;
	}
}
