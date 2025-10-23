package br.com.nayan.reserva_salao.repository;

import br.com.nayan.reserva_salao.entity.Casa;
import br.com.nayan.reserva_salao.entity.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CasaRepository extends JpaRepository<Casa, Long> {
    List<Casa> findByCondominio(Condominio condominio);
    List<Casa> findByResponsavelContainingIgnoreCase(String nome);
    Boolean existsByNumero(Long numero);
    Optional<Casa> findByNumero(Long numero);
}