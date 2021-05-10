package com.awrzosek.ski_station.basic;

import java.math.BigDecimal;

public final class BasicConsts {

	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:./sql/SkiStation.db?foreign_keys=on";

	public static final String EMPTY_STRING = "";

	//TODO - te parametry możnaby potem jakoś ustawiać przez program też, ofc skipass prize bedzie liczone

	public static final int MAX_NO_OF_CLIENTS = 50;
	public static final double NO_OF_CLIENTS_PER_MIN = 2.0;
	public static int ACTIVE_NO_OF_CLIENTS;

	public static BigDecimal ONE_DAY_SKIPASS_PRIZE = new BigDecimal("50.00");
	public static BigDecimal THREE_DAYS_SKIPASS_PRIZE = new BigDecimal("120.00");
	public static BigDecimal ONE_WEEK_SKIPASS_PRIZE = new BigDecimal("250.00");
	public static BigDecimal TWO_WEEKS_SKIPASS_PRIZE = new BigDecimal("400.00");
}
