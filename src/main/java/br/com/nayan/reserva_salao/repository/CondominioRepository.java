package br.com.nayan.reserva_salao.repository;

import br.com.nayan.reserva_salao.entity.CondominioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CondominioRepository extends JpaRepository<CondominioEntity, Long> {
    Optional<CondominioEntity> findByNome(String nome);
    Boolean existsByNome(String nome);
}
