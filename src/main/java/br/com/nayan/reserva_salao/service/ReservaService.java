package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.ReservaRequestDTO;
import br.com.nayan.reserva_salao.dto.ReservaResponseDTO;
import br.com.nayan.reserva_salao.entity.Casa;
import br.com.nayan.reserva_salao.entity.Condominio;
import br.com.nayan.reserva_salao.entity.Reserva;
import br.com.nayan.reserva_salao.entity.Salao;
import br.com.nayan.reserva_salao.repository.CasaRepository;
import br.com.nayan.reserva_salao.repository.CondominioRepository;
import br.com.nayan.reserva_salao.repository.ReservaRepository;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
        // Validações básicas
        if(Objects.isNull(reservaDTO.getCasaId())) {
            throw new IllegalArgumentException("A reserva deve estar associada a uma casa.");
        }
        if(Objects.isNull(reservaDTO.getCondominioId())){
            throw new IllegalArgumentException("A reserva deve estar associada a um condominio");
        }
        if(!Optional.ofNullable(reservaDTO.getData()).isPresent()){
            throw new IllegalArgumentException("A reserva deve estar associada a uma data");
        }
        if(Objects.isNull(reservaDTO.getSalaoId())){
            throw new IllegalArgumentException("A reserva deve estar associada a um salao");
        }
        //Logica para criar reserva

        Casa casa = casaRepository.findById(reservaDTO.getCasaId()).orElseThrow(() ->
                new EntityNotFoundException("Casa nao encontrada id com o: " + reservaDTO.getCasaId())
        );
        Condominio condominio = condominioRepository.findById(reservaDTO.getCondominioId()).orElseThrow(()->
                new EntityNotFoundException("Condominio nao encontrado com o id: " + reservaDTO.getCondominioId())
        );
        Salao salao = salaoRepository.findById(reservaDTO.getSalaoId()).orElseThrow(( )->
            new EntityNotFoundException("Salao nao encontrado com o id: " + reservaDTO.getSalaoId())
        );

        Reserva reserva = new Reserva();
        reserva.setCasa(casa);
        reserva.setData(reservaDTO.getData());
        reserva.setCondominio(condominio);
        reserva.setSalao(salao);

        Reserva reservaSalva = reservaRepository.save(reserva);

        return ReservaResponseDTO.builder()
                .id(reservaSalva.getId())
                .casaId(reservaSalva.getCasa().getId())
                .salaoId(reservaSalva.getSalao().getId())
                .condominioId(reservaSalva.getSalao().getId())
                .data(reservaSalva.getData())
                .build();
    }

    public ReservaResponseDTO getById(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Reserva nao encontrada com o id: " + id)
        );

        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .casaId(reserva.getCasa().getId())
                .salaoId(reserva.getSalao().getId())
                .condominioId(reserva.getCondominio().getId())
                .data(reserva.getData())
                .build();
    }
}
