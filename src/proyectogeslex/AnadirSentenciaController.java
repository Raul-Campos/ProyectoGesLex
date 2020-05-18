/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import map.Sentencia;
import map.SentenciaId;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirSentenciaController implements Initializable {

    @FXML
    private TextField tfTitulo;
    @FXML
    private Button btnSentencia;
    @FXML
    private TextArea txDesc;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private File file;
    private Session session;
    private int codigoExpediente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void seleccionarFichero(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        file = chooser.showOpenDialog(new Stage());
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        file = null;
        tfTitulo.setText("");
        txDesc.setText("");
    }

    @FXML
    private void aceptarSentencia(ActionEvent event) throws ParseException {
        Sentencia sentencia = new Sentencia();
        SentenciaId id = new SentenciaId();
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String formatoFecha = formato.format(fecha);
        Date fecha2 = new SimpleDateFormat("yyyy-MM-dd").parse(formatoFecha);

        id.setTitulo(tfTitulo.getText());
        id.setCodExpediente(codigoExpediente);
        sentencia.setId(id);
        sentencia.setFechaPublicacion(fecha2);
        sentencia.setDescripcion(txDesc.getText());

        Transaction tx = session.getTransaction();
        try {
            //Convierte fichero a array de bytes
            byte[] pdf = Files.readAllBytes(file.toPath());
            sentencia.setPdf(pdf);

            //Guarda la sentencia
            tx.begin();
            session.save(sentencia);
            tx.commit();

            //Cierra ventana
            Stage cerrar = (Stage) tfTitulo.getScene().getWindow();
            cerrar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            Alert alertaNuevoDoc = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoDoc.setHeaderText("Archivo no seleccionado");
            alertaNuevoDoc.setContentText("Porfavor seleccione una sentencia");
            alertaNuevoDoc.showAndWait();
        }
    }

    @FXML
    private void cancelarSentencia(ActionEvent event) {
        Stage cerrar = (Stage) tfTitulo.getScene().getWindow();
        cerrar.close();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setCodigoExpediente(int codigoExpediente) {
        this.codigoExpediente = codigoExpediente;
    }
}
