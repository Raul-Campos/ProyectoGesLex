/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirClienteController implements Initializable {

    @FXML
    private MenuItem anadirCliente;
    @FXML
    private MenuItem borrarCliente;
    @FXML
    private MenuItem verCliente;
    @FXML
    private MenuItem anadirExpediente;
    @FXML
    private MenuItem verExpedientes;
    @FXML
    private MenuItem anadirMinutas;
    @FXML
    private MenuItem verMinutas;

     private Session session;
       private SessionFactory sesion;
    @FXML
    private TextField textfieldDni;
    @FXML
    private TextField textfieldFecha;
    @FXML
    private TextField textfieldNombre;
    @FXML
    private ChoiceBox<?> cbSitFam;
    @FXML
    private ChoiceBox<?> cbSitLab;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void anadirCliente(ActionEvent event) {
    }

    @FXML
    private void borrarCliente(ActionEvent event) {
    }

    @FXML
    private void verCliente(ActionEvent event) {
    }

    @FXML
    private void anadirExpediente(ActionEvent event) {
    }

    @FXML
    private void verExpedientes(ActionEvent event) {
    }

    @FXML
    private void anadirMinutas(ActionEvent event) {
    }

    @FXML
    private void verMinutas(ActionEvent event) {
    }
     public void setSession(Session session) {
        this.session = session;
      
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    @FXML
    private void textfieldDni(ActionEvent event) {
    }

    @FXML
    private void textfieldFecha(ActionEvent event) {
    }

    @FXML
    private void textfieldNombre(ActionEvent event) {
    }

    @FXML
    private void radiobH(ActionEvent event) {
    }

    @FXML
    private void radiobM(ActionEvent event) {
    }

    @FXML
    private void btnEnviar(ActionEvent event) {
    }

    @FXML
    private void btnLimpiar(ActionEvent event) {
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
    }
}
