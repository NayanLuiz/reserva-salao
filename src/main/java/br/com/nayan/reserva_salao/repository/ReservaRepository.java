package br.com.nayan.reserva_salao.repository;

import br.com.nayan.reserva_salao.entity.Casa;
import br.com.nayan.reserva_salao.entity.Reserva;
import br.com.nayan.reserva_salao.entity.Salao;
import br.com.nayan.reserva_salao.entity.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByData(LocalDate data);
    List<Reserva> findByCondominio(Condominio condominio);
    List<Reserva> findBySalao(Salao salao);
    List<Reserva> findByCasa(Casa casa);
}
