package br.com.nayan.reserva_salao.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MenuViewController {

    @FXML
    private BorderPane painelPrincipal;

    //  contexto do Spring para carregar as outras telas com injeção de dependência
    private final ApplicationContext context;

    public MenuViewController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void initialize() {
        // abre a Home assim que o programa inicia
        onAbrirHome();
    }

    @FXML
    public void onAbrirHome() {
        carregarTela("/home.fxml");
    }

    @FXML
    public void onAbrirReservas() {
        carregarTela("/reserva.fxml");
    }

    private void carregarTela(String caminhoFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFxml));
            loader.setControllerFactory(context::getBean); // O Pulo do Gato do Spring
            Parent tela = loader.load();
            painelPrincipal.setCenter(tela);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}