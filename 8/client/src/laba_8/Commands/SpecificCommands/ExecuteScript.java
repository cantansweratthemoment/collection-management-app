package laba_8.Commands.SpecificCommands;

import javafx.scene.control.Alert;
import laba_8.Commands.Command;
import laba_8.Client.Receiver;

import java.util.Scanner;

public class ExecuteScript extends Command {
    private final Receiver receiver;
    private static String path;

    public ExecuteScript(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String aboutCommand() {
        return ("О команде 'execute_script': считывается и исполняется скрипт из указанного файла.\n");
    }

    @Override
    public void execute(String[] args) throws StackOverflowError, InterruptedException, ClassNotFoundException {
        try {
            if (args.length != 2)
                System.out.println("Неправильный формат команды! Команда не будет выполнена.");//TODO Везде заменить этот вывод штуку на Alert.
            else
                path = args[1];
            receiver.execute_script(args[1]);
        } catch (StackOverflowError e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ой!");
            alert.setHeaderText("У вас стек умер... В это раз без штрафа, в следующий раз давайте без рекурсий.");
            alert.showAndWait();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }

    public static String getPath() {
        return path;
    }
}