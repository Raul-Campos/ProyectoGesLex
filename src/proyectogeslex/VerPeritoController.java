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
public class VerPeritoController implements Initializable {

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
    private TableView<?> tablePeritos;
    @FXML
    private TableColumn<?, ?> columnDNi;
    @FXML
    private TableColumn<?, ?> columnNombre;
    @FXML
    private TableColumn<?, ?> columnApellidos;
    @FXML
    private TableColumn<?, ?> columnDireccion;
    @FXML
    private TableColumn<?, ?> columnProvinca;
    @FXML
    private TableColumn<?, ?> columnTelefono;
    @FXML
    private TableColumn<?, ?> columnEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void borrarCliente(ActionEvent event) {
    }

    @FXML
    private void buscarPerito(ActionEvent event) {
    }
    
}
