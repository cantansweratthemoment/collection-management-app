package laba_6.Commands.SpecificCommands;

import laba_6.Commands.Command;
import laba_6.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Команда remove_by_id.
 */
public class RemoveById extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;


    public RemoveById(Receiver receiver) {
        this.receiver = receiver;
    }

    public RemoveById() {

    }

    @Override
    public void aboutCommand() {
        System.out.println("О команде\u001B[36m remove_by_id\u001B[0m: удаляется элемент из коллекции по его id.");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.remove_by_id(args[1]);
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}
