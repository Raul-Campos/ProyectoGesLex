/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex.configuracion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import map.Usuarios;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class CambioUsuarioController implements Initializable {

    @FXML
    private Button btnAceptar;
    @FXML
    private PasswordField tfContra;
    @FXML
    private PasswordField tfConfContra;
    @FXML
    private TextField tfUsuario;
    private Usuarios user;
    private Session session;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void aceptar(ActionEvent event) {

        if (!tfUsuario.getText().equals("") && !tfContra.getText().equals("") &&
            !tfConfContra.getText().equals("")) {

            //Comprueba la confirmación de la  contraseña
            if (tfContra.getText().equals(tfConfContra.getText()) && 
                tfContra.getText().equals(user.getContrasena())) {

                Transaction tx = session.getTransaction();

                //Elimina el usuario y lo cambia por uno nuevo
                tx.begin();
                session.delete(user);
                user.setNombre(tfUsuario.getText());
                session.save(user);
                tx.commit();

                limpiar();

                Alert alertaCambio = new Alert(Alert.AlertType.INFORMATION);
                alertaCambio.setHeaderText("Cambio de realizado.");
                alertaCambio.setContentText("Cambio de nombre de usuario "
                        + "realizado con éxito");
                alertaCambio.showAndWait();

            } else {
                Alert alertaPwdIncorrecta = new Alert(Alert.AlertType.INFORMATION);
                alertaPwdIncorrecta.setHeaderText("Fallo de confirmación");
                alertaPwdIncorrecta.setContentText("Fallo en la confirmación de "
                        + "su contraseña, pruebe  introducirla de nuevo");
                alertaPwdIncorrecta.showAndWait();
            }
        } else {
            Alert campos = new Alert(Alert.AlertType.INFORMATION);
            campos.setHeaderText("Campos incompletos");
            campos.setContentText("Porfavor rellene todos los campos");
            campos.showAndWait();
        }
    }

    private void limpiar() {
        tfUsuario.setText("");
        tfContra.setText("");
        tfConfContra.setText("");
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
