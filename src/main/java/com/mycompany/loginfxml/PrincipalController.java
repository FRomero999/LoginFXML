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
        }
    }

    @FXML
    private void borrarTarea(ActionEvent event) {

        if ((actividadActual != null) && pedirConfirmacion()) {

            try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
                Transaction t = s.beginTransaction();
                s.delete(actividadActual);
                t.commit();

                Usuario actualizado = s.get(Usuario.class, SessionData.getUsuario().getId());
                SessionData.setUsuario(actualizado);

                actividadActual = null;

                actualizarTabla();

                System.out.println(SessionData.getUsuario());
                borrarFormulario();
                detalle.setText("Actividad borrada con éxito");
            }

        }
    }

    private void borrarFormulario() {
        textId.setText("");
        textActividad.setText("");
        textCategoria.setText("");
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
}
