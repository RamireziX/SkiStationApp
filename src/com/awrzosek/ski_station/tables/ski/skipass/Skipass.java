package com.awrzosek.ski_station.tables.ski.skipass;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.util.Date;

public class Skipass extends BasicTable {
    private Long clientId;
    private Long skipassTypeId;
    private boolean rented;
    private boolean active;
    private Date dateFrom;
    private Date dateTo;

    public Skipass(Long id, Long clientId, Long skipassTypeId, boolean rented, boolean active, Date dateFrom, Date dateTo)
    {
        super(id);
        this.clientId = clientId;
        this.skipassTypeId = skipassTypeId;
        this.rented = rented;
        this.active = active;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    public Long getSkipassTypeId()
    {
        return skipassTypeId;
    }

    public void setSkipassTypeId(Long skipassTypeId)
    {
        this.skipassTypeId = skipassTypeId;
    }

    public boolean isRented()
    {
        return rented;
    }

    public void setRented(boolean rented)
    {
        this.rented = rented;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public Date getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(Date dateTo)
    {
        this.dateTo = dateTo;
    }
}
