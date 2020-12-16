package com.awrzosek.ski_station;

import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;

public class Main {

    public static void main(String[] args) {

        Client client = new Client(null, "jan", "marek", "kowalski", null, null,
                null, null,null, null);
        ClientDao clientDao = new ClientDao();
        clientDao.add(client);
        //List<Client> clients = clientDao.getAll();
        System.out.println(" ");
    }
}
