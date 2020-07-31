package laba_8.Commands.SpecificCommands;

import laba_8.Client.Receiver;
import laba_8.Commands.Command;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class SortByStreet extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public SortByStreet(Receiver receiver) {
        this.receiver = receiver;
    }

    public SortByStreet() {
    }

    @Override
    public String aboutCommand() {
        return "";
    }

    @Override
    public void execute(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args.length > 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.sort_by_street();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}
