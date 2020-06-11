/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.RollbackException;
import map.Expediente;
import map.Perito;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirPeritoController implements Initializable {

    @FXML
    private TextField tfApellidos;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfDNI;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfProvincia;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private Session session;
    private SessionFactory sesion;
    private Expediente expediente;
    private Perito existente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void LimpiarPerito(ActionEvent event) {
        tfNombre.setText("");
        tfApellidos.setText("");
        tfDNI.setText("");
        tfDireccion.setText("");
        tfProvincia.setText("");
        tfTelefono.setText("");
        tfEmail.setText("");

    }

    @FXML
    private void AceptarPerito(ActionEvent event) {
        Perito perito = new Perito();
        Alert alerta;
        boolean errorFormato = false;
        boolean alert = true;

        String dniRegexp = "(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))";
        Pattern pat = Pattern.compile(dniRegexp);
        Matcher mat = pat.matcher(tfDNI.getText());

        if (tfDNI.getText() != null && !tfDNI.getText().equals("") && (mat.matches())) {
            perito.setDniPerito(tfDNI.getText());

            //comprueba nombre
            String name = "[A-Za-z \\p{L}]*";
            pat = Pattern.compile(name);
            mat = pat.matcher(tfNombre.getText());
            if (tfNombre.getText() != null && !tfNombre.getText().equals("") && (mat.matches())) {
                perito.setNombre(tfNombre.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un nombre");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfApellidos.getText() != null && !tfApellidos.getText().equals("") && (mat.matches())) {
                perito.setApellidos(tfApellidos.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce unos apellidos validos");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfProvincia.getText() != null && !tfProvincia.getText().equals("") && (mat.matches())) {
                perito.setProvincia(tfProvincia.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce una provincia");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfDireccion.getText() != null && !tfDireccion.getText().equals("")) {
                perito.setDireccion(tfDireccion.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce una direccion valida");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfTelefono.getText() != null && !tfTelefono.getText().isEmpty() && tfTelefono.getText().matches("[6|7|9][0-9]{8}$")) {
                perito.setTelefono(Integer.parseInt(tfTelefono.getText()));

            } else if (!tfTelefono.getText().matches("[6|7|9][0-9]{8}$") && alert) {
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

            if (tfEmail.getText() != null && !tfEmail.getText().equals("")) {
                perito.setEmail(tfEmail.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un email valido");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (!errorFormato) {
                Transaction tx = session.getTransaction();

                try {

                    if (existente == null) {
                        tx.begin();
                        session.save(perito);
                        tx.commit();
                    } else {
                        perito.getExpedientes().addAll(existente.getExpedientes());
                        tx.begin();
                        session.clear();
                        session.update(perito);
                        tx.commit();
                    }

                    Stage stage = (Stage) btnAceptar.getScene().getWindow();
                    stage.close();
                } catch (NonUniqueObjectException ex) {
                    tx.rollback();
                    Alert alertaExistente = new Alert(Alert.AlertType.INFORMATION);
                    alertaExistente.setHeaderText("Perito existente");
                    alertaExistente.setContentText("Ya se ha añadido ese perito anteriormente.");
                    alertaExistente.showAndWait();

                } catch (RollbackException e) {
                    tx.rollback();
                    alerta = new Alert(Alert.AlertType.INFORMATION, "Error al guardar los datos. Inténtelo de nuevo");
                    alerta.setContentText(e.getLocalizedMessage());
                    alerta.showAndWait();
                }
            }

        } else if (alert) {
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un DNI valido");
            alerta.showAndWait();
            errorFormato = true;
            alert = false;
        }
    }

    @FXML
    private void CancelarPerito(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void setSession(Session session) {
        this.session = session;

    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

    public void setExistente(Perito existente) {
        this.existente = existente;
    }

    public void cargarDatos() {

        if (existente != null) {
            tfDNI.setText(existente.getDniPerito());
            tfDNI.setDisable(true);
            tfNombre.setText(existente.getNombre());
            tfApellidos.setText(existente.getApellidos());
            tfDireccion.setText(existente.getDireccion());
            tfProvincia.setText(existente.getProvincia());
            tfTelefono.setText(String.valueOf(existente.getTelefono()));
            tfEmail.setText(existente.getEmail());
        }
    }
}
