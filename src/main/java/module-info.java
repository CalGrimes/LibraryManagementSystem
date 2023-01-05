module com.calgrimes.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires eu.hansolo.tilesfx;

    opens com.calgrimes.librarymanagementsystem to javafx.fxml;
    exports com.calgrimes.librarymanagementsystem;
}