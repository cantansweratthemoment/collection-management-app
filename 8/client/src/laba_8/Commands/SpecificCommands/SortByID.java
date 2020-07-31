package laba_8.Commands.SpecificCommands;

import laba_8.Client.Receiver;
import laba_8.Commands.Command;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class SortByID extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public SortByID(Receiver receiver) {
        this.receiver = receiver;
    }

    public SortByID() {
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
            receiver.sort_by_id();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}
