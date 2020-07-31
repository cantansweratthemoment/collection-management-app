package laba_8.Commands.SpecificCommands;

import laba_8.Commands.Command;
import laba_8.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class AddIfMin extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;


    public AddIfMin(Receiver receiver) {
        this.receiver = receiver;
    }

    public AddIfMin() {

    }

    @Override
    public String aboutCommand() {
        return ("О команде 'add_if_min': Добавляется новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            Scanner sc = new Scanner(System.in);
            receiver.add_if_min(sc);
        }
    }

    public void executeForScript(String[] args, Scanner sc) {
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
