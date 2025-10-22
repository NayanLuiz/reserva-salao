package br.com.nayan.reserva_salao.repository;

import br.com.nayan.reserva_salao.entity.Salao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaoRepository extends JpaRepository<Salao, Long> {
    Optional<Salao> findByArea(String area);
}
