/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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

        Aviso aviso = new Aviso();

        try {
            java.sql.Date fecha = java.sql.Date.valueOf(dpFecha.getValue());
            aviso.setEmail(tfEmail.getText());
            aviso.setDescripcion(txDesc.getText());
            aviso.setExpediente(expediente);
            aviso.setFecha(fecha);

            Transaction tx = session.getTransaction();

            //Guarda el aviso
            tx.begin();
            session.save(aviso);
            tx.commit();

            //Cierra ventana
            Stage cerrar = (Stage) tfEmail.getScene().getWindow();
            cerrar.close();
            
        } catch (NullPointerException ex) {
            Alert alertaNuevoAviso = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoAviso.setHeaderText("Error al guardar aviso");
            alertaNuevoAviso.setContentText("Porfavor rellene todos los campos necesarios (email y fecha)");
            alertaNuevoAviso.showAndWait();
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
