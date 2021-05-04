package com.awrzosek.ski_station.initializers.tables;

import com.awrzosek.ski_station.initializers.BasicDataInitializer;
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

	//TODO ta metoda
	@Override
	protected List<SkipassType> provideRecords()
	{
		List<SkipassType> skipassTypes = new ArrayList<>();

		return skipassTypes;
	}
}
