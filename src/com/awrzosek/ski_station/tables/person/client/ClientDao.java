package com.awrzosek.ski_station.tables.person.client;

import com.awrzosek.ski_station.basic.BasicDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.awrzosek.ski_station.tables.person.client.ClientConsts.*;

public class ClientDao implements BasicDao<Client> {
    @Override
    public Optional<Client> get(Long id)
    {
        return Optional.empty();
    }

    @Override
    public List<Client> getAll()
    {
        List<Client> clients = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from " + TAB_NAME))
        {
            while(result.next())
                clients.add(processClientForSelect(result));
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
        return clients;
    }

    @Override
    public boolean add(Client client)
    {
        //@formatter:off
        String query =
                "insert into " + TAB_NAME +
                        " ( " + FLD_FIRST_NAME + ", " +
                        FLD_SECOND_NAME + ", " +
                        FLD_SURNAME + ", " +
                        FLD_DATE_OF_BIRTH + ", " +
                        FLD_PESEL + ", " +
                        FLD_PERSONAL_ID_NUMBER + ", " +
                        FLD_DATE_ENTERED + ", " +
                        FLD_PHONE + ", " +
                        FLD_E_MAIL + ") " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        //@formatter:on

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            processClientForAdding(client, preparedStatement);
            return preparedStatement.execute();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Client client)
    {
        return true;
    }

    @Override
    public boolean delete(Client client)
    {
        //@formatter:off
        String query =
                "delete from " + TAB_NAME + " where " + FLD_ID + " = ?";
        //@formatter:on

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, client.getId());
            return preparedStatement.execute();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private Client processClientForSelect(ResultSet result) throws SQLException
    {
        LocalDate dateOfBirth = result.getDate(FLD_DATE_OF_BIRTH).toLocalDate();
        LocalDate dateEntered = result.getDate(FLD_DATE_ENTERED).toLocalDate();

        return new Client(result.getLong(FLD_ID), result.getString(FLD_FIRST_NAME), result.getString(FLD_SECOND_NAME),
                result.getString(FLD_SURNAME), dateOfBirth, result.getString(FLD_PESEL),
                result.getString(FLD_PERSONAL_ID_NUMBER), result.getString(FLD_PHONE),
                result.getString(FLD_E_MAIL), dateEntered);
    }

    private void processClientForAdding(Client client, PreparedStatement preparedStatement) throws SQLException
    {
        preparedStatement.setString(1, client.getFirstName());
        preparedStatement.setString(2, client.getSecondName());
        preparedStatement.setString(3, client.getSurname());
        preparedStatement.setDate(4, Date.valueOf(client.getDateOfBirth()));
        preparedStatement.setString(5, client.getPesel());
        preparedStatement.setString(6, client.getPersonalIdNumber());
        preparedStatement.setDate(7, Date.valueOf(client.getDateEntered()));
        preparedStatement.setString(8, client.getPhone());
        preparedStatement.setString(9, client.getEMail());
    }
}
