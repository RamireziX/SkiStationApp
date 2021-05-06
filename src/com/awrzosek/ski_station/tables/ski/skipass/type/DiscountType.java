package com.awrzosek.ski_station.tables.ski.skipass.type;

public enum DiscountType {
	REGULAR("Normalny", 1.0),
	KIDS("Dzieci do 10 roku życia", 0.5),
	JUNIORS("Młodzież do 15 roku życia", 0.7),
	SENIORS_65("Seniorzy między 65 a 74 rokiem życia", 0.7),
	SENIORS_75("Seniorzy powyżej 75 roku życia", 0.5),
	KIDS_UNDER_120("Dzieci poniżej 120 cm wzrostu", 0.0),
	GROUP_20("Grupa od 20 osób", 0.7);

	private final String label;
	private final double discount;

	DiscountType(String label, double discount)
	{
		this.label = label;
		this.discount = discount;
	}

	public String toString()
	{
		return this.label;
	}

	public double getDiscount()
	{
		return this.discount;
	}
}
