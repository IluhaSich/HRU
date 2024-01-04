module com.example.infosecurity {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.infosecurity to javafx.fxml;
    exports com.example.infosecurity;
}