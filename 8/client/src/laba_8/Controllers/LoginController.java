package laba_8.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import laba_8.Client.ConsoleManager;
import laba_8.Client.Receiver;
import laba_8.Properties.Bundle;

import javax.swing.text.LabelView;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private Label login;

    @FXML
    private Label pass;

    @FXML
    private TextField loginField;

    @FXML
    private Text warningText1;

    @FXML
    private Text warningText2;

    @FXML
    private Text emptyFieldsWarningText;

    ResourceBundle resourceBundle = Bundle.getResourceBundle();

    @FXML
    void initialize() {
        loginField.setPromptText(resourceBundle.getString("Enter your username"));
        passwordField.setPromptText(resourceBundle.getString("Enter the password"));
        pass.setText(resourceBundle.getString("password"));
        login.setText(resourceBundle.getString("login"));
        submitButton.setText(resourceBundle.getString("submit"));
        warningText1.setText(resourceBundle.getString("serverProblem"));
        warningText2.setText(resourceBundle.getString("warning2"));
        emptyFieldsWarningText.setText(resourceBundle.getString("warningEmptyFields"));
        warningText1.setVisible(false);
        warningText2.setVisible(false);
        emptyFieldsWarningText.setVisible(false);
        submitButton.setOnAction(actionEvent -> {
            warningText1.setVisible(false);
            warningText2.setVisible(false);
            emptyFieldsWarningText.setVisible(false);
            String login = loginField.getText();
            String password = passwordField.getText();
            Receiver.loginController = this;
            if (!login.equals("") && !password.equals("")) {
                try {
                    ConsoleManager.invoke("login " + login + " " + password);
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                emptyFieldsWarning();
            }
        });
    }

    public void warning1() {
        warningText1.setVisible(true);
    }

    public void warning2() {
        warningText2.setVisible(true);
    }

    public void emptyFieldsWarning() {
        emptyFieldsWarningText.setVisible(true);
    }

    public void succes() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainn.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("organization database");
            stage.setScene(scene);
            stage.show();
            stage.setMinHeight(500);
            stage.setMinWidth(800);
            stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//TODO Сделать кнопку возвращения из окна логина/регистрации.