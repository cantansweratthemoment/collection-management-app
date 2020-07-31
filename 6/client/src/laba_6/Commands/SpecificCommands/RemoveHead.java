package laba_6.Commands.SpecificCommands;

import laba_6.Commands.Command;
import laba_6.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Команда remove_head.
 */
public class RemoveHead extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;


    public RemoveHead(Receiver receiver) {
        this.receiver = receiver;
    }

    public RemoveHead() {

    }

    @Override
    public void aboutCommand() {
        System.out.println("О команде\u001B[36m remove_head\u001B[0m: первый элемент коллекции выводится и удаляется.");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.remove_head();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}

