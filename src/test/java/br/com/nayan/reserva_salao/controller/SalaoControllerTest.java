package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.SalaoRequestDTO;
import br.com.nayan.reserva_salao.dto.SalaoResponseDTO;
import br.com.nayan.reserva_salao.service.SalaoService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SalaoController.class)
class SalaoControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    private SalaoService salaoService;

    @Test
    @DisplayName("Should create salão successfully")
    void createSalaoSuccessfully() throws Exception {

        SalaoRequestDTO salaoRequestDTO = new SalaoRequestDTO();

        salaoRequestDTO.setArea("Churrasqueira");
        salaoRequestDTO.setCondominio("Residencial Planalto I");

        SalaoResponseDTO salaoResponseDTO = SalaoResponseDTO.builder()
                .id(1L)
                .area("Churrasqueira")
                .condominio("Residencial Planalto I")
                .build();

        when(salaoService.create(any(SalaoRequestDTO.class)))
                .thenReturn(salaoResponseDTO);

        mvc.perform(
                post("/api/salao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(salaoRequestDTO))
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.area").value("Churrasqueira"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.condominio").value("Residencial Planalto I"));
    }

    @Test
    @DisplayName("Should get salão by id successfully")
    void getSalaoById() throws Exception {

        Long salaoId = 1L;

        SalaoResponseDTO salaoResponseDTO = SalaoResponseDTO.builder()
                .id(1L)
                .area("Churrasqueira")
                .condominio("Residencial Planalto I")
                .build();

        when(salaoService.getById(salaoId))
                .thenReturn(salaoResponseDTO);

        mvc.perform(
                        get("/api/salao/{id}", salaoId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(salaoId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.area").value("Churrasqueira"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.condominio").value("Residencial Planalto I"));
    }

    @Test
    @DisplayName("Should delete salão by id successfully")
    void deleteSalaoById() throws Exception {

        Long salaoId = 1L;

        doNothing().when(salaoService).deleteSalaoById(salaoId);

        mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/salao/{id}", salaoId))
                .andExpect(status().isNoContent());
    }
}