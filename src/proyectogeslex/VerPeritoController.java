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
import map.Perito;
import org.hibernate.Query;
import org.hibernate.Session;
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
    private Session session;

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
        
        //Comprueba si hay una opción seleccionada
        if (cbColumna.getValue() != null) {

            //Comprueba si se ha introducido un parámetro de busqueda
            if (tfBusqueda.getText() != null) {
                
                List<Perito> peritos = consultaPeritos(cbColumna.getValue(), tfBusqueda.getText());
                 
                //Comprueba si encuentra datos relacionados con la búsqueda
                if (!peritos.isEmpty()) {
                    tablePeritos.setItems(FXCollections.observableArrayList(peritos));
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
    
    //Devuelve una lista en función del campo en el que desea buscar y el valor que busca
    private List<Perito> consultaPeritos(String campo, String valor){
        Query consulta;
        
        if(campo.equals("DNI"))
            campo = "dniPerito";
        else if(campo.equals("Dirección"))
            campo = "direccion";
        else if(campo.equals("Teléfono")){
            campo = "telefono";
            int tlf = Integer.valueOf(valor);
            consulta = session.createQuery("from Letrado where "+campo+" = ?").setParameter(0, tlf);
            return consulta.list();
        }
        
        consulta = session.createQuery("from Perito where "+campo+" = ?").setParameter(0, valor);
        return consulta.list();
    }
}
