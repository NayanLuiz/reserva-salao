package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.CasaRequestDTO;
import br.com.nayan.reserva_salao.dto.CasaResponseDTO;
import br.com.nayan.reserva_salao.service.CasaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CasaController.class)
class CasaControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    private CasaService casaService;

    @Test
    @DisplayName("Should create house successfully")
    void createHouseSuccessfully() throws Exception {

        CasaRequestDTO casaRequestDTO = new CasaRequestDTO();

        casaRequestDTO.setNumero(1L);
        casaRequestDTO.setResponsavel("Thallyta");
        casaRequestDTO.setCondominioNome("Residencial Planalto I");

        CasaResponseDTO casaResponseDTO = CasaResponseDTO.builder()
                .id(1L)
                .numero(1L)
                .responsavel("Thallyta")
                .condominioNome("Residencial Planalto I")
                .build();

        when(casaService.create(any(CasaRequestDTO.class))).thenReturn(casaResponseDTO);

        mvc.perform(post("/api/casa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(casaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responsavel").value("Thallyta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.condominioNome").value("Residencial Planalto I"));
    }

    @Test
    @DisplayName("Should get condiminium by id sucessfully")
    void getCondominiumById() throws Exception {

        Long casaId = 1L;
        CasaResponseDTO casaResponseDTO = CasaResponseDTO.builder().id(1L).numero(10L).responsavel("Thallyta").condominioNome("Residencial Planalto I").build();

        when(casaService.getById(casaId)).thenReturn(casaResponseDTO);

        mvc.perform(get("/api/casa/{id}", casaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(casaId)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value(10L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responsavel").value("Thallyta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.condominioNome").value("Residencial Planalto I"));
    }

    @Test
    @DisplayName("Should delete house by id successfully")
    void deleteHouseBiId() throws Exception {

        Long casaId = 1L;

        doNothing().when(casaService).deleteCasaById(casaId);

        mvc.perform(delete("/api/casa/{id}", casaId)).andExpect(status().isNoContent());

        verify(casaService, times(1)).deleteCasaById(casaId);

    }
}