package br.com.nayan.reserva_salao.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalaoResponseDTO {
    private Long id;
    private String area;
    private String condominio;
}
