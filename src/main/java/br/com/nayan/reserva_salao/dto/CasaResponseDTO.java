package br.com.nayan.reserva_salao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CasaResponseDTO {
    private Long id;
    private Long numero;
    private String responsavel;
    private String condominioNome;
}
