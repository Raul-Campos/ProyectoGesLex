/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import map.Expediente;
import map.Incidente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirIncidenteController implements Initializable {

    @FXML
    private DatePicker datePickeFecha;
    @FXML
    private TextField tfLugar;
    @FXML
    private TextField tfEnviadoPor;
    @FXML
    private ChoiceBox<String> cbTipo;
    @FXML
    private TextField tfDefensaJuridica;
    @FXML
    private CheckBox chNo;
    @FXML
    private CheckBox chSi;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private Session session;
    private SessionFactory sesion;
    
    private Expediente expediente;
    @FXML
    private TextField tfHora;
    private Incidente existente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipo.getItems().add("Amistoso");
        cbTipo.getItems().add("Asestado");
    }

    @FXML
    private void limpiar(ActionEvent event) {
        datePickeFecha.setValue(null);
        tfLugar.setText("");
        tfEnviadoPor.setText("");
        tfDefensaJuridica.setText("");
        cbTipo.setValue(null);
        chNo.setSelected(false);
        chSi.setSelected(false);
    }

    @FXML
    private void aceptarIncidente(ActionEvent event) throws ParseException, IOException {

        boolean camposRellenos = true;

        //Comprobación de campos
        if (datePickeFecha.getValue() == null) {
            camposRellenos = false;

        }
        if (tfHora.getText().equals("")) {
            camposRellenos = false;
        }
        if (tfLugar.getText().equals("")) {
            camposRellenos = false;
        }
        if (tfEnviadoPor.getText().equals("")) {
            camposRellenos = false;
        }
        if (cbTipo.getValue() == null) {
            camposRellenos = false;
        }
        if (!chSi.isSelected() && !chNo.isSelected()) {
            camposRellenos = false;
        }

        if (camposRellenos) {

            Incidente incidente = new Incidente();
            if (existente != null) {
                incidente = existente;
            }

            incidente.setLugar(tfLugar.getText());
            incidente.setDefensa(tfDefensaJuridica.getText());
            incidente.setEnviadoPor(tfEnviadoPor.getText());
            if (chNo.isSelected()) {
                incidente.setFallecidos("No");
            } else if (chSi.isSelected()) {
                incidente.setFallecidos("Si");
            }

            incidente.setParte(cbTipo.getValue());
            incidente.setCodigoExpediente(expediente.getCodigo());
            incidente.setExpediente(expediente);

            Transaction tx = session.getTransaction();

            try {
                incidente.setFechaHora(dateToDateTime());

                if (existente == null) {
                    tx.begin();
                    session.save(incidente);
                    tx.commit();
                } else {
                    tx.begin();
                    session.merge(incidente);
                    tx.commit();
                }

                //Cierra ventana
                Stage cerrar = (Stage) tfDefensaJuridica.getScene().getWindow();
                cerrar.close();
            } catch (ParseException ex) {
                tx.rollback();
                Alert alertaNuevoDoc = new Alert(Alert.AlertType.INFORMATION);
                alertaNuevoDoc.setHeaderText("Error de formato");
                alertaNuevoDoc.setContentText("Porfavor intruzca la hora en el formato correcto(HH:MM:SS).");
                alertaNuevoDoc.showAndWait();
            }

        } else {
            Alert alertaNuevoInc = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoInc.setHeaderText("Error al añadir incidente");
            alertaNuevoInc.setContentText("Porfavor rellene los campos necesarios (todos exceptop defensa)");
            alertaNuevoInc.showAndWait();
        }

    }

    @FXML
    private void cancelarIncidente(ActionEvent event) {
        Stage cerrar = (Stage) tfEnviadoPor.getScene().getWindow();
        cerrar.close();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

    @FXML
    private void opcionCheckBoxNo(ActionEvent event) {
        if (chSi.isSelected()) {
            chSi.setSelected(false);
        }
        chNo.setSelected(true);
    }

    @FXML
    private void opcionCheckBoxSi(ActionEvent event) {
        if (chNo.isSelected()) {
            chNo.setSelected(false);
        }
        chSi.setSelected(true);
    }

    private Date dateToDateTime() throws ParseException {
        Date fecha = null;
        String datos = datePickeFecha.getValue().toString() + " " + tfHora.getText();

        String strDateFormat = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat formato = new SimpleDateFormat(strDateFormat);
        fecha = formato.parse(datos);
        System.out.println(fecha.toString());
        return fecha;
    }

    private void cargarfechas() {
        String hora = "hh:mm:ss";
        String fecha = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(hora);
        tfHora.setText(df.format(existente.getFechaHora()));
        df = new SimpleDateFormat(fecha);

        String fecha1 = df.format(existente.getFechaHora());
        LocalDate fec = LocalDate.parse(fecha1);
        datePickeFecha.setValue(fec);
    }

    //Detecta que el incidente existe y carga sus datos desde la bd
    public void cargarDatos() {

        if (existente != null) {
            //Muestra los datos
            cargarfechas();
            tfLugar.setText(existente.getLugar());
            tfDefensaJuridica.setText(existente.getDefensa());
            tfEnviadoPor.setText(existente.getEnviadoPor());
            cbTipo.setValue(existente.getParte());

            if (existente.getFallecidos().equals("Si")) {
                chSi.setSelected(true);
            } else {
                chNo.setSelected(true);
            }
        }

    }

    public void setExistente(Incidente existente) {
        this.existente = existente;
    }
}
