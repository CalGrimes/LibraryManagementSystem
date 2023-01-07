package com.calgrimes.librarymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login {
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblLoginErr;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    public Login() {
    }

    public void actUserLogin(ActionEvent event) throws IOException {
        this.checkLogin();
    }

    private void checkLogin() throws IOException {
        Main m = new Main();
        if (this.txtUsername.getText().toString().equals("a") && this.txtPassword.getText().toString().equals("a")) {
            this.lblLoginErr.setText("Success!");
            m.changeScene("system.fxml");
        } else if (this.txtUsername.getText().isEmpty() && this.txtPassword.getText().isEmpty()) {
            this.lblLoginErr.setText("Please enter your data.");
        } else {
            this.lblLoginErr.setText("Wrong username or password!");
        }

    }
}
