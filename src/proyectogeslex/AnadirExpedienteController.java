/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import map.Cliente;
import map.Letrado;
import map.Procurador;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class AnadirExpedienteController implements Initializable {

    @FXML
    private ChoiceBox<String> chCliente;
    @FXML
    private ChoiceBox<String> chLetrado;
    @FXML
    private ChoiceBox<String> chProcurador;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Calendar c = Calendar.getInstance();
        tfFechaC.setText(Integer.toString(c.get(Calendar.DATE))+"/"+Integer.toString(c.get(Calendar.MONTH))+"/"+Integer.toString(c.get(Calendar.YEAR)));
        
        
       
       
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
        
        
        Transaction tx = session.getTransaction();

        tx.begin();
      //  session.merge();
        tx.commit();

        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.close();
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
        if(chbFecha.isSelected())
            tfFechaC.setDisable(false);
        else
            tfFechaC.setDisable(true);
    }
    
    @FXML
    private void cargarChoices(MouseEvent event) {
        
        List<Letrado> letrados=null;
        List<Procurador> procuradores;
        
        letrados.clear();
        Query consulta;
       
        
        consulta = session.createQuery("from Letrado");
        letrados = consulta.list();
        letrados.forEach((letrado) -> {
            chLetrado.getItems().add(letrado.getDniLetrado()+"  "+letrado.getApellidos()+","+letrado.getNombre());
        });
        
        consulta = session.createQuery("from Procurador");
        procuradores = consulta.list();
        procuradores.forEach((procurador) -> {
            chProcurador.getItems().add(procurador.getDniProcurador()+"  "+procurador.getApellidos()+","+procurador.getNombre());
        });
    }

    @FXML
    private void cargarDatosCliente(MouseEvent event) {
        List<Cliente> clientes;
        Query consulta =session.createQuery("from Cliente");
        clientes = consulta.list();
       
        clientes.forEach((cliente) -> {
            chCliente.getItems().add(cliente.getDni()+"  "+cliente.getApellidos()+","+cliente.getNombre());
        });
    }
}
