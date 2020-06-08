/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import map.Usuarios;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import proyectogeslex.configuracion.CambioContrasenaController;
import proyectogeslex.configuracion.CambioServidorSMTPController;
import proyectogeslex.configuracion.CambioUsuarioController;

/**
 * FXML Controller class
 *
 * @author Jose Carlos PC
 */
public class MenuPrincipal2Controller implements Initializable {

    @FXML
    private Button btnVerExp;
    private Session session;
    private SessionFactory sesion;
    @FXML
    private BorderPane principal;
    @FXML
    private ImageView imagenPersonas;
    @FXML
    private ImageView imagenExpedientes;
    @FXML
    private Button btnBuscarClientes;
    @FXML
    private Button btnVerVehiculos;
    private Stage escenario;
    private TabPane actual;
    private String emailUser;
    private String emailPassword;
    private Usuarios user;
    @FXML
    private Button btnVerConfiguracion;
    @FXML
    public Text labelUser;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              

    }

    @FXML
    private void verActores(ActionEvent event) throws IOException {

        //Carga las vista y controlador
        FXMLLoader fxmlLoaderClientes = new FXMLLoader(getClass().getResource("VerClientes.fxml"));
        BorderPane verClientes = fxmlLoaderClientes.load();
        VerClientesController controladorClientes = (VerClientesController) fxmlLoaderClientes.getController();

        FXMLLoader fxmlLoaderLetrado = new FXMLLoader(getClass().getResource("VerLetrado.fxml"));
        BorderPane verLetrados = fxmlLoaderLetrado.load();
        VerLetradoController controladorLetrados = (VerLetradoController) fxmlLoaderLetrado.getController();

        FXMLLoader fxmlLoaderPeritos = new FXMLLoader(getClass().getResource("VerPerito.fxml"));
        BorderPane verPeritos = fxmlLoaderPeritos.load();
        VerPeritoController controladorPeritos = (VerPeritoController) fxmlLoaderPeritos.getController();

        FXMLLoader fxmlLoaderProcuradores = new FXMLLoader(getClass().getResource("VerProcurador.fxml"));
        BorderPane verProcuradores = fxmlLoaderProcuradores.load();
        VerProcuradorController controladorProcuradoes = (VerProcuradorController) fxmlLoaderProcuradores.getController();

        //Pasa la sesión al resto de vistas
        controladorClientes.setSesion(sesion);
        controladorClientes.setSession(session);
        controladorProcuradoes.setSession(session);
        controladorProcuradoes.setSesion(sesion);
        controladorLetrados.setSession(session);
        controladorLetrados.setSesion(sesion);
        controladorPeritos.setSession(session);
        controladorPeritos.setSesion(sesion);

        //Crea pestañas para cargar vista de actores
        TabPane tabPane = new TabPane();
        Tab tabClientes = new Tab();
        Tab tabLetrados = new Tab();
        Tab tabPeritos = new Tab();
        Tab tabProcuradores = new Tab();
        
        //Añade todas las tabs
        tabPane.getTabs().addAll(tabClientes, tabLetrados, tabPeritos, tabProcuradores);
        tabClientes.setContent(verClientes);
        tabClientes.setText("Clientes");
        tabLetrados.setContent(verLetrados);
        tabLetrados.setText("Letrados");
        tabPeritos.setContent(verPeritos);
        tabPeritos.setText("Peritos");
        tabProcuradores.setContent(verProcuradores);
        tabProcuradores.setText("Procuradores");

        //tabClientes.getContent().setStyle("-fx-background-color: #FF2D00;");
        //Evita que se cierren las pestañas
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        principal.setCenter(tabPane);
        
        actual = tabPane;
        tabPane.applyCss();
        ajustarVista();
        actualizarTamañoVista();
    }

    @FXML
    private void VerExp(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoaderExpedientes = new FXMLLoader(getClass().getResource("VerExpediente.fxml"));
        BorderPane verExpedientes = fxmlLoaderExpedientes.load();
        VerExpedienteController controladorExpedientes = (VerExpedienteController) fxmlLoaderExpedientes.getController();

        controladorExpedientes.setSession(session);
        controladorExpedientes.setSesion(sesion);
        controladorExpedientes.setEmailPassword(emailPassword);
        controladorExpedientes.setEmailUser(emailUser);

        principal.setCenter(verExpedientes);
        actual = controladorExpedientes.getTabPanePrincipal();

        escenario = (Stage) principal.getScene().getWindow();
        BorderPane contenido = (BorderPane) principal.getCenter();
         
       contenido.setPrefWidth((double) (escenario.getWidth() - btnBuscarClientes.getWidth()));
        contenido.setPrefHeight((double) (escenario.heightProperty().doubleValue()+50.0));
        escenario.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            contenido.setPrefWidth((double) (escenario.getWidth() - btnBuscarClientes.getWidth()));
        });

        escenario.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            contenido.setPrefHeight((double) (escenario.heightProperty().doubleValue() - 50.0));
        });

    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }


    @FXML
    private void verVehiculos(ActionEvent event) throws IOException {

        //Carga la vista y controlador
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerVehiculos.fxml"));
        BorderPane verVehiculos = fxmlLoader.load();
        VerVehiculosController controladorVehiculos = (VerVehiculosController) fxmlLoader.getController();

        controladorVehiculos.setSession(session);

        //Crea pestaña para cargar vista de vehículos
        TabPane tabPane = new TabPane();
        Tab tabVehiculos = new Tab();

        tabPane.getTabs().add(tabVehiculos);
        tabVehiculos.setContent(verVehiculos);
        tabVehiculos.setText("Vehículos");

        //Evita que se cierren las pestañas
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        principal.setCenter(tabPane);

        actual = tabPane;

        ajustarVista();
        actualizarTamañoVista();
    }
    
    @FXML
    private void verConfiguracion(ActionEvent event) throws IOException {
        
        //Carga la vista y controlador de cambio de contraseña
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("configuracion/CambioContrasena.fxml"));
        BorderPane cambioContra = fxmlLoader.load();
        CambioContrasenaController controladorContras = (CambioContrasenaController) fxmlLoader.getController();
        
        //Carga la vista y controlador de cambio de usuario
        FXMLLoader fxmlLoaderUsuario = new FXMLLoader(getClass().getResource("configuracion/CambioUsuario.fxml"));
        BorderPane cambioUsuario = fxmlLoaderUsuario.load();
        CambioUsuarioController controladorUsuarios = (CambioUsuarioController) fxmlLoaderUsuario.getController();
        
        //Carga la vista y controlador de cambio de servidor SMTP
        FXMLLoader fxmlLoaderSMTP = new FXMLLoader(getClass().getResource("configuracion/CambioServidorSMTP.fxml"));
        BorderPane cambioSMTP = fxmlLoaderSMTP.load();
        CambioServidorSMTPController controladorSMTP = (CambioServidorSMTPController) fxmlLoaderSMTP.getController();
        
        controladorContras.setSession(session);
        controladorContras.setUser(user);
        controladorUsuarios.setSession(session);
        controladorUsuarios.setUser(user);
        controladorSMTP.setSession(session);
        
        //Crea pestaña para cargar vista de comnfiguración
        TabPane tabPane = new TabPane();
        Tab tabCambioContras = new Tab();
        Tab tabCambioUsuarios = new Tab();
        Tab tabCambioSMTP = new Tab();

        tabPane.getTabs().addAll(tabCambioContras, tabCambioUsuarios, tabCambioSMTP);
        tabCambioContras.setContent(cambioContra);
        tabCambioContras.setText("Contraseña");
        tabCambioUsuarios.setContent(cambioUsuario);
        tabCambioUsuarios.setText("Usuario");
        tabCambioSMTP.setContent(cambioSMTP);
        tabCambioSMTP.setText("SMTP");

        //Evita que se cierren las pestañas
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        principal.setCenter(tabPane);

        actual = tabPane;
       
        ajustarVista();
        actualizarTamañoVista();
    }

    //Actualiza el tamaño de la vistade clientes si cabmia el tamaño de la ventana
    private void ajustarVista() {

        escenario = (Stage) principal.getScene().getWindow();

        //Ajusta el contenido de las pestañas en función del tamaño del escenario
        for (Tab tab : actual.getTabs()) {
            BorderPane contenido = (BorderPane) tab.getContent();
            contenido.setPrefWidth((double) (escenario.getWidth() - (btnBuscarClientes.getWidth())));
            contenido.setPrefHeight((double) (escenario.heightProperty().doubleValue() - 50.0));
        }
        
    }

    //Actualiza el tamaño de las pestañas si cabmia el tamaño de la ventana
    public void actualizarTamañoVista() {

        escenario = (Stage) principal.getScene().getWindow();

        //Recorre todas las pestañas
        for (Tab tab : actual.getTabs()) {

            BorderPane contenido = (BorderPane) tab.getContent();
            escenario.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    contenido.setPrefWidth((double) (escenario.getWidth() - btnBuscarClientes.getWidth()));
                }
            });

            escenario.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    contenido.setPrefHeight((double) (escenario.heightProperty().doubleValue() - 50.0));
                }
            });
        }
    }
    
    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }
    
    public void setUser(Usuarios user){
        this.user = user;
    }
    public void setLabelUser(String labelUser) {
        this.labelUser.setText(labelUser);
    }


}
