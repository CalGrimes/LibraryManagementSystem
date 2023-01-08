package com.calgrimes.librarymanagementsystem.Helpers;

import com.calgrimes.librarymanagementsystem.Main;
import javafx.scene.control.Alert;

public class DialogHelper {

    public static void showAlertCompletion(String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Main.stg);
        alert.setTitle("Success!");
        alert.setHeaderText(headerText);

        alert.showAndWait();
    }
}
