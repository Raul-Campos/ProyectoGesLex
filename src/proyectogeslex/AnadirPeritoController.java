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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import map.Perito;
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
        Perito perito=new Perito();
        perito.setNombre(tfNombre.getText());
        perito.setApellidos(tfApellidos.getText());
        perito.setDniPerito(tfDNI.getText());
        perito.setDireccion(tfDireccion.getText());
        perito.setProvincia(tfProvincia.getText());
        perito.setTelefono(Integer.parseInt(tfTelefono.getText()));
        perito.setEmail(tfEmail.getText());
        
         Transaction tx = session.getTransaction();

        tx.begin();
        session.merge(perito);
        tx.commit();

        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.close();
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
}
