package com.awrzosek.ski_station.cong_prize_management;

import com.awrzosek.ski_station.basic.BasicConsts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class QueueManager {//TODO żeby może zwracało sekundy (ale może to już gui zrobi) + raczej bedziemy opierać
	// głównie o stability i może nie pokazywać czasu a stabilna/niestabilna
	public static int CLIENTS_IN_MINUTE;

	//TODO - F2.2 + ta ilość klientów może być w klasie i jakaś metoda co zeruje to co 60 sekund

	public BigDecimal calculateWaitTime() throws Exception
	{
		BigDecimal utilization =
				BigDecimal.valueOf(CLIENTS_IN_MINUTE / BasicConsts.SERVICE_RATE_PER_MINUTE);
		if (utilization.compareTo(BigDecimal.ONE) < 0)
		{
			BigDecimal divisor =
					BigDecimal.valueOf(2 * BasicConsts.SERVICE_RATE_PER_MINUTE * (1 - utilization.doubleValue()));
			return utilization.divide(divisor, 10, RoundingMode.HALF_UP);
		} else
			throw new Exception("stability exc");//TODO jakoś to ogarnąć
	}
}
