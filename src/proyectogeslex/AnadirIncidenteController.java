/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import map.Expediente;
import map.Incidente;
import org.hibernate.Session;
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
    private Expediente expediente;
    @FXML
    private TextField tfHora;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipo.getItems().add("Amistoso");
        cbTipo.getItems().add("Asestado");
    }

   
    @FXML
    private void Limpiar(ActionEvent event) {
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
        Incidente incidente = new Incidente();
        
        incidente.setFechaHora(DateToDateTime());
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

        Transaction tx = session.getTransaction();
        //Guarda el incidente
        tx.begin();
        session.merge(incidente);
        tx.commit();

        //Cierra ventana
        Stage cerrar = (Stage) tfDefensaJuridica.getScene().getWindow();
        cerrar.close();
        
       
    }

    @FXML
    private void cancelarIncidente(ActionEvent event) {
        Stage cerrar = (Stage) tfEnviadoPor.getScene().getWindow();
        cerrar.close();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

    @FXML
    private void OpcionCheckBoxNo(ActionEvent event) {
        if(chSi.isSelected())
            chSi.setSelected(false);
        chNo.setSelected(true);
    }

    @FXML
    private void OpcionCheckBoxSi(ActionEvent event) {
        if(chNo.isSelected())
            chNo.setSelected(false);
        chSi.setSelected(true);
    }
    
    private Date DateToDateTime() throws ParseException{
        Date fecha = null;
        String datos=datePickeFecha.getValue().toString()+" "+tfHora.getText();
        
        String strDateFormat="yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat formato = new SimpleDateFormat(strDateFormat);
        fecha=formato.parse(datos);
        System.out.println(fecha.toString());
        return fecha;
    }
}
