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
import javafx.scene.control.TextField;
import map.Usuarios;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class CambioContrasenaController implements Initializable {

    @FXML
    private Button btnAceptar;
    @FXML
    private TextField tfContraActual;
    @FXML
    private TextField tfNuevaContra;
    @FXML
    private TextField tfConfNuevaContra;
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

        if (!tfContraActual.getText().equals("")
                && !tfNuevaContra.getText().equals("")
                && !tfConfNuevaContra.getText().equals("")) {
            
            //Comprueba que introduce correctamente su contraseña actual
            if (user.getContrasena().equals(tfContraActual.getText())) {

                //Comprueba la confirmación de la nueva contraseña
                if (tfNuevaContra.getText().equals(tfConfNuevaContra.getText())) {

                    user.setContrasena(tfNuevaContra.getText());
                    Transaction tx = session.getTransaction();

                    //Cambia la contraseña del usuario
                    tx.begin();
                    session.update(user);
                    tx.commit();

                    limpiar();

                    Alert alertaCambio = new Alert(Alert.AlertType.INFORMATION);
                    alertaCambio.setHeaderText("Cambio de contraseña.");
                    alertaCambio.setContentText("Cambio de contraseña "
                            + "realizado con éxito");
                    alertaCambio.showAndWait();

                } else {
                    Alert alertaPwdIncorrecta = new Alert(Alert.AlertType.INFORMATION);
                    alertaPwdIncorrecta.setHeaderText("Fallo de confirmación");
                    alertaPwdIncorrecta.setContentText("Fallo en la confirmación de "
                            + "su nueva contraseña, pruebe  introducirla de nuevo");
                    alertaPwdIncorrecta.showAndWait();
                }

            } else {
                Alert alertaPwdIncorrecta = new Alert(Alert.AlertType.INFORMATION);
                alertaPwdIncorrecta.setHeaderText("Contraseña errónea");
                alertaPwdIncorrecta.setContentText("La contraseña actual no "
                        + "coincide, intentlo de nuevo");
                alertaPwdIncorrecta.showAndWait();
            }
        }else{
            Alert campos = new Alert(Alert.AlertType.INFORMATION);
            campos.setHeaderText("Campos incompletos");
            campos.setContentText("Porfavor rellene todos los campos");
            campos.showAndWait();
        }
    }

    private void limpiar() {
        tfContraActual.setText("");
        tfNuevaContra.setText("");
        tfConfNuevaContra.setText("");
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
