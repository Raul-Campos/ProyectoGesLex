/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import map.Cliente;
import map.Perito;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class VerPeritoController implements Initializable {

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
    private TableView<Perito> tablePeritos;
    @FXML
    private TableColumn<Perito, String> columnNombre;
    @FXML
    private TableColumn<Perito, String> columnApellidos;
    @FXML
    private TableColumn<Perito, String> columnDireccion;
    @FXML
    private TableColumn<Perito, String> columnTelefono;
    @FXML
    private TableColumn<Perito, String> columnEmail;
    @FXML
    private TableColumn<Perito, String> columnDNI;
    @FXML
    private TableColumn<Perito, String> columnProvincia;
    
    private SessionFactory sesion;
    private Session session;
    @FXML
    private Button btnAñadir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Enlaza las columnas con los campos de letrado
        columnDNI.setCellValueFactory(new PropertyValueFactory<>("dniPerito"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        cbColumna.getItems().addAll("DNI", "Nombre", "Apellidos", "Provincia", 
                "Dirección", "Teléfono", "Email");
    }    



    @FXML
    private void buscarPerito(ActionEvent event) {
    }

    @FXML
    private void borrarPerito(ActionEvent event) {
        
        Perito peritoABorrar = (Perito) tablePeritos.getSelectionModel().getSelectedItem();
        
        if(peritoABorrar != null){
            
            //Elimina el perito seleccionado
            Transaction tx = session.getTransaction();
            
            tx.begin();
            session.delete(peritoABorrar);
            tx.commit();
            
            cargarPeritos();
        }else{
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Perito no seleccionado");
            alerta.setContentText("Porfavor seleccione el perito que desee eliminar");
            alerta.showAndWait();
        }
    }

    public void setSession(Session session) {
        this.session = session;
        cargarPeritos();
    }
    
    private void cargarPeritos(){
        
        //Busca todos los peritos en la base de datos
        Query consulta = session.createQuery("from Perito");
        List<Perito> peritos = consulta.list();
        
        //Muestra los peritos en la tabla
        tablePeritos.setItems(FXCollections.observableArrayList(peritos));
    }

    @FXML
    private void añadirPerito(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirPerito.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir Perito");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        
        stage.show();

        AnadirPeritoController anadirPerito = (AnadirPeritoController) fxmlLoader.getController();
        anadirPerito.setSesion(sesion);
        anadirPerito.setSession(session);
        cargarPeritos();
    }
    
    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }
}
