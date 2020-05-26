/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Raul
 */
public class ProyectoGesLex extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        //Carga login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = fxmlLoader.load();

        LoginController controladorLogin = (LoginController) fxmlLoader.getController();

        //Crea conexión
        SessionFactory sesion = SessionFactoryUtil.getSessionFactory();
        Session session = sesion.openSession();

        controladorLogin.setSesion(sesion);
        controladorLogin.setSession(session);

        //Comprueba los avisos para ver si hay que enviar
        String email = getParameters().getNamed().get("email");
        String password = getParameters().getNamed().get("password");
        //Thread comprobarAvisos = new Thread(new EnviarAvisos(email, password, session));
        //comprobarAvisos.start();
        
        controladorLogin.setEmailUser(email);
        controladorLogin.setEmailPassword(password);

        //Muestra escena
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
