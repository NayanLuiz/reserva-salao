package br.com.nayan.reserva_salao.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReservaResponseDTO {
    private Long id;
    private Long casaId;
    private Long condominioId;
    private Long salaoId;
    private LocalDate data;
}
