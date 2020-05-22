/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import map.Usuarios;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class CrearCuentaController implements Initializable {

    @FXML
    private Button btncrear;
    @FXML
    private TextField tfusuario;
    @FXML
    private TextField tfcontrasena;
    @FXML
    private TextField tfcontrasena2;

    private Session session;
    private SessionFactory sesion;
    @FXML
    private Button btncancelar;

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void Cancelar(ActionEvent event) {
        Stage stage = (Stage) btncancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void CrearCuenta(ActionEvent event) {
        Usuarios usuario = new Usuarios();
        Alert alerta;

        boolean errorFormato = false;
        boolean alert = true;

        if (tfusuario.getText() != null && !tfusuario.getText().equals("")) {
            usuario.setNombre(tfusuario.getText());
        } else if (alert) {
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce su nuevo usuario");
            alerta.showAndWait();
            errorFormato = true;
            alert = false;
        }

        if ((tfcontrasena.getText().equals(tfcontrasena2.getText()) && tfcontrasena.getText() != null) && !tfcontrasena.getText().equals("")) {

            usuario.setContrasena(tfcontrasena.getText());
            Transaction tx = session.getTransaction();

            tx.begin();
            session.save(usuario);
            tx.commit();
            alerta = new Alert(Alert.AlertType.CONFIRMATION, "Cuenta creada con exito", ButtonType.OK);
            alerta.setHeaderText("Crear Cuenta");
            alerta.showAndWait();

            Stage stage = (Stage) btncancelar.getScene().getWindow();
            stage.close();

        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Error al Crear Cuenta");
            alerta.setContentText("Las contraseñas no coinciden o no validas. Por favor reviselas.");
            alerta.showAndWait();
        }
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }
}
