package laba_8.Commands.SpecificCommands;

import laba_8.Commands.Command;
import laba_8.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class PrintFieldDescendingAnnualTurnover extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;


    public PrintFieldDescendingAnnualTurnover(Receiver receiver) {
        this.receiver = receiver;
    }

    public PrintFieldDescendingAnnualTurnover() {

    }

    @Override
    public String aboutCommand() {
        return ("О команде 'print_field_descending_annual_turnover': выводятся значения поля annualTurnover всех элементов в порядке убывания.\n");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.print_field_descending_annual_turnover();
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}
