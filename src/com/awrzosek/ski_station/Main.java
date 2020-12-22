package com.awrzosek.ski_station;

import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Client client = new Client(null, "jan", "marek", "kowalski", LocalDate.of(1996, 3, 31), null,
                null, null,null, LocalDate.now());
        ClientDao clientDao = new ClientDao();
        clientDao.add(client);
        List<Client> clients = clientDao.getAll();
        for(Client c : clients){
            System.out.println("Client:");
            System.out.println(c.getFirstName());
            System.out.println(c.getDateOfBirth());
        }
    }
}
