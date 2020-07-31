package laba_6;

import laba_6.Client.ConsoleManager;

import java.io.IOException;

/**
 * Главный класс приложения.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        try {
            ConsoleManager consoleManager = new ConsoleManager();
            consoleManager.interactive(args[0], args[1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Аргументы введены некорректно.");
        }
    }
}