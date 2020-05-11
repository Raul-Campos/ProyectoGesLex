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
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class VerExpedienteController implements Initializable {

    @FXML
    private TabPane tabPaneAsociado;
    @FXML
    private TableView<?> tableDocumentos;
    @FXML
    private TableColumn<?, ?> columnDocNombre;
    @FXML
    private TableColumn<?, ?> columnDocFecha;
    @FXML
    private TableColumn<?, ?> columnDocAportador;
    @FXML
    private TableColumn<?, ?> columnDocDescrip;
    @FXML
    private Button btnAnadirDoc;
    @FXML
    private Button btnEliminarDoc;
    @FXML
    private TableView<?> tableDocumentos1;
    @FXML
    private TableColumn<?, ?> columnSentTitulo;
    @FXML
    private TableColumn<?, ?> columnSentFecha;
    @FXML
    private TableColumn<?, ?> columnSentDescrip;
    @FXML
    private Button btnAnadirSent;
    @FXML
    private Button btnEliminarSent;
    @FXML
    private TableView<?> tableDocumentos11;
    @FXML
    private TableColumn<?, ?> columnAvisoID;
    @FXML
    private TableColumn<?, ?> columnAvisoFecha;
    @FXML
    private TableColumn<?, ?> columnAvisoEmail;
    @FXML
    private TableColumn<?, ?> columnAvisoDescrip;
    @FXML
    private Button btnAnadirAviso;
    @FXML
    private Button btnEliminarAviso;
    @FXML
    private TabPane tabPanePrincipal;
    @FXML
    private ChoiceBox<?> cbColumna;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<?> tableExpedientes;
    @FXML
    private TableColumn<?, ?> columnCodExpediente;
    @FXML
    private TableColumn<?, ?> columnFechaCreacion;
    @FXML
    private TableColumn<?, ?> columnFechaCierre;
    @FXML
    private TableColumn<?, ?> columnDNICliente;
    @FXML
    private TableColumn<?, ?> columnDNILetrado;
    @FXML
    private TableColumn<?, ?> columnDNIProcurador;
    @FXML
    private HBox idbajo;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnBorrar;
    private Session session;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void anadirDocumento(ActionEvent event) {
    }

    @FXML
    private void eliminarDoc(ActionEvent event) {
    }

    @FXML
    private void anadirSentencia(ActionEvent event) {
    }

    @FXML
    private void eliminarSentencia(ActionEvent event) {
    }

    @FXML
    private void anadirAviso(ActionEvent event) {
    }

    @FXML
    private void eliminarAviso(ActionEvent event) {
    }

    @FXML
    private void buscarExpediente(ActionEvent event) {
    }

    @FXML
    private void añadirExpediente(ActionEvent event) {
    }

    @FXML
    private void borrarExpediente(ActionEvent event) {
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public TabPane getTabPaneAsociado() {
        return tabPaneAsociado;
    }

    public TabPane getTabPanePrincipal() {
        return tabPanePrincipal;
    }
    
    
}
