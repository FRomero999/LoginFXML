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
import org.hibernate.query.Query;

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
        
        if( !SessionData.getUsuario().getAlias().equals(txtAlias.getText()) ){
            System.out.println("Hay que guardar el alias");
            SessionData.getUsuario().setAlias( txtAlias.getText() );
            try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
                Transaction t = s.beginTransaction();
                s.update(SessionData.getUsuario());
                t.commit();
            }
        }
        
        if( txtContraseña.getText().length()>0 ){
            System.out.println("Hay que guardar la contraseña");

            try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
                Transaction t = s.beginTransaction();
                Query q = s.createQuery("update Usuario us set us.password=md5(:pass) where us.id=:id");
                q.setParameter("pass", txtContraseña.getText());
                q.setParameter("id", SessionData.getUsuario().getId());
                if(q.executeUpdate()==0){
                    System.out.println("Hubo error al almancenar el password");
                    t.rollback();
                }else{
                    t.commit();
                    
                    SessionData.setUsuario(null);
                    salirVentana("primary");
                    return;
                }
            }            
        }

        salirVentana("principal");
    }

    @FXML
    private void volver(ActionEvent event) {
        salirVentana("principal");
    }

    private void salirVentana(String donde) {
        try {
            App.setRoot(donde);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    
}
