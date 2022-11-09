package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PrincipalController implements Initializable{

    @FXML
    private Button secondaryButton;
    @FXML
    private Label info;


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }


    @FXML
    private void pulsacion(KeyEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        info.setText( "Hola " + (SessionData.getUsuario()).getAlias() );
        System.out.println( SessionData.getUsuario() );
    }
}