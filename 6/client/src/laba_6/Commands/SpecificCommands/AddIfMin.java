package laba_6.Commands.SpecificCommands;

import laba_6.Commands.Command;
import laba_6.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Команда add_if_min.
 */
public class AddIfMin extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;


    public AddIfMin(Receiver receiver) {
        this.receiver = receiver;
    }

    public AddIfMin() {

    }

    @Override
    public void aboutCommand() {
        System.out.println("О команде\u001B[36m add_if_min\u001B[0m: Добавляется новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            Scanner sc = new Scanner(System.in);
            receiver.add_if_min(sc);
        }
    }
    public void executeForScript(String[] args, Scanner sc){
        if (args.length > 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            try {
                receiver.add_if_min(sc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
