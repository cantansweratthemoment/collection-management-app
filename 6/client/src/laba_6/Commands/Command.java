package laba_6.Commands;

import java.io.IOException;
import java.util.Scanner;

/**
 * Абстрактная команда: класс-отец всех команд.
 */
public abstract class Command {
    /**
     * Метоод - описание команды.
     */
    public abstract void aboutCommand();

    /**
     * Метод - исполнение команды.
     *
     * @param args - аругменты команды.
     */
    public abstract void execute(String[] args) throws IOException, InterruptedException, ClassNotFoundException;
    public abstract void executeForScript(String[] args, Scanner sc);
}
