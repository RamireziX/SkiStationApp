package com.awrzosek.ski_station.hardware_connection;

import com.awrzosek.ski_station.basic.BasicConsts;
import com.awrzosek.ski_station.cong_prize_management.QueueManager;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;

import java.sql.Connection;
import java.sql.SQLException;

public class HardwareConnectionManager {
	private SkipassDao skipassDao;

	public HardwareConnectionManager(Connection connection)
	{
		skipassDao = new SkipassDao(connection);
	}

	public boolean registerEntry(Skipass skipass) throws SQLException
	{
		if (!skipass.isActive())
		{
			BasicConsts.ACTIVE_NO_OF_CLIENTS++;
			skipass.setActive(true);
			skipassDao.update(skipass);
			return true;
		}

		return false;
	}

	public boolean registerExit(Skipass skipass) throws SQLException
	{
		if (skipass.isActive())
		{
			BasicConsts.ACTIVE_NO_OF_CLIENTS--;
			skipass.setActive(false);
			skipassDao.update(skipass);
			return true;
		}

		return false;
	}

	public boolean registerLift(Skipass skipass)
	{
		if (skipass.isActive())
		{
			QueueManager.CLIENTS_IN_MINUTE++;
			return true;
		}

		return false;
	}

	public SkipassDao getSkipassDao()
	{
		return skipassDao;
	}
}
