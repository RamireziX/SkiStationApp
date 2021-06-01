package com.awrzosek.ski_station.tables.ski.skipass.map;

import java.util.HashMap;
import java.util.Map;

public enum Duration {
	ONE_DAY(1),
	THREE_DAYS(3),
	ONE_WEEK(7),
	TWO_WEEKS(14);

	private final int days;
	private static final Map<Integer, Duration> BY_DAYS = new HashMap<>();

	static
	{
		for (Duration duration : Duration.values())
			BY_DAYS.put(duration.days, duration);
	}

	Duration(int duration)
	{
		this.days = duration;
	}

	public static Duration valueOf(int days)
	{
		Duration duration = BY_DAYS.get(days);
		if (duration == null)
			throw new IllegalArgumentException("Nieprawidłowa długość karnetu: " + days);

		return duration;
	}

	public int getDays()
	{
		return this.days;
	}
}
