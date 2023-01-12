package com.calgrimes.librarymanagementsystem.Helpers;

import com.calgrimes.librarymanagementsystem.DBObjects.Book;
import com.calgrimes.librarymanagementsystem.Main;
import com.calgrimes.librarymanagementsystem.Utilities.LogLevel;
import com.calgrimes.librarymanagementsystem.Utilities.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.RadialGradient;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialogHelper {

    public static void showAlertCompletion(String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Main.stg);
        alert.setTitle("Success!");
        alert.setHeaderText(headerText);

        alert.showAndWait();
    }

    public static void showErrorAlert(String title, String headerText, String contentText)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(Main.stg);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private static Dialog<?> setupDialog(String title, String headerText)
    {
        Dialog<?> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(title);

        // Set the system icon of the dialog
        dialog.initOwner(Main.stg);

        return dialog;
    }


    public static Optional<Book> showAddBook() {
        // Create the custom dialog.
        Dialog<Book> dialog = (Dialog<Book>) setupDialog("Add Book", "Please enter the book information:");


        // Set the button types.
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Constraints to ensure css styles don't reduce field sizes when applied
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow( Priority.ALWAYS );

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow( Priority.ALWAYS );

        // Create the book_id, book_name, book_genre, book_price labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.getColumnConstraints().addAll( new ColumnConstraints( 60 ), col1, new ColumnConstraints( 50 ), col2 );
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField bookIdField = new TextField();
        bookIdField.setPromptText("0");
        bookIdField.setText("0");
        TextField bookNameField = new TextField();
        bookNameField.setPromptText("Harry Potter");
        TextField bookGenreField = new TextField();
        bookGenreField.setPromptText("Fantasy");
        TextField bookPriceField = new TextField();
        bookPriceField.setPromptText("12.99");

        // Disable the bookIdField
        bookIdField.setMouseTransparent(true);
        bookIdField.setFocusTraversable(false);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(bookIdField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(bookNameField, 1, 1);
        grid.add(new Label("Genre:"), 0, 2);
        grid.add(bookGenreField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(bookPriceField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the book_name field by default.
        Platform.runLater(() -> bookNameField.requestFocus());

        bookPriceField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Regular expression to match GBP Pound format
            String pattern = "^£[0-9]+(\\.[0-9]{2})?$";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(newValue);
            if (m.find()) {
                // Input is valid
                bookPriceField.setStyle("-fx-border-color: green;");
            } else {
                // Input is invalid
                bookPriceField.setStyle("-fx-border-color: red;");
            }
        });

        // Convert the result to a book when the add button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                // Parse bookId to int
                int bookId = Integer.parseInt(bookIdField.getText());

                // Check other fields are not empty
                List<String> editableFields = List.of(bookNameField.getText(), bookGenreField.getText(), bookPriceField.getText());

                // Check if any editable field in the dialog shown is empty.
                if (editableFields.stream().anyMatch(String::isEmpty))
                {
                    Logger.log("Some fields contain missing entries", LogLevel.ERROR);
                    showErrorAlert("Invalid Input", "Not all fields appear to have entries", "Please enter a data in to every field.");
                }
                else {

                    // Validate bookPrice and return a new Book object.
                    if (bookPriceField.getStyle().contains("-fx-border-color: green;")) {
                        double bookPrice = Double.parseDouble(bookPriceField.getText().substring(1));
                        return new Book(bookId, bookNameField.getText(), bookGenreField.getText(), bookPrice);
                    } else {
                        // Input is invalid
                        Logger.log("Please enter a valid GBP Pound amount in the format '£X.XX'", LogLevel.ERROR);
                        showErrorAlert("Invalid Input", "Invalid GBP Pound format", "Please enter a valid GBP Pound amount in the format '£X.XX'");
                    }
                }

            }
            return null;
        });
        Optional<Book> result = dialog.showAndWait();

        return result;
    }

}
