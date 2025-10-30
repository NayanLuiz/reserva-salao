package br.com.nayan.reserva_salao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "Reserva"
)

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "casa_id", nullable = false)
    private CasaEntity casaEntity;

    @ManyToOne
    @JoinColumn(name = "condominio_id", nullable = false)
    private CondominioEntity condominioEntity;

    @ManyToOne
    @JoinColumn(name = "salao_id", nullable = false)
    private SalaoEntity salao;
}
