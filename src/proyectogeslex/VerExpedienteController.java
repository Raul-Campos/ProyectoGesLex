
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import email.EnviarAvisos;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.persistence.RollbackException;
import javax.xml.parsers.ParserConfigurationException;
import map.Documento;
import map.Expediente;
import map.Incidente;
import org.hibernate.Transaction;
import map.Aviso;
import map.Perito;
import map.Sentencia;
import map.Vehiculo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class VerExpedienteController implements Initializable {

    @FXML
    private TabPane tabPaneAsociado;
    @FXML
    private TableView<Documento> tableDocumentos;
    @FXML
    private TableColumn<Documento, String> columnDocNombre;
    @FXML
    private TableColumn<Documento, String> columnDocFecha;
    @FXML
    private TableColumn<Documento, String> columnDocAportador;
    @FXML
    private TableColumn<Documento, String> columnDocDescrip;
    @FXML
    private Button btnAnadirDoc;
    @FXML
    private Button btnEliminarDoc;
    @FXML
    private TableView<Sentencia> tableSentencias;
    @FXML
    private TableColumn<Sentencia, String> columnSentTitulo;
    @FXML
    private TableColumn<Sentencia, String> columnSentFecha;
    @FXML
    private TableColumn<Sentencia, String> columnSentDescrip;
    @FXML
    private Button btnAnadirSent;
    @FXML
    private Button btnEliminarSent;
    @FXML
    private TableColumn<Aviso, String> columnAvisoID;
    @FXML
    private TableColumn<Aviso, String> columnAvisoFecha;
    @FXML
    private TableColumn<Aviso, String> columnAvisoEmail;
    @FXML
    private TableColumn<Aviso, String> columnAvisoDescrip;
    @FXML
    private Button btnAnadirAviso;
    @FXML
    private Button btnEliminarAviso;
    @FXML
    private TabPane tabPanePrincipal;
    @FXML
    private ChoiceBox<String> cbColumna;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Expediente> tableExpedientes;
    @FXML
    private TableColumn<Expediente, String> columnCodExpediente;
    @FXML
    private TableColumn<Expediente, String> columnFechaCreacion;
    @FXML
    private TableColumn<Expediente, String> columnFechaCierre;
    @FXML
    private TableColumn<Expediente, String> columnDNICliente;
    @FXML
    private TableColumn<Expediente, String> columnDNILetrado;
    @FXML
    private TableColumn<Expediente, String> columnDNIProcurador;
    @FXML
    private HBox idbajo;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnBorrar;

    @FXML
    private TableView<Aviso> tableAvisos;

    @FXML
    private TableView<Incidente> tableIncidente;
    @FXML
    private TableColumn<Incidente, String> columnIncidenteFecha;
    @FXML
    private TableColumn<Incidente, String> columnIncidenteLugar;
    @FXML
    private TableColumn<Incidente, String> columnIncidenteDefensa;
    @FXML
    private TableColumn<Incidente, String> columnIncidenteTipo;
    @FXML
    private TableColumn<Incidente, String> columnIncidenteFallecidos;
    @FXML
    private TableColumn<Incidente, String> columnIncidenteEnviado;
    @FXML
    private Button btnAnadirIncidente;
    @FXML
    private Button btnEliminarIncidente;

    private Expediente expedienteSeleccionado;
    private SessionFactory sesion;
    private Session session;
    @FXML
    private TableColumn<Vehiculo, String> columnCocheMatricula;
    @FXML
    private TableColumn<Vehiculo, String> columnCocheMarca;
    @FXML
    private TableColumn<Vehiculo, String> columnCocheModelo;
    @FXML
    private TableColumn<Vehiculo, String> columnCocheColor;
    @FXML
    private TableColumn<Vehiculo, String> columnCocheBastidor;
    @FXML
    private TableColumn<Vehiculo, String> columnCocheAseguradora;
    @FXML
    private TableColumn<Vehiculo, String> columnCochePoliza;
    @FXML
    private TableColumn<Vehiculo, String> columnCocheRol;
    @FXML
    private Button btnAnadirCoche;
    @FXML
    private Button btnEliminarCoche;
    @FXML
    private TableColumn<Perito, String> columnPeritoDni;
    @FXML
    private TableColumn<Perito, String> columnPeritoNombre;
    @FXML
    private TableColumn<Perito, String> columnPeritoApellidos;
    @FXML
    private TableColumn<Perito, String> columnPeritoDireccion;
    @FXML
    private TableColumn<Perito, String> columnPeritoProvincia;
    @FXML
    private TableColumn<Perito, String> columnPeritoTlf;
    @FXML
    private TableColumn<Perito, String> columnPeritoEmail;
    @FXML
    private Button btnAnadirPerito;
    @FXML
    private Button btnEliminarPerito;
    @FXML
    private TableView<Vehiculo> tableCoches;
    @FXML
    private TableView<Perito> tablePeritos;
    @FXML
    private ComboBox<String> cbCoche;
    @FXML
    private ComboBox<String> cbPerito;
    @FXML
    private TableColumn<Expediente, String> columnHojaEncargo;
    List<Vehiculo> vehiculos = null;
    List<Perito> peritos = null;
    private String emailUser;
    private String emailPassword;
    @FXML
    private Button btnVerDoc;
    @FXML
    private Button btnVerSent;
    @FXML
    private Button btnModificar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnCodExpediente.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        columnFechaCierre.setCellValueFactory(new PropertyValueFactory<>("fechaCierre"));
        columnDNICliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        columnDNILetrado.setCellValueFactory(new PropertyValueFactory<>("letrado"));
        columnDNIProcurador.setCellValueFactory(new PropertyValueFactory<>("procurador"));
        columnHojaEncargo.setCellValueFactory(new PropertyValueFactory<>("hoja"));

        cbColumna.getItems().addAll("Código", "Fecha de creación", "Fecha de cierre", "Cliente", "Letrado", "Procurador");

        //Tabla de documentos
        columnDocNombre.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDocFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnDocDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnDocAportador.setCellValueFactory(new PropertyValueFactory<>("aportadoPor"));

        //Quita el color gris del header al tabpane asociado al principal
        tabPaneAsociado.getStyleClass().add("floating");

        //Tabla Sentencias
        columnSentTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnSentDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnSentFecha.setCellValueFactory(new PropertyValueFactory<>("fechaPublicacion"));

        //Tabla Incidente
        columnIncidenteDefensa.setCellValueFactory(new PropertyValueFactory<>("defensa"));
        columnIncidenteEnviado.setCellValueFactory(new PropertyValueFactory<>("enviadoPor"));
        columnIncidenteFallecidos.setCellValueFactory(new PropertyValueFactory<>("fallecidos"));
        columnIncidenteFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        columnIncidenteLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        columnIncidenteTipo.setCellValueFactory(new PropertyValueFactory<>("parte"));

        //Tabla avisos
        columnAvisoID.setCellValueFactory(new PropertyValueFactory<>("idAviso"));
        columnAvisoFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnAvisoEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnAvisoDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        //Tabla Coche
        columnCocheMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnCocheAseguradora.setCellValueFactory(new PropertyValueFactory<>("aseguradora"));
        columnCocheBastidor.setCellValueFactory(new PropertyValueFactory<>("numeroBastidor"));
        columnCocheColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        columnCocheMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnCocheModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnCochePoliza.setCellValueFactory(new PropertyValueFactory<>("numeroPoliza"));
        columnCocheRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        //Tabla Perito
        columnPeritoApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnPeritoDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnPeritoDni.setCellValueFactory(new PropertyValueFactory<>("dniPerito"));
        columnPeritoEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPeritoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnPeritoProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        columnPeritoTlf.setCellValueFactory(new PropertyValueFactory<>("telefono"));

    }

    @FXML
    private void anadirDocumento(ActionEvent event) throws IOException {
        Expediente seleccionado = tableExpedientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            //Abre ventana modal para añadir un documento al expediente seleccionado
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirDocumento.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            AnadirDocumentoController documentoController = (AnadirDocumentoController) fxmlLoader.getController();
            documentoController.setSession(session);
            documentoController.setCodigoExpediente(seleccionado.getCodigo());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir documentos");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

            cargarDocumentos(expedienteSeleccionado);

        } else {

            //Si no selecciona ningúno
            Alert alertaNuevoDoc = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoDoc.setHeaderText("Expediente no seleccionado");
            alertaNuevoDoc.setContentText("Porfavor seleccione un expediente para añadir un documento a ese expediente.");
            alertaNuevoDoc.showAndWait();
        }

    }

    @FXML
    private void eliminarDoc(ActionEvent event) {

        Documento documentoABorrar = tableDocumentos.getSelectionModel().getSelectedItem();

        if (documentoABorrar != null) {
            Transaction tx = session.getTransaction();
            tx.begin();
            session.delete(documentoABorrar);
            tx.commit();
        } else {
            Alert alertaBorrarDoc = new Alert(Alert.AlertType.INFORMATION);
            alertaBorrarDoc.setHeaderText("Documento no seleccionado");
            alertaBorrarDoc.setContentText("Porfavor seleccione el documento que desee eliminar");
            alertaBorrarDoc.showAndWait();
        }
        cargarDocumentos(expedienteSeleccionado);
    }

    @FXML
    private void anadirSentencia(ActionEvent event) throws IOException {
        Expediente seleccionado = tableExpedientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            //Abre ventana modal para añadir un documento al expediente seleccionado
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirSentencia.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            AnadirSentenciaController sentenciaController = (AnadirSentenciaController) fxmlLoader.getController();
            sentenciaController.setSession(session);
            sentenciaController.setCodigoExpediente(seleccionado.getCodigo());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir Sentencias");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

            cargarSentencia(expedienteSeleccionado);

        } else {

            //Si no selecciona ningúno
            Alert alertaNuevoSent = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoSent.setHeaderText("Expediente no seleccionado");
            alertaNuevoSent.setContentText("Porfavor seleccione un expediente para añadir un documento a ese expediente.");
            alertaNuevoSent.showAndWait();
        }
    }

    @FXML
    private void eliminarSentencia(ActionEvent event) {
        Sentencia sentenciaABorrar = tableSentencias.getSelectionModel().getSelectedItem();

        if (sentenciaABorrar != null) {
            Transaction tx = session.getTransaction();
            tx.begin();
            session.delete(sentenciaABorrar);
            tx.commit();
        } else {
            Alert alertaBorrarSent = new Alert(Alert.AlertType.INFORMATION);
            alertaBorrarSent.setHeaderText("Sentencia no seleccionada");
            alertaBorrarSent.setContentText("Porfavor seleccione la sentencia que desee eliminar");
            alertaBorrarSent.showAndWait();
        }
        cargarSentencia(expedienteSeleccionado);
    }

    @FXML
    private void anadirIncidente(ActionEvent event) throws IOException {
        Expediente seleccionado = tableExpedientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            if (seleccionado.getIncidente() == null) {
                //Abre ventana modal para añadir un documento al expediente seleccionado
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirIncidente.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                AnadirIncidenteController incidenteController = (AnadirIncidenteController) fxmlLoader.getController();
                incidenteController.setSession(session);
                incidenteController.setExpediente(seleccionado);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Añadir Incidente");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.showAndWait();

                cargarIncidente(expedienteSeleccionado);

            } else {
                Alert alertaIncExistente = new Alert(Alert.AlertType.INFORMATION);
                alertaIncExistente.setHeaderText("Incidente existente");
                alertaIncExistente.setContentText("Ya hay un incidente añadido, si desea añadir uno nuevo elimine el actual.");
                alertaIncExistente.showAndWait();
            }

        } else {

            //Si no selecciona ningúno
            Alert alertaNuevoSent = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoSent.setHeaderText("Expediente no seleccionado");
            alertaNuevoSent.setContentText("Porfavor seleccione un expediente para añadir un documento a ese expediente.");
            alertaNuevoSent.showAndWait();
        }
    }

    @FXML
    private void eliminarIncidente(ActionEvent event) {
        Incidente incidenteABorrar = tableIncidente.getSelectionModel().getSelectedItem();

        if (incidenteABorrar != null) {
            Transaction tx = session.getTransaction();
            tx.begin();
            session.delete(incidenteABorrar);
            tx.commit();
        } else {
            Alert alertaBorrarSent = new Alert(Alert.AlertType.INFORMATION);
            alertaBorrarSent.setHeaderText("Incidente no seleccionada");
            alertaBorrarSent.setContentText("Porfavor seleccione el incidente que desee eliminar");
            alertaBorrarSent.showAndWait();
        }
        cargarIncidente(expedienteSeleccionado);
    }

    @FXML
    private void anadirAviso(ActionEvent event) throws IOException {

        Expediente seleccionado = tableExpedientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            //Abre ventana modal para añadir un documento al expediente seleccionado
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirAviso.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            AnadirAvisoController avisoController = (AnadirAvisoController) fxmlLoader.getController();
            avisoController.setSession(session);
            avisoController.setExpediente(seleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir avisos");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            cargarAvisos(seleccionado);

            Thread comprobarAvisos = new Thread(new EnviarAvisos(emailUser, emailPassword, session));
            // comprobarAvisos.start();
        } else {

            //Si no selecciona ningúno
            Alert alertaNuevoAviso = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoAviso.setHeaderText("Expediente no seleccionado");
            alertaNuevoAviso.setContentText("Porfavor seleccione un expediente para añadir un aviso a ese expediente.");
            alertaNuevoAviso.showAndWait();
        }

    }

    @FXML
    private void eliminarAviso(ActionEvent event) {

        Aviso avisoABorrar = tableAvisos.getSelectionModel().getSelectedItem();

        if (avisoABorrar != null) {
            Transaction tx = session.getTransaction();
            tx.begin();
            session.delete(avisoABorrar);
            tx.commit();
        } else {
            Alert alertaBorrarDoc = new Alert(Alert.AlertType.INFORMATION);
            alertaBorrarDoc.setHeaderText("Aviso no seleccionado");
            alertaBorrarDoc.setContentText("Porfavor seleccione el aviso que desee eliminar");
            alertaBorrarDoc.showAndWait();
        }
        cargarAvisos(expedienteSeleccionado);
    }

    @FXML
    private void buscarExpediente(ActionEvent event) {

        //Comprueba si hay una opción seleccionada
        if (cbColumna.getValue() != null) {

            //Comprueba si se ha introducido un parámetro de busqueda
            if (tfBusqueda.getText() != null) {

                List<Expediente> expedientes = consultaExpediente(cbColumna.getValue(), tfBusqueda.getText());

                //Comprueba si encuentra datos relacionados con la búsqueda
                if (!expedientes.isEmpty() && expedientes != null) {
                    tableExpedientes.setItems(FXCollections.observableArrayList(expedientes));
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
    private void añadirExpediente(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirExpediente.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir Expediente");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        stage.showAndWait();

        AnadirExpedienteController anadirClientes = (AnadirExpedienteController) fxmlLoader.getController();
        anadirClientes.setSesion(sesion);
        anadirClientes.setSession(session);
        cargarExpedientes();

    }

    @FXML
    private void borrarExpediente(ActionEvent event) {
        Expediente expedienteABorrar = tableExpedientes.getSelectionModel().getSelectedItem();

        if (expedienteABorrar != null) {
            Transaction tx = session.getTransaction();
            tx.begin();
            session.delete(expedienteABorrar);
            tx.commit();
        } else {
            Alert alertaEliminarExpediente = new Alert(Alert.AlertType.INFORMATION);
            alertaEliminarExpediente.setHeaderText("Expediente no seleccionado");
            alertaEliminarExpediente.setContentText("Porfavor seleccione el expediente que desee eliminar");
            alertaEliminarExpediente.showAndWait();
        }
        cargarExpedientes();
    }

    public void setSession(Session session) {
        this.session = session;
        cargarExpedientes();
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public TabPane getTabPaneAsociado() {
        return tabPaneAsociado;
    }

    public TabPane getTabPanePrincipal() {
        return tabPanePrincipal;
    }

    //Devuelve una lista en función del campo en el que desea buscar y el valor que busca
    private List<Expediente> consultaExpediente(String campo, String valor) {
        Query consulta = null;

        if (campo.equals("Fecha de creación") || campo.equals("Fecha de cierre")) {

            //Comprueba que fecha buscar
            if (campo.equals("Fecha de creación")) {
                campo = "fechaCreacion";
            } else {
                campo = "fechaCierre";
            }
            Date fecha;

            try {
                fecha = new SimpleDateFormat("yyyy-MM-dd").parse(valor);

                consulta = session.createQuery("from Expediente where " + campo + " = ?").setParameter(0, fecha);
                return consulta.list();
            } catch (ParseException ex) {

                //Error al introducir la fecha
                Alert fechaAlerta = new Alert(Alert.AlertType.INFORMATION);
                fechaAlerta.setHeaderText("Error al buscar fecha");
                fechaAlerta.setContentText("El texto introducido no corresponde al formato de una fecha, porfavor vuelva a intentarlo.");
                fechaAlerta.showAndWait();
            }

        } else if (campo.equals("Código")) {
            try {
                consulta = session.createQuery("from Expediente where codigo = ?").setParameter(0, Integer.valueOf(valor));
                return consulta.list();
            } catch (NumberFormatException ex) {

                //error al introducir valor numérico
                Alert codigoAlerta = new Alert(Alert.AlertType.INFORMATION);
                codigoAlerta.setHeaderText("Error al buscar código");
                codigoAlerta.setContentText("El valor introducido debe de ser númerico, porfavor vulve a intentarlo.");
                codigoAlerta.showAndWait();
            }
        } else if (campo.equals("Cliente")) {
            consulta = session.createQuery("select e from Expediente e JOIN e.cliente c where c.dni  = ?").setParameter(0, valor);
            return consulta.list();
        } else if (campo.equals("Procurador")) {
            consulta = session.createQuery("select e from Expediente e JOIN e.procurador p where p.dniProcurador  = ?").setParameter(0, valor);
            return consulta.list();
        } else {
            consulta = session.createQuery("select e from Expediente e JOIN e.letrado l where l.dniLetrado  = ?").setParameter(0, valor);
            return consulta.list();
        }

        return new ArrayList<Expediente>();
    }

    private void cargarExpedientes() {

        //Busca todos los expedientes en la base de datos
        Query consulta = session.createQuery("from Expediente");
        List<Expediente> expedientes = consulta.list();

        //Muestra los clientes en la tabla
        tableExpedientes.setItems(FXCollections.observableArrayList(expedientes));
    }

    //Al hacer click sobre un expediente de la tabla muestra en la parte inferior
    //todos los documentos, sentencias, etc, asociados a él
    @FXML
    private void expedienteSeleccionado(MouseEvent event) {

        expedienteSeleccionado = tableExpedientes.getSelectionModel().getSelectedItem();
        cargarDocumentos(expedienteSeleccionado);
        cargarIncidente(expedienteSeleccionado);
        cargarSentencia(expedienteSeleccionado);
        cargarAvisos(expedienteSeleccionado);
        cargarCoches(expedienteSeleccionado);
        cargarPeritos(expedienteSeleccionado);
    }

    private void cargarDocumentos(Expediente expediente) {

        //Trae todos los documentos del expediente seleccionado
        Query consulta = session.createQuery("select d from Documento d JOIN "
                + "d.expediente e where e.codigo = ?").setParameter(0, expediente.getCodigo());
        List<Documento> documentos = consulta.list();

        tableDocumentos.setItems(FXCollections.observableArrayList(documentos));
    }

    public void cargarSentencia(Expediente expediente) {
        //Trae todos los documentos del expediente seleccionado
        Query consulta = session.createQuery("select d from Sentencia d JOIN "
                + "d.expediente e where e.codigo = ?").setParameter(0, expediente.getCodigo());
        List<Sentencia> sentencias = consulta.list();

        tableSentencias.setItems(FXCollections.observableArrayList(sentencias));
    }

    public void cargarIncidente(Expediente expediente) {
        //Trae todos los documentos del expediente seleccionado
        Query consulta = session.createQuery("select d from Incidente d JOIN "
                + "d.expediente e where e.codigo = ?").setParameter(0, expediente.getCodigo());
        List<Incidente> incidente = consulta.list();

        tableIncidente.setItems(FXCollections.observableArrayList(incidente));
    }

    public void cargarAvisos(Expediente expediente) {

        //Trae todos los avisos del expediente seleccionado
        Query consulta = session.createQuery("select a from Aviso a JOIN "
                + "a.expediente e where e.codigo = ?").setParameter(0, expediente.getCodigo());
        List<Aviso> avisos = consulta.list();

        tableAvisos.setItems(FXCollections.observableArrayList(avisos));
    }

    private void cargarCoches(Expediente expediente) {

        //Trae todos los avisos del expediente seleccionado
        Query consulta = session.createQuery("select a from Vehiculo a JOIN "
                + "a.expedientes e where e.codigo = ?").setParameter(0, expediente.getCodigo());
        List<Vehiculo> coche = consulta.list();

        tableCoches.setItems(FXCollections.observableArrayList(coche));

        if (vehiculos == null) {
            consulta = session.createQuery("from Vehiculo");
            vehiculos = consulta.list();

            vehiculos.forEach((vehiculo) -> {
                cbCoche.getItems().add((vehiculo.getMatricula() + "  " + vehiculo.getMarca() + " " + vehiculo.getModelo()));
            });
            AutoFillBox.autoCompleteComboBoxPlus(cbCoche, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        }
    }

    private void cargarPeritos(Expediente expediente) {

        //Trae todos los peritos del expediente seleccionado
        Query consulta = session.createQuery("select a from Perito a JOIN "
                + "a.expedientes e where e.codigo = ?").setParameter(0, expediente.getCodigo());
        List<Perito> peritot = consulta.list();

        tablePeritos.setItems(FXCollections.observableArrayList(peritot));

        if (peritos == null) {
            consulta = session.createQuery("from Perito");
            peritos = consulta.list();

            peritos.forEach((perito) -> {
                cbPerito.getItems().add((perito.getDniPerito() + "  " + perito.getNombre() + " " + perito.getApellidos()));
            });
            AutoFillBox.autoCompleteComboBoxPlus(cbPerito, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        }
    }

    @FXML
    private void anadirCoche(ActionEvent event) throws IOException {
        Expediente seleccionado = tableExpedientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (cbCoche.getValue() != null) {
                vehiculos.forEach((vehiculo) -> {

                    if (cbCoche.getValue().equals(vehiculo.getMatricula() + "  " + vehiculo.getMarca() + " " + vehiculo.getModelo())) {
                        vehiculo.getExpedientes().add(seleccionado);
                        Transaction tx = session.getTransaction();

                        try {

                            tx.begin();
                            session.update(vehiculo);
                            tx.commit();
                        } catch (RollbackException e) {
                            tx.rollback();
                            Alert alerta;
                            alerta = new Alert(Alert.AlertType.INFORMATION, "Error al guardar los datos. Inténtelo de nuevo");
                            alerta.setContentText(e.getLocalizedMessage());
                            alerta.showAndWait();
                        }
                        Alert alertaNuevoAviso = new Alert(Alert.AlertType.INFORMATION);
                        alertaNuevoAviso.setHeaderText("Vehiculo Guardado");
                        alertaNuevoAviso.setContentText("Vehiculo asociado correctamente al expediente.");
                        alertaNuevoAviso.showAndWait();
                        cargarCoches(seleccionado);

                    }

                });
            } else {
                Alert alertaNuevoAviso = new Alert(Alert.AlertType.INFORMATION);
                alertaNuevoAviso.setHeaderText("Vehiculo no seleccionado");
                alertaNuevoAviso.setContentText("Porfavor seleccione un vehiculo para asociarlo a un expediente.");
                alertaNuevoAviso.showAndWait();
            }

        } else {

            //Si no selecciona ningúno
            Alert alertaNuevoAviso = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoAviso.setHeaderText("Expediente no seleccionado");
            alertaNuevoAviso.setContentText("Porfavor seleccione un expediente para añadir un vehiculo a ese expediente.");
            alertaNuevoAviso.showAndWait();
        }

    }

    @FXML
    private void eliminarCoche(ActionEvent event) {
        Vehiculo cocheABorrar = tableCoches.getSelectionModel().getSelectedItem();

        if (cocheABorrar != null) {
            Transaction tx = session.getTransaction();
            tx.begin();
            session.delete(cocheABorrar);
            tx.commit();
        } else {
            Alert alertaEliminarCoche = new Alert(Alert.AlertType.INFORMATION);
            alertaEliminarCoche.setHeaderText("Coche no seleccionado");
            alertaEliminarCoche.setContentText("Porfavor seleccione el coche que desee eliminar");
            alertaEliminarCoche.showAndWait();
        }
        cargarCoches(expedienteSeleccionado);
    }

    @FXML
    private void anadirPerito(ActionEvent event) throws IOException {
        Expediente seleccionado = tableExpedientes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (cbPerito.getValue() != null) {
                peritos.forEach((perito) -> {

                    if (cbPerito.getValue().equals(perito.getDniPerito() + "  " + perito.getNombre() + " " + perito.getApellidos())) {
                        perito.getExpedientes().add(seleccionado);
                        Transaction tx = session.getTransaction();

                        try {

                            tx.begin();
                            session.update(perito);
                            tx.commit();
                        } catch (RollbackException e) {
                            tx.rollback();
                            Alert alerta;
                            alerta = new Alert(Alert.AlertType.INFORMATION, "Error al guardar los datos. Inténtelo de nuevo");
                            alerta.setContentText(e.getLocalizedMessage());
                            alerta.showAndWait();
                        }
                        Alert alertaNuevoAviso = new Alert(Alert.AlertType.INFORMATION);
                        alertaNuevoAviso.setHeaderText("Perito Guardado");
                        alertaNuevoAviso.setContentText("Perito asociado correctamente al expediente.");
                        alertaNuevoAviso.showAndWait();
                        cargarPeritos(seleccionado);

                    }

                });
            } else {
                Alert alertaNuevoAviso = new Alert(Alert.AlertType.INFORMATION);
                alertaNuevoAviso.setHeaderText("Perito no seleccionado");
                alertaNuevoAviso.setContentText("Porfavor seleccione un perito para asociarlo a un expediente.");
                alertaNuevoAviso.showAndWait();
            }

            //Abre ventana modal para añadir un documento al expediente seleccionado
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirPerito.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            AnadirPeritoController peritoController = (AnadirPeritoController) fxmlLoader.getController();
            peritoController.setSession(session);
            peritoController.setExpediente(seleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir Perito");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            cargarPeritos(seleccionado);

        } else {

            //Si no selecciona ningúno
            Alert alertaNuevoAviso = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoAviso.setHeaderText("Expediente no seleccionado");
            alertaNuevoAviso.setContentText("Porfavor seleccione un expediente para añadir un perito a ese expediente.");
            alertaNuevoAviso.showAndWait();
        }
    }

    @FXML
    private void eliminarPerito(ActionEvent event) {
        Perito peritoABorrar = tablePeritos.getSelectionModel().getSelectedItem();

        if (peritoABorrar != null) {
            Transaction tx = session.getTransaction();
            tx.begin();
            session.delete(peritoABorrar);
            tx.commit();
        } else {
            Alert alertaBorrarPerito = new Alert(Alert.AlertType.INFORMATION);
            alertaBorrarPerito.setHeaderText("Perito no seleccionado");
            alertaBorrarPerito.setContentText("Porfavor seleccione el perito que desee eliminar");
            alertaBorrarPerito.showAndWait();
        }
    }

    @FXML
    private void verDocumento(ActionEvent event) throws FileNotFoundException, IOException, ParserConfigurationException {

        Documento doc = tableDocumentos.getSelectionModel().getSelectedItem();

        if (doc != null) {
            File ficheroPdf = File.createTempFile(doc.getId().getNombre(), ".pdf", new File("src"));
            
            //Lo elimina al cerrar la aplicación
            ficheroPdf.deleteOnExit();

            //Copia el fichero del documento seleccionado en la máquina
            FileOutputStream os = new FileOutputStream(ficheroPdf);
            os.write(doc.getPdf());
            os.close();
            
            //Muestra el fichero
            if(Desktop.isDesktopSupported())
                Desktop.getDesktop().open(ficheroPdf);

        } else {
            Alert alertaVerDocumento = new Alert(Alert.AlertType.INFORMATION);
            alertaVerDocumento.setHeaderText("Documento no seleccionado");
            alertaVerDocumento.setContentText("Porfavor seleccione el documente que desee visualizar");
            alertaVerDocumento.showAndWait();
        }
    }

    @FXML
    private void verSentencia(ActionEvent event) throws FileNotFoundException, IOException, ParserConfigurationException {

        Sentencia sent = tableSentencias.getSelectionModel().getSelectedItem();

        if (sent != null) {

            File ficheroPdf = File.createTempFile("sent" + sent.getId().getCodExpediente(), ".pdf");
            String ficheroHtml = "sent" + sent.getId().getCodExpediente() + ".html";

            //Copia el fichero del documento seleccionado en la máquina
            FileOutputStream os = new FileOutputStream(ficheroPdf);
            os.write(sent.getPdf());
            os.close();
            
            //Muestra fichero
            if(Desktop.isDesktopSupported())
                Desktop.getDesktop().open(ficheroPdf);
            
            //Convierte el fichero pdf en html
            /*PDDocument pdf = PDDocument.load(ficheroPdf);
            Writer output = new PrintWriter("src\\proyectogeslex\\" + ficheroHtml, "utf-8");
            new PDFDomTree().writeText(pdf, output);

            WebView browser = new WebView();
            WebEngine webEngine = browser.getEngine();

            webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                if (newState == State.SUCCEEDED) {
                    Document d = webEngine.getDocument();
                }
            });

            //Muestra el fichero html
            webEngine.load(this.getClass().getResource(ficheroHtml).toExternalForm());

            Scene scene = new Scene(browser);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ver sentencia");
            stage.setScene(scene);
            stage.showAndWait();

            //Elimina los ficheros pdf y html
            output.close();
            pdf.close();
            ficheroPdf.delete();
            File file2 = new File("src\\proyectogeslex\\" + ficheroHtml);
            file2.delete();*/
        } else {
            Alert alertaVerSentencia = new Alert(Alert.AlertType.INFORMATION);
            alertaVerSentencia.setHeaderText("Sentencia no seleccionada");
            alertaVerSentencia.setContentText("Porfavor seleccione la sentencia que desee visualizar");
            alertaVerSentencia.showAndWait();
        }
    }

    @FXML
    private void modificarDocumento(ActionEvent event) throws IOException {

        Expediente seleccionado = tableExpedientes.getSelectionModel().getSelectedItem();
        Documento documento = tableDocumentos.getSelectionModel().getSelectedItem();

        if (documento != null) {

            //Abre ventana modal para añadir un documento al expediente seleccionado
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnadirDocumento.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            AnadirDocumentoController documentoController = (AnadirDocumentoController) fxmlLoader.getController();
            documentoController.setSession(session);
            documentoController.setCodigoExpediente(seleccionado.getCodigo());
            documentoController.setExistente(documento);
            documentoController.cargarDatos();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir documentos");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

            cargarDocumentos(expedienteSeleccionado);

        } else {

            //Si no selecciona ningúno
            Alert alertaNuevoDoc = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoDoc.setHeaderText("Documento no seleccionado");
            alertaNuevoDoc.setContentText("Porfavor seleccione el documento que desea modificar");
            alertaNuevoDoc.showAndWait();
        }
    }

}
