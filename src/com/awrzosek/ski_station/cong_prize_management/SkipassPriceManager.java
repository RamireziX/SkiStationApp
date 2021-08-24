package com.awrzosek.ski_station.cong_prize_management;

import com.awrzosek.ski_station.basic.BasicConsts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class SkipassPriceManager {//TODO zawrzeć to jakoś fajnie w ui - może być póki co brzydko

	private static final BigDecimal NOK_TO_ZL_IN_DECEMBER_2016 = BigDecimal.valueOf(0.49);

	public BigDecimal calculateSkipassPrice(BigDecimal c, BigDecimal a, BigDecimal b, BigDecimal minimalPrice,
											BigDecimal maximumPrice)
	{
		List<BigDecimal> priceRange = new ArrayList<>();
		for (BigDecimal i = minimalPrice; i.compareTo(maximumPrice) < 0; i = i.add(BigDecimal.ONE))
			priceRange.add(i);

		HashMap<BigDecimal, BigDecimal> priceToRevenue = new HashMap<>();
		BigDecimal power, eToPower, dividend, divisor, demand, revenue;
		for (BigDecimal p : priceRange)
		{
			power = (b.multiply(p)).add(a);
			eToPower = BigDecimal.valueOf(Math.pow(Math.E, power.doubleValue()));
			dividend = (eToPower).multiply(c);
			divisor = eToPower.add(BigDecimal.ONE);
			demand = dividend.divide(divisor, RoundingMode.HALF_UP);

			if (demand.intValue() > BasicConsts.MAX_NO_OF_CLIENTS)
				demand = BigDecimal.valueOf(BasicConsts.MAX_NO_OF_CLIENTS);

			revenue = demand.multiply(p);
			priceToRevenue.put(p, revenue);
		}

		BigDecimal finalPrice = Collections.max(priceToRevenue.entrySet(), Map.Entry.comparingByValue()).getKey();
		BigDecimal finalPriceInZl = finalPrice.multiply(NOK_TO_ZL_IN_DECEMBER_2016);

		return finalPriceInZl.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal calculateThreeDaysPrice(BigDecimal oneDayPrice)
	{
		BigDecimal threeDaysPrice = oneDayPrice.multiply(BigDecimal.valueOf(3)).multiply(BigDecimal.valueOf(0.8));
		return threeDaysPrice.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal calculateOneWeekDaysPrice(BigDecimal oneDayPrice)
	{
		BigDecimal oneWeekPrice = oneDayPrice.multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(0.7));
		return oneWeekPrice.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal calculateTwoWeeksDaysPrice(BigDecimal oneDayPrice)
	{
		BigDecimal twoWeeksPrice = oneDayPrice.multiply(BigDecimal.valueOf(14)).multiply(BigDecimal.valueOf(0.6));
		return twoWeeksPrice.setScale(2, RoundingMode.HALF_UP);
	}
}
