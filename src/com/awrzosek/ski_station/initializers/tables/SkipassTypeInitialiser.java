package com.awrzosek.ski_station.initializers.tables;

import com.awrzosek.ski_station.initializers.BasicDataInitializer;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeDao;

import java.math.BigDecimal;
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

		skipassTypes.add(new SkipassType("Normalny", BigDecimal.valueOf(1.0)));
		skipassTypes.add(new SkipassType("Dzieci do 10 roku życia", BigDecimal.valueOf(0.5)));
		skipassTypes.add(new SkipassType("Młodzież do 15 roku życia", BigDecimal.valueOf(0.7)));
		skipassTypes.add(new SkipassType("Seniorzy między 65 a 74 rokiem życia", BigDecimal.valueOf(0.7)));
		skipassTypes.add(new SkipassType("Seniorzy powyżej 75 roku życia", BigDecimal.valueOf(0.5)));
		skipassTypes.add(new SkipassType("Dzieci poniżej 120 cm wzrostu", BigDecimal.valueOf(0.0)));
		skipassTypes.add(new SkipassType("Grupa od 20 osób", BigDecimal.valueOf(0.7)));


		return skipassTypes;
	}
}
