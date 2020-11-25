package com.awrzosek.ski_station.tables.person;

import java.util.Date;

public class Employee extends BasicPerson{
    private String buildingNr;
    private String aptNr;
    private String streetNr;
    private String streetName;
    private String city;
    private String voivodeship;
    private String zipcode;
    private String login;
    private String passwd;
    private String accountNr;
    private String bankName;

    public Employee(Long id, String firstName, String secondName, String surname, String fullName, Date dateOfBirth, String pesel,
                    String personalIdNumber, String phone, String eMail, String buildingNr, String aptNr, String streetNr, String streetName,
                    String city, String voivodeship, String zipcode, String login, String passwd, String accountNr, String bankName)
    {
        super(id, firstName, secondName, surname, fullName, dateOfBirth, pesel, personalIdNumber, phone, eMail);
        this.buildingNr = buildingNr;
        this.aptNr = aptNr;
        this.streetNr = streetNr;
        this.streetName = streetName;
        this.city = city;
        this.voivodeship = voivodeship;
        this.zipcode = zipcode;
        this.login = login;
        this.passwd = passwd;
        this.accountNr = accountNr;
        this.bankName = bankName;
    }

    public String getBuildingNr()
    {
        return buildingNr;
    }

    public void setBuildingNr(String buildingNr)
    {
        this.buildingNr = buildingNr;
    }

    public String getAptNr()
    {
        return aptNr;
    }

    public void setAptNr(String aptNr)
    {
        this.aptNr = aptNr;
    }

    public String getStreetNr()
    {
        return streetNr;
    }

    public void setStreetNr(String streetNr)
    {
        this.streetNr = streetNr;
    }

    public String getStreetName()
    {
        return streetName;
    }

    public void setStreetName(String streetName)
    {
        this.streetName = streetName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getVoivodeship()
    {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship)
    {
        this.voivodeship = voivodeship;
    }

    public String getZipcode()
    {
        return zipcode;
    }

    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPasswd()
    {
        return passwd;
    }

    public void setPasswd(String passwd)
    {
        this.passwd = passwd;
    }

    public String getAccountNr()
    {
        return accountNr;
    }

    public void setAccountNr(String accountNr)
    {
        this.accountNr = accountNr;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }
}
