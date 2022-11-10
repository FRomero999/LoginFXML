/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author FranciscoRomeroGuill
 */
public class PerfilController implements Initializable {

    @FXML
    private Button btnModificar;
    @FXML
    private Button btnVolver;
    @FXML
    private TextField txtAlias;
    @FXML
    private TextField txtContraseña;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtAlias.setText( SessionData.getUsuario().getAlias() );
    }    

    @FXML
    private void modificar(ActionEvent event) {
        SessionData.getUsuario().setAlias( txtAlias.getText() );
        try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.update(SessionData.getUsuario());
            t.commit();
        }
        salirVentana();
    }

    @FXML
    private void volver(ActionEvent event) {
        salirVentana();
    }

    private void salirVentana() {
        try {
            App.setRoot("principal");
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
