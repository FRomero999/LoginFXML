package com.mycompany.loginfxml;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PrincipalController {

    @FXML
    private Button secondaryButton;
    @FXML
    private Label label;


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void hover(MouseEvent event) {
    }

    @FXML
    private void pulsacion(KeyEvent event) {
    }
}