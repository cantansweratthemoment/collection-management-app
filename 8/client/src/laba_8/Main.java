package laba_8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import laba_8.Client.ConsoleManager;

import java.util.List;


public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parameters parameters = getParameters();
        List<String> args = parameters.getRaw();
        try {
            ConsoleManager consoleManager = new ConsoleManager();
            consoleManager.start(args.get(0), args.get(1));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Аргументы введены некорректно.");
        }
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("welcome.fxml"));
        stage.setTitle("welcome");
        stage.setScene(new Scene(root, 700, 400));
        stage.setResizable(false);
        stage.show();
    }
}