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
import map.Vehiculo;
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
    private TextField tfExpediente;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void LimpiarVehiculo(ActionEvent event) {
        tfMatricula.setText("");
        tfExpediente.setText("");
        tfMarca.setText("");
        tfModelo.setText("");
        tfColor.setText("");
        tfBastidor.setText("");
        tfAseguradora.setText("");
        tfPoliza.setText("");
            
               
    }

    @FXML
    private void AceptarVehiculo(ActionEvent event) {
        Vehiculo vehiculo=new Vehiculo();
        vehiculo.setMatricula(tfMatricula.getText());
        //vehiculo.setExpediente(expediente);
        vehiculo.setMarca(tfMarca.getText());
        vehiculo.setModelo(tfModelo.getText());
        vehiculo.setColor(tfColor.getText());
        vehiculo.setNumeroBastidor(tfBastidor.getText());
        vehiculo.setAseguradora(tfAseguradora.getText());
        vehiculo.setNumeroPoliza(tfPoliza.getText());
        
          Transaction tx = session.getTransaction();

        tx.begin();
        session.merge(vehiculo);
        tx.commit();

        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.close();
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
    
}
