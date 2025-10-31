package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.CasaResponseDTO;
import br.com.nayan.reserva_salao.dto.CondominioRequestDTO;
import br.com.nayan.reserva_salao.dto.CondominioResponseDTO;
import br.com.nayan.reserva_salao.dto.SalaoResponseDTO;
import br.com.nayan.reserva_salao.entity.CasaEntity;
import br.com.nayan.reserva_salao.entity.CondominioEntity;
import br.com.nayan.reserva_salao.entity.SalaoEntity;
import br.com.nayan.reserva_salao.repository.CasaRepository;
import br.com.nayan.reserva_salao.repository.CondominioRepository;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CondominioService {

    @Autowired
    private CondominioRepository condominioRepository;
    @Autowired
    private SalaoRepository salaoRepository;
    @Autowired
    private CasaRepository casaRepository;

    public CondominioResponseDTO create(CondominioRequestDTO condominioDTO) {
        // Validações básicas
        if(Objects.isNull(condominioDTO.getNome())){
            throw new IllegalArgumentException("O nome do condominio é obrigatório.");
        }
        //Logica para criar condominio
        if(condominioRepository.existsByNome(condominioDTO.getNome())){;
            throw new IllegalArgumentException("Condominio com esse nome ja existe.");
        }

        CondominioEntity novoCondominioEntity = new CondominioEntity();
        novoCondominioEntity.setNome(condominioDTO.getNome());
        novoCondominioEntity.setSalao(new ArrayList<SalaoEntity>());
        novoCondominioEntity.setCasaEntity(new ArrayList<CasaEntity>());

        CondominioEntity condominioEntity = condominioRepository.save(novoCondominioEntity);

        return CondominioResponseDTO.builder()
                .id(condominioEntity.getId())
                .nome(condominioEntity.getNome())
                .salao(List.of())
                .casa(List.of())
                .build();
    }

    public CondominioEntity getByName(String nome) {
        return condominioRepository.findByNome(nome)
                .orElseThrow(() -> new EntityNotFoundException("Condominio nao encontrado."));
    }

    public CondominioResponseDTO getById(Long id) {
        CondominioEntity condominioEntity = condominioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Condominio nao encontrado."));

        return CondominioResponseDTO.builder()
                .id(condominioEntity.getId())
                .nome(condominioEntity.getNome())
                .casa(condominioEntity.getCasaEntity().stream().map(casa -> {
                    return CasaResponseDTO.builder()
                            .id(casa.getId())
                            .numero(casa.getNumero())
                            .responsavel(casa.getResponsavel())
                            .condominioNome(condominioEntity.getNome())
                            .build();
                        }
                        )
                        .toList())
                .salao(condominioEntity.getSalao().stream().map(salao -> {
                    return SalaoResponseDTO.builder()
                            .id(salao.getId())
                            .area(salao.getArea())
                            .condominio(condominioEntity.getNome())
                            .build();
                })
                .toList())
                .build();
    }

    public void deleteCondominioById(Long id){
        condominioRepository.deleteById(id);
    }

}

