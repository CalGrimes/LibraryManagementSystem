package com.calgrimes.librarymanagementsystem;

import com.calgrimes.librarymanagementsystem.Utilities.LogLevel;
import com.calgrimes.librarymanagementsystem.Utilities.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import org.jooq.SQLDialect;

import java.io.IOException;



public class Main extends Application {

    public static final boolean DEBUG = true;
    public static final SQLDialect dialect = SQLDialect.POSTGRES;
    public static Stage stg;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Logger.log("Starting Program.", LogLevel.UTILITY);

        stg = primaryStage;

        primaryStage.setResizable(false);
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("login.fxml"));
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(new Scene(root, 600.0, 400.0));
        primaryStage.getIcons().add(new Image(this.getClass().getResource("/img/open-book.png").toString()));
        primaryStage.show();
    }
    public void changeScene(String fxml) throws IOException {
        Logger.logf("Changing scene to: %s.",  LogLevel.UTILITY, fxml.split(".fxml")[0]);
        Parent pane = (Parent)FXMLLoader.load(this.getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        java.lang.System.setProperty("org.jooq.no-logo", "true");
        java.lang.System.setProperty("org.jooq.no-tips", "true");
        launch();
    }
}