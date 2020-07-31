package laba_8.Client;

import laba_8.Commands.SpecificCommands.*;

import java.io.IOException;

public class ConsoleManager {
    public static Invoker invoker = new Invoker();

    public void start(String host, String port) {
        Communicator communicator = null;
        try {
            communicator = new Communicator(host, Integer.parseInt(port));
            communicator.startCommunication();
        } catch (NumberFormatException | IOException e) {
            System.out.println("Проблемы с аргументами.");
            System.exit(0);
        }
        Sender sender = new Sender(communicator);
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
        //invoker.register("execute_script", new ExecuteScript(receiver));
        invoker.register("exit", new Exit(receiver));
        invoker.register("remove_head", new RemoveHead(receiver));
        invoker.register("add_if_min", new AddIfMin(receiver));
        invoker.register("history", new History(receiver));
        invoker.register("filter_by_annual_turnover", new FilterByAnnualTurnover(receiver));
        invoker.register("filter_starts_with_name", new FilterStartsWithName(receiver));
        invoker.register("print_field_descending_annual_turnover", new PrintFieldDescendingAnnualTurnover(receiver));
        invoker.register("visualize", new Visualize(receiver));
        invoker.register("sort_by_id", new SortByID(receiver));
        invoker.register("sort_by_owner", new SortByOwner(receiver));
        invoker.register("sort_by_name", new SortByName(receiver));
        invoker.register("sort_by_x", new SortByX(receiver));
        invoker.register("sort_by_y", new SortByY(receiver));
        invoker.register("sort_by_date", new SortByDate(receiver));
        invoker.register("sort_by_annual_turnover", new SortByTurnover(receiver));
        invoker.register("sort_by_type", new SortByType(receiver));
        invoker.register("sort_by_street", new SortByStreet(receiver));
        invoker.register("sort_by_zipcode", new SortByZipcode(receiver));
        invoker.register("sort_by_town_x", new SortByLX(receiver));
        invoker.register("sort_by_town_y", new SortByLY(receiver));
        invoker.register("sort_by_town", new SortByTown(receiver));
    }

    public static void invoke(String command) throws InterruptedException, IOException, ClassNotFoundException {
        invoker.executeCommand(command.split(" "));
    }
    /*public void interactive(String host, String port) throws IOException, InterruptedException, ClassNotFoundException {
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
    }*/
}