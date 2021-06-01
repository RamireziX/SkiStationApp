package com.awrzosek.ski_station.initializers.tables;

import com.awrzosek.ski_station.basic.BasicConsts;
import com.awrzosek.ski_station.initializers.BasicDataInitializer;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SkipassInitializer extends BasicDataInitializer<Skipass> {

	public SkipassInitializer(Connection connection)
	{
		dao = new SkipassDao(connection);
	}

	@Override
	protected List<Skipass> provideRecords()
	{
		List<Skipass> skipasses = new ArrayList<>();

		for (int i = 0; i < BasicConsts.MAX_NO_OF_CLIENTS; i++)
			skipasses.add(new Skipass(null, null, false, false, null, null));

		return skipasses;
	}
}
