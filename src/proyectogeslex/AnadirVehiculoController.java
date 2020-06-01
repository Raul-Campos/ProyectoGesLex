/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.RollbackException;
import map.Cliente;
import map.Expediente;
import map.Vehiculo;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirVehiculoController implements Initializable {

    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfBastidor;
    @FXML
    private TextField tfColor;
    @FXML
    private TextField tfPoliza;
    @FXML
    private TextField tfAseguradora;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private Session session;
    private SessionFactory sesion;
    @FXML
    private ChoiceBox<String> cbRol;
    private Vehiculo existente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbRol.getItems().addAll("Cliente", "Implicado/a");
    }

    @FXML
    private void LimpiarVehiculo(ActionEvent event) {
        tfMatricula.setText("");

        tfMarca.setText("");
        tfModelo.setText("");
        tfColor.setText("");
        tfBastidor.setText("");
        tfAseguradora.setText("");
        tfPoliza.setText("");

    }

    @FXML
    private void AceptarVehiculo(ActionEvent event) {

        Vehiculo vehiculo = new Vehiculo();
        if (existente != null) {
            vehiculo = existente;
        }

        Alert alerta;
        boolean errorFormato = false;
        boolean alert = true;
        if (tfMatricula.getText() != null && !tfMatricula.getText().equals("")) {
            vehiculo.setMatricula(tfMatricula.getText());

            if (tfAseguradora.getText() != null && !tfAseguradora.getText().equals("")) {
                vehiculo.setAseguradora(tfAseguradora.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce una aseguradora");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfBastidor.getText() != null && !tfBastidor.getText().equals("")) {
                vehiculo.setNumeroBastidor(tfBastidor.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un numero de bastidor");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfColor.getText() != null && !tfColor.getText().equals("")) {
                vehiculo.setColor(tfColor.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un color");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfMarca.getText() != null && !tfMarca.getText().equals("")) {
                vehiculo.setMarca(tfMarca.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce una marca");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfModelo.getText() != null && !tfModelo.getText().equals("")) {
                vehiculo.setModelo(tfModelo.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce su modelo de coche");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (tfPoliza.getText() != null && !tfPoliza.getText().equals("")) {
                vehiculo.setNumeroPoliza(tfPoliza.getText());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce su numero de poliza");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }
            if (cbRol.getValue() != null && !cbRol.getValue().isEmpty()) {
                vehiculo.setRol(cbRol.getValue());
            } else if (alert) {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Seleccione el rol del vehiculo");
                alerta.showAndWait();
                errorFormato = true;
                alert = false;
            }

            if (!errorFormato) {
                Transaction tx = session.getTransaction();
                try {
                    if (existente == null) {
                        tx.begin();
                        session.save(vehiculo);
                        tx.commit();
                    } else {
                        tx.begin();
                        session.merge(vehiculo);
                        tx.commit();
                    }
                    Stage stage = (Stage) btnAceptar.getScene().getWindow();
                    stage.close();
                } catch (NonUniqueObjectException ex) {
                    tx.rollback();
                    Alert alertaExistente = new Alert(Alert.AlertType.INFORMATION);
                    alertaExistente.setHeaderText("Vehiculo existente");
                    alertaExistente.setContentText("Ya se ha añadido ese vehiculo anteriormente.");
                    alertaExistente.showAndWait();

                } catch (RollbackException e) {
                    tx.rollback();
                    alerta = new Alert(Alert.AlertType.INFORMATION, "Error al guardar los datos. Inténtelo de nuevo");
                    alerta.setContentText(e.getLocalizedMessage());
                    alerta.showAndWait();
                }
            }

        } else if (alert) {
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce una matricula valida");
            alerta.showAndWait();
            errorFormato = true;
            alert = false;
        }

    }

    @FXML
    private void CancelarVehiculo(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void setSession(Session session) {
        this.session = session;

    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    public void setExistente(Vehiculo existente) {
        this.existente = existente;
    }

    public void cargarDatos() {

        if (existente != null) {
            //Muestra los datos
            tfMatricula.setText(existente.getMatricula());
            tfMatricula.setDisable(true);
            tfMarca.setText(existente.getMarca());
            tfModelo.setText(existente.getModelo());
            tfColor.setText(existente.getColor());
            tfAseguradora.setText(existente.getAseguradora());
            tfBastidor.setText(existente.getNumeroBastidor());
            tfPoliza.setText(existente.getNumeroPoliza());
            cbRol.setValue(existente.getRol());
        }

    }
}
