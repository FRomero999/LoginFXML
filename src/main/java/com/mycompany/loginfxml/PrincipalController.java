package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Actividad;

public class PrincipalController implements Initializable{

    @FXML
    private Button secondaryButton;
    @FXML
    private Label info;
    @FXML
    private TableView<Actividad> tabla;
    @FXML
    private TableColumn<Actividad, Integer> cId;
    @FXML
    private TableColumn<Actividad, String> cNombre;
    @FXML
    private TableColumn<Actividad, String> cCategoria;
    @FXML
    private TextField textId;
    @FXML
    private TextField textActividad;
    @FXML
    private TextField textCategoria;


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
        
        cId.setCellValueFactory( new PropertyValueFactory("id") );
        cNombre.setCellValueFactory( new PropertyValueFactory("nombre") );
        cCategoria.setCellValueFactory( new PropertyValueFactory("categoria") );
        
        tabla.getItems().addAll( SessionData.getUsuario().getActividades() );

    }

    @FXML
    private void mostrarTarea(MouseEvent event) {
        
        Actividad actividad = tabla.getSelectionModel().getSelectedItem();
        
        textId.setText(""+actividad.getId());
        textActividad.setText(actividad.getNombre());
        textCategoria.setText(actividad.getCategoria());
        
    }
}