package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.CondominioRequestDTO;
import br.com.nayan.reserva_salao.dto.CondominioResponseDTO;
import br.com.nayan.reserva_salao.service.CondominioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CondominioController.class)
class CondominioControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    private CondominioService condominioService;

    @Test
    @DisplayName("Should create condominium successfully")
    void shouldCreateCondominiumSuccessfully() throws Exception {

        CondominioRequestDTO requestDTO = new CondominioRequestDTO();
        requestDTO.setNome("Condominio A");

        CondominioResponseDTO responseDTO = CondominioResponseDTO.builder()
                .id(1L)
                .nome("Condominio A")
                .salao(List.of())
                .casa(List.of())
                .build();

        when(condominioService.create(any(CondominioRequestDTO.class)))
                .thenReturn(responseDTO);

        mvc.perform(post("/api/condominio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Condominio A"));
    }

    @Test
    @DisplayName("Should throw exception when condominium already exists")
    void shouldThrowExceptionWhenCondominiumAlreadyExists() throws Exception {

        CondominioRequestDTO requestDTO = new CondominioRequestDTO();
        requestDTO.setNome("Condominio A");

        when(condominioService.create(any(CondominioRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Condominio com esse nome ja existe."));

        mvc.perform(post("/api/condominio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should throw exception when name is missing")
    void shouldThrowExceptionWhenNameIsMissing() throws Exception {

        CondominioRequestDTO requestDTO = new CondominioRequestDTO();
        requestDTO.setNome("");

        mvc.perform(post("/api/condominio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() throws Exception {

        CondominioRequestDTO requestDTO = new CondominioRequestDTO();
        requestDTO.setNome(null);

        mvc.perform(post("/api/condominio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get condominium by ID successfully")
    void shouldGetCondominiumByIdSuccessfully() throws Exception {

        Long condominioId = 1L;

        CondominioResponseDTO responseDTO = CondominioResponseDTO.builder()
                .id(condominioId)
                .nome("Condominio A")
                .salao(List.of())
                .casa(List.of())
                .build();

        when(condominioService.getById(condominioId))
                .thenReturn(responseDTO);

        mvc.perform(get("/api/condominio/{id}", condominioId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(condominioId))
                .andExpect(jsonPath("$.nome").value("Condominio A"));
    }

    @Test
    @DisplayName("Should delete condominium by ID successfully")
    void shouldDeleteCondominiumByIdSuccessfully() throws Exception {

        Long condominioId = 1L;

        doNothing().when(condominioService).deleteCondominioById(condominioId);

        mvc.perform(delete("/api/condominio/{id}", condominioId))
                .andExpect(status().isNoContent());

        verify(condominioService, times(1)).deleteCondominioById(condominioId);
    }


}
