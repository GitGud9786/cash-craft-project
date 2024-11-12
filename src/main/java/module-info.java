module org.example.cashcraft {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.cashcraft to javafx.fxml;
    exports org.example.cashcraft;
}