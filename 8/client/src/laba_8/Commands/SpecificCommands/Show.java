package laba_8.Commands.SpecificCommands;

import laba_8.Commands.Command;
import laba_8.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Show extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public Show(Receiver receiver) {
        this.receiver = receiver;
    }

    public Show() {

    }

    @Override
    public String aboutCommand() {
        return ("О команде 'show': выводятся все элементы коллекции в виде таблички.\n");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.show();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}
