/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import map.Cliente;
import map.Documento;
import map.DocumentoId;
import map.Expediente;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    private Session session;
    @FXML
    private TableView<?> tableSentencias;
    @FXML
    private TableView<?> tableAvisos;
    @FXML
    private TableView<?> tableHoja;
    @FXML
    private TableColumn<?, ?> columnHojaCod;
    @FXML
    private TableColumn<?, ?> columnHojaEstado;
    @FXML
    private Button btnAnadirHoja;
    @FXML
    private Button btnEliminarHoja;

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

        cbColumna.getItems().addAll("Código", "Fecha de creación", "Fecha de cierre", "Cliente", "Letrado", "Procurador");

        //Tabla de documentos
        columnDocNombre.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDocFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnDocDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnDocAportador.setCellValueFactory(new PropertyValueFactory<>("aportadoPor"));

        //Quita el color gris del header al tabpane asociado al principal
        tabPaneAsociado.getStyleClass().add("floating");
    }

    @FXML
    private void anadirDocumento(ActionEvent event) throws IOException {
        /*DocumentoId id = new DocumentoId();
        id.setNombre("Prueba");
        Documento doc = new Documento();
        doc.setId(id);
        doc.setAportadoPor("Raúl");
        Expediente expediente = (Expediente) session.createQuery("from Expediente where codigo = 2").uniqueResult();
        id.setCodExpediente(expediente.getCodigo());
        doc.setExpediente(expediente);
        doc.setFecha(new Date());
        doc.setDescripcion("Prueba de descripción");
        File file = new File("C:\\Users\\Raul\\Documents\\prueba.pdf");
        byte[] pdf = Files.readAllBytes(file.toPath());
        doc.setPdf(pdf);

        Transaction tx = session.getTransaction();
        tx.begin();
        session.save(doc);
        tx.commit();*/

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
    private void añadirExpediente(ActionEvent event) {
    }

    @FXML
    private void borrarExpediente(ActionEvent event) {
    }

    public void setSession(Session session) {
        this.session = session;
        cargarExpedientes();
    }

    public TabPane getTabPaneAsociado() {
        return tabPaneAsociado;
    }

    public TabPane getTabPanePrincipal() {
        return tabPanePrincipal;
    }

    @FXML
    private void anadirHoja(ActionEvent event) {
    }

    @FXML
    private void eliminarHoja(ActionEvent event) {
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

    private void verDatosAscoiados() {

    }

    //Al hacer click sobre un expediente de la tabla muestra en la parte inferior
    //todos los documentos, sentencias, etc, asociados a él
    @FXML
    private void expedienteSeleccionado(MouseEvent event) throws FileNotFoundException, IOException {

        Expediente expediente = tableExpedientes.getSelectionModel().getSelectedItem();

        //Trae todos los documentos del expediente seleccionado
        Query consulta = session.createQuery("select d from Documento d JOIN "
                + "d.expediente e where e.codigo = ?").setParameter(0, expediente.getCodigo());
        List<Documento> documentos = consulta.list();

        tableDocumentos.setItems(FXCollections.observableArrayList(documentos));
        //columnDocDescrip.setCellValueFactory(v -> new SimpleStringProperty("hdfkjdskjflksd"));
        /*File file = new File("prueba.pdf");
        FileOutputStream os = new FileOutputStream(file);
        os.write(documentos.get(0).getPdf());
        os.close();*/
    }
}
