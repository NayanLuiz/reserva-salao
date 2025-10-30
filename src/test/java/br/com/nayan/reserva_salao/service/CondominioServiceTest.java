package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.CondominioRequestDTO;
import br.com.nayan.reserva_salao.dto.CondominioResponseDTO;
import br.com.nayan.reserva_salao.entity.CasaEntity;
import br.com.nayan.reserva_salao.entity.CondominioEntity;
import br.com.nayan.reserva_salao.entity.SalaoEntity;
import br.com.nayan.reserva_salao.repository.CasaRepository;
import br.com.nayan.reserva_salao.repository.CondominioRepository;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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

        CasaEntity casaEntity = buildCasa();
        SalaoEntity salao = buildSalao();
        CondominioEntity condominioEntity = buildCondominio();

        when(condominioRepository.existsByNome(condominioRequestDTO.getNome()))
                .thenReturn(false);
        when(condominioRepository.save(any())).thenReturn(condominioEntity);

        CondominioResponseDTO condominioResponseDTO = condominioService.create(condominioRequestDTO);

        assertThat(condominioResponseDTO).isNotNull();
        assertThat(condominioResponseDTO.getId()).isEqualTo(condominioEntity.getId());

        verify(condominioRepository,times(1)).save(any());
    }

    @Test
    @DisplayName("Crate a condominium without name")
    void createCondominiumWithouName() {

        CondominioRequestDTO condominioRequestDTO = buildCondominioDTO();

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

        CondominioEntity pedidoCondominioEntity = buildCondominio();
        pedidoCondominioEntity.setSalao(List.of(buildSalao()));
        pedidoCondominioEntity.setCasaEntity(List.of(buildCasa()));

        when(condominioRepository.findById(pedidoCondominioEntity.getId()))
                .thenReturn(Optional.of(pedidoCondominioEntity));

        condominioService.getById(1L);

//        verify(condominioRepository,times(1)).findById(any());

    }

    @Test
    @DisplayName("Get condominium by name successfully")
    void getByNameSucessfuly() {

        CondominioEntity pedidoCondominioEntity = buildCondominio();

        when(condominioRepository.findByNome(pedidoCondominioEntity.getNome()))
                .thenReturn(Optional.of(pedidoCondominioEntity));

        CondominioEntity condominioEntity = condominioService.getByName(pedidoCondominioEntity.getNome());

        assertThat(condominioEntity).isNotNull();
        assertThat(condominioEntity.getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("Delete condominium by id successfully")
    void deleteCondominioById() {

        CondominioEntity condominioEntity = buildCondominio();

        condominioService.deleteCondominioById(condominioEntity.getId());

        verify(condominioRepository,times(1)).deleteById(any());
    }

    private CondominioRequestDTO buildCondominioDTO() {
        CondominioRequestDTO condominioRequestDTO = new CondominioRequestDTO();
        condominioRequestDTO.setNome(buildCondominio().getNome());

        return condominioRequestDTO;
    }

    private CondominioEntity buildCondominio() {
        CondominioEntity condominioEntity = new CondominioEntity();
        condominioEntity.setId(1L);
        condominioEntity.setNome("Condominio A");
        condominioEntity.setSalao(List.of());
        condominioEntity.setCasaEntity(List.of());

        return condominioEntity;
    }

    private SalaoEntity buildSalao(){
        SalaoEntity salao = new SalaoEntity();
        salao.setId(1L);
        salao.setArea("Salao Principal");
        salao.setCondominioEntity(buildCondominio());

        return salao;
    }

    private CasaEntity buildCasa(){
        CasaEntity casaEntity = new CasaEntity();
        casaEntity.setId(1L);
        casaEntity.setNumero(101L);
        casaEntity.setResponsavel("Vinicius");
        casaEntity.setCondominioEntity(buildCondominio());

        return casaEntity;
    }
}