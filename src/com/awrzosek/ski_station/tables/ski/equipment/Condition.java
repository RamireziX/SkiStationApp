package com.awrzosek.ski_station.tables.ski.equipment;

public enum Condition {
	EX("Świetny"),
	GOOD("Dobry"),
	AVG("Średni"),
	POOR("Słaby"),
	BROKEN("Zniszczony");

	private final String label;

	Condition(String label)
	{
		this.label = label;
	}

	public String toString()
	{
		return this.label;
	}
}
