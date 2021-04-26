package com.awrzosek.ski_station.tables.ski.equipment.rent;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.time.LocalDate;

public class EquipmentRent extends BasicTable {
    private Long clientId;
    private Long equipmentId;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private RentType rentType;

    public EquipmentRent()
    {
        super();
    }

    public EquipmentRent(Long id, Long clientId, Long equipmentId, LocalDate rentDate, LocalDate returnDate,
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

    public LocalDate getRentDate()
    {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate)
    {
        this.rentDate = rentDate;
    }

    public LocalDate getReturnDate()
    {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate)
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
