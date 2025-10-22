package br.com.nayan.reserva_salao.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRequestDTO {
    private Long casaId;
    private Long condominioId;
    private Long salaoId;
    private LocalDate data;
}
