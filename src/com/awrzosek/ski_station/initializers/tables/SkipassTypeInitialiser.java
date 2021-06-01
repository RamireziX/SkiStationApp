package com.awrzosek.ski_station.initializers.tables;

import com.awrzosek.ski_station.initializers.BasicDataInitializer;
import com.awrzosek.ski_station.tables.ski.skipass.type.DiscountType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SkipassTypeInitialiser extends BasicDataInitializer<SkipassType> {

	public SkipassTypeInitialiser(Connection connection)
	{
		dao = new SkipassTypeDao(connection);
	}

	@Override
	protected List<SkipassType> provideRecords()
	{
		List<SkipassType> skipassTypes = new ArrayList<>();

		for (DiscountType discountType : DiscountType.values())
			skipassTypes.add(new SkipassType(discountType));

		return skipassTypes;
	}
}
