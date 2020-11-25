package com.awrzosek.ski_station.tables.ski.equipment;

public enum RentType {
    TO_GO("Zabierany przez klienta"),
    STAY("Zostawiany na stacji");

    private final String label;

    RentType(String label){
        this.label = label;
    }

    public String toString()
    {
        return this.label;
    }
}
