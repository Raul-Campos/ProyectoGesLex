/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogeslex;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
    private Button btnVerNotificaciones;
    @FXML
    private Button btnVerVehiculos;
    private Stage escenario;
    private TabPane actual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void BuscarCliente(ActionEvent event) throws IOException {

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

        controladorClientes.setSesion(sesion);
        controladorClientes.setSession(session);

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

        ajustarVista();
        actualizarTamañoVista();
    }

    @FXML
    private void VerExp(ActionEvent event) {

    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSesion(SessionFactory sesion) {
        this.sesion = sesion;
    }

    @FXML
    private void verNotificaciones(ActionEvent event) {
    }

    @FXML
    private void verVehiculos(ActionEvent event) throws IOException {

        //Carga la vista y controlador
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerVehiculos.fxml"));
        BorderPane verVehiculos = fxmlLoader.load();
        VerVehiculosController controladorClientes = (VerVehiculosController) fxmlLoader.getController();

        //Crea pestaña para cargar vista de clientes
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

    //Actualiza el tamaño de la vistade clientes si cabmia el tamaño de la ventana
    private void ajustarVista() {

        escenario = (Stage) principal.getScene().getWindow();

        //Ajusta el contenido de las pestañas en función del tamaño del escenario
        for (Tab tab : actual.getTabs()) {
            BorderPane contenido = (BorderPane) tab.getContent();
            contenido.setPrefWidth((double) (escenario.getWidth() - btnBuscarClientes.getWidth()));
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
}
