package laba_8.Server;

import laba_8.Database.DatabaseCommunicator;
import laba_8.Database.OrganizationsBase;
import laba_8.Database.SecretBase;
import laba_8.Utils.CollectionManager;
import laba_8.Utils.CollectionUtils;
import laba_8.Classes.Organization;
import laba_8.Utils.MessageMailSender;
import laba_8.Utils.ParserJson;

import javax.mail.MessagingException;
import java.io.*;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Scanner;

import static java.nio.ByteBuffer.wrap;

public class Receiver {
    private DatagramChannel channel;

    public Receiver(DatagramChannel channel) {
        this.channel = channel;
    }

    public void info(String arg, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            byte[] answer = CollectionManager.information().getBytes();
            channel.send(wrap(answer), socketAddress);
        } else {
            channel.send(wrap("Пользователь не прошел проверку".getBytes()), socketAddress);
        }
    }

    public void show(String arg, SocketAddress socketAddress) throws IOException, SQLException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            //byte[] answer = CollectionManager.fullInformation().getBytes();
            OrganizationsBase.loadCollection(CollectionManager.getCollection());
            ParserJson.collectionToJson();
            String contents = readUsingFiles(ParserJson.getFileName());
            byte[] answer = contents.getBytes();
            channel.send(wrap(answer), socketAddress);
        } else {
            channel.send(wrap("Пользователь не прошел проверку".getBytes()), socketAddress);
        }
    }
    public void visualize(String arg, SocketAddress socketAddress) throws IOException, SQLException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            OrganizationsBase.loadCollection(CollectionManager.getCollection());
            ParserJson.collectionToJson();
            String contents = readUsingFiles(ParserJson.getFileName());
            byte[] answer = contents.getBytes();
            channel.send(wrap(answer), socketAddress);
        } else {
            channel.send(wrap("Пользователь не прошел проверку".getBytes()), socketAddress);
        }
    }

    public void add(Object o, String arg, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            Organization organization = (Organization) o;
            DatabaseCommunicator.getOrganizations().addOrganizationToTheBase(organization, -1);
            byte[] answer = CollectionManager.addOrganization().getBytes();
            channel.send(wrap(answer), socketAddress);
        } else {
            channel.send(wrap("Пользователь не прошел проверку".getBytes()), socketAddress);
        }
    }

    public void update(String arg, Object o, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        byte[] answer = new byte[0];
        int ID;
        ID = Integer.parseInt(arr[2]);
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            if (OrganizationsBase.validationExecutorCommandUser(ID, arr[0])) {
                try {
                    if (CollectionUtils.doesExist(ID)) {
                        Organization organization = (Organization) o;
                        DatabaseCommunicator.getOrganizations().deleteOrganizationFromDataBase(ID);
                        DatabaseCommunicator.getOrganizations().addOrganizationToTheBase(organization, ID);
                        CollectionManager.updateElement((Organization) o, ID);
                        answer = "Организация обновлена.".getBytes();
                    } else {
                        answer = "В коллекции нет организации с таким ID.".getBytes();
                    }
                } catch (NumberFormatException | SQLException e) {
                    e.printStackTrace();
                    answer = "ID был введён некорректно. Команда не выполнена.".getBytes();
                } finally {
                    channel.send(wrap(answer), socketAddress);
                }
            } else {
                channel.send(wrap("Данный объект вам не принадлежит либо его не существует.".getBytes()), socketAddress);
            }
        } else {
            channel.send(wrap("Пользователь не прошел проверку.".getBytes()), socketAddress);
        }
    }

    public void remove_by_id(String arg, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            byte[] answer = new byte[0];
            int ID;
            try {
                ID = Integer.parseInt(arr[2]);
                if (OrganizationsBase.validationExecutorCommandUser(ID, arr[0])) {
                    if (CollectionUtils.doesExist(ID)) {
                        DatabaseCommunicator.getOrganizations().deleteOrganizationFromDataBase(ID);
                        CollectionManager.removeElement(ID);
                        answer = "Элемент удалён.".getBytes();
                    } else {
                        answer = "Такого элемента нет в коллекции.".getBytes();
                    }
                } else {
                    answer = "Данный объект не принадлежит вам.".getBytes();
                }
            } catch (NumberFormatException | SQLException e) {
                answer = "Неправильный аргумент команды! Команда не будет выполнена.".getBytes();
            } finally {
                channel.send(wrap(answer), socketAddress);
            }
        } else {
            channel.send(wrap("Пользователь не прошел проверку.".getBytes()), socketAddress);
        }
    }

    public void clear(String arg, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            CollectionManager.clearCollectionOnDataBase(arr[0]);
            byte[] answer = "Коллекция очищена".getBytes();
            channel.send(wrap(answer), socketAddress);
        } else {
            channel.send(wrap("Пользователь не прошел проверку.".getBytes()), socketAddress);
        }
    }

    public void remove_head(String arg, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            byte[] answer = CollectionManager.removeHead(arr[0]).getBytes();
            channel.send(wrap(answer), socketAddress);
        } else {
            channel.send(wrap("Пользователь не прошел проверку.".getBytes()), socketAddress);
        }
    }

    public void add_if_min(String arg, Object o, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            byte[] answer = CollectionManager.addIfMin((Organization) o).getBytes();
            channel.send(wrap(answer), socketAddress);
        } else {
            channel.send(wrap("Пользователь не прошел проверку.".getBytes()), socketAddress);
        }
    }

    public void filter_by_annual_turnover(String arg, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        byte[] answer = new byte[0];
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            try {
                answer = CollectionManager.filterByAnnualTurnover(Double.parseDouble(arr[2])).getBytes();
            } catch (NumberFormatException e) {
                answer = "Неправильный аргумент команды! Команда не будет выполнена.".getBytes();
            } finally {
                channel.send(wrap(answer), socketAddress);
            }
        } else {
            channel.send(wrap("Пользователь не прошел проверку.".getBytes()), socketAddress);
        }
    }

    public void filter_starts_with_name(String arg, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            byte[] answer = CollectionManager.filterStartsWithName(arr[2]).getBytes();
            channel.send(wrap(answer), socketAddress);
        } else {
            channel.send(wrap("Пользователь не прошел проверку.".getBytes()), socketAddress);
        }
    }

    public void login(String arg, SocketAddress socketAddress) throws IOException, SQLException {
        String[] arr = arg.split(" ");
        String resultForSending;
        if (DatabaseCommunicator.getUsers().isValue("login", arr[0])) {
            if (DatabaseCommunicator.getUsers().isValue("password", SecretBase.HasingPsw(arr[1]))) {
                resultForSending = "Успешно!";
            } else resultForSending = "Не_успешно!";
        } else resultForSending = "Не_успешно!";
        resultForSending = resultForSending.concat(" " + DatabaseCommunicator.getUsers().getColorUser(arr[0]));
        channel.send(wrap(resultForSending.getBytes()), socketAddress);
    }

    public void register(String arg, SocketAddress socketAddress) throws IOException, SQLException {
        String[] arr = arg.split(" ");
        String resultForSending;
        if (!(DatabaseCommunicator.getUsers().isValue("login", arr[0])) && !(DatabaseCommunicator.getUsers().isValue("color", arr[3]))) {
            DatabaseCommunicator.getUsers().addUserToTheBase(arr[0], arr[1], arr[3]);
            try {
                MessageMailSender.SendMessage(arr[0], arr[1], arr[2]);
                resultForSending = "Успешно!";
            } catch (MessagingException e) {
                resultForSending = "Успешно, но сообщение с логином и паролем не было отправлено из-за неполадок с подключением!";
            }
        } else resultForSending = "Пользователь с данным логином уже зарегистрирован!";
        channel.send(wrap(resultForSending.getBytes()), socketAddress);
    }

    public void print_field_descending_annual_turnover(String arg, SocketAddress socketAddress) throws IOException {
        String[] arr = arg.split(" ");
        if (OrganizationsBase.validation(arr[0], arr[1])) {
            byte[] answer = CollectionManager.printFieldDescendingAnnualTurnover().getBytes();
            channel.send(wrap(answer), socketAddress);
        } else {
            channel.send(wrap("Пользователь не прошел проверку.".getBytes()), socketAddress);
        }
    }

    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}