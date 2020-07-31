package laba_8.Commands;

import java.io.IOException;
import java.util.Scanner;

public abstract class Command {
    public abstract String aboutCommand();

    public abstract void execute(String[] args) throws IOException, InterruptedException, ClassNotFoundException;

    public abstract void executeForScript(String[] args, Scanner sc);
}
