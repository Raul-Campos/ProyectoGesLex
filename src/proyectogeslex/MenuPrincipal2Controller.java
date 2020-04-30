/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class MenuPrincipal2Controller implements Initializable {

    @FXML
    private TitledPane TPaneClientes;
    @FXML
    private Button btnBuscarC;
    @FXML
    private TitledPane TPaneLetrados;
    @FXML
    private TitledPane TPaneExpedientes;
    @FXML
    private Button btnVerExp;
    @FXML
    private Button btnPeritos;
    @FXML
    private Button btnSentencias;
    @FXML
    private Button btnDocumentos;
    @FXML
    private Button btnEncargo;
    @FXML
    private Button btnIncidentes;
    @FXML
    private TitledPane TPaneVehiculos;
    @FXML
    private TitledPane TPaneAvisos;
    @FXML
    private TitledPane TPaneConfiguracion;
    private Session session;
    private SessionFactory sesion;
    @FXML
    private BorderPane principal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BuscarCliente(ActionEvent event) throws IOException {
        
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerClientes.fxml"));
          BorderPane verClientes = fxmlLoader.load();
          VerClientesController controladorClientes = (VerClientesController) fxmlLoader.getController();
          
          controladorClientes.setSesion(sesion);
          controladorClientes.setSession(session);
          
          principal.setCenter(verClientes);
    }

    @FXML
    private void VerExp(ActionEvent event) {
    }

    @FXML
    private void Peritos(ActionEvent event) {
    }

    @FXML
    private void Sentencias(ActionEvent event) {
    }

    @FXML
    private void Documentos(ActionEvent event) {
    }

    @FXML
    private void HojaEncargo(ActionEvent event) {
    }

    @FXML
    private void Incidentes(ActionEvent event) {
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }
    
    
    
}
