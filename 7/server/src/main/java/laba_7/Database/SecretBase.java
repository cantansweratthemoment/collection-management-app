package laba_7.Database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class SecretBase {
    private Statement statement;
    private Connection connection;

    public SecretBase(Connection connection) throws SQLException {
        this.connection = connection;
        this.statement=connection.createStatement();
        this.createSecretBase();
    }
    public void createSecretBase() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS  users " +
                "(login TEXT, " +
                " password TEXT)";
        statement.execute(createTableSQL);
    }
    public void addUserToTheBase(String login, String password) throws SQLException {

        String sql = "INSERT INTO users (login, password) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, HasingPsw(password));
        preparedStatement.execute();
    }
    public boolean isValue(String word, String value) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT " + word + " FROM users");
        while (rs.next())
            if (value.equals(rs.getString(1)))
                return true;
        return false;
    }

    public static String HasingPsw(String psw) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD2");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Не найден алгоритм хэширования");
        }
        byte[] messageDigest = md.digest(psw.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "23d2092wda" + hashtext + "wd231d";
        }
        return hashtext;
    }
}
