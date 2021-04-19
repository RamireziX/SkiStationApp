package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;

public class ManageClients {
	public boolean addClient(Client client, Equipment equipment, SkipassType skipassType)
	{
		ClientDao clientDao = new ClientDao();
		//clientDao.add(client);

		return false;
	}

	public boolean removeClient(Client client)
	{
		return false;
	}
}
