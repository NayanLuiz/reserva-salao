package br.com.nayan.reserva_salao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class ReservaRequestDTO {
    @NotNull
    private Long numero;
    @NotBlank @NotNull
    private String condominio;
    @NotBlank @NotNull
    private String salao;
    @NotNull
    private LocalDate data;
}
