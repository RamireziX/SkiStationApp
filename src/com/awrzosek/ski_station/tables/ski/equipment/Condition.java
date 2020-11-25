package com.awrzosek.ski_station.tables.ski.equipment;

public enum Condition {
    EX("Świetna"),
    GOOD("Dobra"),
    AVG("Średnia"),
    POOR("Słaba"),
    BROKEN("Zniszczony");

    private final String label;

    Condition(String label){
        this.label = label;
    }

    public String toString()
    {
        return this.label;
    }
}
