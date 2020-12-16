package com.awrzosek.ski_station.tables.person.client;

import com.awrzosek.ski_station.tables.person.BasicPerson;

import java.util.Date;

public class Client extends BasicPerson {
    Date dateEntered;

    public Client(Long id, String firstName, String secondName, String surname, Date dateOfBirth, String pesel,
                  String personalIdNumber, String phone, String eMail, Date dateEntered)
    {
        super(id, firstName, secondName, surname, dateOfBirth, pesel, personalIdNumber, phone, eMail);
        this.dateEntered = dateEntered;
    }

    public Date getDateEntered()
    {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered)
    {
        this.dateEntered = dateEntered;
    }
}
