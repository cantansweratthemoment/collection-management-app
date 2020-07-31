package laba_8.Commands.SpecificCommands;

import laba_8.Client.Receiver;
import laba_8.Commands.Command;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Register extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public Register(Receiver receiver) {
        this.receiver = receiver;
    }

    public Register() {
    }

    @Override
    public String aboutCommand() {
        return ("");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length != 5) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.register(args[1], args[2], args[3], args[4]);
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {
    }
}