package laba_8.Database;

import java.sql.*;

public class DatabaseCommunicator {
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "ataha6622";
    private static Connection connection;
    private static Statement statement;
    private static OrganizationsBase organizations;
    private static SecretBase users;

    public void start() throws SQLException {
        try{connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
        users = new SecretBase(connection);
        organizations = new OrganizationsBase(connection);
        }
        catch (SQLException e){
            System.out.println("Проблемы с подключением к базе данных.");
            e.printStackTrace();
            System.exit(0);
        }
        if (connection != null) {
            System.out.println("Подключение к базе данных прошло успешно.");
        } else {
            System.out.println("Не удалось подключиться к базе данных.");
        }
    }

    public static OrganizationsBase getOrganizations() {
        return organizations;
    }

    public static SecretBase getUsers() {
        return users;
    }
}