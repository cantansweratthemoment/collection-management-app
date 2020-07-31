package laba_6.Client;

import laba_6.Commands.SerializedCommands.SerializedArgumentCommand;
import laba_6.Commands.SerializedCommands.SerializedCombinedCommand;
import laba_6.Commands.SerializedCommands.SerializedObjectCommand;
import laba_6.Commands.SerializedCommands.SerializedSimplyCommand;
import laba_6.Commands.SpecificCommands.*;
import laba_6.Utils.Creator;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Ресивер: описывает логику команд.
 */
public class Receiver {
    /**
     * Поле - инвокер.
     */
    private final Invoker invoker;
    private final Sender sender;
    private final DatagramSocket datagramSocket;

    /**
     * Конструктор.
     *
     * @param invoker        - инвокер.
     * @param sender
     * @param datagramSocket
     */
    public Receiver(Invoker invoker, Sender sender, DatagramSocket datagramSocket) {
        this.invoker = invoker;
        this.sender = sender;
        this.datagramSocket = datagramSocket;
    }

    /**
     * Логика команды help.
     */
    public void help() {
        System.out.println("Информация о командах:");
        invoker.getCommands().forEach((name, command) -> command.aboutCommand());
    }

    /**
     * Логика команды info.
     */
    public void info() throws IOException {
        try {
            sender.sendObject(new SerializedSimplyCommand(new Info()));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды show.
     */
    public void show() throws IOException {
        try {
            sender.sendObject(new SerializedSimplyCommand(new Show()));
            byte[] ib = new byte[4096];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды add.
     */
    public void add(Scanner sc) throws IOException {
        try {
            sender.sendObject(new SerializedObjectCommand(new Add(), Creator.createOrganizationFromConsole(sc)));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды update.
     */
    public void update(String ID_s, Scanner sc) throws IOException {
        try {
            sender.sendObject(new SerializedCombinedCommand(new Update(), Creator.createOrganizationFromConsole(sc), ID_s));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды remove_by_id.
     */
    public void remove_by_id(String ID_s) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new RemoveById(), ID_s));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды clear.
     */
    public void clear() throws IOException {
        try {
            sender.sendObject(new SerializedSimplyCommand(new Clear()));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды execute_script.
     */
    public void execute_script(String path) throws InterruptedException, ClassNotFoundException {
        String[] line;
        String command;
        String[] parameters = new String[10];
        String id;
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(path)); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine().trim().split(" ");
                if (line[0].matches("add|add_if_min|update")) {
                    command = line[0];
                    switch (command) {
                        case "add":
                            try {
                                invoker.executeScriptCommandAdd(scanner);
                            } catch (NoSuchElementException e) {
                                System.out.println("Файл закончился, аргументов не хватило.");
                            }
                            break;
                        case "add_if_min":
                            try {
                                invoker.executeScriptCommandAddIfMin(scanner);
                            } catch (NoSuchElementException e) {
                                System.out.println("Файл закончился, аргументов не хватило.");
                            }
                            break;
                        case "update":
                            try {
                                invoker.executeScriptCommandUpdate(scanner, line[1]);
                            } catch (NoSuchElementException e) {
                                System.out.println("Файл закончился, аргументов не хватило.");
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Что-то не так с аргументами команды.");
                            }
                            break;
                    }
                } else {
                    if (line[0].equals("execute_script") && line[1].equals(ExecuteScript.getPath())) {
                        System.out.println("Пресечена попытка рекурсивного вызова скрипта.");
                    } else {
                        invoker.executeCommand(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось найти файл");
        } catch (IOException e) {
            System.out.println("Что-то пошло не так!");
        }
    }

    /**
     * Логика команды exit.
     */
    public void exit() {
        System.out.println("Завершение работы программы.");
        System.exit(0);
    }

    /**
     * Логика команды remove_head.
     */
    public void remove_head() throws IOException {
        try {
            sender.sendObject(new SerializedSimplyCommand(new RemoveHead()));
            byte[] ib = new byte[512];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды add_if_min.
     */
    public void add_if_min(Scanner sc) throws IOException {
        try {
            sender.sendObject(new SerializedObjectCommand(new AddIfMin(), Creator.createOrganizationFromConsole(sc)));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды history.
     */
    public void history() {
        if (invoker.getCommandHistory()[0] == null) {
            System.out.println("История пуста.");
        } else {
            for (int i = 0; i < 7; i++) {
                if (invoker.getCommandHistory()[i] != null) {
                    System.out.println(invoker.getCommandHistory()[i] + " ");
                }
            }
        }
    }

    /**
     * Логика команды filter_by_annual_turnover.
     */
    public void filter_by_annual_turnover(String annualTurnover_s) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new FilterByAnnualTurnover(), annualTurnover_s));
            byte[] ib = new byte[4096];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды filter_starts_with_name.
     */
    public void filter_starts_with_name(String name) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new FilterStartsWithName(), name));
            byte[] ib = new byte[4096];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    /**
     * Логика команды print_field_descending_annual_turnover.
     */
    public void print_field_descending_annual_turnover() throws IOException {
        try {
            sender.sendObject(new SerializedSimplyCommand(new PrintFieldDescendingAnnualTurnover()));
            byte[] ib = new byte[4096];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }
}