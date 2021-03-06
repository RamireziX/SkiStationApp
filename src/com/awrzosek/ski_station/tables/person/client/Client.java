package com.awrzosek.ski_station.tables.person.client;

import com.awrzosek.ski_station.tables.person.BasicPerson;

import java.time.LocalDate;
import java.util.Optional;

public class Client extends BasicPerson {
	LocalDate dateEntered;

	public Client()
	{
	}

	public Client(Long id, String firstName, String secondName, String surname, LocalDate dateOfBirth,
				  String pesel,
				  String personalIdNumber, String phone, String eMail, LocalDate dateEntered)
	{
		super(id, firstName, secondName, surname, dateOfBirth, pesel, personalIdNumber, phone, eMail);
		this.dateEntered = Optional.ofNullable(dateEntered).orElse(LocalDate.now());
	}

	public Client(String firstName, String secondName, String surname, LocalDate dateOfBirth, String pesel,
				  String personalIdNumber, String phone, String eMail, LocalDate dateEntered)
	{
		super(firstName, secondName, surname, dateOfBirth, pesel, personalIdNumber, phone, eMail);
		this.dateEntered = Optional.ofNullable(dateEntered).orElse(LocalDate.now());
	}

	public LocalDate getDateEntered()
	{
		return dateEntered;
	}

	public void setDateEntered(LocalDate dateEntered)
	{
		this.dateEntered = dateEntered;
	}
}
