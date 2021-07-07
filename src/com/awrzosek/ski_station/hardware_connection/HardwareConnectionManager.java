package com.awrzosek.ski_station.hardware_connection;

import com.awrzosek.ski_station.basic.BasicConsts;
import com.awrzosek.ski_station.cong_prize_management.QueueManager;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class HardwareConnectionManager {
	private SkipassDao skipassDao;

	public HardwareConnectionManager(Connection connection)
	{
		skipassDao = new SkipassDao(connection);
	}

	public void displayWaitTime(BigDecimal waitTime)
	{//TODO to dopiero jak będzie gui
	}

	//TODO można exception albo zwracanie stringa albo boola - żeby gui mogło błąd wyświetlić
	public void registerEntry(Skipass skipass) throws SQLException
	{
		if(!skipass.isActive())
		{
			BasicConsts.ACTIVE_NO_OF_CLIENTS++;
			skipass.setActive(true);
			skipassDao.update(skipass);
		}
		else
			System.out.println("Błąd karnetu, proszę skontaktować się z obsługą!");
	}

	public void registerExit(Skipass skipass) throws SQLException
	{
		if(skipass.isActive())
		{
			BasicConsts.ACTIVE_NO_OF_CLIENTS++;
			skipass.setActive(false);
			skipassDao.update(skipass);
		}
		else
			System.out.println("Błąd karnetu, proszę skontaktować się z obsługą!");
	}

	public void registerLift(Skipass skipass)
	{
		if(skipass.isActive())
			QueueManager.CLIENTS_IN_MINUTE++;
		else
			System.out.println("Błąd karnetu, proszę skontaktować się z obsługą!");
	}

}
