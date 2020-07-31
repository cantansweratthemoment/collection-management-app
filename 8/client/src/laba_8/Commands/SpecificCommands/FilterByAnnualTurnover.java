package laba_8.Commands.SpecificCommands;

import laba_8.Commands.Command;
import laba_8.Client.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class FilterByAnnualTurnover extends Command implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public FilterByAnnualTurnover(Receiver receiver) {
        this.receiver = receiver;
    }

    public FilterByAnnualTurnover() {

    }

    @Override
    public String aboutCommand() {
        return("О команде 'filter_by_annual_turnover': выводятся элементы, значение поля annualTurnover которых равно заданному.\n");
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
