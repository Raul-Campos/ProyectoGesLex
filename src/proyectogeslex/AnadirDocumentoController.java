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
import java.time.LocalDateTime;
import java.util.Calendar;
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
import map.Documento;
import map.DocumentoId;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class AnadirDocumentoController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfAportador;
    @FXML
    private Button btnDocumento;
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
        tfAportador.setText("");
        tfNombre.setText("");
        txDesc.setText("");
    }

    @FXML
    private void aceptarDocumento(ActionEvent event) throws ParseException {
        
        Documento documento = new Documento();
        DocumentoId id = new DocumentoId();
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String formatoFecha = formato.format(fecha);
        Date fecha2 = new SimpleDateFormat("yyyy-MM-dd").parse(formatoFecha);

        id.setNombre(tfNombre.getText());
        id.setCodExpediente(codigoExpediente);
        documento.setId(id);
        documento.setFecha(fecha2);
        documento.setAportadoPor(tfAportador.getText());
        documento.setDescripcion(txDesc.getText());

        Transaction tx = session.getTransaction();
        try {
            //Convierte fichero a array de bytes
            byte[] pdf = Files.readAllBytes(file.toPath());
            documento.setPdf(pdf);

            //Guarda el documento
            tx.begin();
            session.save(documento);
            tx.commit();

            //Cierra ventana
            Stage cerrar = (Stage) tfAportador.getScene().getWindow();
            cerrar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch(NullPointerException ex){
            tx.rollback();
            Alert alertaNuevoDoc = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoDoc.setHeaderText("Archivo no seleccionado");
            alertaNuevoDoc.setContentText("Porfavor seleccione un documento");
            alertaNuevoDoc.showAndWait();
        }catch(NonUniqueObjectException ex){
            tx.rollback();
            Alert alertaDocExistente = new Alert(Alert.AlertType.INFORMATION);
            alertaDocExistente.setHeaderText("Documento existente");
            alertaDocExistente.setContentText("Ya se ha adjuntado ese documento anteriormente.");
            alertaDocExistente.showAndWait();  
        }
    }

    @FXML
    private void cancelarDocumento(ActionEvent event) {
        Stage stage = (Stage) tfAportador.getScene().getWindow();
        stage.close();
    }
    
    public void setSession(Session session) {
        this.session = session;
    }

    public void setCodigoExpediente(int codigoExpediente) {
        this.codigoExpediente = codigoExpediente;
    }
}
