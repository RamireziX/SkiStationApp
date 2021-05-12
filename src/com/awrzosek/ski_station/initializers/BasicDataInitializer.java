package com.awrzosek.ski_station.initializers;

import com.awrzosek.ski_station.tables.basic.BasicDao;

import java.sql.SQLException;
import java.util.List;

public abstract class BasicDataInitializer<T> {
	protected BasicDao<T> dao;

	public void initializeData() throws SQLException
	{
		if (dao.checkTableIfEmpty())
		{
			List<T> records = provideRecords();
			for (T record : records)
				dao.add(record);
		}
	}

	protected abstract List<T> provideRecords();

}
