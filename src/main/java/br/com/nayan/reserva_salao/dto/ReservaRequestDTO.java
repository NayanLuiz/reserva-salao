package br.com.nayan.reserva_salao.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class ReservaRequestDTO {
    @NotNull(message = "O número da casa não pode ser nulo")
    private Long numero;        // número da casa
    
    @NotBlank(message = "O nome do condomínio não pode estar vazio")
    private String condominio;  // nome do condomínio
    
    @NotBlank(message = "O nome do salão não pode estar vazio")
    private String salao;       // nome do salão (área)
    
    @NotNull(message = "A data não pode ser nula")
    @FutureOrPresent(message = "A data deve ser no presente ou no futuro")
    private LocalDate data;
}
