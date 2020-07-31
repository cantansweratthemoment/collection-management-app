package laba_7.Client;

import laba_7.Commands.SpecificCommands.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Scanner;

public class ConsoleManager {
    public void interactive(String host, String port) throws IOException, InterruptedException, ClassNotFoundException {
        Communicator communicator = null;
        try {
            communicator = new Communicator(host, Integer.parseInt(port));
            communicator.startCommunication();
        } catch (NumberFormatException e) {
            System.out.println("Проблемы с аргументами.");
            System.exit(0);
        }
        Sender sender = new Sender(communicator);
        Invoker invoker = new Invoker();
        Receiver receiver = new Receiver(invoker, sender, sender.getDatagramSocket());
        invoker.register("login", new Login(receiver));
        invoker.register("register", new Register(receiver));
        invoker.register("help", new Help(receiver));
        invoker.register("info", new Info(receiver));
        invoker.register("show", new Show(receiver));
        invoker.register("add", new Add(receiver));
        invoker.register("update", new Update(receiver));
        invoker.register("remove_by_id", new RemoveById(receiver));
        invoker.register("clear", new Clear(receiver));
        invoker.register("execute_script", new ExecuteScript(receiver));
        invoker.register("exit", new Exit(receiver));
        invoker.register("remove_head", new RemoveHead(receiver));
        invoker.register("add_if_min", new AddIfMin(receiver));
        invoker.register("history", new History(receiver));
        invoker.register("filter_by_annual_turnover", new FilterByAnnualTurnover(receiver));
        invoker.register("filter_starts_with_name", new FilterStartsWithName(receiver));
        invoker.register("print_field_descending_annual_turnover", new PrintFieldDescendingAnnualTurnover(receiver));
        System.out.println("Введите 'login' если Вы уже зарегестрированный пользователь. Введите 'register', если вы хотите зарегистрироваться.\n" +
                "Затем через пробел введите логин и пароль.");
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {
                invoker.executeCommand(sc.nextLine().trim().split(" "));
            }
        }
        communicator.endCommunication();
    }
}