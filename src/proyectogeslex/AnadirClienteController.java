/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import map.Cliente;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirClienteController implements Initializable {

    private Session session;
    private SessionFactory sesion;
    @FXML
    private TextField textfieldDni;
    @FXML
    private TextField textfieldFecha;
    @FXML
    private TextField textfieldNombre;
    @FXML
    private ChoiceBox<String> cbSitFam;
    @FXML
    private ChoiceBox<String> cbSitLab;
    @FXML
    private RadioButton radiobH;
    @FXML
    private RadioButton radiobM;
    @FXML
    private Button btnEnviar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField textfieldApellidos;
    private Cliente existente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbSitFam.getItems().addAll("Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a");
        cbSitLab.getItems().addAll("Empleado/a", "Desempleado/a");
    }

    public void setSession(Session session) {
        this.session = session;

    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    @FXML
    private void btnEnviar(ActionEvent event) {
        Cliente cliente = new Cliente();
        Alert alerta;
        boolean errorFormato = false;
        boolean alert = true;

        String dniRegexp = "(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))";
        Pattern pat = Pattern.compile(dniRegexp);
        Matcher mat = pat.matcher(textfieldDni.getText());

        if (textfieldDni.getText() != null && !textfieldDni.getText().equals("") && (mat.matches())) {

            cliente.setDni(textfieldDni.getText());

            //comprueba nombre
            String name = "[A-Za-z]*";
            pat = Pattern.compile(name);
            mat = pat.matcher(textfieldNombre.getText());
            if (textfieldNombre.getText() != null && !textfieldNombre.getText().equals("") && (mat.matches())) {
                cliente.setNombre(textfieldNombre.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un nombre");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            mat = pat.matcher(textfieldApellidos.getText());
            if (textfieldApellidos.getText() != null && !textfieldApellidos.getText().equals("") && (mat.matches())) {
                cliente.setApellidos(textfieldApellidos.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce los Apellidos");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (cbSitFam.getValue() != null && !cbSitFam.getValue().isEmpty()) {
                cliente.setSituacionFamiliar(cbSitFam.getValue());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Seleccione su situacion familiar");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (cbSitLab.getValue() != null && !cbSitLab.getValue().isEmpty()) {
                cliente.setSituacionLaboral(cbSitLab.getValue());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Seleccione su situacion laboral");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (textfieldFecha.getText() != null && !textfieldFecha.getText().equals("")) {
                cliente.setFechaNacimiento(StringToDate(textfieldFecha.getText()));
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce una fecha de nacimiento valida");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (radiobH.isSelected()) {
                cliente.setSexo("Hombre");
            } else if (radiobM.isSelected()) {
                cliente.setSexo("Mujer");
            } else if (!radiobH.isSelected() && !radiobM.isSelected() && alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Seleccione tipo de sexo");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (!errorFormato) {

                Transaction tx = session.getTransaction();

                try {

                    if (existente == null) {
                        tx.begin();
                        session.save(cliente);
                        tx.commit();
                    } else {
                        tx.begin();
                        session.merge(cliente);
                        tx.commit();
                    }

                    Stage stage = (Stage) btnEnviar.getScene().getWindow();
                    stage.close();
                } catch (NullPointerException ex) {
                    tx.rollback();
                    Alert alertaNuevoCliente = new Alert(Alert.AlertType.INFORMATION);
                    alertaNuevoCliente.setHeaderText("Error al añadir cliente.");
                    alertaNuevoCliente.setContentText("Error al guardar los datos del cliente. Por favor, intentelo de nuevo.");
                    alertaNuevoCliente.showAndWait();
                } catch (NonUniqueObjectException ex) {
                    tx.rollback();
                    Alert alertaClienteExistente = new Alert(Alert.AlertType.INFORMATION);
                    alertaClienteExistente.setHeaderText("Cliente existente");
                    alertaClienteExistente.setContentText("Ya se ha añadido ese cliente anteriormente.");
                    alertaClienteExistente.showAndWait();
                }
            }
        } else if (alert) {
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un DNI");
            alerta.showAndWait();
            errorFormato = true;
            alert = false;
        }

    }

    private static Date StringToDate(String date) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = formato.parse(date);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return fecha;
    }

    @FXML
    private void btnLimpiar(ActionEvent event) {
        textfieldDni.setText("");
        textfieldFecha.setText("");
        textfieldNombre.setText("");
        radiobH.setSelected(false);
        radiobM.setSelected(false);
        cbSitFam.setValue(null);
        cbSitLab.setValue(null);

    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void setExistente(Cliente existente) {
        this.existente = existente;
    }

    //Detecta que el cliente existe y carga sus datos desde la bd
    public void cargarDatos() {

        if (existente != null) {
            //Muestra los datos
            textfieldDni.setText(existente.getDni());
            textfieldApellidos.setText(existente.getApellidos());
            textfieldFecha.setText(existente.getFechaNacimiento().toString());
            textfieldNombre.setText(existente.getNombre());
            cbSitFam.setValue(existente.getSituacionFamiliar());
            cbSitLab.setValue(existente.getSituacionLaboral());
            if (existente.getSexo().equals("Hombre")) {
                radiobH.setSelected(true);
            } else {
                radiobM.setSelected(true);
            }
        }

    }
}
