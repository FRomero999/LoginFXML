package com.mycompany.loginfxml;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PrimaryController {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAceptar;
    @FXML
    private CheckBox chkSesion;
    @FXML
    private Label info;

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void aceptar(ActionEvent event) {
        
        
        try( Session s = HibernateUtil.getSessionFactory().openSession()){
            
            Query q = s.createQuery("from Usuario where alias=:param");
            q.setParameter("param",txtUser.getText() );
            
            if( q.list().size()>0){
                info.setText("Usuario existe");
                info.setStyle("-fx-background-color:green; -fx-text-fill:white;");
            }else{
                info.setText("Usuario no existe");
                info.setStyle("-fx-background-color:red; -fx-text-fill:white;");
               
            }
        }
        
//        if(("francisco".equals( txtUser.getText()))
//             &&("1234".equals(txtPassword.getText()) ) ) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            
//            if( chkSesion.isSelected() ){
//                alert.setContentText("Usuario logueado correctamente y\nquiere mantener abierta la sesión");
//            }else{
//                alert.setContentText("Usuario logueado correctamente");
//            }
//            
//            info.setText("Usuario y contraseña correctas");
//            info.setStyle("-fx-background-color:green; -fx-text-fill:white;");
//            
//            alert.showAndWait();
//            
//            try {
//                App.setRoot("principal");
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            
//        }else{
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Usuario no registrado");
//            alert.showAndWait();            
//        }
    }
    
}