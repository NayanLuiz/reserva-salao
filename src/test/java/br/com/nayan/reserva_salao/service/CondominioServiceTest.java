package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.CondominioRequestDTO;
import br.com.nayan.reserva_salao.dto.CondominioResponseDTO;
import br.com.nayan.reserva_salao.entity.Casa;
import br.com.nayan.reserva_salao.entity.Condominio;
import br.com.nayan.reserva_salao.entity.Salao;
import br.com.nayan.reserva_salao.repository.CasaRepository;
import br.com.nayan.reserva_salao.repository.CondominioRepository;
import br.com.nayan.reserva_salao.repository.ReservaRepository;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CondominioServiceTest {

    @Mock
    private CasaRepository casaRepository;

    @Mock
    private SalaoRepository salaoRepository;

    @Mock
    private CondominioRepository condominioRepository;

    @InjectMocks
    private CondominioService condominioService;


    @Test
    @DisplayName("Crate a condominium sucessfully")
    void createCondominiumSucessfully() {

        CondominioRequestDTO condominioRequestDTO = buildCondominioDTO();

        Casa casa = buildCasa();
        Salao salao = buildSalao();
        Condominio condominio = buildCondominio();

        when(condominioRepository.existsByNome(condominioRequestDTO.getNome()))
                .thenReturn(false);
        when(condominioRepository.save(any())).thenReturn(condominio);

        CondominioResponseDTO condominioResponseDTO = condominioService.create(condominioRequestDTO);

        assertThat(condominioResponseDTO).isNotNull();
        assertThat(condominioResponseDTO.getId()).isEqualTo(condominio.getId());

        verify(condominioRepository,times(1)).save(any());
    }

    @Test
    @DisplayName("Crate a condominium without name")
    void createCondominiumWithouName() {

        CondominioRequestDTO condominioRequestDTO = buildCondominioDTO();

        Casa casa = buildCasa();
        Salao salao = buildSalao();
        Condominio condominio = buildCondominio();
        condominioRequestDTO.setNome(null);

        Throwable throwable = catchThrowable(() -> {
            condominioService.create(condominioRequestDTO);
        });

        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O nome do condominio é obrigatório.");
    }

    @Test
    @DisplayName("Get condominium by id successfully")
    void getByIdSucessfuly() {

        Condominio pedidoCondominio = buildCondominio();
        pedidoCondominio.setSalao(List.of(buildSalao()));
        pedidoCondominio.setCasa(List.of(buildCasa()));

        when(condominioRepository.findById(pedidoCondominio.getId()))
                .thenReturn(Optional.of(pedidoCondominio));

        condominioService.getById(1L);

//        verify(condominioRepository,times(1)).findById(any());

    }

    @Test
    @DisplayName("Get condominium by name successfully")
    void getByNameSucessfuly() {

        Condominio pedidoCondominio = buildCondominio();

        when(condominioRepository.findByNome(pedidoCondominio.getNome()))
                .thenReturn(Optional.of(pedidoCondominio));

        Condominio condominio = condominioService.getByName(pedidoCondominio.getNome());

        assertThat(condominio).isNotNull();
        assertThat(condominio.getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("Delete condominium by id successfully")
    void deleteCondominioById() {

        Condominio condominio = buildCondominio();

        condominioService.deleteCondominioById(condominio.getId());

        verify(condominioRepository,times(1)).deleteById(any());
    }

    private CondominioRequestDTO buildCondominioDTO() {
        CondominioRequestDTO condominioRequestDTO = new CondominioRequestDTO();
        condominioRequestDTO.setNome(buildCondominio().getNome());

        return condominioRequestDTO;
    }

    private Condominio buildCondominio() {
        Condominio condominio = new Condominio();
        condominio.setId(1L);
        condominio.setNome("Condominio A");
        condominio.setSalao(List.of());
        condominio.setCasa(List.of());

        return condominio;
    }

    private Salao buildSalao(){
        Salao salao = new Salao();
        salao.setId(1L);
        salao.setArea("Salao Principal");
        salao.setCondominio(buildCondominio());

        return salao;
    }

    private Casa buildCasa(){
        Casa casa = new Casa();
        casa.setId(1L);
        casa.setNumero(101L);
        casa.setResponsavel("Vinicius");
        casa.setCondominio(buildCondominio());

        return casa;
    }
}