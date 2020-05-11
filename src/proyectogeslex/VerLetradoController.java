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
import map.Letrado;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    private SessionFactory sesion;

    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnBorrar;

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
        
        //Comprueba si hay una opción seleccionada
        if (cbColumna.getValue() != null) {

            //Comprueba si se ha introducido un parámetro de busqueda
            if (tfBusqueda.getText() != null) {
                
                List<Letrado> letrados = consultaLetrados(cbColumna.getValue(), tfBusqueda.getText());
                 
                //Comprueba si encuentra datos relacionados con la búsqueda
                if (!letrados.isEmpty()) {
                    tableLetrados.setItems(FXCollections.observableArrayList(letrados));
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
    private void borrarLetrado(ActionEvent event) {
      
        Letrado LetradoABorrar = (Letrado) tableLetrados.getSelectionModel().getSelectedItem();

        if (LetradoABorrar != null) {

            //Elimina el letrado seleccionado
            Transaction tx = session.getTransaction();

            tx.begin();
            session.delete(LetradoABorrar);
            tx.commit();

            cargarLetrado();
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Letrado no seleccionado");
            alerta.setContentText("Porfavor seleccione el letrado que desee eliminar");
            alerta.showAndWait();
        }
    }

    public void setSession(Session session) {
        this.session = session;
        cargarLetrado();
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    private void cargarLetrado() {

        //Busca todos los letrados en la base de datos
        Query consulta = session.createQuery("from Letrado");
        List<Letrado> letrados = consulta.list();

        //Muestra los letrados en la tabla
        tableLetrados.setItems(FXCollections.observableArrayList(letrados));
    }
    
    //Devuelve una lista en función del campo en el que desea buscar y el valor que busca
    private List<Letrado> consultaLetrados(String campo, String valor){
        Query consulta;
        
        if(campo.equals("DNI"))
            campo = "dniLetrado";
        else if(campo.equals("Dirección"))
            campo = "direccion";
        else if(campo.equals("Teléfono")){
            campo = "telefono";
            int tlf = Integer.valueOf(valor);
            consulta = session.createQuery("from Letrado where "+campo+" = ?").setParameter(0, tlf);
            return consulta.list();
        }
        
        consulta = session.createQuery("from Letrado where "+campo+" = ?").setParameter(0, valor);
        return consulta.list();
    }

    @FXML
    private void añadirLetrado(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirLetrado.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir Letrado");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        stage.show();

        AnadirLetradoController anadirLetrado = (AnadirLetradoController) fxmlLoader.getController();
        anadirLetrado.setSesion(sesion);
        anadirLetrado.setSession(session);
        cargarLetrado();
    }

    @FXML
    private void borrarLetrado(ActionEvent event) {
        Letrado aBorrar = (Letrado) tableLetrados.getSelectionModel().getSelectedItem();

        if (aBorrar != null) {

            //Elimina el cliente seleccionado
            Transaction tx = session.getTransaction();

            tx.begin();
            session.delete(aBorrar);
            tx.commit();

            cargarLetrado();
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Letrado no seleccionado");
            alerta.setContentText("Porfavor seleccione el letrado que desee eliminar");
            alerta.showAndWait();
        }
    }

}
