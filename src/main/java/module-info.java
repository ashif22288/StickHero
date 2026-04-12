module com.example.ap_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires junit;
    requires javafx.media;


    opens com.example.ap_game to javafx.fxml;
    exports com.example.ap_game;
}