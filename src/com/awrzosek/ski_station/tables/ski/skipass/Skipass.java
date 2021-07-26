package com.awrzosek.ski_station.tables.ski.skipass;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.time.LocalDate;

public class Skipass extends BasicTable {
	private Long clientId;
	private boolean rented;
	private boolean active;
	private LocalDate dateFrom;
	private LocalDate dateTo;

	public Skipass()
	{
	}

	public Skipass(Long id, Long clientId, boolean rented, boolean active, LocalDate dateFrom,
				   LocalDate dateTo)
	{
		super(id);
		this.clientId = clientId;
		this.rented = rented;
		this.active = active;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public Skipass(Long clientId, boolean rented, boolean active, LocalDate dateFrom,
				   LocalDate dateTo)
	{
		this.clientId = clientId;
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

	public LocalDate getDateFrom()
	{
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom)
	{
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo()
	{
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo)
	{
		this.dateTo = dateTo;
	}

	public void clearData()
	{
		setActive(false);
		setRented(false);
		setClientId(null);
		setDateTo(null);
		setDateFrom(null);
		setDateTo(null);
	}
}
