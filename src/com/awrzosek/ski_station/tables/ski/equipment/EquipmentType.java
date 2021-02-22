package com.awrzosek.ski_station.tables.ski.equipment;

public enum EquipmentType {
	SKIS("Narty"),
	SNOWBOARD("Snowboard"),
	SKI_POLE("Kije"),
	SHOES("Buty"),
	HELMET("Kask"),
	GOOGLES("Gogle");

	private final String label;

	EquipmentType(String label)
	{
		this.label = label;
	}

	public String toString()
	{
		return this.label;
	}
}
