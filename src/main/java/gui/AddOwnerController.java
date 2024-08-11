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

public class AddOwnerController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addOwnerButton;

    @FXML
    private TextField countryField;

    @FXML
    private TextArea finishMsg;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Button toMainButton;

    @FXML
    private Button toPreviousButton;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    private String type;

    private String[] allTypes = {"state", "person"};

    @FXML
    public void initialize() {
        addOwnerButton.setOnAction(actionEvent -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String country = countryField.getText();
            type = typeChoiceBox.getSelectionModel().getSelectedItem();
            finishMsg.clear();
            DbHandler.addOwner(type, firstName, lastName, country);
            if(type.equals("person")) {
                finishMsg.appendText("Власника " + firstName + " " + lastName + " додано\n");
            } else if (type.equals("state")) {
                finishMsg.appendText("Власника " + country + " додано\n");
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
            stage.setTitle("Курсова робота Дутко Роман ОІ-25");
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
