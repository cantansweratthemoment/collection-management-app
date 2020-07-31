package laba_8.Commands.SpecificCommands;

import laba_8.Commands.Command;
import laba_8.Client.Receiver;

import java.util.Scanner;

public class History extends Command {
    private final Receiver receiver;

    public History(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String aboutCommand() {
        return ("О команде 'history': выводятся 7 последних команд.\n");
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.history();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}
