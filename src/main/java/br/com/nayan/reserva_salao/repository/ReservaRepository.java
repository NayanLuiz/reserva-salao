package br.com.nayan.reserva_salao.repository;

import br.com.nayan.reserva_salao.entity.CasaEntity;
import br.com.nayan.reserva_salao.entity.ReservaEntity;
import br.com.nayan.reserva_salao.entity.CondominioEntity;
import br.com.nayan.reserva_salao.entity.SalaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    List<ReservaEntity> findByData(LocalDate data);

    List<ReservaEntity> findByCondominioEntity(CondominioEntity condominioEntity);
    
    List<ReservaEntity> findBySalao(SalaoEntity salao);

    List<ReservaEntity> findByCasaEntity(CasaEntity casaEntity);
}