/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import map.Cliente;
import map.Expediente;
import map.Letrado;
import map.Procurador;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirExpedienteController implements Initializable {

    @FXML
    private ComboBox<String> chCliente;
    @FXML
    private ComboBox<String> chLetrado;
    @FXML
    private ComboBox<String> chProcurador;
    @FXML
    private TextField tfFechaC;
    @FXML
    private CheckBox chbFecha;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private Session session;
    private SessionFactory sesion;
    @FXML
    private Button btnCargarDatos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Calendar c = Calendar.getInstance();
        tfFechaC.setText(Integer.toString(c.get(Calendar.YEAR))+ "-" +  Integer.toString(c.get(Calendar.MONTH) + 1) + "-" + Integer.toString(c.get(Calendar.DATE))  );
       
       
    }

    @FXML
    private void LimpiarExpediente(ActionEvent event) {
        chCliente.setValue(null);
        chLetrado.setValue(null);
        chProcurador.setValue(null);
        tfFechaC.setText("");
    }

    @FXML
    private void AceptarExpediente(ActionEvent event) {
        Expediente expediente = new Expediente();
        Query consulta;
        boolean datosRellenos = true;

        if (chCliente.getValue() != null) {
            consulta = session.createQuery("from Cliente where dni = '" + chCliente.getValue().substring(0, 9) + "'");
            Cliente cliente = (Cliente) consulta.list().get(0);
            expediente.setCliente(cliente);
        } else {
            datosRellenos = false;
        }

        if (chLetrado.getValue() != null) {
            consulta = session.createQuery("from Letrado where dniLetrado = '" + chLetrado.getValue().substring(0, 9) + "'");
            Letrado letrado = (Letrado) consulta.list().get(0);
            expediente.setLetrado(letrado);
        } else {
            datosRellenos = false;
        }

        if (chProcurador.getValue() != null) {
            consulta = session.createQuery("from Procurador where dniProcurador = '" + chProcurador.getValue().substring(0, 9) + "'");
            Procurador procurador = (Procurador) consulta.list().get(0);
            expediente.setProcurador(procurador);
        } else {
            datosRellenos = false;
        }

        if (!tfFechaC.getText().equals("")) {
            java.sql.Date fecha = java.sql.Date.valueOf(tfFechaC.getText());
            expediente.setFechaCreacion(fecha);
        } else {
            datosRellenos = false;
        }

        if (datosRellenos) {
            Transaction tx = session.getTransaction();

            tx.begin();
            session.merge(expediente);
            tx.commit();

            Stage stage = (Stage) btnAceptar.getScene().getWindow();
            stage.close();
        } else {
            Alert alertaNuevoExp = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoExp.setHeaderText("Campos incompletos");
            alertaNuevoExp.setContentText("Porfavor rellene todos los campos para crear el expediente.");
            alertaNuevoExp.showAndWait();
        }
    }

    @FXML
    private void CancelarExpediente(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void setSession(Session session) {
        this.session = session;

    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    @FXML
    private void EditarFecha(ActionEvent event) {
        if (chbFecha.isSelected()) {
            tfFechaC.setDisable(false);
        } else {
            tfFechaC.setDisable(true);
        }
    }

    @FXML
    private void cargarDatos(ActionEvent event) {


        Query consulta = session.createQuery("from Cliente");
        List<Cliente> clientes = consulta.list();
       
        clientes.forEach((cliente) -> {
            chCliente.getItems().add((cliente.getDni() + "  " + cliente.getApellidos() + "," + cliente.getNombre()));
        });
        AutoFillBox.autoCompleteComboBoxPlus(chCliente, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        
        consulta = session.createQuery("from Letrado");
        List<Letrado> letrados = consulta.list();
        letrados.forEach((letrado) -> {
            chLetrado.getItems().add(letrado.getDniLetrado() + "  " + letrado.getApellidos() + "," + letrado.getNombre());
        });
                AutoFillBox.autoCompleteComboBoxPlus(chLetrado, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));


        consulta = session.createQuery("from Procurador");
        List<Procurador> procuradores = consulta.list();
        procuradores.forEach((procurador) -> {
            chProcurador.getItems().add(procurador.getDniProcurador() + "  " + procurador.getApellidos() + "," + procurador.getNombre());
        });
                AutoFillBox.autoCompleteComboBoxPlus(chProcurador, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

    }

    private static Date StringToDate(String date) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = formato.parse(date);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return fecha;
    }
}
