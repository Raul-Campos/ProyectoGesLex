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
import map.Vehiculo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class VerVehiculosController implements Initializable {

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
    private TableView<Vehiculo> tableVehiculos;
    @FXML
    private TableColumn<Vehiculo, String> matriculaColumn;
    @FXML
    private TableColumn<Vehiculo, String> marcaColumn;
    @FXML
    private TableColumn<Vehiculo, String> modeloColumn;
    @FXML
    private TableColumn<Vehiculo, String> colorColumn;
    @FXML
    private TableColumn<Vehiculo, String> numBastidorColumn;
    @FXML
    private TableColumn<Vehiculo, String> aseguradoraColumn;
    @FXML
    private TableColumn<Vehiculo, String> nPolizaColumn;
    @FXML
    private TableColumn<Vehiculo, String> rolColumn;
    
    private Session session;
    private SessionFactory sesion;
    
    @FXML
    private Button btnAñadir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Enlaza las columnas con los campos de vehiculos
        matriculaColumn.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        numBastidorColumn.setCellValueFactory(new PropertyValueFactory<>("numeroBastidor"));
        aseguradoraColumn.setCellValueFactory(new PropertyValueFactory<>("aseguradora"));
        nPolizaColumn.setCellValueFactory(new PropertyValueFactory<>("numeroPoliza"));
        rolColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));
        
        //Añade opciones
        cbColumna.getItems().addAll("Matricula", "Expediente", "Marca", "Modelo",
                "Color", "Nº de bastidor", "Aseguradora", "Nº de poliza", "Rol");
    }

    @FXML
    private void buscarVehiculo(ActionEvent event) {
        
        //Comprueba si hay una opción seleccionada
        if (cbColumna.getValue() != null) {

            //Comprueba si se ha introducido un parámetro de busqueda
            if (tfBusqueda.getText() != null) {
                
                List<Vehiculo> vehiculos = consultaVehiculo(cbColumna.getValue(), tfBusqueda.getText());
                 
                //Comprueba si encuentra datos relacionados con la búsqueda
                if (!vehiculos.isEmpty()) {
                    tableVehiculos.setItems(FXCollections.observableArrayList(vehiculos));
                } else {
                    Alert alertaBuqueda = new Alert(Alert.AlertType.INFORMATION);
                    alertaBuqueda.setHeaderText("Error de búsqueda");
                    alertaBuqueda.setContentText("No se ha podido encontrar datos relacionados con la búsqueda realizada.");
                    alertaBuqueda.showAndWait();
                }
            }

        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Opción no seleccionado");
            alerta.setContentText("Porfavor seleccione un campo por el que desee realizar la búsqueda");
            alerta.showAndWait();
        }
    }

    @FXML
    private void borrarVehiculo(ActionEvent event) {
        Vehiculo vehiculoABorrar = (Vehiculo) tableVehiculos.getSelectionModel().getSelectedItem();
        
        if(vehiculoABorrar != null){
            
            //Elimina el vehiculo seleccionado
            Transaction tx = session.getTransaction();
            
            tx.begin();
            session.delete(vehiculoABorrar);
            tx.commit();
            
            cargarVehiculos();
        }else{
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Vehículo no seleccionado");
            alerta.setContentText("Porfavor seleccione el vehículo que desee eliminar");
            alerta.showAndWait();
        }
    }
    
    public void setSession(Session session) {
        this.session = session;
        cargarVehiculos();
    }
    
    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }
    
    private void cargarVehiculos(){
        
        //Busca todos los vehiculos en la base de datos
        Query consulta = session.createQuery("from Vehiculo");
        List<Vehiculo> vehiculos = consulta.list();
        
        //Muestra los vehiculos en la tabla
        tableVehiculos.setItems(FXCollections.observableArrayList(vehiculos));
    }

    @FXML
    private void AñadirVehiculo(ActionEvent event) throws IOException {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirVehiculo.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir Vehiculo");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        stage.show();

        AnadirVehiculoController anadirVehiculo = (AnadirVehiculoController) fxmlLoader.getController();
        anadirVehiculo.setSesion(sesion);
        anadirVehiculo.setSession(session);
        cargarVehiculos();
    }
    
     //Devuelve una lista en función del campo en el que desea buscar y el valor que busca
    private List<Vehiculo> consultaVehiculo(String campo, String valor){
        if(campo.equals("Nº de bastidor"))
            campo = "numeroBastidor";
        else if(campo.equals("Nº de poliza"))
            campo = "numeroPoliza";
        
        Query consulta = session.createQuery("from Vehiculo where "+campo+" = ?").setParameter(0, valor);
        return consulta.list();
    }
    
}
