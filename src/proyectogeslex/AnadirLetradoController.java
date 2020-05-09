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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import map.Letrado;
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
        Letrado letrado=new Letrado();
        
        letrado.setNombre(tfNombre.getText());
        letrado.setApellidos(tfApellidos.getText());
        letrado.setDniLetrado(tfDni.getText());
        letrado.setDireccion(tfDireccion.getText());
        letrado.setTelefono(Integer.parseInt(tfNumero.getText()));
        letrado.setEmail(tfEmail.getText());
        letrado.setColegio(tfColegio.getText());
        
         Transaction tx = session.getTransaction();

        tx.begin();
        session.merge(letrado);
        tx.commit();

        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.close();
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
