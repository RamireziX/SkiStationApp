package com.awrzosek.ski_station.tables.ski.skipass.type;

import java.math.BigDecimal;

public enum DiscountType {
	REGULAR("Normalny", BigDecimal.valueOf(1.0)),
	KIDS("Dzieci do 10 roku życia", BigDecimal.valueOf(0.5)),
	JUNIORS("Młodzież do 15 roku życia", BigDecimal.valueOf(0.7)),
	SENIORS_65("Seniorzy między 65 a 74 rokiem życia", BigDecimal.valueOf(0.7)),
	SENIORS_75("Seniorzy powyżej 75 roku życia", BigDecimal.valueOf(0.5)),
	KIDS_UNDER_120("Dzieci poniżej 120 cm wzrostu", BigDecimal.valueOf(0.0)),
	GROUP_20("Grupa od 20 osób", BigDecimal.valueOf(0.7));

	private final String label;
	private final BigDecimal discount;

	DiscountType(String label, BigDecimal discount)
	{
		this.label = label;
		this.discount = discount;
	}

	public String toString()
	{
		return this.label;
	}

	public BigDecimal getDiscount()
	{
		return this.discount;
	}
}
