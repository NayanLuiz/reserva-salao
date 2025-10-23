package br.com.nayan.reserva_salao.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRequestDTO {
    private Long numero;
    private String condominio;
    private String salao;
    private LocalDate data;
}
