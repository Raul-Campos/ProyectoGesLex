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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import map.Procurador;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirProcuradorController implements Initializable {

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
    private void LimpiarProcurador(ActionEvent event) {
        tfNombre.setText("");
        tfApellidos.setText("");
        tfDni.setText("");
        tfDireccion.setText("");
        tfNumero.setText("");
        tfEmail.setText("");

    }

    @FXML
    private void AceptarProcurador(ActionEvent event) {
        Procurador procurador = new Procurador();

        procurador.setNombre(tfNombre.getText());
        procurador.setApellidos(tfApellidos.getText());
        procurador.setDniProcurador(tfDni.getText());
        procurador.setDireccion(tfDireccion.getText());
        procurador.setTelefono(Integer.parseInt(tfNumero.getText()));
        procurador.setEmail(tfEmail.getText());

        Transaction tx = session.getTransaction();

        try {
            tx.begin();
            session.merge(procurador);
            tx.commit();
        } catch (NonUniqueObjectException ex) {
            tx.rollback();
            Alert alertaExistente = new Alert(Alert.AlertType.INFORMATION);
            alertaExistente.setHeaderText("Procurador existente");
            alertaExistente.setContentText("Ya se ha añadido ese procurador anteriormente.");
            alertaExistente.showAndWait();
        }

        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void CancelarProcurador(ActionEvent event) {
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
