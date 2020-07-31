package laba_7;

import laba_7.Server.Communicator;
import laba_7.Database.DatabaseCommunicator;
import laba_7.Utils.MessageMailSender;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        if (args.length != 3) {
            System.out.println("Вы должны были ввести порт, логин и пароль от почты.\nПопробуйте ещё раз!");
            System.exit(1);
        }
        new MessageMailSender(args[1], args[2]);
        DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator();
        databaseCommunicator.start();
        Communicator communicator = new Communicator();
        communicator.run(args[0]);
    }
}