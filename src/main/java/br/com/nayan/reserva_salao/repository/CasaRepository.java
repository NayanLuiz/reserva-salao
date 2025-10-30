package br.com.nayan.reserva_salao.repository;

import br.com.nayan.reserva_salao.entity.CasaEntity;
import br.com.nayan.reserva_salao.entity.CondominioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CasaRepository extends JpaRepository<CasaEntity, Long> {
    List<CasaEntity> findByCondominio(CondominioEntity condominioEntity);
    Boolean existsByNumero(Long numero);
    Optional<CasaEntity> findByNumero(Long numero);
    Object findByResponsavel(String responsavel);
    Object existByCondominio(String responsavel);
}