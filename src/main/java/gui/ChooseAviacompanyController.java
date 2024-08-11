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

public class ChooseAviacompanyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button chooseButton;

    @FXML
    private TextArea finishMsg;

    @FXML
    private TextField nameField;

    @FXML
    private Button toMainButton;
    @FXML
    void initialize() {
        chooseButton.setOnAction(actionEvent -> {
            String name = nameField.getText();
            finishMsg.clear();
            if(DbHandler.checkIfExist(name, "company_name", "company")) {
                PassCompID.comp_id = DbHandler.getID(name);
                finishMsg.appendText("Авіакомпанію " + name + " обрано\n");
            } else {
                finishMsg.appendText("Авіакомпанії " + name + " \nне існує в базі даних!\n");
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
