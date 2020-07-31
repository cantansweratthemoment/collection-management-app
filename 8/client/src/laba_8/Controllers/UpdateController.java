package laba_8.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import laba_8.Classes.Organization;
import laba_8.Classes.OrganizationType;
import laba_8.Client.ConsoleManager;
import laba_8.Client.Receiver;
import laba_8.Properties.Bundle;
import laba_8.Utils.Creator;
import laba_8.Utils.Readers;

public class UpdateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameFiled;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    @FXML
    private TextField turnoverField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField zipcodeField;

    @FXML
    private TextField lxField;

    @FXML
    private TextField lyField;

    @FXML
    private TextField townField;

    @FXML
    private ChoiceBox<String> typeField;

    @FXML
    private Button addButton;

    @FXML
    private Text addtext;

    @FXML
    private TextField idField;
    public static String final_name;
    public static Double final_x;
    public static Double final_y;
    public static Double final_annualTurnover;
    public static String final_street;
    public static String final_zipcode;
    public static Float final_lx;
    public static Float final_ly;
    public static String final_town;
    public static OrganizationType final_type;

    ResourceBundle resourceBundle = Bundle.getResourceBundle();

    @FXML
    void initialize() {
        nameFiled.setPromptText(resourceBundle.getString("enterAName"));
        turnoverField.setPromptText(resourceBundle.getString("Enter the company's annual turnover (must be greater than 0)."));
        xField.setPromptText(resourceBundle.getString("Enter the x coordinate of the organization (no more than 643)."));
        yField.setPromptText(resourceBundle.getString("Enter the organization's Y coordinate."));
        streetField.setPromptText(resourceBundle.getString("Enter the address."));
        zipcodeField.setPromptText(resourceBundle.getString("Enter your zip code."));
        lxField.setPromptText(resourceBundle.getString("Enter the x coordinate of the city."));
        lyField.setPromptText(resourceBundle.getString("Enter the y coordinate of the city."));
        townField.setPromptText(resourceBundle.getString("Enter the city name."));
        idField.setPromptText(resourceBundle.getString("enterID"));
        addtext.setText(resourceBundle.getString("add"));
        Receiver.updateController = this;
        ObservableList<String> types = FXCollections.observableArrayList("COMMERCIAL", "PUBLIC", "TRUST", "PRIVATE_LIMITED_COMPANY", "OPEN_JOINT_STOCK_COMPANY");        typeField.setItems(types);
        typeField.setValue(resourceBundle.getString("PUBLIC"));
        addButton.setOnAction(actionEvent -> {
            String name = nameFiled.getText();
            final_name = Readers.readName(name);
            String x = xField.getText();
            final_x = Readers.readX(x);
            String y = yField.getText();
            final_y = Readers.readY(y);
            String annualTurnover = turnoverField.getText();
            final_annualTurnover = Readers.readAnnualTurnover(annualTurnover);
            String street = streetField.getText();
            final_street = Readers.readStreet(street);
            String zipcode = zipcodeField.getText();
            final_zipcode = Readers.readZipCode(zipcode);
            String lx = lxField.getText();
            final_lx = Readers.readLocation(lx);
            String ly = lyField.getText();
            final_ly = Readers.readLocation(ly);
            String town = townField.getText();
            final_town = Readers.readTownName(town);
            String type = typeField.getValue();
            final_type = Readers.readType(type);
            String ID = idField.getText();
            if (final_name != null && final_x != null && final_y != null && final_annualTurnover != null && final_street != null && final_zipcode != null && final_lx != null && final_ly != null && final_town != null && final_type != null && ID != null) {
                create();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ой!");
                alert.setHeaderText("Убедитесь, пожалуйста, в том, что вы всё ввели правильно..");
                alert.showAndWait();
            }
            try {
                ConsoleManager.invoke("update " + ID);
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public Organization create() {
        return Creator.create(final_name, final_x, final_y, final_annualTurnover, final_type, final_street, final_zipcode, final_lx, final_ly, final_town);
    }
}