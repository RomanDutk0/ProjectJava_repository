package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAviacompanyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createButton;

    @FXML
    private TextArea finishMsg;

    @FXML
    private TextField nameField;

    @FXML
    private Button toMainButton;

    @FXML
    void initialize() {
        createButton.setOnAction(actionEvent -> {
            String name = nameField.getText();
            DbHandler.createAviacompany(name);
            finishMsg.clear();
            finishMsg.appendText("Авіакомпанію " + name + " створено\n");
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
            stage.setTitle("Курсова робота Дутко Роман ОІ-25");
            stage.show();
        });
    }
}
