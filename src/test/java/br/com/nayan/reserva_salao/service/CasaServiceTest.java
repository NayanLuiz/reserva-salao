package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.CasaRequestDTO;
import br.com.nayan.reserva_salao.dto.CasaResponseDTO;
import br.com.nayan.reserva_salao.entity.CasaEntity;
import br.com.nayan.reserva_salao.entity.CondominioEntity;
import br.com.nayan.reserva_salao.repository.CasaRepository;
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
class CasaServiceTest {

    @Mock
    private CasaRepository casaRepository;

    @Mock
    private CondominioService condominioService;

    @InjectMocks
    private CasaService casaService;


    @Test
    @DisplayName("Create a house sucessfuly")
    void createHouseSucessfuly(){

        CasaRequestDTO casaRequestDTO = buildCasaDTO ();

        CondominioEntity condominioEntity = buildCondominio();
        CasaEntity casaEntity = buildCasa();

        when(casaRepository.existsByNumero(casaRequestDTO.getNumero()))
                .thenReturn(false);
        when(condominioService.getByName(casaRequestDTO.getCondominioNome()))
                .thenReturn(condominioEntity);
        when(casaRepository.save(any()))
                .thenReturn(casaEntity);

        CasaResponseDTO casaResponseDTO = casaService.create(casaRequestDTO);

        assertThat(casaResponseDTO).isNotNull();
        assertThat(casaResponseDTO.getId()).isEqualTo(casaEntity.getId());

    }

    @Test
    @DisplayName("When a house doesn't have a number")
    void createHouseWithouNumber(){

        CasaRequestDTO casaRequestDTO = buildCasaDTO();

        when(casaRepository.existsByNumero(casaRequestDTO.getNumero()))
                .thenReturn(true);

        Throwable throwable = catchThrowable(() -> {
            casaService.create(casaRequestDTO);
        });

        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(  "Casa com esse numero ja existe.");

    }

    @Test
    @DisplayName("When a house is created without a responsable")
    void createHouseWithouResponsable(){

        CasaRequestDTO casaRequestDTO = buildCasaDTO();
        casaRequestDTO.setResponsavel(null);

        Throwable throwable = catchThrowable(() -> {
            casaService.create(casaRequestDTO);
        });

        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(  "A casa deve estar associada a um responsavel");
    }

    @Test
    @DisplayName("When a house is created without a Condominium")
    void createHouseWithouCondominium(){

        CasaRequestDTO casaRequestDTO = buildCasaDTO();
        casaRequestDTO.setCondominioNome(null);

        Throwable throwable = catchThrowable(() -> {
            casaService.create(casaRequestDTO);
        });

        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(  "A casa deve estar associada a um condominio");
    }

    @Test
    @DisplayName("When a house is created with number's house already exist")
    void createHouseWithNumberExistAlready(){

        CasaRequestDTO casaRequestDTO = buildCasaDTO();

        when(casaRepository.existsByNumero(casaRequestDTO.getNumero()))
                .thenReturn(true);

        Throwable throwable = catchThrowable(() -> {
            casaService.create(casaRequestDTO);
        });

        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Casa com esse numero ja existe.");
    }

    @Test
    @DisplayName("Get a house by Id sucessfuly")
    void getHouseByIdSucessfuly() {

        CasaEntity pedidoCasaEntity = buildCasa();
        pedidoCasaEntity.setNumero(1L);
        pedidoCasaEntity.setResponsavel("Thallyta");
        pedidoCasaEntity.setCondominioEntity(buildCondominio());

        when(casaRepository.findById(pedidoCasaEntity.getId()))
                .thenReturn(Optional.of(pedidoCasaEntity));

        casaService.getById(1L);

    }

    @Test
    void deleteCasaById() {

        CasaEntity casaEntity = buildCasa();

        casaService.deleteCasaById(casaEntity.getId());

        verify(casaRepository,times(1)).deleteById(any());

    }

    private CasaRequestDTO buildCasaDTO() {
        CasaRequestDTO casaRequestDTO = new CasaRequestDTO();
        casaRequestDTO.setNumero(buildCasa().getNumero());
        casaRequestDTO.setResponsavel(buildCasa().getResponsavel());
        casaRequestDTO.setCondominioNome("Residencial Planalto I");

        return casaRequestDTO;
    }

    private CasaEntity buildCasa(){

        CasaEntity casaEntity = new CasaEntity();
        casaEntity.setId(1L);
        casaEntity.setNumero(1L);
        casaEntity.setResponsavel("Thallyta");
        casaEntity.setCondominioEntity(buildCondominio());

        return casaEntity;
    }

    private CondominioEntity buildCondominio(){

        CondominioEntity condominioEntity = new CondominioEntity();
        condominioEntity.setNome("Residencial Planalto I");

        return condominioEntity;
    }

}