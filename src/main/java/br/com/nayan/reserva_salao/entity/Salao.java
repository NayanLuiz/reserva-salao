package br.com.nayan.reserva_salao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "Salao"
)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Salao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominio_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SALAO_CONDOMINIO"))
    private Condominio condominio;
}
