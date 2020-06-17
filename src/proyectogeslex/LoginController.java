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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import map.Usuarios;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
    private Session session;
    private SessionFactory sesion;
    private String emailUser;
    private String emailPassword;
    @FXML
    private AnchorPane anchorGeneral;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void registrarse(ActionEvent event) throws IOException {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CrearCuenta.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registrarse");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("recursos/logo.png"));
        //stage.setResizable(false);

        stage.show();

       CrearCuentaController crearCuenta = (CrearCuentaController) fxmlLoader.getController();
        crearCuenta.setSesion(sesion);
        crearCuenta.setSession(session);
    }

    @FXML
    private void entrar(ActionEvent event) throws IOException {

        //Comprueba si existe el usuario
        Usuarios usuario = (Usuarios) session.createQuery("from Usuarios where nombre = ?").setParameter(0, tfusuario.getText()).uniqueResult();

        if (usuario != null && tfcontrasena.getText().equals(usuario.getContrasena())) {

            //Carga Menú
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuPrincipal2.fxml"));
            Parent root = fxmlLoader.load();
            MenuPrincipal2Controller controladorMenu = (MenuPrincipal2Controller) fxmlLoader.getController();
            
            controladorMenu.setSesion(sesion);
            controladorMenu.setSession(session);
            controladorMenu.setEmailUser(emailUser);
            controladorMenu.setEmailPassword(emailPassword);
            controladorMenu.setUser(usuario);
            controladorMenu.setLabelUser(usuario.getNombreper()+",");

          
            //Muestra menú
            Stage menu = new Stage(); 
            menu.setScene(new Scene(root));
            menu.setMaximized(true);
            menu.setTitle("GesLex");    
            menu.getIcons().add(new Image("recursos/logo.png"));
            menu.show();
            
            menu.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    session.close();
                    sesion.close();
                    menu.close();
                }
            });
            
            //Cierra login
            Stage actual = (Stage) tfusuario.getScene().getWindow();
            actual.close();

        }else{
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText("Error al iniciar sesión");
            alerta.setContentText("Usuario o contraseña erróneos.");
            alerta.showAndWait();
        }
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    @FXML
    private void CapturarEnter(KeyEvent event) throws IOException {
       if (event.getCode().equals(KeyCode.ENTER))
       {
           Usuarios usuario = (Usuarios) session.createQuery("from Usuarios where nombre = ?").setParameter(0, tfusuario.getText()).uniqueResult();

        if (usuario != null && tfcontrasena.getText().equals(usuario.getContrasena())) {

            //Carga Menú
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuPrincipal2.fxml"));
            Parent root = fxmlLoader.load();
            MenuPrincipal2Controller controladorMenu = (MenuPrincipal2Controller) fxmlLoader.getController();
            
            controladorMenu.setSesion(sesion);
            controladorMenu.setSession(session);
            controladorMenu.setEmailUser(emailUser);
            controladorMenu.setEmailPassword(emailPassword);
            controladorMenu.setUser(usuario);
            controladorMenu.setLabelUser(usuario.getNombreper()+",");
            
            //Muestra menú
            Stage menu = new Stage();
            menu.setScene(new Scene(root, 1200, 775));
            menu.setTitle("GesLex");
            menu.getIcons().add(new Image("recursos/logo.png"));
            menu.show();
            
            menu.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    session.close();
                    sesion.close();
                    menu.close();
                }
            });
            
            //Cierra login
            Stage actual = (Stage) tfusuario.getScene().getWindow();
            actual.close();

        }else{
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText("Error al iniciar sesión");
            alerta.setContentText("Usuario o contraseña erróneos.");
            alerta.showAndWait();
        }
       }
    }
    
    
}
