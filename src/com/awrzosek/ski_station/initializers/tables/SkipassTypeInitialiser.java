package com.awrzosek.ski_station.initializers.tables;

import com.awrzosek.ski_station.basic.BasicConsts;
import com.awrzosek.ski_station.initializers.BasicDataInitializer;
import com.awrzosek.ski_station.tables.ski.skipass.type.DiscountType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeDao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkipassTypeInitialiser extends BasicDataInitializer<SkipassType> {
	private static final int ONE_DAY = 1;
	private static final int THREE_DAYS = 3;
	private static final int ONE_WEEK = 7;
	private static final int TWO_WEEKS = 14;

	public SkipassTypeInitialiser(Connection connection)
	{
		dao = new SkipassTypeDao(connection);
	}

	//TODO ta metoda
	@Override
	protected List<SkipassType> provideRecords()
	{
		Map<Integer, BigDecimal> daysToSkipassPrize = new HashMap<>();
		daysToSkipassPrize.put(ONE_DAY, BasicConsts.ONE_DAY_SKIPASS_PRIZE);
		daysToSkipassPrize.put(THREE_DAYS, BasicConsts.THREE_DAYS_SKIPASS_PRIZE);
		daysToSkipassPrize.put(ONE_WEEK, BasicConsts.ONE_WEEK_SKIPASS_PRIZE);
		daysToSkipassPrize.put(TWO_WEEKS, BasicConsts.TWO_WEEKS_SKIPASS_PRIZE);

		List<SkipassType> skipassTypes = new ArrayList<>();

		for (DiscountType discountType : DiscountType.values())
		{
			for (Map.Entry<Integer, BigDecimal> dayToPrize : daysToSkipassPrize.entrySet())
				skipassTypes.add(new SkipassType(dayToPrize.getKey(), discountType,
						dayToPrize.getValue().multiply(BigDecimal.valueOf(discountType.getDiscount()))));
		}

		return skipassTypes;
	}
}
