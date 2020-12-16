package com.awrzosek.ski_station.tables.person.client;

import com.awrzosek.ski_station.basic.BasicDao;
import static com.awrzosek.ski_station.tables.person.client.ClientConsts.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public void add(Client client)
    {
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into " + TAB_NAME + " (FIRST_NAME) values (\"name\");"))
        {
            //processClientForAdding(client, preparedStatement);
            preparedStatement.execute();

        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }


    }

    @Override
    public void update(Client client)
    {

    }

    @Override
    public void delete(Client client)
    {

    }

    private Client processClientForSelect(ResultSet result) throws SQLException
    {
        Date dateOfBirth = result.getTimestamp(FLD_DATE_OF_BIRTH);
        Date dateEntered = result.getTimestamp(FLD_DATE_ENTERED);

        return new Client( Long.valueOf(result.getLong(FLD_ID)), result.getString(FLD_FIRST_NAME), result.getString(FLD_SECOND_NAME),
                result.getString(FLD_SURNAME), dateOfBirth, result.getString(FLD_PESEL),
                result.getString(FLD_PERSONAL_ID_NUMBER), result.getString(FLD_PHONE),
                result.getString(FLD_E_MAIL), dateEntered);
    }

    private void processClientForAdding(Client client, PreparedStatement preparedStatement) throws SQLException
    {
        preparedStatement.setString(1, client.getFirstName());
        preparedStatement.setString(2, client.getSecondName());
        preparedStatement.setString(3, client.getSurname());
        preparedStatement.setDate(4, (java.sql.Date) client.getDateOfBirth());
        preparedStatement.setString(5, client.getPesel());
        preparedStatement.setString(6, client.getPersonalIdNumber());
        preparedStatement.setDate(7, (java.sql.Date) client.getDateEntered());
        preparedStatement.setString(8, client.getPhone());
        preparedStatement.setString(9, client.getEMail());
    }
}
