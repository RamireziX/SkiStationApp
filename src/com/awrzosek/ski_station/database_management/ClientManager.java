package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.basic.BasicConsts;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRent;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.RentType;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;
import com.awrzosek.ski_station.tables.ski.skipass.map.Duration;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMap;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMapConsts;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMapDao;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientManager {
	private ClientDao clientDao;
	private SkipassDao skipassDao;
	private EquipmentRentDao equipmentRentDao;
	private SkipassSkipassTypeMapDao sstmDao;

	//TODO może dać throws do wszystkich metod - i tak w gui mam try catch z sql
	public ClientManager(Connection connection)
	{
		clientDao = new ClientDao(connection);
		skipassDao = new SkipassDao(connection);
		equipmentRentDao = new EquipmentRentDao(connection);
		sstmDao = new SkipassSkipassTypeMapDao(connection);
	}

	public void addClient(Client client, HashMap<Equipment, RentType> equipmentsToRentType,
						  List<SkipassType> skipassTypes, Duration duration)
	{
		//TODO pomyśleć co z pokazywaniem błędów - ale to jak już będzie gui
		try
		{
			List<Skipass> skipasses = skipassDao.getNotRented(skipassTypes.size());
			if (skipasses.size() == skipassTypes.size())
			{
				clientDao.add(client);
				initSkipasses(skipasses, skipassTypes, client, duration);
				if (equipmentsToRentType != null)
					rentEquipments(equipmentsToRentType, client, duration);
			} else
				System.err.println("Wystąpił błąd - brak dostępnych skipassów!");//TODO może raczej boolean i false

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public boolean removeClient(Client client)
	{
		try
		{
			if (!clientDao.hasRentedEquipment(client))
			{
				unlinkSkipassesAndDelete(client);
				return true;
			}
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
		return false;
	}

	public void removeRentedEquipment(EquipmentRent equipmentRent)
	{
		try
		{
			equipmentRentDao.delete(equipmentRent);
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
	}

	public void removeAllRentedEquipment(Client client)
	{
		try
		{
			for (EquipmentRent equipmentRent : equipmentRentDao.listByClient(client))
				equipmentRentDao.delete(equipmentRent);
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
	}

	public void addRentedEquipment(Client client, HashMap<Equipment, RentType> equipmentsToRentType)
	{
		try
		{
			Skipass skipass = skipassDao.listByClient(client).get(0);
			for (Map.Entry<Equipment, RentType> eq : equipmentsToRentType.entrySet())
			{
				EquipmentRent equipmentRent = new EquipmentRent(client.getId(), eq.getKey().getId(), LocalDate.now(),
						skipass.getDateTo(), eq.getValue());
				equipmentRentDao.add(equipmentRent);
			}
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
	}

	//TODO add/delete skipass

	public ClientDao getClientDao()
	{
		return clientDao;
	}

	public boolean unlinkSelectedSkipasses(Client client, List<Skipass> skipasses) throws SQLException
	{
		if (skipassDao.listByClient(client).size() == skipasses.size())
			return false;

		for (Skipass s : skipasses)
			unlinkSkipass(s);

		return true;
	}

	public boolean initialiseSkipass(Client client, SkipassType skipassType, Duration duration) throws SQLException
	{
		List<Skipass> skipasses = skipassDao.getNotRented(1);
		if (skipasses.size() != 0)
		{
			Skipass skipass = skipasses.get(0);
			initSkipassValues(client, duration, skipass, skipassType);
			return true;
		}

		return false;
	}

	private void unlinkSkipass(Skipass s) throws SQLException
	{
		//@formatter:off
			String query =
					"select * from " + SkipassSkipassTypeMapConsts.TAB_NAME +
							" where " + SkipassSkipassTypeMapConsts.FLD_SKIPASS_ID + " = " + s.getId();
		//@formatter:on
		SkipassSkipassTypeMap sstm = sstmDao.getByQuery(query).orElse(null);
		if (sstm != null)
			sstmDao.delete(sstm);
		s.clearData();
		skipassDao.update(s);
	}

	private void unlinkSkipassesAndDelete(Client client) throws SQLException
	{
		for (Skipass s : skipassDao.listByClient(client))
			unlinkSkipass(s);

		clientDao.delete(client);
	}

	private void initSkipasses(List<Skipass> skipasses, List<SkipassType> skipassTypes, Client client,
							   Duration duration) throws SQLException
	{
		int i = 0;
		for (Skipass skipass : skipasses)
		{
			SkipassType skipassType = skipassTypes.get(i);
			initSkipassValues(client, duration, skipass, skipassType);
			i++;
		}
	}

	private void initSkipassValues(Client client, Duration duration, Skipass skipass, SkipassType skipassType)
			throws SQLException
	{
		BigDecimal price = calculatePrice(duration, skipassType);
		SkipassSkipassTypeMap sstm = new SkipassSkipassTypeMap(skipass.getId(), skipassType.getId(),
				duration, price.setScale(2, RoundingMode.HALF_UP));
		sstmDao.add(sstm);
		skipass.setClientId(client.getId());
		skipass.setDateFrom(LocalDate.now());
		skipass.setDateTo(LocalDate.now().plusDays(duration.getDays()));
		skipass.setRented(true);
		skipass.setActive(false);
		skipassDao.update(skipass);
	}

	private BigDecimal calculatePrice(Duration duration, SkipassType skipassType)
	{
		BigDecimal price = new BigDecimal(0);
		BigDecimal discount = skipassType.getDiscount();

		switch (duration)
		{
			case ONE_DAY:
				price = BasicConsts.ONE_DAY_SKIPASS_PRICE.multiply(discount);
				break;
			case THREE_DAYS:
				price = BasicConsts.THREE_DAYS_SKIPASS_PRICE.multiply(discount);
				break;
			case ONE_WEEK:
				price = BasicConsts.ONE_WEEK_SKIPASS_PRICE.multiply(discount);
				break;
			case TWO_WEEKS:
				price = BasicConsts.TWO_WEEKS_SKIPASS_PRICE.multiply(discount);
				break;
		}
		return price;
	}

	private void rentEquipments(HashMap<Equipment, RentType> equipmentsToRentType, Client client,
								Duration duration) throws SQLException
	{
		for (Map.Entry<Equipment, RentType> equipmentToRent : equipmentsToRentType.entrySet())
		{
			EquipmentRent rent = new EquipmentRent();
			rent.setClientId(client.getId());
			rent.setEquipmentId(equipmentToRent.getKey().getId());
			rent.setRentDate(LocalDate.now());
			rent.setReturnDate(LocalDate.now().plusDays(duration.getDays()));
			rent.setRentType(equipmentToRent.getValue());
			equipmentRentDao.add(rent);
		}
	}
}
