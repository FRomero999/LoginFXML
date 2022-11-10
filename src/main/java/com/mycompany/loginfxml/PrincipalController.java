package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Actividad;
import models.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PrincipalController implements Initializable {

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
    private MenuItem menuSalir;
    @FXML
    private Label detalle;

    private Actividad actividadActual = null;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnAñadir;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        info.setText("Hola " + (SessionData.getUsuario()).getAlias());
        System.out.println(SessionData.getUsuario());

        cId.setCellValueFactory(new PropertyValueFactory("id"));
        cNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        cCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));

        btnActualizar.setDisable(true);
        btnBorrar.setDisable(true);
        
        actualizarTabla();

    }

    @FXML
    private void mostrarTarea(MouseEvent event) {

        Actividad actividad = tabla.getSelectionModel().getSelectedItem();

        System.out.println(tabla.getSelectionModel().getSelectedIndex());

        if (actividad != null) {
            textId.setText("" + actividad.getId());
            textActividad.setText(actividad.getNombre());
            textCategoria.setText(actividad.getCategoria());

            actividadActual = actividad;
    
            btnActualizar.setDisable(false);
            btnBorrar.setDisable(false);
            
        }
    }

    @FXML
    private void borrarTarea(ActionEvent event) {

        if ((actividadActual != null) && pedirConfirmacion()) {

            try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
                Transaction t = s.beginTransaction();
                s.delete(actividadActual);
                t.commit();

                actualizarUsuario(s);

                actividadActual = null;

                actualizarTabla();

                System.out.println(SessionData.getUsuario());
                borrarFormulario();
                detalle.setText("Actividad borrada con éxito");
            }

        }
    }

    private void actualizarUsuario(final Session s) {
        Usuario actualizado = s.get(Usuario.class, SessionData.getUsuario().getId());
        SessionData.setUsuario(actualizado);
    }

    private void borrarFormulario() {
        textId.setText("");
        textActividad.setText("");
        textCategoria.setText("");
        btnActualizar.setDisable(true);       
    }

    private Boolean pedirConfirmacion() {
        Alert confirmacion = new Alert(AlertType.CONFIRMATION);
        confirmacion.setTitle("Borrar");
        confirmacion.setHeaderText("Solicitud de borrado");
        confirmacion.setContentText("La actividad " + actividadActual.getNombre() + " va a ser eliminada.");
        var result = confirmacion.showAndWait();
        return (result.get()) == ButtonType.OK;
    }

    private void actualizarTabla() {
        tabla.getItems().clear();
        tabla.getItems().addAll(SessionData.getUsuario().getActividades());
    }

    @FXML
    private void actualizarTarea(ActionEvent event) {
        
        if (actividadActual != null){

            actividadActual.setNombre( textActividad.getText() );
            actividadActual.setCategoria( textCategoria.getText() );
            
            try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
                Transaction t = s.beginTransaction();
                s.update(actividadActual);
                t.commit();
                
                actualizarUsuario(s);            
                
                actualizarTabla();
                detalle.setText("Actividad actualizada con éxito");
                
            }
        }
    }

    @FXML
    private void añadirTarea(ActionEvent event) {
        
        Actividad a = leerFormulario();
        
        if( a!=null ){   
            try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
                Transaction t = s.beginTransaction();
                s.save(a);
                t.commit();
                
                actualizarUsuario(s);            
                
                actualizarTabla();
                detalle.setText("Actividad almacenada con éxito");
                
                actividadActual = a;              
                
                borrarFormulario();
            }           
        }
    }

    private Actividad leerFormulario() {
        String nombre = textActividad.getText();
        String categoria = textCategoria.getText();
        
        if( "".equals(nombre) || "".equals(categoria)){
            detalle.setText("No se permiten tareas sin nombre ni categoria");
            return null;
        }else{
            Actividad a = new Actividad();
            a.setCategoria(categoria);
            a.setNombre(nombre);
            a.setUsuario( SessionData.getUsuario() );
            return a;
        }
    }
        
    
}
