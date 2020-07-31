package laba_8.Client;

import laba_8.Commands.Command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Invoker {
    private final HashMap<String, Command> commands = new HashMap<>();
    private String[] commandHistory = new String[7];
    private static int historyPointer;

    public void register(String name, Command command) {
        commands.put(name, command);
    }

    public void executeCommand(String[] name) throws IOException, InterruptedException, ClassNotFoundException {
        try {
            if (name.length > 0) {
                if (name[0].equals("logout")) {
                    Receiver.isLogin = false;
                    Receiver.myLogin = "";
                    Receiver.myPassword = "";
                    Receiver.myColor = "";
                    System.out.println("Вы разлогинились.");
                }
                if (Receiver.isLogin || name[0].equals("login") || name[0].equals("register") || name[0].equals("exit")) {
                    Command command = commands.get(name[0]);
                    command.execute(name);
                    addCommandToHistory(name[0]);
                } else {
                    System.out.println("Введите 'login' если Вы уже зарегестрированный пользователь. Введите 'register', если вы хотите зарегистрироваться.\nЗатем через пробел введите логин и пароль.");
                }
            } else {
                System.out.println("Вы не ввели команду.");
            }
        } catch (IllegalStateException | NullPointerException e) {
            if (!name[0].equals("") && (!name[0].equals("execute_script")) && !name[0].equals("logout")) {
                System.out.println("Не существует команды '" + name[0] + "'. Для справки используйте команду\u001B[36m help\u001B[0m.");
            }
        }
    }

    public void executeScriptCommandAdd(Scanner sc) {
        String[] str = {"add"};
        Command add = commands.get("add");
        add.executeForScript(str, sc);
        addCommandToHistory("add");
    }

    public void executeScriptCommandAddIfMin(Scanner sc) {
        String[] str = {"add_if_min"};
        Command add = commands.get("add_if_min");
        add.executeForScript(str, sc);
        addCommandToHistory("add_if_min");
    }

    public void executeScriptCommandUpdate(Scanner sc, String argument) {
        String[] str = {"update", argument};
        Command add = commands.get("update");
        add.executeForScript(str, sc);
        addCommandToHistory("update");
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public void addCommandToHistory(String onlyName) {
        for (int i = 0; i < 7; i++) {
            if (commandHistory[i] == null) {
                historyPointer = i;
                break;
            }
        }
        if (historyPointer < 7) {
            commandHistory[historyPointer] = onlyName;
        } else {
            for (int i = 0; i < 6; i++) {
                commandHistory[i] = commandHistory[i + 1];
            }
            commandHistory[6] = onlyName;
        }
    }

    public String[] getCommandHistory() {
        return commandHistory;
    }
}