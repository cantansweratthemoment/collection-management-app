package laba_7;

import laba_7.Client.ConsoleManager;

import java.io.IOException;

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