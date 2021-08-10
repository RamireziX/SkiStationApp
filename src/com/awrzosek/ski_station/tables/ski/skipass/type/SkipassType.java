package com.awrzosek.ski_station.tables.ski.skipass.type;

import com.awrzosek.ski_station.tables.basic.BasicTable;

import java.math.BigDecimal;

public class SkipassType extends BasicTable {
	private String discountDescription;
	private BigDecimal discount;

	public SkipassType()
	{
	}

	public SkipassType(Long id, String discountDescription, BigDecimal discount)
	{
		super(id);
		this.discountDescription = discountDescription;
		this.discount = discount;
	}

	public SkipassType(String discountDescription, BigDecimal discount)
	{
		this.discountDescription = discountDescription;
		this.discount = discount;
	}

	public String getDiscountDescription()
	{
		return discountDescription;
	}

	public void setDiscountDescription(String discountDescription)
	{
		this.discountDescription = discountDescription;
	}

	public BigDecimal getDiscount()
	{
		return discount;
	}

	public void setDiscount(BigDecimal discount)
	{
		this.discount = discount;
	}
}
