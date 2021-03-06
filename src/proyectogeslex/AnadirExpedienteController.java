/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import map.Cliente;
import map.Expediente;
import map.Letrado;
import map.Procurador;
import org.hibernate.NonUniqueObjectException;

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
    private Text labelHoja;
    @FXML
    private Button btnSelecionarFic;
    private File hoja;
    
    private Expediente existente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Calendar c = Calendar.getInstance();
        tfFechaC.setText(Integer.toString(c.get(Calendar.YEAR)) + "-" + Integer.toString(c.get(Calendar.MONTH) + 1) + "-" + Integer.toString(c.get(Calendar.DATE)));
        labelHoja.setText("");
    }

    @FXML
    private void limpiarExpediente(ActionEvent event) {
        chCliente.setValue(null);
        chLetrado.setValue(null);
        chProcurador.setValue(null);
        tfFechaC.setText("");
    }

    @FXML
    private void AceptarExpediente(ActionEvent event) throws IOException {
        
        Expediente expediente = new Expediente();
        if(existente!=null)
            expediente=existente;
        
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

        if (!tfFechaC.getText().equals("") && validarFecha(tfFechaC.getText())) {
            java.sql.Date fecha = java.sql.Date.valueOf(tfFechaC.getText());
            expediente.setFechaCreacion(fecha);
        } else {
            datosRellenos = false;
            Alert fec = new Alert(Alert.AlertType.INFORMATION);
                fec.setHeaderText("Error Formato fecha");
                fec.setContentText("Error en el formato de fecha. Por favor, intentelo de nuevo de la siguiente manera: yyyy-MM-dd");
                fec.showAndWait();
        }

        if (hoja != null) {
            byte[] pdf = Files.readAllBytes(hoja.toPath());
            expediente.setHoja(pdf);
   
        }
        expediente.setEstadoHoja(expediente.estadoHoja());
        if (datosRellenos) {
            Transaction tx = session.getTransaction();
            try {

                if (existente == null) {
                    tx.begin();
                    session.save(expediente);
                    tx.commit();
                } else {
                    tx.begin();
                    session.update(expediente);
                    tx.commit();
                }

                Stage stage = (Stage) btnAceptar.getScene().getWindow();
                stage.close();
            } catch (NullPointerException ex) {
                tx.rollback();
                Alert alertaNuevoExp = new Alert(Alert.AlertType.INFORMATION);
                alertaNuevoExp.setHeaderText("Error al añadir Expediente.");
                alertaNuevoExp.setContentText("Error al guardar los datos del Expediente. Por favor, intentelo de nuevo.");
                alertaNuevoExp.showAndWait();
            } catch (NonUniqueObjectException ex) {
                tx.rollback();
                Alert alertaExpExistente = new Alert(Alert.AlertType.INFORMATION);
                alertaExpExistente.setHeaderText("Expediente existente");
                alertaExpExistente.setContentText("Ya se ha añadido ese expediente anteriormente.");
                alertaExpExistente.showAndWait();
            }
        } else {
            Alert alertaNuevoExp = new Alert(Alert.AlertType.INFORMATION);
            alertaNuevoExp.setHeaderText("Campos incompletos");
            alertaNuevoExp.setContentText("Porfavor rellene todos los campos para crear el expediente.");
            alertaNuevoExp.showAndWait();
        }
    }

    private static boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyy-MM-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
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

    public void setExistente(Expediente existente) {
        this.existente = existente;
    }

    @FXML
    private void EditarFecha(ActionEvent event) {
        if (chbFecha.isSelected()) {
            tfFechaC.setDisable(false);
        } else {
            tfFechaC.setDisable(true);
        }
    }

    public void cargaDato(){
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

    @FXML
    private void SeleccionarFichero(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        hoja = chooser.showOpenDialog(new Stage());
        labelHoja.setText(hoja.getAbsolutePath());
    }

    public void cargarDatosExp() {
        if (existente != null) {
            //Muestra los datos
            chCliente.setValue(existente.getCliente().toString());
            chLetrado.setValue(existente.getLetrado().toString());
            tfFechaC.setText(existente.getFechaCreacion().toString());
            chProcurador.setValue(existente.getProcurador().toString());
            /*if (existente.getHoja() != null) {
                labelHoja.setText(Arrays.toString(existente.getHoja()));
                writeByte(existente.getHoja());
            }*/

        }
    }

    public void writeByte(byte[] bytes) {
        try {

            // Initialize a pointer 
            // in file using OutputStream 
            OutputStream os
                    = new FileOutputStream(hoja);

            // Starts writing the bytes in it 
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file 
            os.close();
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }
}
