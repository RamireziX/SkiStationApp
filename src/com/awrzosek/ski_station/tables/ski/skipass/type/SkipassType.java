package com.awrzosek.ski_station.tables.ski.skipass.type;

import com.awrzosek.ski_station.tables.basic.BasicTable;

public class SkipassType extends BasicTable {
	private DiscountType discountType;

	public SkipassType()
	{
	}

	public SkipassType(Long id, DiscountType discountType)
	{
		super(id);
		this.discountType = discountType;
	}

	public SkipassType(DiscountType discountType)
	{
		this.discountType = discountType;
	}

	public DiscountType getDiscountType()
	{
		return discountType;
	}

	public void setDiscountType(DiscountType discountType)
	{
		this.discountType = discountType;
	}
}
