package laba_8.Client;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import laba_8.Controllers.*;
import laba_8.Classes.Organization;
import laba_8.Commands.SerializedCommands.SerializedArgumentCommand;
import laba_8.Commands.SerializedCommands.SerializedCombinedCommand;
import laba_8.Commands.SpecificCommands.*;
import laba_8.Properties.Bundle;
import laba_8.Utils.CollectionManager;
import laba_8.Utils.Comparators.*;
import laba_8.Utils.ParserJson;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Receiver {

    private final Invoker invoker;
    private final Sender sender;
    private final DatagramSocket datagramSocket;
    public static RegisterController registerController;
    public static LoginController loginController;
    public static ShowController showController;
    public static AddController addController;
    public static UpdateController updateController;
    public static AddIfMinController addIfMinController;
    public static SortController sortController;

    public Receiver(Invoker invoker, Sender sender, DatagramSocket datagramSocket) {
        this.invoker = invoker;
        this.sender = sender;
        this.datagramSocket = datagramSocket;
    }

    ResourceBundle resourceBundle = Bundle.getResourceBundle();

    public void help() {
        StringBuilder result = new StringBuilder();
        invoker.getCommands().forEach((s, command) -> {
            result.append(command.aboutCommand());
        });
        result.append("О команде 'logout': разлогиниться.\n");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Информация о командах:");
        alert.setContentText(String.valueOf(result));
        alert.showAndWait();
    }
       /* System.out.println("Информация о командах:");
        invoker.getCommands().forEach((name, command) -> command.aboutCommand());
        System.out.println("О команде\u001B[36m logut\u001B[0m: разлогиниться.");*/

    public static boolean isLogin = false;
    public static String myLogin = "";
    public static String myPassword = "";
    public static String myColor = "";

    public void login(String login, String password) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new Login(), login + " " + password));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            sender.getDatagramSocket().setSoTimeout(5000);
            sender.getDatagramSocket().receive(ip);
            String result = new String(ib);
            System.out.println(result);
            result = result.trim();
            String result_arr[] = result.split(" ");
            if (result_arr[0].equals("Не_успешно!")) {
                isLogin = false;
                myLogin = "";
                myPassword = "";
                myColor = "";
                loginController.warning2();
            } else {
                isLogin = true;
                myLogin = login;
                myPassword = password;
                myColor = result_arr[1];
                loginController.succes();
            }
        } catch (SocketTimeoutException | PortUnreachableException e) {
            loginController.succes();//TODO Убрать, это строка для меня.
            loginController.warning1();
        }
    }

    public void register(String login, String password, String mail, String color) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new Register(), login + " " + password + " " + mail + " " + color));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            sender.getDatagramSocket().setSoTimeout(5000);
            sender.getDatagramSocket().receive(ip);
            String result = new String(ib);
            System.out.println(result);
            if (result.trim().equals("Пользователь с данным логином уже зарегистрирован!")) {
                isLogin = false;
                myLogin = "";
                myPassword = "";
                myColor = "";
                registerController.warning2();
            } else {
                isLogin = true;
                myLogin = login;
                myPassword = password;
                myColor = color;
                registerController.succes();
            }
        } catch (SocketTimeoutException | PortUnreachableException e) {
            registerController.warning1();
        }
    }

    public void info() throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new Info(), myLogin + " " + myPassword));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("info"));
            alert.setHeaderText(resourceBundle.getString("info"));
            result = result.replace("Тип коллекции:", resourceBundle.getString("Type of collection:"));
            result = result.replace("Дата инициализации:", resourceBundle.getString("Initialization date:"));
            result = result.replace("Количество элементов:", resourceBundle.getString("Number of elements:"));
            alert.setContentText(result);
            alert.showAndWait();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }

    public void warningAboutServerBeingNotAvailable() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ой!");
        alert.setHeaderText("У сервера проблемы!");
        alert.showAndWait();
    }

    public void show() throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new Show(), myLogin + " " +
                    myPassword));
            byte[] ib = new byte[8126];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            FileWriter writer = new FileWriter("secret_client_information_dont_look.json", false);
            writer.write(result.trim());
            writer.flush();
            ParserJson.fromJsonToCollection();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
            Scene scene = new Scene(root);
            showController.show();
            stage.setTitle("show");
            stage.setScene(scene);
            stage.show();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }


    public void add(Scanner sc) throws IOException {
        try {
            Organization organization = addController.create();
            sender.sendObject(new SerializedCombinedCommand(new Add(), organization, myLogin + " " + myPassword));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("!");
            alert.setHeaderText(result);
            alert.showAndWait();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }

    public void update(String ID_s, Scanner sc) throws IOException {
        try {
            Organization organization = updateController.create();
            sender.sendObject(new SerializedCombinedCommand(new Update(), organization, myLogin + " " + myPassword + " " + ID_s));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("!");
            alert.setHeaderText(result);
            alert.showAndWait();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }

    public void remove_by_id(String ID_s) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new RemoveById(), myLogin + " " + myPassword + " " + ID_s));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("!");
            alert.setHeaderText(result);
            alert.showAndWait();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }

    public void clear() throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new Clear(), myLogin + " " + myPassword));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("!");
            alert.setHeaderText(result);
            alert.showAndWait();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }

    public void execute_script(String path) throws InterruptedException, ClassNotFoundException {
        String[] line;
        String command;
        String[] parameters = new String[10];
        String id;
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(path)); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine().trim().split(" ");
                if (line[0].matches("add|add_if_min|update")) {
                    command = line[0];
                    switch (command) {
                        case "add":
                            try {
                                invoker.executeScriptCommandAdd(scanner);
                            } catch (NoSuchElementException e) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Ой!");
                                alert.setHeaderText("Файл закончился, аргументов не хватило.");
                                alert.showAndWait();
                            }
                            break;
                        case "add_if_min":
                            try {
                                invoker.executeScriptCommandAddIfMin(scanner);
                            } catch (NoSuchElementException e) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Ой!");
                                alert.setHeaderText("Файл закончился, аргументов не хватило.");
                                alert.showAndWait();
                            }
                            break;
                        case "update":
                            try {
                                invoker.executeScriptCommandUpdate(scanner, line[1]);
                            } catch (NoSuchElementException e) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Ой!");
                                alert.setHeaderText("Файл закончился, аргументов не хватило.");
                                alert.showAndWait();
                            } catch (ArrayIndexOutOfBoundsException e) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Ой!");
                                alert.setHeaderText("Что-то не так с аргументами команды.");
                                alert.showAndWait();
                            }
                            break;
                    }
                } else {
                    if (line[0].equals("execute_script") && line[1].equals(ExecuteScript.getPath())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Ой!");
                        alert.setHeaderText("Пресечена попытка рекурсивного вызова скрипта.");
                        alert.showAndWait();
                    } else {
                        invoker.executeCommand(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ой!");
            alert.setHeaderText("Не удалось найти файл.");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ой!");
            alert.setHeaderText("Что-то пошло не так!");
            alert.showAndWait();
        }
    }

    public void exit() {
        System.out.println("Завершение работы программы.");
        System.exit(0);
    }

    public void remove_head() throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new RemoveHead(), myLogin + " " + myPassword));
            byte[] ib = new byte[512];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("!");
            alert.setHeaderText(result);
            alert.showAndWait();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }

    public void add_if_min(Scanner sc) throws IOException {
        try {
            Organization organization = addIfMinController.create();
            sender.sendObject(new SerializedCombinedCommand(new AddIfMin(), organization, myLogin + " " + myPassword));
            byte[] ib = new byte[256];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("!");
            alert.setHeaderText(result);
            alert.showAndWait();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }

    public void history() {
        String result = "";
        if (invoker.getCommandHistory()[0] == null) {
            result = result.concat("История пуста.");
        } else {
            for (int i = 0; i < 7; i++) {
                if (invoker.getCommandHistory()[i] != null) {
                    result = result.concat(invoker.getCommandHistory()[i] + "\n");
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("History");
        alert.setHeaderText(resourceBundle.getString("Recent teams:"));
        alert.setContentText(result);
        alert.showAndWait();
    }

    public void filter_by_annual_turnover(String annualTurnover_s) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new FilterByAnnualTurnover(), myLogin + " " + myPassword + " " + annualTurnover_s));
            byte[] ib = new byte[4096];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            System.out.println(result);
        } catch (SocketTimeoutException e) {
            System.out.println("У сервера проблемы, команда не выполнится.");
        }
    }

    public void filter_starts_with_name(String name) throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new FilterStartsWithName(), myLogin + " " + myPassword + " " + name));
            byte[] ib = new byte[4096];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("!");
            alert.setHeaderText(result);
            alert.showAndWait();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }

    public void print_field_descending_annual_turnover() throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new PrintFieldDescendingAnnualTurnover(), myLogin + " " + myPassword));
            byte[] ib = new byte[4096];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("!");
            alert.setHeaderText(result);
            alert.showAndWait();
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }


    public void visualize() throws IOException {
        try {
            sender.sendObject(new SerializedArgumentCommand(new Show(), myLogin + " " +
                    myPassword));
            byte[] ib = new byte[8126];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            String result = new String(ib);
            FileWriter writer = new FileWriter("secret_client_information_dont_look.json", false);
            writer.write(result.trim());
            writer.flush();
            ParserJson.fromJsonToCollection();
            paintObjects(CollectionManager.getCollection());
        } catch (SocketTimeoutException | PortUnreachableException e) {
            warningAboutServerBeingNotAvailable();
        }
    }

    public void paintObjects(List<Organization> collection) {
        Stage stage = new Stage();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Group root = new Group();
        Scene scene = new Scene(root, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        stage.setScene(scene);
        collection.forEach(organization -> {
            double x = organization.getX();
            double y = organization.getY();
            if (organization.getY() >= primaryScreenBounds.getHeight()) {
                y = Math.ceil(Math.abs(Math.cos(organization.getY())) * primaryScreenBounds.getMaxY());
                System.out.println(y);
            }
            if (organization.getX() >= primaryScreenBounds.getWidth()) {
                x = Math.ceil(Math.abs(Math.cos(organization.getX())) * primaryScreenBounds.getMaxX());
                System.out.println(x);
            }
            Circle circle = new Circle(x, y, organization.getAnnualTurnover() / 100);
            circle.setFill(Paint.valueOf(organization.getColor()));
            PathTransition tt = new PathTransition(Duration.millis(1500), circle);
            tt.setNode(circle);
            tt.setPath(new Line(x, y + 20, x, y));
            tt.setAutoReverse(true);
            tt.setCycleCount(Animation.INDEFINITE);
            root.getChildren().add(circle);
            try {
                Thread.sleep((int) (Math.random() * 400));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tt.play();
        });
        scene.setOnMouseClicked(e -> {
            double mouse_x = e.getX();
            double mouse_y = e.getY();
            collection.forEach(organization1 -> {
                double mouse_x_max = mouse_x + organization1.getAnnualTurnover() / 100;
                double mouse_x_min = mouse_x - organization1.getAnnualTurnover() / 100;
                double mouse_y_max = mouse_y + organization1.getAnnualTurnover() / 100;
                double mouse_y_min = mouse_y - organization1.getAnnualTurnover() / 100;
                double xx = organization1.getX();
                double yy = organization1.getY();
                double alternative_x = Math.ceil(Math.abs(Math.cos(organization1.getX())) * primaryScreenBounds.getMaxX());
                double alternative_y = Math.ceil(Math.abs(Math.cos(organization1.getY())) * primaryScreenBounds.getMaxY());
                if ((((xx >= mouse_x_min) && (xx <= mouse_x_max)) || ((alternative_x >= mouse_x_min) && (alternative_x <= mouse_x_max))) && (((yy >= mouse_y_min) && (yy <= mouse_y_max)) || ((alternative_y <= mouse_y_max) && (alternative_y >= mouse_y_min)))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("!");
                    alert.setHeaderText(organization1.getName() + "\n" +
                            organization1.getAnnualTurnover() + "\n" +
                            organization1.getOwner());
                    alert.showAndWait();
                }
            });
        });
        scene.setRoot(root);
        stage.show();
    }

    public void sort_by_id() throws IOException {
        CollectionManager.getCollection().sort(new IDComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_owner() throws IOException {
        CollectionManager.getCollection().sort(new OwnerComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_name() throws IOException {
        CollectionManager.getCollection().sort(new NameComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_x() throws IOException {
        CollectionManager.getCollection().sort(new XComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_y() throws IOException {
        CollectionManager.getCollection().sort(new YComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_date() throws IOException {
        CollectionManager.getCollection().sort(new DateComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_annual_turnover() throws IOException {
        CollectionManager.getCollection().sort(new TurnoverComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_type() throws IOException {
        CollectionManager.getCollection().sort(new TypeComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_street() throws IOException {
        CollectionManager.getCollection().sort(new StreetComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_zipcode() throws IOException {
        CollectionManager.getCollection().sort(new ZipcodeComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_town_x() throws IOException {
        CollectionManager.getCollection().sort(new LXComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_town_y() throws IOException {
        CollectionManager.getCollection().sort(new LYComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }

    public void sort_by_town() throws IOException {
        CollectionManager.getCollection().sort(new TownComparator());
        showController.death();
        sortController.death();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("show.fxml"));
        Scene scene = new Scene(root);
        showController.show();
        stage.setTitle("show");
        stage.setScene(scene);
        stage.show();
        showController.show();
    }
}