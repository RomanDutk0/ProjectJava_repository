package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import base.plane.*;
import base.utilities.PlaneUtils;
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

public class AddPlaneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addPlaneButton;

    @FXML
    private Button toMainButton;

    @FXML
    private ChoiceBox<String> modelChoiceBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea finishMsg;

    private int company_id;

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    private String[] allModels = {"Airbus", "Boeing", "Douglas", "Mriya"};

    private String model;
    @FXML
    public void initialize() {
        addPlaneButton.setOnAction(actionEvent -> {
            String name = nameField.getText();
            model = modelChoiceBox.getSelectionModel().getSelectedItem();
            finishMsg.clear();
            if(!DbHandler.checkIfExist(name, "company_name", "company")) {
                Plane plane = PlaneUtils.createPlane(model, name);
                DbHandler.addPlane(plane, name, PassCompID.comp_id);
                finishMsg.appendText("Літак " + name + " додано\n");
            } else {
                finishMsg.appendText("Виберіть дійсну авіакомпанію!");
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

    public void getModel(ActionEvent event) {
        model = modelChoiceBox.getValue();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modelChoiceBox.getItems().addAll(allModels);
        modelChoiceBox.setOnAction(this::getModel);
    }
}
