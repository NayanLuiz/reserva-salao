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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CasaRepository casaRepository;
    private final CondominioRepository condominioRepository;
    private final SalaoRepository salaoRepository;

    // CRIAR RESERVA
    public ReservaResponseDTO create(ReservaRequestDTO dto) {

        // Validações básicas
        if (dto.getNumero() == null) {
            throw new IllegalArgumentException("A reserva deve estar associada a uma casa.");
        }
        if (dto.getCondominio() == null || dto.getCondominio().isBlank()) {
            throw new IllegalArgumentException("A reserva deve estar associada a um condominio");
        }
        if (dto.getSalao() == null || dto.getSalao().isBlank()) {
            throw new IllegalArgumentException("A reserva deve estar associada a um salao");
        }
        if (dto.getData() == null) {
            throw new IllegalArgumentException("A reserva deve estar associada a uma data");
        }

        CasaEntity casa = casaRepository.findByNumero(dto.getNumero())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Casa não encontrada com número: " + dto.getNumero()));

        CondominioEntity condominio = condominioRepository.findByNome(dto.getCondominio())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Condomínio não encontrado: " + dto.getCondominio()));

        SalaoEntity salao = salaoRepository.findByArea(dto.getSalao())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Salão não encontrado: " + dto.getSalao()));

        ReservaEntity reserva = ReservaEntity.builder()
                .data(dto.getData())
                .casaEntity(casa)
                .condominioEntity(condominio)
                .salao(salao)
                .build();

        reservaRepository.save(reserva);

        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .numero(casa.getNumero())
                .condominio(condominio.getNome())
                .salao(salao.getArea())
                .data(reserva.getData())
                .build();
    }

    // LISTAR TODOS
    public List<ReservaResponseDTO> findAll() {
        return reservaRepository.findAll()
                .stream()
                .map(reserva -> ReservaResponseDTO.builder()
                        .id(reserva.getId())
                        .numero(reserva.getCasaEntity().getNumero())
                        .condominio(reserva.getCondominioEntity().getNome())
                        .salao(reserva.getSalao().getArea())
                        .data(reserva.getData())
                        .build())
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    public ReservaResponseDTO getById(Long id) {
            ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Reserva não encontrada com id: " + id));

        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .numero(reserva.getCasaEntity().getNumero())
                .condominio(reserva.getCondominioEntity().getNome())
                .salao(reserva.getSalao().getArea())
                .data(reserva.getData())
                .build();
    }

    // EXCLUIR
    public void delete(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new EntityNotFoundException("Reserva não encontrada com id: " + id);
        }
        reservaRepository.deleteById(id);
    }
}
