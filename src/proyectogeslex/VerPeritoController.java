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
import map.Cliente;
import map.Perito;
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
    @FXML
    private Button btnModificar;

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

        if (peritoABorrar != null) {

            //Elimina el perito seleccionado
            Transaction tx = session.getTransaction();

            try {
                tx.begin();
                session.delete(peritoABorrar);
                tx.commit();
            } catch (ConstraintViolationException ex) {

                //Controla que no se borra información asociada a otros objetos
                tx.rollback();
                Alert alertaBorrar = new Alert(Alert.AlertType.INFORMATION);
                alertaBorrar.setHeaderText("Imposible eliminar");
                alertaBorrar.setContentText("Hay expedientes que contienen la información "
                        + "de este perito, elimine los expedientes relacionados antes de eliminarlo.");
                alertaBorrar.showAndWait();
            }

            cargarPeritos();
        } else {
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

    private void cargarPeritos() {

        //Busca todos los peritos en la base de datos
        Query consulta = session.createQuery("from Perito");
        List<Perito> peritos = consulta.list();

        //Muestra los peritos en la tabla
        tablePeritos.getItems().clear();
        tablePeritos.setItems(FXCollections.observableArrayList(peritos));
    }

    //Devuelve una lista en función del campo en el que desea buscar y el valor que busca
    private List<Perito> consultaPeritos(String campo, String valor) {
        Query consulta;

        if (campo.equals("DNI")) {
            campo = "dniPerito";
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

        consulta = session.createQuery("from Perito where " + campo + " = ?").setParameter(0, valor);
        return consulta.list();
    }

    @FXML
    private void anadirPerito(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirPerito.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AnadirPeritoController anadirPerito = (AnadirPeritoController) fxmlLoader.getController();
        anadirPerito.setSesion(sesion);
        anadirPerito.setSession(session);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir Perito");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("recursos/logo.png"));
        stage.setResizable(false);
        stage.showAndWait();

        cargarPeritos();
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    //Detecta que el perito existe y carga sus datos desde la bd
    @FXML
    private void modificarPerito(ActionEvent event) throws IOException, InterruptedException {

        Perito perito = tablePeritos.getSelectionModel().getSelectedItem();

        if (perito != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirPerito.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            AnadirPeritoController anadirPerito = (AnadirPeritoController) fxmlLoader.getController();
            anadirPerito.setSesion(sesion);
            anadirPerito.setSession(session);
            anadirPerito.setExistente(perito);
            anadirPerito.cargarDatos();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir Perito");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("recursos/logo.png"));
            stage.setResizable(false);
            stage.showAndWait();

            cargarPeritos();
        }
    }

    @FXML
    private void recargarTabla(ActionEvent event) {
        cargarPeritos();
        cbColumna.valueProperty().set(null);
    }
}
