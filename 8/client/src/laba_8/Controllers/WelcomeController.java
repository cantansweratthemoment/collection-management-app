package laba_8.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import laba_8.Properties.Bundle;

public class WelcomeController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="welcomeText"
    private Text welcomeText; // Value injected by FXMLLoader

    @FXML // fx:id="pleaseLogInText"
    private Text pleaseLogInText; // Value injected by FXMLLoader

    @FXML // fx:id="logInButton"
    private Button logInButton; // Value injected by FXMLLoader

    @FXML // fx:id="registerButton"
    private Button registerButton; // Value injected by FXMLLoader

    ResourceBundle resourceBundle = Bundle.getResourceBundle();

    @FXML
    private Text title1;

    @FXML
    private Text title2;

    @FXML
    private ChoiceBox<String> language;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ObservableList<String> types = FXCollections.observableArrayList("English", "Polish", "Norwegian", "Russian");
        language.setItems(types);
        language.setValue("Russian");
        logInButton.setText(resourceBundle.getString("login"));
        registerButton.setText(resourceBundle.getString("registration"));
        title1.setText(resourceBundle.getString("welcome"));
        title2.setText(resourceBundle.getString("PlsLogIn"));
        logInButton.setOnAction(actionEvent -> {
            String ll = language.getValue();
            Bundle.setResourceBundle(ll);
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
                Scene scene = new Scene(root, 700, 400);
                stage.setTitle(resourceBundle.getString("welcome"));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        registerButton.setOnAction(actionEvent -> {
            String ll = language.getValue();
            Bundle.setResourceBundle(ll);
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("registration.fxml"));
                Scene scene = new Scene(root, 700, 400);
                stage.setTitle(resourceBundle.getString("registration"));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}