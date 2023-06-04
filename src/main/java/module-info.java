module sample.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens sample.chat to javafx.fxml;
    exports sample.chat;
    exports controller;
    opens controller to javafx.fxml;
    exports server;
    opens server to javafx.fxml;
}