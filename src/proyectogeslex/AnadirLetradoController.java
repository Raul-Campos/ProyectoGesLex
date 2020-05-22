/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.RollbackException;
import map.Letrado;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirLetradoController implements Initializable {

    @FXML
    private TextField tfApellidos;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfDni;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfColegio;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private Session session;
    private SessionFactory sesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void LimpiarLetrado(ActionEvent event) {
        tfNombre.setText("");
        tfApellidos.setText("");
        tfDni.setText("");
        tfDireccion.setText("");
        tfNumero.setText("");
        tfEmail.setText("");
        tfColegio.setText("");
    }

    @FXML
    private void AceptarLetrado(ActionEvent event) {
        Letrado letrado = new Letrado();
        Alert alerta;

        boolean errorFormato = false;
        boolean alert = true;

        String dniRegexp = "(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))";
        Pattern pat = Pattern.compile(dniRegexp);
        Matcher mat = pat.matcher(tfDni.getText());

        if (tfDni.getText() != null && !tfDni.getText().equals("") && (mat.matches())) {
            letrado.setDniLetrado(tfDni.getText());

            //comprueba nombre
            String name = "[A-Za-z]*";
            pat = Pattern.compile(name);
            mat = pat.matcher(tfNombre.getText());
            if (tfNombre.getText() != null && !tfNombre.getText().equals("") && (mat.matches())) {
                letrado.setNombre(tfNombre.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un nombre");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfApellidos.getText() != null && !tfApellidos.getText().equals("") && (mat.matches())) {
                letrado.setApellidos(tfApellidos.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce unos apellidos validos");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfDireccion.getText() != null && !tfDireccion.getText().equals("")) {
                letrado.setDireccion(tfDireccion.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce una direccion valida");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfNumero.getText() != null && !tfNumero.getText().isEmpty() && tfNumero.getText().matches("[6|7|9][0-9]{8}$")) {
                letrado.setTelefono(Integer.parseInt(tfNumero.getText()));

            } else if (!tfNumero.getText().matches("[6|7|9][0-9]{8}$") && alert) {
                errorFormato = true;
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número de teléfono valido");
                alerta.showAndWait();
                alert = false;
            } else if (alert) {
                errorFormato = true;
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número de teléfono");
                alerta.showAndWait();
                alert = false;
            }

            if (tfColegio.getText() != null && !tfColegio.getText().equals("")) {
                letrado.setColegio(tfColegio.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un colegio valido");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfEmail.getText() != null && !tfEmail.getText().equals("")) {
                letrado.setEmail(tfEmail.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un email valido");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            letrado.setDniLetrado(tfDni.getText());

            if (!errorFormato) {
                Transaction tx = session.getTransaction();

                try {
                    tx.begin();
                    session.save(letrado);
                    tx.commit();

                    Stage stage = (Stage) btnAceptar.getScene().getWindow();
                    stage.close();
                } catch (NonUniqueObjectException ex) {
                    tx.rollback();
                    Alert alertaExistente = new Alert(Alert.AlertType.INFORMATION);
                    alertaExistente.setHeaderText("Letrado existente");
                    alertaExistente.setContentText("Ya se ha añadido ese letrado anteriormente.");
                    alertaExistente.showAndWait();

                } catch (RollbackException e) {
                    tx.rollback();
                    alerta = new Alert(Alert.AlertType.INFORMATION, "Error al guardar los datos. Inténtelo de nuevo");
                    alerta.setContentText(e.getLocalizedMessage());
                    alerta.showAndWait();
                }
            }

        } else if (alert) {
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un DNI");
            alerta.showAndWait();
            errorFormato = true;
            alert = false;
        }

    }

    @FXML
    private void CancelarLetrado(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void setSession(Session session) {
        this.session = session;

    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }
}
