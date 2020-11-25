package com.awrzosek.ski_station.tables.ski.skipass;

public enum DiscountType {
    KIDS("Dzieci do 10 roku życia"),
    JUNIORS("Młodzież do 15 roku życia"),
    SENIORS_65("Seniorzy między 65 a 74 rokiem życia"),
    SENIORS_75("Seniorzy powyżej 75 roku życia"),
    KIDS_UNDER_120("Dzieci poniżej 120 cm wzrostu"),
    GROUP_20("Grupa od 20 osób");

    private final String label;

    DiscountType(String label){
        this.label = label;
    }

    public String toString()
    {
        return this.label;
    }
}
