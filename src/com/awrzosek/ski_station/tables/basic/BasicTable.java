package com.awrzosek.ski_station.tables.basic;

public abstract class BasicTable {
	protected Long id;

	public BasicTable()
	{
	}

	public BasicTable(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
