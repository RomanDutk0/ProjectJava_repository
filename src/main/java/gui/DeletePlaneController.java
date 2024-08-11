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

public class DeletePlaneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deletePlaneButton;

    @FXML
    private TextArea finishMsg;

    @FXML
    private TextField nameField;

    @FXML
    private Button toMainButton;

    @FXML
    void initialize() {
        deletePlaneButton.setOnAction(actionEvent -> {
            String name = nameField.getText();
            if(DbHandler.checkIfExist(name, "plane_name", "planes")) {
                DbHandler.deletePlane(name);
                finishMsg.clear();
                finishMsg.appendText("Літак " + name + " видалено\n");
            } else {
                finishMsg.appendText("Такого літака не існує в базі!");
            }
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
