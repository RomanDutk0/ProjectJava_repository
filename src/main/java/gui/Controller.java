package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addPlaneButton;

    @FXML
    private Button deletePlaneButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button chooseAviacompanyButton;

    @FXML
    private Button otherCommandsButton;

    @FXML
    private Button createAviacompanyButton;

    @FXML
    public Button deleteCompanyButton;

    @FXML
    public Button ownerMenuButton;

    private static int company_id;
    @FXML
    void initialize() {

        addPlaneButton.setOnAction(actionEvent -> {
            if(DbHandler.countComps()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка!");
                alert.setContentText("Немає авіакомпаній в базі даних\nСтворіть нову!");
                alert.show();
            } else {
                addPlaneButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/gui/AddPlane.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                AddPlaneController addPlaneController = loader.getController();
                addPlaneController.setCompany_id(company_id);
                addPlaneController.initialize();

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Курсова робота Дутко Роман ОІ-25");
                stage.show();
            }
        });

        deletePlaneButton.setOnAction(actionEvent -> {
            deletePlaneButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/DeletePlane.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Курсова робота Дутко Роман ОІ-25");
            stage.show();
        });

        createAviacompanyButton.setOnAction(actionEvent -> {
            createAviacompanyButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/CreateCompany.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Курсова робота Дутко Роман ОІ-25");
            stage.show();
        });

        chooseAviacompanyButton.setOnAction(actionEvent -> {
            chooseAviacompanyButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/ChooseCompany.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Курсова робота Дутко Роман ОІ-25");
            stage.show();
        });

        otherCommandsButton.setOnAction(actionEvent -> {
            otherCommandsButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/OtherCommands.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            OtherCommandsController otherCommsController = loader.getController();
            otherCommsController.setCompany_id(company_id);
            otherCommsController.initialize();

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Курсова робота Дутко Роман ОІ-25");
            stage.show();
        });

        deleteCompanyButton.setOnAction(actionEvent -> {
            deleteCompanyButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/DeleteAviacompany.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Курсова робота Дутко Роман ОІ-25");
            stage.show();
        });

        exitButton.setOnAction(actionEvent -> {
            addPlaneButton.getScene().getWindow().hide();
            System.exit(0);
        });


//        ownerMenuButton.setOnAction(actionEvent -> {
//            ownerMenuButton.getScene().getWindow().hide();
//
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/gui/OwnerMenu.fxml"));
//
//            try {
//                loader.load();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            Parent root = loader.getRoot();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Курсова робота Дутко Роман ОІ-25");
//            stage.show();
//        });

    }
}
