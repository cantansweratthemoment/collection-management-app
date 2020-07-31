package laba_7.Commands.SpecificCommands;

import laba_7.Commands.Command;
import laba_7.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
public class Info extends Command implements Serializable {

    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public Info() {
    }

    public Info(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void aboutCommand() {
        System.out.println("О команде\u001B[36m info\u001B[0m: выводится информация о коллекции.");
    }

    @Override
    public void execute(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args.length !=1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.info();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}

