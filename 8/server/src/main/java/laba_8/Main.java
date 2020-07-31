package laba_8;

import laba_8.Server.Communicator;
import laba_8.Database.DatabaseCommunicator;
import laba_8.Utils.MessageMailSender;

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