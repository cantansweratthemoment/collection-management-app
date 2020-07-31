package Commands;

import Collection.CollectionManager;
import Collection.CollectionUtils;
import Commands.SpecificCommands.ExecuteScript;
import Commands.Utils.Creator;
import Commands.Utils.ParserJson;
import StoredClasses.Organization;

import java.io.*;
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

    /**
     * Конструктор.
     *
     * @param invoker - инвокер.
     */
    public Receiver(Invoker invoker) {
        this.invoker = invoker;
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
    public void info() {
        CollectionManager.information();
    }

    /**
     * Логика команды show.
     */
    public void show() {
        CollectionManager.fullInformation();
    }

    /**
     * Логика команды add.
     */
    public void add(Scanner sc) {
        CollectionManager.addOrganization(Creator.createOrganizationFromConsole(sc));
        System.out.println("Организация добавлена в коллекцию.");
    }

    /**
     * Логика команды update.
     */
    public void update(String ID_s, Scanner sc) {
        Integer ID;
        try {
            ID = Integer.parseInt(ID_s);
            if (CollectionUtils.doesExist(ID)) {
                CollectionManager.updateElement(Creator.createOrganizationFromConsole(sc), ID);
                System.out.println("Организация обновлена.");
            } else {
                System.out.println("В коллекции нет организации с таким ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID был введён некорректно. Команда не выполнена.");
        }
    }

    /**
     * Логика команды remove_by_id.
     */
    public void remove_by_id(String ID_s) {
        Integer ID;
        try {
            ID = Integer.parseInt(ID_s);
            if (CollectionUtils.doesExist(ID)) {
                CollectionManager.removeElement(ID);
                System.out.println("Элемент удалён.");
            } else {
                System.out.println("Такого элемента нет в коллекции.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неправильный аргумент команды! Команда не будет выполнена.");
        }
    }

    /**
     * Логика команды clear.
     */
    public void clear() {
        CollectionManager.clearCollection();
        System.out.println("Коллекция очищена.");
    }

    /**
     * Логика команды save.
     */
    public void save() {
        ParserJson.collectionToJson();
        System.out.println("Коллекция сохранена в файл.");
    }

    /**
     * Логика команды execute_script.
     */
    public void execute_script(String path) {
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
                            }catch (ArrayIndexOutOfBoundsException e){
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
    public void remove_head() {
        CollectionManager.removeHead();
    }

    /**
     * Логика команды add_if_min.
     */
    public void add_if_min(Scanner sc) {
        CollectionManager.addIfMin(Creator.createOrganizationFromConsole(sc));
        System.out.println("Команда выполнена.");
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
    public void filter_by_annual_turnover(String annualTurnover_s) {
        Double annualTurnover;
        try {
            annualTurnover = Double.parseDouble(annualTurnover_s);
            CollectionManager.filterByAnnualTurnover(annualTurnover);
        } catch (NumberFormatException e) {
            System.out.println("Неправильный аргумент команды! Команда не будет выполнена.");
        }
    }

    /**
     * Логика команды filter_starts_with_name.
     */
    public void filter_starts_with_name(String name) {
        CollectionManager.filterStartsWithName(name);
    }

    /**
     * Логика команды print_field_descending_annual_turnover.
     */
    public void print_field_descending_annual_turnover() {
        CollectionManager.printFieldDescendingAnnualTurnover();
    }
}