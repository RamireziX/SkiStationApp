package com.awrzosek.ski_station.tables.ski.skipass.type;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.math.BigDecimal;

public class SkipassType extends BasicTable {
	private int duration;//TODO zamienić to na enum 1, 3, 7, 14
	private DiscountType discountType;
	private BigDecimal price;

	public SkipassType()
	{
	}

	public SkipassType(Long id, int duration, DiscountType discountType, BigDecimal price)
	{
		super(id);
		this.duration = duration;
		this.discountType = discountType;
		this.price = price;
	}

	public SkipassType(int duration, DiscountType discountType, BigDecimal price)
	{
		this.duration = duration;
		this.discountType = discountType;
		this.price = price;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public DiscountType getDiscountType()
	{
		return discountType;
	}

	public void setDiscountType(DiscountType discountType)
	{
		this.discountType = discountType;
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
