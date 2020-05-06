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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class VerVehiculosController implements Initializable {

    @FXML
    private ChoiceBox<?> cbColumna;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private HBox idbajo;
    @FXML
    private Button btnBorrar;
    @FXML
    private HBox idcentro;
    @FXML
    private TableView<?> tableVehiculos;
    @FXML
    private TableColumn<?, ?> matriculaColumn;
    @FXML
    private TableColumn<?, ?> expedienteColumn;
    @FXML
    private TableColumn<?, ?> marcaColumn;
    @FXML
    private TableColumn<?, ?> modeloColumn;
    @FXML
    private TableColumn<?, ?> colorColumn;
    @FXML
    private TableColumn<?, ?> numBastidorColumn;
    @FXML
    private TableColumn<?, ?> aseguradoraColumn;
    @FXML
    private TableColumn<?, ?> nPolizaColumn;
    @FXML
    private TableColumn<?, ?> rolColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarCLiente(ActionEvent event) {
    }

    @FXML
    private void borrarCliente(ActionEvent event) {
    }
    
}
