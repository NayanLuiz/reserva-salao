package br.com.nayan.reserva_salao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class ReservaRequestDTO {
    private Long numero;        // número da casa
    private String condominio;  // nome do condomínio
    private String salao;       // nome do salão (área)
    private LocalDate data;
}
