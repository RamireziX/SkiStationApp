package com.awrzosek.ski_station.tables.person;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.time.LocalDate;
import java.util.Date;

public abstract class BasicPerson extends BasicTable {
    private String firstName;
    private String secondName;
    private String surname;
    private String fullName;
    private LocalDate dateOfBirth;
    private String pesel;
    private String personalIdNumber;
    private String phone;
    private String eMail;

    public BasicPerson(Long id, String firstName, String secondName, String surname, LocalDate dateOfBirth, String pesel,
                       String personalIdNumber, String phone, String eMail)
    {
        super(id);
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        setFullName(firstName, secondName, surname);
        this.dateOfBirth = dateOfBirth;
        this.pesel = pesel;
        this.personalIdNumber = personalIdNumber;
        this.phone = phone;
        this.eMail = eMail;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
        setFullName(firstName, this.secondName, this.surname);
    }

    public String getSecondName()
    {
        return secondName;
    }

    public void setSecondName(String secondName)
    {
        this.secondName = secondName;
        setFullName(this.firstName, secondName, this.surname);
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
        setFullName(this.firstName, this.secondName, surname);
    }

    public String getFullName()
    {
        return fullName;
    }

    private void setFullName(String firstName, String secondName, String surname){
        this.fullName = firstName + " " + ((secondName == null || secondName.isEmpty())  ? "" : secondName + " " ) + surname;
    }

    private void setFullName(String fullName){
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPesel()
    {
        return pesel;
    }

    public void setPesel(String pesel)
    {
        this.pesel = pesel;
    }

    public String getPersonalIdNumber()
    {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(String personalIdNumber)
    {
        this.personalIdNumber = personalIdNumber;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEMail()
    {
        return eMail;
    }

    public void setEMail(String eMail)
    {
        this.eMail = eMail;
    }


}
