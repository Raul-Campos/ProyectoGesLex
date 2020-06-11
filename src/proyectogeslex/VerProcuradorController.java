/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import map.Procurador;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

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
    private SessionFactory sesion;

    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnModificar;

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
        cbColumna.getItems().addAll("DNI", "Nombre", "Apellidos", "Dirección", "Teléfono", "Email");
    }

    @FXML
    private void borrarProcurador(ActionEvent event) {

        Procurador procuradorABorrar = (Procurador) tableProcurador.getSelectionModel().getSelectedItem();

        if (procuradorABorrar != null) {

            //Elimina el procurador seleccionado
            Transaction tx = session.getTransaction();

            try {
                tx.begin();
                session.delete(procuradorABorrar);
                tx.commit();
            } catch (ConstraintViolationException ex) {

                //Controla que no se borra información asociada a otros objetos
                tx.rollback();
                Alert alertaBorrar = new Alert(Alert.AlertType.INFORMATION);
                alertaBorrar.setHeaderText("Imposible eliminar");
                alertaBorrar.setContentText("Hay expedientes que contienen la información "
                        + "de este procurador, elimine los expedientes relacionados antes de eliminarlo.");
                alertaBorrar.showAndWait();
            }

            cargarProcuradores();
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Procurador no seleccionado");
            alerta.setContentText("Porfavor seleccione el procurador que desee eliminar");
            alerta.showAndWait();
        }
    }

    @FXML
    private void buscarProcurador(ActionEvent event) {

        //Comprueba si hay una opción seleccionada
        if (cbColumna.getValue() != null) {

            //Comprueba si se ha introducido un parámetro de busqueda
            if (tfBusqueda.getText() != null) {

                List<Procurador> procuradores = consultaProcurador(cbColumna.getValue(), tfBusqueda.getText());

                //Comprueba si encuentra datos relacionados con la búsqueda
                if (!procuradores.isEmpty()) {
                    tableProcurador.setItems(FXCollections.observableArrayList(procuradores));
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

    public void setSession(Session session) {
        this.session = session;
        cargarProcuradores();
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    private void cargarProcuradores() {

        //Busca todos los procuradores en la base de datos
        Query consulta = session.createQuery("from Procurador");
        List<Procurador> procuradores = consulta.list();

        //Muestra los procuradores en la tabla
        tableProcurador.getItems().clear();
        tableProcurador.setItems(FXCollections.observableArrayList(procuradores));
    }

    @FXML
    private void añadirProcurador(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirProcurador.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AnadirProcuradorController anadirProcurador = (AnadirProcuradorController) fxmlLoader.getController();
        anadirProcurador.setSesion(sesion);
        anadirProcurador.setSession(session);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir Procurador");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("recursos/logo.png"));
        stage.setResizable(false);
        stage.showAndWait();

        cargarProcuradores();
    }

    //Devuelve una lista en función del campo en el que desea buscar y el valor que busca
    private List<Procurador> consultaProcurador(String campo, String valor) {
        Query consulta;

        if (campo.equals("DNI")) {
            campo = "dniProcurador";
        } else if (campo.equals("Dirección")) {
            campo = "direccion";
        } else if (campo.equals("Teléfono")) {
            campo = "telefono";

            try {
                int tlf = Integer.valueOf(valor);
                consulta = session.createQuery("from Letrado where " + campo + " = ?").setParameter(0, tlf);
                return consulta.list();
            } catch (NumberFormatException ex) {

                //error al introducir valor numérico
                Alert codigoAlerta = new Alert(Alert.AlertType.INFORMATION);
                codigoAlerta.setHeaderText("Error al buscar código");
                codigoAlerta.setContentText("El valor introducido debe de ser númerico, porfavor vulve a intentarlo.");
                codigoAlerta.showAndWait();

                return new ArrayList<>();
            }
        }

        consulta = session.createQuery("from Procurador where " + campo + " = ?").setParameter(0, valor);
        return consulta.list();
    }

    @FXML
    private void modificarProcurador(ActionEvent event) throws IOException {

        Procurador procurador = tableProcurador.getSelectionModel().getSelectedItem();

        if (procurador != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirProcurador.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            AnadirProcuradorController anadirProcurador = (AnadirProcuradorController) fxmlLoader.getController();
            anadirProcurador.setSesion(sesion);
            anadirProcurador.setSession(session);
            anadirProcurador.setExistente(procurador);
            anadirProcurador.cargarDatos();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir Procurador");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("recursos/logo.png"));
            stage.setResizable(false);
            stage.showAndWait();

            cargarProcuradores();
        }
    }
}
