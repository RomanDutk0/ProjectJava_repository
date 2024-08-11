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

public class OtherCommandsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button calculateButton;

    @FXML
    private Button findPlaneButton;

    @FXML
    private TextArea finishMsg;

    @FXML
    private Button printAviacompanyButton;

    @FXML
    private Button printPlaneButton;

    @FXML
    private Button toMainButton;

    @FXML
    private TextField textField;

    @FXML
    private TextField minField;

    @FXML
    private TextField maxField;
    private int company_id;

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
    @FXML
    void initialize() {
        calculateButton.setOnAction(actionEvent -> {
            int sum = DbHandler.calculateCapacity(PassCompID.comp_id);
            finishMsg.clear();
            finishMsg.appendText("Загальна місткість для всієї обраної авіакомпанії - " + sum);
        });

        printAviacompanyButton.setOnAction(actionEvent -> {
            String planes = DbHandler.printAviacompany(PassCompID.comp_id);
            finishMsg.clear();
            finishMsg.appendText(planes);
        });

        printPlaneButton.setOnAction(actionEvent -> {
            String name = textField.getText();
            finishMsg.clear();
            if(DbHandler.checkIfExist(name, "plane_name", "planes")) {
                String plane = DbHandler.printPlane(name);
                finishMsg.appendText(plane);
            } else {
                finishMsg.appendText("Такого літака не існує!");
            }
        });

        findPlaneButton.setOnAction(actionEvent -> {
            String min = minField.getText();
            String max = maxField.getText();
            finishMsg.clear();
            String foundPlane = DbHandler.findPlane(min, max);
            finishMsg.appendText(foundPlane);
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
