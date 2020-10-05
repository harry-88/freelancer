package sample;

import java.sql.*;

public class Database {
    private Connection connection;
    public Database(Connection connection)
    {
        this.connection = connection;
    }

    public void createTable()
    {
        String query = "CREATE TABLE IF NOT EXISTS account(userId int primary key unique ,name varchar(55),password varchar(55))";
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public boolean checkUserById(String id,String password)
    {
        String query = "SELECT * FROM account WHERE userId = ? AND  password=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,password);
            ResultSet resultSet = null;
            resultSet = preparedStatement.executeQuery();
            resultSet.absolute(1);
             if (resultSet != null)
                if (resultSet.getString(1).equals(id) && resultSet.getString(3).equals(password))
                    return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean checkUserByName(String name,String password)
    {
        String query = "SELECT * FROM account WHERE name = ? AND  password=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.absolute(1);
            if (resultSet!= null)
                if (resultSet.getString(1).equals(name) && resultSet.getString(3).equals(password))
                    return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void addData(String id,String name,String password)
    {

        String query = "INSERT INTO account(userId,name,password)VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,password);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isPresent(String id)
    {
        ResultSet resultSet = null;
        String rol = null;
        String query = "SELECT * FROM account WHERE userId =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                rol = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (rol == null)
            return false;
        else
            return true;
    }


}
