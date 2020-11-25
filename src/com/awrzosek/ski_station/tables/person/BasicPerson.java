package com.awrzosek.ski_station.tables.person;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.util.Date;

public abstract class BasicPerson extends BasicTable {
    private String firstName;
    private String SecondName;
    private String surname;
    private String fullName;
    private Date dateOfBirth;
    private String pesel;
    private String personalIdNumber;
    private String phone;
    private String eMail;

    public BasicPerson(Long id, String firstName, String secondName, String surname, String fullName, Date dateOfBirth, String pesel,
                       String personalIdNumber, String phone, String eMail)
    {
        super(id);
        this.firstName = firstName;
        SecondName = secondName;
        this.surname = surname;
        this.fullName = fullName;
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
    }

    public String getSecondName()
    {
        return SecondName;
    }

    public void setSecondName(String secondName)
    {
        SecondName = secondName;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
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

    public String geteMail()
    {
        return eMail;
    }

    public void seteMail(String eMail)
    {
        this.eMail = eMail;
    }
}
