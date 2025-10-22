package br.com.nayan.reserva_salao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Condominio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Condominio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @OneToMany(mappedBy = "condominio", fetch = FetchType.LAZY)
    private List<Salao> salao;

    @OneToMany(mappedBy = "condominio", fetch = FetchType.LAZY)
    private List<Casa> casa;
}
