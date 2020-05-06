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

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class AñadirVehiculoController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void guardarVehiculo(ActionEvent event) {
    }
    
}
