package laba_6;

import laba_6.Server.Communicator;
import laba_6.Utils.FileUtils.ParserJson;

import java.io.IOException;

/**
 * Главный класс приложения.
 */
public class Main {
    public static void main(String[] args){
        if (args.length == 0 || args[0].trim().length() == 0||args.length==1) {
            System.out.println("Вы должны были ввести порт и файл, из которого следует загрузить коллекцию.\nПопробуйте ещё раз!");
            System.exit(1);
        }
        String fileName = args[1];
        ParserJson.setFileName(fileName);
        Runtime.getRuntime().addShutdownHook(new Thread(ParserJson::collectionToJson));
        Communicator communicator = new Communicator();
        communicator.run(args[0]);
    }
}