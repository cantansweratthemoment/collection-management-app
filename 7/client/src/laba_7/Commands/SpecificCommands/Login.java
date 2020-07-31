package laba_7.Commands.SpecificCommands;

import laba_7.Client.Receiver;
import laba_7.Commands.Command;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Login extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public Login(Receiver receiver) {
        this.receiver = receiver;
    }

    public Login() {
    }

    @Override
    public void aboutCommand() {
        System.out.println("О команде\u001B[36m login\u001B[0m: логин.");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.login(args[1], args[2]);
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {
    }
}