package laba_8.Commands.SpecificCommands;

import laba_8.Commands.Command;
import laba_8.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Update extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;


    public Update(Receiver receiver) {
        this.receiver = receiver;
    }

    public Update() {

    }

    @Override
    public String aboutCommand() {
        return ("О команде 'update': обновляется значение элемента коллекции, id которого равен заданному.\n");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            Scanner sc = new Scanner(System.in);
            receiver.update(args[1], sc);
        }
    }

    public void executeForScript(String[] args, Scanner sc) {
        if (args.length != 2) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            try {
                receiver.update(args[1], sc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}