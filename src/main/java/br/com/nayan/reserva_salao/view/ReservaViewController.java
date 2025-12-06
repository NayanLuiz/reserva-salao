package br.com.nayan.reserva_salao.view;

import br.com.nayan.reserva_salao.dto.ReservaRequestDTO;
import br.com.nayan.reserva_salao.dto.ReservaResponseDTO;
import br.com.nayan.reserva_salao.service.ReservaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class ReservaViewController {

    @Autowired
    private ReservaService reservaService;

    // campos do formulário
    @FXML private TextField txtNumeroCasa;
    @FXML private TextField txtNomeCondominio;
    @FXML private TextField txtNomeSalao;
    @FXML private DatePicker dpDataReserva;

    // vriável para controlar edição (se null, é novo cadastro)
    private Long idParaEditar = null;

    // campos de Filtro
    @FXML private TextField filtroCondominio;
    @FXML private TextField filtroSalao;
    @FXML private DatePicker filtroData;

    // tabela
    @FXML private TableView<ReservaResponseDTO> tabelaReservas;
    @FXML private TableColumn<ReservaResponseDTO, Long> colId;
    @FXML private TableColumn<ReservaResponseDTO, Long> colCasa;
    @FXML private TableColumn<ReservaResponseDTO, String> colCondominio;
    @FXML private TableColumn<ReservaResponseDTO, String> colSalao;
    @FXML private TableColumn<ReservaResponseDTO, LocalDate> colData;

    // listas para a tabela
    private ObservableList<ReservaResponseDTO> dadosMaster = FXCollections.observableArrayList();
    private FilteredList<ReservaResponseDTO> dadosFiltrados;

    @FXML
    public void initialize() {
        configurarColunas();

        // inicializa a lista filtrada
        dadosFiltrados = new FilteredList<>(dadosMaster, p -> true);
        tabelaReservas.setItems(dadosFiltrados);

        // add listeners para os filtros (filtra enquanto digita)
        filtroCondominio.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        filtroSalao.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        filtroData.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());

        // carrega dados iniciais (simulado ou real se tiver o getAll no service)
        // carregarDadosDoBanco();
    }

    private void configurarColunas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCasa.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colCondominio.setCellValueFactory(new PropertyValueFactory<>("condominio"));
        colSalao.setCellValueFactory(new PropertyValueFactory<>("salao"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
    }

    // lógica CRUD

    @FXML
    public void onSalvar() {
        try {
            ReservaRequestDTO dto = new ReservaRequestDTO();
            dto.setNumero(Long.parseLong(txtNumeroCasa.getText()));
            dto.setCondominio(txtNomeCondominio.getText());
            dto.setSalao(txtNomeSalao.getText());
            dto.setData(dpDataReserva.getValue());

            if (idParaEditar == null) {
                // criar
                ReservaResponseDTO salvo = reservaService.create(dto);
                dadosMaster.add(salvo);
                mostrarAlerta("Sucesso", "Reserva criada!");
            } else {
                // alterar
                // como o service original não tem update pronto, tipo (deletar + criar)
                // era bom ter um metodo update no service
                reservaService.deleteReservaById(idParaEditar);
                ReservaResponseDTO atualizado = reservaService.create(dto);

                // atualiza visualmente: remove o antigo, põe o novo
                dadosMaster.removeIf(r -> r.getId().equals(idParaEditar));
                dadosMaster.add(atualizado);

                mostrarAlerta("Sucesso", "Reserva atualizada!");
                idParaEditar = null; // volta pra o modo criação
            }
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro", e.getMessage());
        }
    }

    @FXML
    public void onEditar() {
        ReservaResponseDTO selecionada = tabelaReservas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            // joga os dados da tabela para os campos
            txtNumeroCasa.setText(String.valueOf(selecionada.getNumero()));
            txtNomeCondominio.setText(selecionada.getCondominio());
            txtNomeSalao.setText(selecionada.getSalao());
            dpDataReserva.setValue(selecionada.getData());

            // marca o ID para saber que é uma edição
            idParaEditar = selecionada.getId();
        } else {
            mostrarAlerta("Atenção", "Selecione uma reserva para editar.");
        }
    }

    @FXML
    public void onExcluir() {
        ReservaResponseDTO selecionada = tabelaReservas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            reservaService.deleteReservaById(selecionada.getId());
            dadosMaster.remove(selecionada);
        }
    }

    // lógica de Filtros
    private void aplicarFiltros() {
        dadosFiltrados.setPredicate(reserva -> {
            // se não houver filtros, mostra tudo
            if (reserva == null) return false;

            String condFilter = filtroCondominio.getText().toLowerCase();
            String salaoFilter = filtroSalao.getText().toLowerCase();
            LocalDate dataFilter = filtroData.getValue();

            boolean matchesCondominio = condFilter.isEmpty() || reserva.getCondominio().toLowerCase().contains(condFilter);
            boolean matchesSalao = salaoFilter.isEmpty() || reserva.getSalao().toLowerCase().contains(salaoFilter);
            boolean matchesData = dataFilter == null || Objects.equals(reserva.getData(), dataFilter);

            // retorna verdadeiro apenas se atender aos 3 critérios
            return matchesCondominio && matchesSalao && matchesData;
        });
    }

    @FXML
    public void limparFiltros() {
        filtroCondominio.clear();
        filtroSalao.clear();
        filtroData.setValue(null);
    }

    @FXML
    public void limparCampos() {
        txtNumeroCasa.clear();
        txtNomeCondominio.clear();
        txtNomeSalao.clear();
        dpDataReserva.setValue(null);
        idParaEditar = null;
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}