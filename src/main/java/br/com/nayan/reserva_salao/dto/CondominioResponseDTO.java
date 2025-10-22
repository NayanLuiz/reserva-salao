package br.com.nayan.reserva_salao.dto;

import br.com.nayan.reserva_salao.entity.Casa;
import br.com.nayan.reserva_salao.entity.Salao;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CondominioResponseDTO {
    private Long id;
    private String nome;
    private List<SalaoResponseDTO> salao;
    private List<CasaResponseDTO> casa;
}
