package com.awrzosek.ski_station.tables.ski.equipment;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.util.Date;

public class EquipmentRent extends BasicTable {
    private Long clientId;
    private Long equipmentId;
    private Date rentDate;
    private Date returnDate;
    private RentType rentType;

    public EquipmentRent(Long id, Long clientId, Long equipmentId, Date rentDate, Date returnDate,
                         RentType rentType)
    {
        super(id);
        this.clientId = clientId;
        this.equipmentId = equipmentId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.rentType = rentType;
    }

    public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    public Long getEquipmentId()
    {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId)
    {
        this.equipmentId = equipmentId;
    }

    public Date getRentDate()
    {
        return rentDate;
    }

    public void setRentDate(Date rentDate)
    {
        this.rentDate = rentDate;
    }

    public Date getReturnDate()
    {
        return returnDate;
    }

    public void setReturnDate(Date returnDate)
    {
        this.returnDate = returnDate;
    }

    public RentType getRentType()
    {
        return rentType;
    }

    public void setRentType(RentType rentType)
    {
        this.rentType = rentType;
    }
}
