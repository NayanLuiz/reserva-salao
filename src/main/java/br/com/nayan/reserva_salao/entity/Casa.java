package br.com.nayan.reserva_salao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "Casa"
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Casa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long numero;

    @Column(nullable = false, length = 100)
    private String responsavel;

    @ManyToOne
    @JoinColumn(name = "condominio_id", nullable = false)
    private Condominio condominio;
}