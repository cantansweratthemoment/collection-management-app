package laba_8.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import laba_8.Client.ConsoleManager;
import laba_8.Client.Receiver;
import laba_8.Properties.Bundle;

public class SortController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Text questionText;

    @FXML
    private Button okButton;

    ResourceBundle resourceBundle = Bundle.getResourceBundle();

    @FXML
    void initialize() {
        ObservableList<String> types = FXCollections.observableArrayList("ID", resourceBundle.getString("owner"), resourceBundle.getString("name"), "X", "Y", resourceBundle.getString("creationDate"), resourceBundle.getString("annualTurnover"), resourceBundle.getString("type"), resourceBundle.getString("street"), "Zipcode", resourceBundle.getString("town") + " X", resourceBundle.getString("town") + " Y", resourceBundle.getString("town"));
        questionText.setText(resourceBundle.getString("how to sort?"));
        choiceBox.setItems(types);
        choiceBox.setValue("ID");
        okButton.setOnAction(actionEvent -> {
            Receiver.sortController = this;
            String type = choiceBox.getValue();
            if ("ID".equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_id");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (type.equals(resourceBundle.getString("owner"))) {
                try {
                    ConsoleManager.invoke("sort_by_owner");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (type.equals(resourceBundle.getString("name"))) {
                try {
                    ConsoleManager.invoke("sort_by_name");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if ("X".equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_x");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if ("Y".equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_y");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (resourceBundle.getString("creationDate").equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_date");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (resourceBundle.getString("annualTurnover").equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_annual_turnover");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (resourceBundle.getString("type").equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_type");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (resourceBundle.getString("street").equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_street");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if ("Zipcode".equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_zipcode");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if ((resourceBundle.getString("town") + " X").equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_town_x");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if ((resourceBundle.getString("town") + " Y").equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_town_y");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (resourceBundle.getString("town").equals(type)) {
                try {
                    ConsoleManager.invoke("sort_by_town");
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void death() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}