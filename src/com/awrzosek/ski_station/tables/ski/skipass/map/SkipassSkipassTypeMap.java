package com.awrzosek.ski_station.tables.ski.skipass.map;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.math.BigDecimal;

public class SkipassSkipassTypeMap extends BasicTable {
	private Long skipassId;
	private Long skipassTypeId;
	private Duration duration;
	private BigDecimal price;

	public SkipassSkipassTypeMap()
	{
	}

	public SkipassSkipassTypeMap(Long skipassId, Long skipassTypeId, Duration duration, BigDecimal price)
	{
		this.skipassId = skipassId;
		this.skipassTypeId = skipassTypeId;
		this.duration = duration;
		this.price = price;
	}

	public SkipassSkipassTypeMap(Long id, Long skipassId, Long skipassTypeId, Duration duration, BigDecimal price)
	{
		super(id);
		this.skipassId = skipassId;
		this.skipassTypeId = skipassTypeId;
		this.duration = duration;
		this.price = price;
	}

	public Long getSkipassId()
	{
		return skipassId;
	}

	public void setSkipassId(Long skipassId)
	{
		this.skipassId = skipassId;
	}

	public Long getSkipassTypeId()
	{
		return skipassTypeId;
	}

	public void setSkipassTypeId(Long skipassTypeId)
	{
		this.skipassTypeId = skipassTypeId;
	}

	public Duration getDuration()
	{
		return duration;
	}

	public void setDuration(Duration duration)
	{
		this.duration = duration;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}
}
