package laba_7.Commands.SpecificCommands;

import laba_7.Commands.Command;
import laba_7.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Команда print_field_descending_annual_turnover.
 */
public class PrintFieldDescendingAnnualTurnover extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;


    public PrintFieldDescendingAnnualTurnover(Receiver receiver) {
        this.receiver = receiver;
    }

    public PrintFieldDescendingAnnualTurnover() {

    }

    @Override
    public void aboutCommand() {
        System.out.println("О команде\u001B[36m print_field_descending_annual_turnover\u001B[0m: выводятся значения поля annualTurnover всех элементов в порядке убывания.");
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
