package br.com.nayan.reserva_salao.view;

import br.com.nayan.reserva_salao.dto.ReservaRequestDTO;
import br.com.nayan.reserva_salao.dto.ReservaResponseDTO;
import br.com.nayan.reserva_salao.service.ReservaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Controller
@RequiredArgsConstructor
public class ReservaViewController implements Initializable {

    private final ReservaService reservaService;

    // filtros
    @FXML private TextField filtroCondominio;
    @FXML private TextField filtroSalao;
    @FXML private DatePicker filtroData;

    // campos do formulário
    @FXML private TextField txtNomeCondominio;
    @FXML private TextField txtNomeSalao;
    @FXML private TextField txtNumeroCasa;
    @FXML private DatePicker dpDataReserva;

    // tabela e colunas
    @FXML private TableView<ReservaResponseDTO> tabelaReservas;
    @FXML private TableColumn<ReservaResponseDTO, Long> colId;
    @FXML private TableColumn<ReservaResponseDTO, String> colCondominio;
    @FXML private TableColumn<ReservaResponseDTO, String> colSalao;
    @FXML private TableColumn<ReservaResponseDTO, Long> colCasa;
    @FXML private TableColumn<ReservaResponseDTO, LocalDate> colData;

    private final ObservableList<ReservaResponseDTO> dadosMaster = FXCollections.observableArrayList();
    private Long idParaEditar = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabela();
        carregarReservas();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCondominio.setCellValueFactory(new PropertyValueFactory<>("condominio"));
        colSalao.setCellValueFactory(new PropertyValueFactory<>("salao"));
        colCasa.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabelaReservas.setItems(dadosMaster);
    }

    private void carregarReservas() {
        try {
            dadosMaster.clear();
            List<ReservaResponseDTO> lista = reservaService.findAll();
            dadosMaster.addAll(lista);
        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao carregar reservas: " + e.getMessage());
        }
    }

    @FXML
    private void limparFiltros() {
        filtroCondominio.clear();
        filtroSalao.clear();
        filtroData.setValue(null);
        carregarReservas();
    }

    @FXML
    private void limparCampos() {
        txtNumeroCasa.clear();
        txtNomeCondominio.clear();
        txtNomeSalao.clear();
        dpDataReserva.setValue(null);
        idParaEditar = null;
    }

    @FXML
    private void onSalvar() {
        try {
            ReservaRequestDTO dto = new ReservaRequestDTO();
            dto.setNumero(Long.parseLong(txtNumeroCasa.getText()));
            dto.setCondominio(txtNomeCondominio.getText());
            dto.setSalao(txtNomeSalao.getText());
            dto.setData(dpDataReserva.getValue());

            reservaService.create(dto);
            carregarReservas();
            limparCampos();
            mostrarAlerta("Sucesso", "Reserva criada!");
        } catch (NumberFormatException nfe) {
            mostrarAlerta("Erro", "Número da casa inválido.");
        } catch (Exception ex) {
            mostrarAlerta("Erro", ex.getMessage());
        }
    }

    @FXML
    private void onEditar() {
        ReservaResponseDTO selecionada = tabelaReservas.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma reserva para editar.");
            return;
        }
        idParaEditar = selecionada.getId();
        txtNumeroCasa.setText(String.valueOf(selecionada.getNumero()));
        txtNomeCondominio.setText(selecionada.getCondominio());
        txtNomeSalao.setText(selecionada.getSalao());
        dpDataReserva.setValue(selecionada.getData());
    }

    @FXML
    private void onExcluir() {
        ReservaResponseDTO selecionada = tabelaReservas.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma reserva para excluir.");
            return;
        }
        reservaService.delete(selecionada.getId());
        carregarReservas();
        mostrarAlerta("Sucesso", "Reserva excluída!");
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
