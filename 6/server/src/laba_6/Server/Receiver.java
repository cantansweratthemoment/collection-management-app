package laba_6.Server;

import laba_6.Utils.CollectionUtils.CollectionManager;
import laba_6.Utils.CollectionUtils.CollectionUtils;
import laba_6.Utils.FileUtils.ParserJson;
import laba_6.StoredClasses.Organization;

import java.io.*;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

import static java.nio.ByteBuffer.wrap;


/**
 * Ресивер: описывает логику команд.
 */
public class Receiver {
    private DatagramChannel channel;

    /**
     * Конструктор.
     *
     * @param channel - сокет.
     */
    public Receiver(DatagramChannel channel) {
        this.channel = channel;
    }

    /**
     * Логика команды info.
     */
    public void info(SocketAddress socketAddress) throws IOException {
        byte[] answer = CollectionManager.information().getBytes();
        channel.send(wrap(answer), socketAddress);
    }

    /**
     * Логика команды show.
     */
    public void show(SocketAddress socketAddress) throws IOException {
        byte[] answer = CollectionManager.fullInformation().getBytes();
        channel.send(wrap(answer), socketAddress);
    }

    /**
     * Логика команды add.
     */
    public void add(Object o, SocketAddress socketAddress) throws IOException {
        byte[] answer = CollectionManager.addOrganization((Organization) o).getBytes();
        channel.send(wrap(answer), socketAddress);
    }

    /**
     * Логика команды update.
     */
    public void update(String ID_s, Object o, SocketAddress socketAddress) throws IOException {
        byte[] answer = new byte[0];
        int ID;
        try {
            ID = Integer.parseInt(ID_s);
            if (CollectionUtils.doesExist(ID)) {
                CollectionManager.updateElement((Organization) o, ID);
                answer = "Организация обновлена".getBytes();
            } else {
                answer = "В коллекции нет организации с таким ID.".getBytes();
            }
        } catch (NumberFormatException e) {
            answer = "ID был введён некорректно. Команда не выполнена.".getBytes();
        } finally {
            channel.send(wrap(answer), socketAddress);
        }
    }

    /**
     * Логика команды remove_by_id.
     */
    public void remove_by_id(String arg, SocketAddress socketAddress) throws IOException {
        byte[] answer = new byte[0];
        channel.send(wrap(answer), socketAddress);
        int ID;
        try {
            ID = Integer.parseInt(arg);
            if (CollectionUtils.doesExist(ID)) {
                CollectionManager.removeElement(ID);
                answer = "Элемент удалён.".getBytes();
            } else {
                answer = "Такого элемента нет в коллекции.".getBytes();
            }
        } catch (NumberFormatException e) {
            answer = "Неправильный аргумент команды! Команда не будет выполнена.".getBytes();
        } finally {
            channel.send(wrap(answer), socketAddress);
        }
    }

    /**
     * Логика команды clear.
     */
    public void clear(SocketAddress socketAddress) throws IOException {
        CollectionManager.clearCollection();
        byte[] answer = "Коллекция очищена".getBytes();
        channel.send(wrap(answer), socketAddress);
    }

    /**
     * Логика команды save.
     */
    public void save() {
        ParserJson.collectionToJson();
        System.out.println("Коллекция сохранена в файл.");
    }

    /**
     * Логика команды remove_head.
     */
    public void remove_head(SocketAddress socketAddress) throws IOException {
        byte[] answer = CollectionManager.removeHead().getBytes();
        channel.send(wrap(answer), socketAddress);
    }

    /**
     * Логика команды add_if_min.
     */
    public void add_if_min(Object o, SocketAddress socketAddress) throws IOException {
        byte[] answer = CollectionManager.addIfMin((Organization) o).getBytes();
        channel.send(wrap(answer), socketAddress);
    }

    /**
     * Логика команды filter_by_annual_turnover.
     */
    public void filter_by_annual_turnover(String arg, SocketAddress socketAddress) throws IOException {
        byte[] answer = new byte[0];
        try {
            answer = CollectionManager.filterByAnnualTurnover(Double.parseDouble(arg)).getBytes();
        } catch (NumberFormatException e) {
            answer = "Неправильный аргумент команды! Команда не будет выполнена.".getBytes();
        } finally {
            channel.send(wrap(answer), socketAddress);
            ;
        }
    }

    /**
     * Логика команды filter_starts_with_name.
     */
    public void filter_starts_with_name(String arg, SocketAddress socketAddress) throws IOException {
        byte[] answer = CollectionManager.filterStartsWithName(arg).getBytes();
        channel.send(wrap(answer), socketAddress);
    }

    /**
     * Логика команды print_field_descending_annual_turnover.
     */
    public void print_field_descending_annual_turnover(SocketAddress socketAddress) throws IOException {
        byte[] answer = CollectionManager.printFieldDescendingAnnualTurnover().getBytes();
        channel.send(wrap(answer), socketAddress);
    }
}