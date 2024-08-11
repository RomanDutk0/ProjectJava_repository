package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteOwnerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField countryField;

    @FXML
    private Button deleteOwnerButton;

    @FXML
    private TextArea finishMsg;

    @FXML
    private Button toMainButton;

    @FXML
    private Button toPreviousButton;

    @FXML
    void initialize() {
        deleteOwnerButton.setOnAction(actionEvent -> {
            DbHandler.deleteOwner(PassCompID.comp_id);
            finishMsg.clear();
            finishMsg.appendText("Власника видалено");
        });

        toMainButton.setOnAction(actionEvent -> {
            toMainButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/MainMenu.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Курсова робота Пастернака Володимира КН-207");
            stage.show();
        });

        toPreviousButton.setOnAction(actionEvent -> {
            toPreviousButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/OwnerMenu.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Курсова робота Пастернака Володимира КН-207");
            stage.show();
        });
    }
}
