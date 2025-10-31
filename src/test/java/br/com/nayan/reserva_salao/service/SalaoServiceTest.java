package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.SalaoRequestDTO;
import br.com.nayan.reserva_salao.dto.SalaoResponseDTO;
import br.com.nayan.reserva_salao.entity.CondominioEntity;
import br.com.nayan.reserva_salao.entity.SalaoEntity;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaoServiceTest {

    @Mock
    private CondominioService condominioService;

    @Mock
    private SalaoRepository salaoRepository;

    @InjectMocks
    private SalaoService salaoService;

    @Test
    @DisplayName("Create a saloon sucessfuly")
    void createSaloonSucessfuly() {

        SalaoRequestDTO salaoRequestDTO = buildSalaoDTO();
        SalaoEntity salaoEntity = buildSalao();
        CondominioEntity condominioEntity = buildCondominio();

        when(condominioService.getByName(salaoRequestDTO.getCondominio()))
                .thenReturn(condominioEntity);
        when(salaoRepository.save(any()))
                .thenReturn(salaoEntity);

        SalaoResponseDTO salaoResponseDTO = salaoService.create(salaoRequestDTO);

        assertThat(salaoResponseDTO).isNotNull();
        assertThat(salaoResponseDTO.getId()).isEqualTo(salaoEntity.getId());
    }

    @Test
    @DisplayName("Create a saloon without area")
    void createSaloonWithoutArea() {

        SalaoRequestDTO salaoRequestDTO = buildSalaoDTO();

        salaoRequestDTO.setArea(null);

        Throwable throwable = catchThrowable(() -> {
            salaoService.create(salaoRequestDTO);
        });

        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A área do salão não pode ser nula ou vazia");
    }

    @Test
    @DisplayName("Create a saloon without condominium")
    void createSaloonWithoutCondominium() {

        SalaoRequestDTO salaoRequestDTO = buildSalaoDTO();

        salaoRequestDTO.setCondominio(null);

        Throwable throwable = catchThrowable(() -> {
            salaoService.create(salaoRequestDTO);
        });

        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O salão deve estar associado a um condomínio");
    }

    @Test
    void getById() {

        SalaoEntity novoSalaoEntity = buildSalao();

        novoSalaoEntity.setArea("Churrasqueira");
        novoSalaoEntity.setCondominioEntity(buildCondominio());

        when(salaoRepository.findById(novoSalaoEntity.getId()))
                .thenReturn(Optional.of(novoSalaoEntity));

        salaoService.getById(1L);
    }

    @Test
    void deleteSalaoById() {

        SalaoEntity salao = buildSalao();

        salaoService.deleteSalaoById(salao.getId());

        verify(salaoRepository,times(1)).deleteById(any());
    }

    private SalaoRequestDTO buildSalaoDTO() {
        SalaoRequestDTO salaoRequestDTO = new SalaoRequestDTO();
        salaoRequestDTO.setArea(buildSalao().getArea());
        salaoRequestDTO.setCondominio("Residencial Planalto I");

        return salaoRequestDTO;
    }

    private SalaoEntity buildSalao(){
        SalaoEntity salaoEntity = new SalaoEntity();
        salaoEntity.setId(1L);
        salaoEntity.setArea("Churrasqueira");
        salaoEntity.setCondominioEntity(buildCondominio());

        return salaoEntity;
    }

    private CondominioEntity buildCondominio(){
        CondominioEntity condominioEntity = new CondominioEntity();
        condominioEntity.setNome("Residencial Planalto I");

        return condominioEntity;
    }
}