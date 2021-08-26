package com.awrzosek.ski_station.cong_prize_management;

import com.awrzosek.ski_station.basic.BasicConsts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class QueueManager {
	public static int CLIENTS_IN_MINUTE;

	public Long calculateWaitTime() throws StabilityException
	{
		BigDecimal utilization =
				BigDecimal.valueOf(CLIENTS_IN_MINUTE / BasicConsts.SERVICE_RATE_PER_MINUTE);

		if (utilization.compareTo(BigDecimal.ONE) < 0)
		{
			BigDecimal divisor =
					BigDecimal.valueOf(2 * BasicConsts.SERVICE_RATE_PER_MINUTE * (1 - utilization.doubleValue()));
			BigDecimal quotient = utilization.divide(divisor, 10, RoundingMode.HALF_UP);
			return quotient.multiply(BigDecimal.valueOf(60)).longValue();
		} else
			throw new StabilityException("Uwaga! Długi czas oczekiwania!");
	}
}
