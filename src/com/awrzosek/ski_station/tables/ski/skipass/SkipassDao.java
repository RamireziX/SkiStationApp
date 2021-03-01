package com.awrzosek.ski_station.tables.ski.skipass;

import com.awrzosek.ski_station.basic.BasicDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SkipassDao extends BasicDao<Skipass> {
	@Override
	public Optional<Skipass> get(Long id)
	{
		return Optional.empty();
	}

	@Override
	public List<Skipass> getAll()
	{
		return null;
	}

	@Override
	public boolean add(Skipass skipass)
	{
		return true;
	}

	@Override
	public boolean update(Skipass skipass)
	{
		return true;
	}

	@Override
	public boolean delete(Skipass skipass)
	{
		return true;
	}

	@Override
	protected Skipass processForSelect(ResultSet result) throws SQLException
	{
		return null;
	}

	@Override
	protected void processForAdding(Skipass skipass, PreparedStatement preparedStatement) throws SQLException
	{

	}
}
