package model;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    public void loadDatabase() throws SQLException {
        String url = "jdbc:sqlite:./dictionary.db";

        Connection connection = DriverManager.getConnection(url);
        if (connection != null) {

            DatabaseMetaData meta = connection.getMetaData();

            Statement statement = getStatement(connection);
            /**item en el listado debe indicar el termino de buscado, la pagina seleccionada y la fecha en que fue realizada la busqueda */
            statement.executeUpdate("create table if not exists history (id INTEGER, searchTerm string PRIMARY KEY, selectedPage string, date string)");
            statement.executeUpdate("create table if not exists catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");

        }
    }

    public ArrayList<String> getTitles() throws SQLException{
        ArrayList<String> titles = new ArrayList<>();
        Connection connection = getConnection();

        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = getStatement(connection);

        ResultSet resultSet = statement.executeQuery("select * from catalog");
        while(resultSet.next()) titles.add(resultSet.getString("title"));

        if(connection != null)
            connection.close();

        return titles;
    }
    public ArrayList<String> getHistory() throws SQLException{
        ArrayList<String> history = new ArrayList<>();
        Connection connection = getConnection();

        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = getStatement(connection);

        ResultSet resultSet = statement.executeQuery("select * from history");
        while(resultSet.next()) history.add(resultSet.getString("searchTerm")+" / "+resultSet.getString("selectedPage")+" / "+resultSet.getString("date"));

        if(connection != null)
            connection.close();

        return history;
    }
    public boolean saveHistory(String userSearchTerm, String selectedPage, String date) throws  SQLException{
        boolean itWasDone = false;
        Connection connection = getConnection();

        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

        Statement statement = getStatement(connection);

        statement.executeUpdate("replace into history values(null, '"+ userSearchTerm + "', '"+ selectedPage + "', '"+date+"')");

        if(connection != null)
            connection.close();

        itWasDone = true;
        return itWasDone;
    }
    public boolean saveInfo(String title, String extract) throws SQLException {
        boolean itWasDone = false;
        Connection connection = getConnection();

        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

        Statement statement = getStatement(connection);

        statement.executeUpdate("replace into catalog values(null, '" + title + "', '" + extract + "', 1)");

        if(connection != null)
            connection.close();

        itWasDone = true;
        return itWasDone;
    }

    public String getContent(String title) throws SQLException {
        String resultSetString = null;
        Connection connection = getConnection();

        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = getStatement(connection);

        ResultSet resultSet = statement.executeQuery("select * from catalog WHERE title = '" + title + "'" );
        resultSet.next();
        resultSetString = resultSet.getString("extract");

        if(connection != null)
            connection.close();

        return resultSetString;
    }

    public boolean deleteEntry(String title) throws SQLException {
        boolean itWasDone = false;
        Connection connection = getConnection();

        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = getStatement(connection);

        statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'" );
        if(connection != null)
            connection.close();
        itWasDone = true;
        return itWasDone;
    }
    private Statement getStatement(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        return statement;
    }

    private Connection getConnection(){
        Connection connection = null;
        return connection;
    }
}

