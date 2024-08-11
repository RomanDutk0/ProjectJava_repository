module com.example.coursetask {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.almasb.fxgl.all;
    requires org.slf4j;
    requires org.mockito;
    requires org.junit.jupiter.api;

    opens gui to javafx.fxml;
    exports gui;
}
