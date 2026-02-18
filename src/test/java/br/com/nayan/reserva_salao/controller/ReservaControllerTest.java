package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.ReservaRequestDTO;
import br.com.nayan.reserva_salao.dto.ReservaResponseDTO;
import br.com.nayan.reserva_salao.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.MediaType;


@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    private ReservaService reservaService;

    @Test
    @DisplayName("Should create reservation successfully")
    void createReservationSuccessfully() throws Exception {

        ReservaRequestDTO reservaRequestDTO = new ReservaRequestDTO();

        reservaRequestDTO.setNumero(1L);
        reservaRequestDTO.setSalao("Churrasqueira");
        reservaRequestDTO.setData(LocalDate.now());
        reservaRequestDTO.setCondominio("Residencial Planalto I");

        ReservaResponseDTO reservaResponseDTO = ReservaResponseDTO.builder()
                .id(1L)
                .numero(1L)
                .salao("Churrasqueira")
                .data(LocalDate.now())
                .condominio("Residencial Planalto I")
                .build();

        when(reservaService.create(any(ReservaRequestDTO.class))).thenReturn(reservaResponseDTO);

        mvc.perform(
                post("/api/reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(reservaRequestDTO))
        )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.numero").value(1L))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.salao").value("Churrasqueira"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.data").value(LocalDate.now().toString()))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.condominio").value("Residencial Planalto I"));
    }

    @Test
    @DisplayName("Should get reservation by id successfully")
    void getReservationByIdSuccessfully() throws Exception {

        Long reservaId = 1L;
        ReservaResponseDTO reservaResponseDTO = ReservaResponseDTO.builder()
                .id(1L)
                .numero(1L)
                .salao("Churrasqueira")
                .data(LocalDate.now())
                .condominio("Residencial Planalto I")
                .build();

        when(reservaService.getById(reservaId))
                .thenReturn(reservaResponseDTO);

        mvc.perform(
                get("/api/reserva/{id}", reservaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(reservaId))
        )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.numero").value(1L))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.salao").value("Churrasqueira"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.data").value(LocalDate.now().toString()))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.condominio").value("Residencial Planalto I"));
    }

    @Test
    @DisplayName("Should delete reservation by id successfully")
    void deleteReservationByIdSuccessfully() throws Exception {

        Long reservaId = 1L;

        mvc.perform(
                delete("/api/reserva/{id}", reservaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(reservaId))
        )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isNoContent());

        verify(reservaService, times(1)).delete(reservaId);
    }
}