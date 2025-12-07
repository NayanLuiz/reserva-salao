package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.ReservaRequestDTO;
import br.com.nayan.reserva_salao.dto.ReservaResponseDTO;
import br.com.nayan.reserva_salao.entity.CasaEntity;
import br.com.nayan.reserva_salao.entity.CondominioEntity;
import br.com.nayan.reserva_salao.entity.ReservaEntity;
import br.com.nayan.reserva_salao.entity.SalaoEntity;
import br.com.nayan.reserva_salao.repository.CasaRepository;
import br.com.nayan.reserva_salao.repository.CondominioRepository;
import br.com.nayan.reserva_salao.repository.ReservaRepository;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private CasaRepository casaRepository;
    @Autowired
    private SalaoRepository salaoRepository;
    @Autowired
    private CondominioRepository condominioRepository;

    public ReservaResponseDTO create(ReservaRequestDTO reservaDTO) {
        // validações
        if(Objects.isNull(reservaDTO.getNumero())) {
            throw new IllegalArgumentException("A reserva deve estar associada a uma casa.");
        }
        if(Objects.isNull(reservaDTO.getCondominio())){
            throw new IllegalArgumentException("A reserva deve estar associada a um condominio");
        }
        if(!Optional.ofNullable(reservaDTO.getData()).isPresent()){
            throw new IllegalArgumentException("A reserva deve estar associada a uma data");
        }
        if(Objects.isNull(reservaDTO.getSalao())){
            throw new IllegalArgumentException("A reserva deve estar associada a um salao");
        }

        //logica pra criar reserva

        CasaEntity casaEntity = casaRepository.findByNumero(reservaDTO.getNumero()).orElseThrow(() -> {
            return new EntityNotFoundException("Casa nao encontrada id com o id: " + reservaDTO.getNumero());
                }
        );
        CondominioEntity condominioEntity = condominioRepository.findByNome(reservaDTO.getCondominio()).orElseThrow(()-> {
            return new EntityNotFoundException("Condominio nao encontrado com o id: " + reservaDTO.getCondominio());
                }

        );
        SalaoEntity salao = salaoRepository.findByArea(reservaDTO.getSalao()).orElseThrow(( )-> {
            return new EntityNotFoundException("Salao nao encontrado com area: " + reservaDTO.getSalao());
                }

        );

        ReservaEntity reserva = new ReservaEntity();
        reserva.setCasaEntity(casaEntity);
        reserva.setData(reservaDTO.getData());
        reserva.setCondominioEntity(condominioEntity);
        reserva.setSalao(salao);

        ReservaEntity reservaSalva = reservaRepository.save(reserva);

        return ReservaResponseDTO.builder()
                .id(reservaSalva.getId())
                .numero(reservaSalva.getCasaEntity().getId())
                .salao(reservaSalva.getSalao().getArea())
                .condominio(reservaSalva.getSalao().getCondominioEntity().getNome())
                .data(reservaSalva.getData())
                .build();
    }

    public ReservaResponseDTO getById(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Reserva nao encontrada com o id: " + id)
        );

        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .numero(reserva.getCasaEntity().getId())
                .salao(reserva.getSalao().getArea())
                .condominio(reserva.getCondominioEntity().getNome())
                .data(reserva.getData())
                .build();
    }
    public void deleteReservaById(Long id) {
//        Reserva reserva = reservaRepository.findById(id).orElseThrow(() ->
//                new EntityNotFoundException("Reserva nao encontrada com o id: " + id)
//        );
        reservaRepository.deleteById(id);
    }



//    public ReservaResponseDTO putById(Long id, ReservaRequestDTO reservaDTO){
//
//        if(Objects.isNull(reservaDTO.getNumero())) {
//            throw new IllegalArgumentException("A reserva deve estar associada a uma casa.");
//        }
//        // create / put / getById - ajuste do builder do DTO
//        return ReservaResponseDTO.builder()
//                .id(reservaDTO.getId())
//                .numero(Novareserva.getCasa().getNumero()) // ou .getId() se não existir getNumero()
//                .salao(Novareserva.getSalao().getArea())
//                .condominio(Novareserva.getCondominio().getNome()) // usar o condominio da reserva, não do salao
//                .data(Novareserva.getData())
//                .build();
//
//        if(Objects.isNull(reservaDTO.getCondominio())){
//            throw new IllegalArgumentException("A reserva deve estar associada a um condominio");
//        }
//        if(!Optional.ofNullable(reservaDTO.getData()).isPresent()){
//            throw new IllegalArgumentException("A reserva deve estar associada a uma data");
//        }
//        if(Objects.isNull(reservaDTO.getSalao())){
//            throw new IllegalArgumentException("A reserva deve estar associada a um salao");
//        }
//
//        Casa casa = casaRepository.findByNumero(reservaDTO.getNumero()).orElseThrow(() ->
//                new EntityNotFoundException("Casa nao encontrada id com o: " + reservaDTO.getNumero())
//        );
//        Condominio condominio = condominioRepository.findByNome(reservaDTO.getCondominio()).orElseThrow(()->
//                new EntityNotFoundException("Condominio nao encontrado com o id: " + reservaDTO.getCondominio())
//        );
//        Salao salao = salaoRepository.findByArea(reservaDTO.getSalao()).orElseThrow(( )->
//                new EntityNotFoundException("Salao nao encontrado com area: " + reservaDTO.getSalao())
//        );
//
//        Reserva reserva = reservaRepository.findById(id).orElseThrow(() ->
//                new EntityNotFoundException("Reserva nao encontrada com o id: " + id)
//        );
//
//        reserva.setCasa(casa);
//        reserva.setData(reservaDTO.getData());
//        reserva.setCondominio(condominio);
//        reserva.setSalao(salao);
//
//        Reserva reservaSalva = reservaRepository.save(reserva);
//
//        return ReservaResponseDTO.builder()
//                .id(reservaSalva.getId())
//                .numero(reservaSalva.getCasa().getId())
//                .salao(reservaSalva.getSalao().getArea())
//                .condominio(reservaSalva.getSalao().getCondominio().getNome())
//                .data(reservaSalva.getData())
//                .build();
//    }
}
