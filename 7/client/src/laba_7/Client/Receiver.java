package laba_7.Client;

import com.google.gson.stream.JsonToken;
import laba_7.Classes.Organization;
import laba_7.Commands.SerializedCommands.SerializedArgumentCommand;
import laba_7.Commands.SerializedCommands.SerializedCombinedCommand;
import laba_7.Commands.SerializedCommands.SerializedObjectCommand;
import laba_7.Commands.SerializedCommands.SerializedSimplyCommand;
import laba_7.Commands.SpecificCommands.*;
import laba_7.Utils.Creator;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Receiver {

    private final Invoker invoker;
    private final Sender sender;
    private final DatagramSocket datagramSocket;

    public Receiver(Invoker invoker, Sender sender, DatagramSocket datagramSocket) {
        this.invoker = invoker;
        this.sender = sender;
        this.datagramSocket = datagramSocket;
    }

    public void help() {
        System.out.println("Информация о командах:");
        invoker.getCommands().forEach((name, command) -> command.aboutCommand());
        System.out.println("О команде\u001B[36m logut\u001B[0m: разлогиниться.");
    }

    public static boolean isLogin = false;
    public static String myLogin = "";
    public static String myPassword = "";

    public void login(String login, String password) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new Login(), login + " " + password));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            sender.getDatagramSocket().setSoTimeout(5000);
            sender.getDatagramSocket().receive(ip);
            String result = new String(ib);
            System.out.println(result);
            result = result.trim();
            if (result.equals("Не успешно!")) {
                isLogin = false;
                myLogin = "";
                myPassword = "";
            } else {
                isLogin = true;
                myLogin = login;
                myPassword = password;
            }
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, залогиниться не получится.");
        }
    }

    public void register(String login, String password, String mail) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new Register(), login + " " + password + " " + mail));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            sender.getDatagramSocket().setSoTimeout(5000);
            sender.getDatagramSocket().receive(ip);
            String result = new String(ib);
            System.out.println(result);
            if (result.equals("Не успешно!")) {
                System.exit(0);
            }
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, зарегестрироваться не получится.");
        }
    }

    public void info() throws IOException {
        try {
                sender.sendObject(new SerializedArgumentCommand(new Info(), myLogin + " " + myPassword));
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

    public void show() throws IOException {
        try {
                sender.sendObject(new SerializedArgumentCommand(new Show(), myLogin + " " +
                        myPassword));
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

    public void add(Scanner sc) throws IOException {
        try {
                Organization organization = Creator.createOrganizationFromConsole(sc);
                organization.setOwner(myLogin);
                sender.sendObject(new SerializedCombinedCommand(new Add(), organization, myLogin + " " + myPassword));
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

    public void update(String ID_s, Scanner sc) throws IOException {
        try {
                Organization organization = Creator.createOrganizationFromConsole(sc);
                organization.setOwner(myLogin);
                sender.sendObject(new SerializedCombinedCommand(new Update(), organization, myLogin + " " + myPassword + " " + ID_s));
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

    public void remove_by_id(String ID_s) throws IOException {
        try {
                sender.sendObject(new SerializedArgumentCommand(new RemoveById(), myLogin + " " + myPassword + " " + ID_s));
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

    public void clear() throws IOException {
        try {
                sender.sendObject(new SerializedArgumentCommand(new Clear(), myLogin + " " + myPassword));
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

    public void exit() {
        System.out.println("Завершение работы программы.");
        System.exit(0);
    }

    public void remove_head() throws IOException {
        try {
                sender.sendObject(new SerializedArgumentCommand(new RemoveHead(), myLogin + " " + myPassword));
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

    public void add_if_min(Scanner sc) throws IOException {
        try {
                Organization organization = Creator.createOrganizationFromConsole(sc);
                organization.setOwner(myLogin);
                sender.sendObject(new SerializedCombinedCommand(new AddIfMin(), organization, myLogin + " " + myPassword));
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

    public void filter_by_annual_turnover(String annualTurnover_s) throws IOException {
        try {
                sender.sendObject(new SerializedArgumentCommand(new FilterByAnnualTurnover(), myLogin + " " + myPassword + " " + annualTurnover_s));
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

    public void filter_starts_with_name(String name) throws IOException {
        try {
                sender.sendObject(new SerializedArgumentCommand(new FilterStartsWithName(), myLogin + " " + myPassword + " " + name));
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

    public void print_field_descending_annual_turnover() throws IOException {
        try {
                sender.sendObject(new SerializedArgumentCommand(new PrintFieldDescendingAnnualTurnover(), myLogin + " " + myPassword));
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