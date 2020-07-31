package laba_8.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import laba_8.Client.ConsoleManager;
import laba_8.Client.Receiver;
import laba_8.Classes.Organization;
import laba_8.Properties.Bundle;
import laba_8.Utils.CollectionManager;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ShowController {


    @FXML
    private GridPane gridPane;

    @FXML
    private Button removeByIdButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button filterStartsWithNameButton;

    @FXML
    private Button addIfMinButton;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeHeadButton;

    @FXML
    private FlowPane gridPane2;

    @FXML
    private Button prientFieldDescendingAnnualTurnoverButton;

    ResourceBundle resourceBundle = Bundle.getResourceBundle();

    @FXML
    void initialize() {
        Receiver.showController = this;
        addButton.setText(resourceBundle.getString("add"));
        updateButton.setText(resourceBundle.getString("update"));
        removeByIdButton.setText(resourceBundle.getString("remove_by_id"));
        removeHeadButton.setText(resourceBundle.getString("remove_ahead"));
        filterStartsWithNameButton.setText(resourceBundle.getString("filter_starts_with_name"));
        prientFieldDescendingAnnualTurnoverButton.setText(resourceBundle.getString("prientFieldDescendingAnnualTurnover"));
        clearButton.setText(resourceBundle.getString("clear"));
        addIfMinButton.setText(resourceBundle.getString("add_if_min"));
        openSort();
        addButton.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("add.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setTitle(resourceBundle.getString("add"));
            stage.setScene(scene);
            stage.show();
        });
        updateButton.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("update.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setTitle(resourceBundle.getString("update"));
            stage.setScene(scene);
            stage.show();
        });
        addIfMinButton.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("addifmin.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setTitle(resourceBundle.getString("add"));
            stage.setScene(scene);
            stage.show();
        });
        removeHeadButton.setOnAction(actionEvent -> {
            try {
                ConsoleManager.invoke("remove_head");
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        prientFieldDescendingAnnualTurnoverButton.setOnAction(actionEvent -> {
            try {
                ConsoleManager.invoke("print_field_descending_annual_turnover");
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        removeByIdButton.setOnAction(actionEvent -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(resourceBundle.getString("remove_by_id"));
            dialog.setHeaderText(resourceBundle.getString("enterID"));
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                try {
                    String res = result.get();
                    ConsoleManager.invoke("remove_by_id " + res);
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });
        clearButton.setOnAction(actionEvent -> {
            try {
                ConsoleManager.invoke("clear");
            } catch (InterruptedException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        });
        filterStartsWithNameButton.setOnAction(actionEvent -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(resourceBundle.getString("filter_starts_with_name"));
            dialog.setHeaderText(resourceBundle.getString("enterAName"));
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                try {
                    String res = result.get();
                    ConsoleManager.invoke("filter_starts_with_name " + res);
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public void show() {
        ObservableList<Organization> organizationObservableList = FXCollections.observableArrayList(
                CollectionManager.getCollection()
        );
        TableView<Organization> table = new TableView<>(organizationObservableList);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        table.setPrefWidth(primaryScreenBounds.getWidth());
        table.setPrefHeight(primaryScreenBounds.getHeight() - 300);
        table.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        TableColumn<Organization, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.getColumns().add(idColumn);
        TableColumn<Organization, Double> ownerColumn = new TableColumn<>(resourceBundle.getString("owner"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        table.getColumns().add(ownerColumn);
        TableColumn<Organization, String> nameColumn = new TableColumn<>(resourceBundle.getString("name"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(nameColumn);
        nameColumn.setEditable(true);
        nameColumn.setOnEditCommit(t -> t.getRowValue().setName(t.getNewValue()));
        TableColumn<Organization, Double> xColumn = new TableColumn<>("X");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        table.getColumns().add(xColumn);
        xColumn.setEditable(true);
        TableColumn<Organization, Double> yColumn = new TableColumn<>("Y");
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        table.getColumns().add(yColumn);
        yColumn.setEditable(true);
        TableColumn<Organization, Double> dateColumn = new TableColumn<>(resourceBundle.getString("creationDate"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        table.getColumns().add(dateColumn);
        TableColumn<Organization, Double> turnoverColumn = new TableColumn<>(resourceBundle.getString("annualTurnover"));
        turnoverColumn.setCellValueFactory(new PropertyValueFactory<>("annualTurnover"));
        table.getColumns().add(turnoverColumn);
        turnoverColumn.setEditable(true);
        TableColumn<Organization, String> typeColumn = new TableColumn<>(resourceBundle.getString("type"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        table.getColumns().add(typeColumn);
        typeColumn.setEditable(true);
        TableColumn<Organization, String> streetColumn = new TableColumn<>(resourceBundle.getString("street"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        table.getColumns().add(streetColumn);
        streetColumn.setEditable(true);
        TableColumn<Organization, String> zipcodeColumn = new TableColumn<>("Zipcode");
        zipcodeColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        table.getColumns().add(zipcodeColumn);
        zipcodeColumn.setEditable(true);
        TableColumn<Organization, Double> lxColumn = new TableColumn<>(resourceBundle.getString("town") + " X");
        lxColumn.setCellValueFactory(new PropertyValueFactory<>("lx"));
        table.getColumns().add(lxColumn);
        lxColumn.setEditable(true);
        TableColumn<Organization, Double> lyColumn = new TableColumn<>(resourceBundle.getString("town") + " Y");
        lyColumn.setCellValueFactory(new PropertyValueFactory<>("ly"));
        table.getColumns().add(lyColumn);
        lyColumn.setEditable(true);
        TableColumn<Organization, String> townColumn = new TableColumn<>(resourceBundle.getString("town"));
        townColumn.setCellValueFactory(new PropertyValueFactory<>("town"));
        table.getColumns().add(townColumn);
        townColumn.setEditable(true);
        FlowPane root1 = new FlowPane(500, 500, table);
        gridPane.add(gridPane2, 0, 0, 1, 2);
        gridPane.add(root1, 0, 1);
    }

    public void openSort() {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("sort.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void death() {
        Stage stage = (Stage) filterStartsWithNameButton.getScene().getWindow();
        stage.close();
    }
}