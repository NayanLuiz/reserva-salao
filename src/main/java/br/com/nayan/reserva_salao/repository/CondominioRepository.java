package br.com.nayan.reserva_salao.repository;

import br.com.nayan.reserva_salao.entity.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CondominioRepository extends JpaRepository<Condominio, Long> {
    Optional<Condominio> findByNome(String nome);
}
