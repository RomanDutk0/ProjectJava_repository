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

public class SelectOwnerController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField countryField;

    @FXML
    private TextArea finishMsg;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Button selectOwnerButton;

    @FXML
    private Button toMainButton;

    @FXML
    private Button toPreviousButton;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    private String type;

    private String[] allTypes = {"state", "person"};

    @FXML
    void initialize() {
        selectOwnerButton.setOnAction(actionEvent -> {

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String country = countryField.getText();
            type = typeChoiceBox.getSelectionModel().getSelectedItem();
            finishMsg.clear();
            PassCompID.comp_id = DbHandler.getOwnerID(type, firstName, lastName, country);
            if(type.equals("person")) {
                if (DbHandler.checkIfExist(firstName, "first_name", "owner") &&
                        DbHandler.checkIfExist(lastName, "last_name", "owner"))
                    finishMsg.appendText("Власника " + firstName + " " + lastName + " обрано\n");
                else
                    finishMsg.appendText("Такого власника не існує!");
            } else if (type.equals("state")){
                if (DbHandler.checkIfExist(country, "country", "owner"))
                    finishMsg.appendText("Власника " + country + " обрано\n");
                else
                    finishMsg.appendText("Такого власника не існує!");
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

    public void getType(ActionEvent event) {
        type = typeChoiceBox.getValue();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeChoiceBox.getItems().addAll(allTypes);
        typeChoiceBox.setOnAction(this::getType);
    }
}
