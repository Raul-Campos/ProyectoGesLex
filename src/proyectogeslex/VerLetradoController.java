/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import map.Cliente;
import map.Letrado;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class VerLetradoController implements Initializable {

    @FXML
    private ChoiceBox<String> cbColumna;
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
    private TableView<Letrado> tableLetrados;
    @FXML
    private TableColumn<Letrado, String> columnNombre;
    @FXML
    private TableColumn<Letrado, String> columnApellidos;
    @FXML
    private TableColumn<Letrado, String> columnColegio;
    @FXML
    private TableColumn<Letrado, String> columnDireccion;
    @FXML
    private TableColumn<Letrado, String> columnTelefono;
    @FXML
    private TableColumn<Letrado, String> columnEmail;
    @FXML
    private TableColumn<Letrado, String> columnDNI;
    private Session session;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Enlaza las columnas con los campos de letrado
        columnDNI.setCellValueFactory(new PropertyValueFactory<>("dniLetrado"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnColegio.setCellValueFactory(new PropertyValueFactory<>("colegio"));
        columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        cbColumna.getItems().addAll("DNI", "Nombre", "Apellidos", "Colegio",
                "Dirección", "Teléfono", "Email");
    }    

    @FXML
    private void buscarLetrado(ActionEvent event) {
        
        Letrado LetradoABorrar = (Letrado) tableLetrados.getSelectionModel().getSelectedItem();
        
        if(LetradoABorrar != null){
            
            //Elimina el letrado seleccionado
            Transaction tx = session.getTransaction();
            
            tx.begin();
            session.delete(LetradoABorrar);
            tx.commit();
            
            cargarLetrado();
        }else{
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Letrado no seleccionado");
            alerta.setContentText("Porfavor seleccione el letrado que desee eliminar");
            alerta.showAndWait();
        }
    }

    @FXML
    private void borrarLetrado(ActionEvent event) {
    }
    
    public void setSession(Session session) {
        this.session = session;
        cargarLetrado();
    }
    
    private void cargarLetrado(){
        
        //Busca todos los letrados en la base de datos
        Query consulta = session.createQuery("from Letrado");
        List<Letrado> letrados = consulta.list();
        
        //Muestra los letrados en la tabla
        tableLetrados.setItems(FXCollections.observableArrayList(letrados));
    }
    
}
