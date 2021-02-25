package com.awrzosek.ski_station;

import com.awrzosek.ski_station.tables.ski.skipass.type.DiscountType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeDao;

import java.math.BigDecimal;
import java.util.List;

public class Main {

	public static void main(String[] args)
	{
		//TODO eq rent i skipass dao - uwaga maja fk
		SkipassTypeDao skipassTypeDao = new SkipassTypeDao();
		skipassTypeDao
				.add(new SkipassType(null, 7, DiscountType.GROUP_20, new BigDecimal(13.32)));
		skipassTypeDao
				.add(new SkipassType(null, 14, DiscountType.SENIORS_65, new BigDecimal(10)));
		skipassTypeDao
				.add(new SkipassType(null, 30, DiscountType.JUNIORS, new BigDecimal(100)));
		List<SkipassType> l = skipassTypeDao.getAll();
		SkipassType skipassType = skipassTypeDao.get(3L).orElse(null);
		skipassType.setDuration(3000);
		skipassTypeDao.update(skipassType);
		skipassTypeDao.delete(skipassType);


		System.out.println("end");

	}
}
