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
import map.Procurador;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class VerProcuradorController implements Initializable {

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
    private TableView<Procurador> tableProcurador;
    @FXML
    private TableColumn<Procurador, String> columnDNI;
    @FXML
    private TableColumn<Procurador, String> columnNombre;
    @FXML
    private TableColumn<Procurador, String> columnApellidos;
    @FXML
    private TableColumn<Procurador, String> columnDireccion;
    @FXML
    private TableColumn<Procurador, String> columnTelefono;
    @FXML
    private TableColumn<Procurador, String> columnEmail;
    private Session session;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Enlaza las columnas con los campos de procuradores
        columnDNI.setCellValueFactory(new PropertyValueFactory<>("dniProcurador"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        //Añade opciones
        cbColumna.getItems().addAll("DNI" , "Nombre", "Apellidos", "Dirección", "Teléfono", "Email");
    }
    
    @FXML
    private void borrarProcurador(ActionEvent event) {
        
        Procurador procuradorABorrar = (Procurador) tableProcurador.getSelectionModel().getSelectedItem();
        
        if(procuradorABorrar != null){
            
            //Elimina el procurador seleccionado
            Transaction tx = session.getTransaction();
            
            tx.begin();
            session.delete(procuradorABorrar);
            tx.commit();
            
            cargarProcuradores();
        }else{
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Procurador no seleccionado");
            alerta.setContentText("Porfavor seleccione el procurador que desee eliminar");
            alerta.showAndWait();
        }
    }

    @FXML
    private void buscarProcurador(ActionEvent event) {
    }
    
    public void setSession(Session session) {
        this.session = session;
        cargarProcuradores();
    }
    
    private void cargarProcuradores(){
        
        //Busca todos los procuradores en la base de datos
        Query consulta = session.createQuery("from Procurador");
        List<Procurador> procuradores = consulta.list();
        
        //Muestra los procuradores en la tabla
        tableProcurador.setItems(FXCollections.observableArrayList(procuradores));
    }
    
}
