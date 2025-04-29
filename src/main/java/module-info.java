module com.example.cashcraft {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires itextpdf;
    requires java.desktop;
    requires javafx.swing;
    requires kernel;
    requires layout;
    requires io;


    opens com.example.cashcraft to javafx.fxml;
    exports com.example.cashcraft;
}