/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnregistrarse;
    @FXML
    private Button btnentrar;
    @FXML
    private TextField tfusuario;
    @FXML
    private PasswordField tfcontrasena;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registrarse(ActionEvent event) {
    }

    @FXML
    private void entrar(ActionEvent event) {
    }
    
}
