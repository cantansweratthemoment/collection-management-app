package laba_8.Commands.SpecificCommands;

import laba_8.Commands.Command;
import laba_8.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Add extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;


    public Add(Receiver receiver) {
        this.receiver = receiver;
    }

    public Add() {

    }

    @Override
    public String aboutCommand() {
        return ("О команде 'add': в коллекцию добавляется новый элемент.\n");//TODO Этой и подобным командам написать, что доступ через show.
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            Scanner sc = new Scanner(System.in);
            receiver.add(sc);
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {
        if (args.length > 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            try {
                receiver.add(sc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
