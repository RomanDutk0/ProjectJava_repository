package gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class MainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 602, 400);
        stage.setTitle("Курсова робота Дутко Роман ОІ-25");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
