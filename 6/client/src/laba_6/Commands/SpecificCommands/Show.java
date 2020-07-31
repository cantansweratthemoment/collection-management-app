package laba_6.Commands.SpecificCommands;

import laba_6.Commands.Command;
import laba_6.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Команда show.
 */
public class Show extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public Show(Receiver receiver) {
        this.receiver = receiver;
    }

    public Show() {

    }

    @Override
    public void aboutCommand() {
        System.out.println("О команде\u001B[36m show\u001B[0m: выводятся все элементы коллекции в строковом представлении.");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.show();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}
