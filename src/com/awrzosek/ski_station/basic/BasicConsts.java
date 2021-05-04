package com.awrzosek.ski_station.basic;

public final class BasicConsts {
	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:./sql/SkiStation.db?foreign_keys=on";
	//TODO - te parametry możnaby potem jakoś ustawiać przez program też
	public static final int MAX_NO_OF_CLIENTS = 10;
	public static final double NO_OF_CLIENTS_PER_MIN = 2.0;
	public static int ACTIVE_NO_OF_CLIENTS;
}
