package br.com.nayan.reserva_salao.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReservaResponseDTO {
    private Long id;
    private Long numero;
    private String condominio;
    private String salao;
    private LocalDate data;
}
