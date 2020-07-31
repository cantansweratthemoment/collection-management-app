package laba_8.Database;

import laba_8.Classes.*;
import laba_8.Utils.CollectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrganizationsBase {
    private Statement statement;
    private static Connection connection;

    public OrganizationsBase(Connection connection) throws SQLException {
        OrganizationsBase.connection = connection;
        this.statement = connection.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS  organizations " +
                "(id serial primary key not null," +
                " owner TEXT NOT NULL , " +
                " name TEXT NOT NULL , " +
                " x DOUBLE PRECISION NOT NULL, " +
                " y DOUBLE PRECISION NOT NULL, " +
                " creationDate TEXT NOT NULL , " +
                " annualTurnover DOUBLE PRECISION NOT NULL," +
                " organizationType TEXT NOT NULL, " +
                " street TEXT NOT NULL, " +
                " zipCode TEXT NOT NULL , " +
                " location_x DOUBLE PRECISION NOT NULL , " +
                " location_y DOUBLE PRECISION NOT NULL , " +
                " town TEXT NOT NULL," +
                "color TEXT NOT NULL)";
        statement.execute(createTableSQL);
    }

    public static void loadCollection(List<Organization> organizations) throws SQLException {
        String query = " SELECT * FROM ORGANIZATIONS ORDER by id;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        organizations.clear();
        while (resultSet.next()) {
            String str = resultSet.getString(6);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            organizations.add(new Organization(resultSet.getInt(1), resultSet.getString(3),
                    new Coordinates(resultSet.getDouble(4), resultSet.getDouble(5)),
                    LocalDateTime.parse(str, dtf),
                    resultSet.getDouble(7), OrganizationType.valueOf(resultSet.getString(8)), new Address(resultSet.getString(9),
                    resultSet.getString(10), new Location(resultSet.getFloat(11), resultSet.getFloat(12), resultSet.getString(13))), resultSet.getString(2), resultSet.getString(14)));
        }
    }

    public void addOrganizationToTheBase(Organization organization, int id) {
        if (id == -1) {
            try {
                String sql = "INSERT INTO organizations (owner , name, x, y, creationDate, annualTurnover, organizationType, street, zipCode, location_x, location_y, town, color) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, organization.getOwner());
                preparedStatement.setString(2, organization.getName());
                preparedStatement.setDouble(3, organization.getCoordinates().getX());
                preparedStatement.setDouble(4, organization.getCoordinates().getY());
                LocalDateTime localDate = organization.getCreationDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedString = localDate.format(formatter);
                preparedStatement.setString(5, formattedString);
                preparedStatement.setDouble(6, organization.getAnnualTurnover());
                preparedStatement.setString(7, organization.getType().getString());
                preparedStatement.setString(8, organization.getOfficialAddress().getStreet());
                preparedStatement.setString(9, organization.getOfficialAddress().getZipCode());
                preparedStatement.setDouble(10, organization.getOfficialAddress().getTown().getX());
                preparedStatement.setDouble(11, organization.getOfficialAddress().getTown().getY());
                preparedStatement.setString(12, organization.getOfficialAddress().getTown().getName());
                preparedStatement.setString(13, organization.getColor());
                preparedStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String sql = "INSERT INTO organizations (id ,owner , name, x, y, creationDate, annualTurnover, organizationType, street, zipCode, location_x, location_y, town, color) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, organization.getOwner());
                preparedStatement.setString(3, organization.getName());
                preparedStatement.setDouble(4, organization.getCoordinates().getX());
                preparedStatement.setDouble(5, organization.getCoordinates().getY());
                LocalDateTime localDate = organization.getCreationDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedString = localDate.format(formatter);
                preparedStatement.setString(6, formattedString);
                preparedStatement.setDouble(7, organization.getAnnualTurnover());
                preparedStatement.setString(8, organization.getType().getString());
                preparedStatement.setString(9, organization.getOfficialAddress().getStreet());
                preparedStatement.setString(10, organization.getOfficialAddress().getZipCode());
                preparedStatement.setDouble(11, organization.getOfficialAddress().getTown().getX());
                preparedStatement.setDouble(12, organization.getOfficialAddress().getTown().getY());
                preparedStatement.setString(13, organization.getOfficialAddress().getTown().getName());
                preparedStatement.setString(14, organization.getColor());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteOrganizationFromDataBase(int id) throws SQLException {
        String sql = "DELETE FROM ORGANIZATIONS WHERE ID = '" + id + "';";
        statement.execute(sql);
    }

    public static boolean validation(String login, String psw) {
        try {
            String query = "SELECT * FROM USERS;";
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(login) && resultSet.getString(2).equals(SecretBase.HasingPsw(psw))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validationExecutorCommandUser(int id, String login) {
        try {
            String query = "SELECT * FROM ORGANIZATIONS;";
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getInt("id") == id && resultSet.getString("owner").equals(login)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void clearCollectionOnDataBase(String owner) {
        try {
            String sql = "DELETE FROM ORGANIZATIONS WHERE owner = '" + owner + "';";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            OrganizationsBase.loadCollection(CollectionManager.getCollection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}