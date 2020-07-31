package laba_6.Commands.SpecificCommands;

import laba_6.Commands.Command;
import laba_6.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Команда clear.
 */

public class Clear extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public Clear(Receiver receiver) {
        this.receiver = receiver;
    }

    public Clear() {

    }

    @Override
    public void aboutCommand() {
        System.out.println("О команде\u001B[36m clear\u001B[0m: очистка коллекции.");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.clear();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}