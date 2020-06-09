/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import map.Aviso;
import map.Expediente;
import map.Smtp;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class AnadirAvisoController implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextArea txDesc;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    private Session session;
    private Expediente expediente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dpFecha.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                if (empty || date.compareTo(today) < 0) {
                    setDisable(true);
                    setStyle("-fx-background-color: #F5BFB3;");
                }
            }
        });
    }

    @FXML
    private void limpiar(ActionEvent event) {
        tfEmail.setText("");
        txDesc.setText("");
        dpFecha.getEditor().clear();
    }

    @FXML
    private void aceptarAviso(ActionEvent event) {

        if (!tfEmail.getText().equals("") && dpFecha.getValue() != null) {

            Aviso aviso = new Aviso();

            java.sql.Date fecha = java.sql.Date.valueOf(dpFecha.getValue());
            aviso.setEmail(tfEmail.getText());
            aviso.setDescripcion(txDesc.getText());
            aviso.setExpediente(expediente);
            aviso.setFecha(fecha);

            if (formatoCorreo()) {
                Transaction tx = session.getTransaction();

                //Guarda el aviso
                tx.begin();
                session.save(aviso);
                tx.commit();

                //Cierra ventana
                Stage cerrar = (Stage) tfEmail.getScene().getWindow();
                cerrar.close();
            } else {
                Alert alertaFormato = new Alert(Alert.AlertType.INFORMATION);
                alertaFormato.setHeaderText("Error en el formato del email");
                alertaFormato.setContentText("Porfavor compruebe el formato del email introducido.");
                alertaFormato.showAndWait();
            }

        } else {
            Alert alertaNuevoAviso = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoAviso.setHeaderText("Error al guardar aviso");
            alertaNuevoAviso.setContentText("Porfavor rellene todos los campos necesarios (email y fecha)");
            alertaNuevoAviso.showAndWait();
        }

    }

    private boolean formatoCorreo() {

        //Comprueba el servidor que esta en uso
        Query consulta = session.createQuery("from Smtp where opcion = :valor");
        consulta.setParameter("valor", "Seleccionado");
        Smtp servidor = (Smtp) consulta.uniqueResult();

        //Comprueba el formato correcto
        Pattern formato = Pattern.compile("[^@;]*@.*\\.[a-zA-Z]{3,3}");
        Matcher match = formato.matcher(tfEmail.getText());

        if (match.matches()) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void cancelarAviso(ActionEvent event) {
        Stage stage = (Stage) tfEmail.getScene().getWindow();
        stage.close();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

}
