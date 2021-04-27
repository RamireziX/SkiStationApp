package com.awrzosek.ski_station.initializers.tables;

import com.awrzosek.ski_station.initializers.BasicDataInitializer;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;

import java.sql.Connection;
import java.util.List;

public class SkipassInitializer extends BasicDataInitializer<Skipass> {

	public SkipassInitializer(Connection connection)
	{
		dao = new SkipassDao(connection);
	}

	@Override
	protected List<Skipass> provideRecords()
	{
		return null;
	}
}
