package laba_6.Commands.SpecificCommands;

import laba_6.Commands.Command;
import laba_6.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Команда filter_by_annual_turnover.
 */
public class FilterByAnnualTurnover extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public FilterByAnnualTurnover(Receiver receiver) {
        this.receiver = receiver;
    }

    public FilterByAnnualTurnover() {

    }

    @Override
    public void aboutCommand() {
        System.out.println("О команде\u001B[36m filter_by_annual_turnover\u001B[0m: выводятся элементы, значение поля annualTurnover которых равно заданному.");
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Неправильный формат команды! Команда не будет выполнена.");
        } else {
            receiver.filter_by_annual_turnover(args[1]);
        }
    }

    @Override
    public void executeForScript(String[] args, Scanner sc) {

    }
}
