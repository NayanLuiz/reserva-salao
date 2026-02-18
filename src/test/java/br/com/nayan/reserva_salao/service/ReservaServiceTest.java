package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.ReservaRequestDTO;
import br.com.nayan.reserva_salao.dto.ReservaResponseDTO;
import br.com.nayan.reserva_salao.entity.CasaEntity;
import br.com.nayan.reserva_salao.entity.CondominioEntity;
import br.com.nayan.reserva_salao.entity.ReservaEntity;
import br.com.nayan.reserva_salao.entity.SalaoEntity;
import br.com.nayan.reserva_salao.repository.CasaRepository;
import br.com.nayan.reserva_salao.repository.CondominioRepository;
import br.com.nayan.reserva_salao.repository.ReservaRepository;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {


    @Mock
    private CasaRepository casaRepository;

    @Mock
    private SalaoRepository salaoRepository;

    @Mock
    private CondominioRepository condominioRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Should create a reservation successfully")
    void createReservaSucessfully() {

        ReservaRequestDTO reservaRequestDTO = buildReservaRequestDTO();
        ReservaEntity reservafeita = buildReserva();
        CasaEntity casaEntity = buildCasa();
        SalaoEntity salao = buildSalao();
        CondominioEntity condominioEntity = buildCondominio();

        when(casaRepository.findByNumero(reservaRequestDTO.getNumero()))
                .thenReturn(Optional.of(casaEntity));
        when(salaoRepository.findByArea(reservaRequestDTO.getSalao()))
                .thenReturn(Optional.of(salao));
        when(condominioRepository.findByNome(reservaRequestDTO.getCondominio()))
                .thenReturn(Optional.of(condominioEntity));
        when(reservaRepository.save(any(ReservaEntity.class)))
                .thenReturn(reservafeita);


        ReservaResponseDTO reservaResponseDTO = reservaService.create(reservaRequestDTO);

        assertThat(reservaResponseDTO).isNotNull();
        assertThat(reservaResponseDTO.getId()).isEqualTo(reservafeita.getId());

    }

    @Test
    @DisplayName("Shouldn't create a reservation without a house number")
    void createReservaWithoutHouse() {

        ReservaRequestDTO reservaRequestDTO = buildReservaRequestDTO();
        reservaRequestDTO.setNumero(null);
        ReservaEntity reservafeita = buildReserva();
        CasaEntity casaEntity = buildCasa();
        SalaoEntity salao = buildSalao();
        CondominioEntity condominioEntity = buildCondominio();

        Throwable throwable = catchThrowable(() -> {
            reservaService.create(reservaRequestDTO);
        });
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A reserva deve estar associada a uma casa.");
    }

    @Test
    @DisplayName("Shouldn't create a reservation without a Condominium")
    void createReservaWithoutCondominium() {

        ReservaRequestDTO reservaRequestDTO = buildReservaRequestDTO();
        reservaRequestDTO.setCondominio(null);
        ReservaEntity reservafeita = buildReserva();
        CasaEntity casaEntity = buildCasa();
        SalaoEntity salao = buildSalao();
        CondominioEntity condominioEntity = buildCondominio();

        Throwable throwable = catchThrowable(() -> {
            reservaService.create(reservaRequestDTO);
        });
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A reserva deve estar associada a um condominio");

    }

    @Test
    @DisplayName("Shouldn't create a reservation without a Saloon")
    void createReservaWithoutSaloon(){

        ReservaRequestDTO reservaRequestDTO = buildReservaRequestDTO();
        reservaRequestDTO.setSalao(null);
        ReservaEntity reservafeita = buildReserva();
        CasaEntity casaEntity = buildCasa();
        SalaoEntity salao = buildSalao();
        CondominioEntity condominioEntity = buildCondominio();

        Throwable throwable = catchThrowable(() -> {
            reservaService.create(reservaRequestDTO);
        });
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A reserva deve estar associada a um salao");

    }

    @Test
    @DisplayName("Shouldn't create a reservation without a Date")
    void createReservaWithoutDate(){

        ReservaRequestDTO reservaRequestDTO = buildReservaRequestDTO();
        reservaRequestDTO.setData(null);
        ReservaEntity reservafeita = buildReserva();
        CasaEntity casaEntity = buildCasa();
        SalaoEntity salao = buildSalao();
        CondominioEntity condominioEntity = buildCondominio();

        Throwable throwable = catchThrowable(() -> {
            reservaService.create(reservaRequestDTO);
        });
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A reserva deve estar associada a uma data");
    }

    @Test
    @DisplayName("Get reservation by id successfully")
    void getById() {

        ReservaEntity reserva = buildReserva();

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        ReservaResponseDTO reservaResponseDTO = reservaService.getById(1L);

        assertThat(reservaResponseDTO).isNotNull();
        assertThat(reservaResponseDTO.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("When reservation isn't found by id, should throw an exception")
    void getReservaNotFound(){

        when(reservaRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        Throwable throwable = catchThrowable(() -> {
            reservaService.getById(5L);
        });
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Reserva não encontrada com id: 5");
    }

    @Test
    @DisplayName("Delete reservation by id successfully")
    void deleteReservaById() {

        ReservaEntity reserva = buildReserva();

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        ReservaResponseDTO reservaResponseDTO = reservaService.getById(1L);

        assertThat(reservaResponseDTO).isNotNull();
        assertThat(reservaResponseDTO.getId()).isEqualTo(1L);
    }

    private ReservaRequestDTO buildReservaRequestDTO() {
        ReservaRequestDTO reservaRequestDTO = new ReservaRequestDTO();
        reservaRequestDTO.setNumero(1L);
        reservaRequestDTO.setCondominio("Condominio A");
        reservaRequestDTO.setSalao("Salao Principal");
        reservaRequestDTO.setData(LocalDate.now());

        return reservaRequestDTO;
    }

    private ReservaEntity buildReserva() {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(1L);
        reserva.setData(LocalDate.now());
        reserva.setCondominioEntity(buildCondominio());
        reserva.setSalao(buildSalao());
        reserva.setCasaEntity(buildCasa());

        return reserva;
    }

    private CondominioEntity buildCondominio() {
        CondominioEntity condominioEntity = new CondominioEntity();
        condominioEntity.setId(1L);
        condominioEntity.setNome("Condominio A");
        condominioEntity.setCasaEntity(List.of());
        condominioEntity.setSalao(List.of());

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