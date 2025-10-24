package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.ReservaRequestDTO;
import br.com.nayan.reserva_salao.dto.ReservaResponseDTO;
import br.com.nayan.reserva_salao.entity.Casa;
import br.com.nayan.reserva_salao.entity.Condominio;
import br.com.nayan.reserva_salao.entity.Reserva;
import br.com.nayan.reserva_salao.entity.Salao;
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
        Reserva reservafeita = buildReserva();
        Casa casa = buildCasa();
        Salao salao = buildSalao();
        Condominio condominio = buildCondominio();

        when(casaRepository.findByNumero(reservaRequestDTO.getNumero())).thenReturn(Optional.of(casa));
        when(salaoRepository.findByArea(reservaRequestDTO.getSalao())).thenReturn(Optional.of(salao));
        when(condominioRepository.findByNome(reservaRequestDTO.getCondominio())).thenReturn(Optional.of(condominio));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservafeita);


        ReservaResponseDTO reservaResponseDTO = reservaService.create(reservaRequestDTO);

        assertThat(reservaResponseDTO).isNotNull();
        assertThat(reservaResponseDTO.getId()).isEqualTo(reservafeita.getId());

    }

    @Test
    @DisplayName("Shouldn't create a reservation without a house number")
    void createReservaWithoutHouse() {

        ReservaRequestDTO reservaRequestDTO = buildReservaRequestDTO();
        reservaRequestDTO.setNumero(null);
        Reserva reservafeita = buildReserva();
        Casa casa = buildCasa();
        Salao salao = buildSalao();
        Condominio condominio = buildCondominio();

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
        Reserva reservafeita = buildReserva();
        Casa casa = buildCasa();
        Salao salao = buildSalao();
        Condominio condominio = buildCondominio();

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
        Reserva reservafeita = buildReserva();
        Casa casa = buildCasa();
        Salao salao = buildSalao();
        Condominio condominio = buildCondominio();

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
        Reserva reservafeita = buildReserva();
        Casa casa = buildCasa();
        Salao salao = buildSalao();
        Condominio condominio = buildCondominio();

        Throwable throwable = catchThrowable(() -> {
            reservaService.create(reservaRequestDTO);
        });
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A reserva deve estar associada a uma data");
    }

    @Test
    @DisplayName("Get reservation by id successfully")
    void getById() {

        Reserva reserva = buildReserva();

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
                .hasMessage("Reserva nao encontrada com o id: 5");
    }

    @Test
    @DisplayName("Delete reservation by id successfully")
    void deleteReservaById() {

        Reserva reserva = buildReserva();

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

    private Reserva buildReserva() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setData(LocalDate.now());
        reserva.setCondominio(buildCondominio());
        reserva.setSalao(buildSalao());
        reserva.setCasa(buildCasa());

        return reserva;
    }

    private Condominio buildCondominio() {
        Condominio condominio = new Condominio();
        condominio.setId(1L);
        condominio.setNome("Condominio A");
        condominio.setCasa(List.of());
        condominio.setSalao(List.of());

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