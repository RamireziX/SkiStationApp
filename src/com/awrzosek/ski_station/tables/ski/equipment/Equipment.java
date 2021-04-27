package com.awrzosek.ski_station.tables.ski.equipment;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.math.BigDecimal;

public class Equipment extends BasicTable {
	private String name;
	private String serialNumber;
	private EquipmentType type;
	private BigDecimal rentPrice;
	private Condition condition;

	public Equipment()
	{
	}

	public Equipment(Long id, String name, String serialNumber, EquipmentType type, BigDecimal rentPrice,
					 Condition condition)
	{
		super(id);
		this.name = name;
		this.serialNumber = serialNumber;
		this.type = type;
		this.rentPrice = rentPrice;
		this.condition = condition;
	}

	public Equipment(String name, String serialNumber, EquipmentType type, BigDecimal rentPrice,
					 Condition condition)
	{
		this.name = name;
		this.serialNumber = serialNumber;
		this.type = type;
		this.rentPrice = rentPrice;
		this.condition = condition;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSerialNumber()
	{
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber)
	{
		this.serialNumber = serialNumber;
	}

	public EquipmentType getType()
	{
		return type;
	}

	public void setType(EquipmentType type)
	{
		this.type = type;
	}

	public BigDecimal getRentPrice()
	{
		return rentPrice;
	}

	public void setRentPrice(BigDecimal rentPrice)
	{
		this.rentPrice = rentPrice;
	}

	public Condition getCondition()
	{
		return condition;
	}

	public void setCondition(Condition condition)
	{
		this.condition = condition;
	}
}
