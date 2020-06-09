/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex.configuracion;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import map.Aviso;
import map.Smtp;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Raul
 */
public class CambioServidorSMTPController implements Initializable {

    @FXML
    private Button btnAceptar;
    @FXML
    private ChoiceBox<String> cbSMTP;
    private Session session;
    @FXML
    private TextField tfHost;
    @FXML
    private TextField tfPuerto;
    @FXML
    private TextField tfEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbSMTP.getItems().addAll("smtp.gmail.com", "mail.gmx.net", "smtp.office365.com");
        
        cbSMTP.setOnAction(e -> {
            if(cbSMTP.getValue() != null){
                mostrarDatos();
            }
        });
    }

    @FXML
    private void aceptar(ActionEvent event) {
        if (cbSMTP.getValue() != null) {

            //Cambia el servidor smtp que se usará y también el correo a usar
            String seleccionado = cbSMTP.getValue();

            Query consulta = session.createQuery("from Smtp");
            List<Smtp> servidores = consulta.list();
            
            

            //Recorre los servidores y pone como seleccionado el elegido
            //en el choicebox y el resto a no seleccionado
            for (Smtp servidorSMTP : servidores) {
                Transaction tx = session.getTransaction();

                if (servidorSMTP.getOpcion().equals("Seleccionado")) {
                    servidorSMTP.setOpcion("No seleccionado");

                    //Guarda los cambios
                    tx.begin();
                    session.update(servidorSMTP);
                    tx.commit();
                }

                if (servidorSMTP.getHost().equals(seleccionado)) {
                    servidorSMTP.setOpcion("Seleccionado");

                    //Guarda los cambios
                    tx.begin();
                    session.update(servidorSMTP);
                    tx.commit();
                }

            }
            
            //Muestra alerta de que se ha realizado el campio con éxito
            Alert alertaCambio = new Alert(Alert.AlertType.INFORMATION);
            alertaCambio.setHeaderText("Cambio realizado");
            alertaCambio.setContentText("Se ha cambiado el servidor SMTP con éxito.");
            alertaCambio.showAndWait();
        }else{
            
            //Muestra alerta de smtp no seleccionado
            Alert alertaChoice = new Alert(Alert.AlertType.INFORMATION);
            alertaChoice.setHeaderText("Servidor SMTP no seleccionado");
            alertaChoice.setContentText("Profavor seleccione el servidor smtp al "
                    + "que desea cambiar.");
            alertaChoice.showAndWait();
        }
    }

    private void mostrarDatos(){
        
        //Busca la opción en la bd
        Query consulta = session.createQuery("from Smtp where host = :valor");
        consulta.setParameter("valor", cbSMTP.getValue());
        Smtp servidor = (Smtp) consulta.uniqueResult();
        
        //Muestra valores
        tfHost.setText(servidor.getHost());
        tfPuerto.setText(servidor.getPuerto());
        tfEmail.setText(servidor.getEmail());
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
