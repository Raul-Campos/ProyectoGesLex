/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import map.Cliente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirClienteController implements Initializable {


    private Session session;
    private SessionFactory sesion;
    @FXML
    private TextField textfieldDni;
    @FXML
    private TextField textfieldFecha;
    @FXML
    private TextField textfieldNombre;
    @FXML
    private ChoiceBox<String> cbSitFam;
    @FXML
    private ChoiceBox<String> cbSitLab;
    @FXML
    private RadioButton radiobH;
    @FXML
    private RadioButton radiobM;
    @FXML
    private Button btnEnviar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField textfieldApellidos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbSitFam.getItems().addAll("Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a");
        cbSitLab.getItems().addAll("Empleado/a", "Desempleado/a");
    }


    public void setSession(Session session) {
        this.session = session;

    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    @FXML
    private void btnEnviar(ActionEvent event) {
        Cliente cliente = new Cliente();
        //falta control de errores
        
        cliente.setDni(textfieldDni.getText());
        cliente.setApellidos(textfieldApellidos.getText());
        cliente.setFechaNacimiento(StringToDate(textfieldFecha.getText()));
        cliente.setNombre(textfieldNombre.getText());
        if (radiobH.isSelected()) {
            cliente.setSexo("Hombre");
        } else if (radiobM.isSelected()) {
            cliente.setSexo("Mujer");
        }
        cliente.setSituacionFamiliar(cbSitFam.getValue());
        cliente.setSituacionLaboral(cbSitLab.getValue());

        Transaction tx = session.getTransaction();

        tx.begin();
        session.save(cliente);
        tx.commit();

        Stage stage = (Stage) btnEnviar.getScene().getWindow();
        stage.close();
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
    private void btnLimpiar(ActionEvent event) {
        textfieldDni.setText("");
        textfieldFecha.setText("");
        textfieldNombre.setText("");
        radiobH.setSelected(false);
        radiobM.setSelected(false);
        cbSitFam.setValue(null);
        cbSitLab.setValue(null);

    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
