package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.CasaRequestDTO;
import br.com.nayan.reserva_salao.dto.CasaResponseDTO;
import br.com.nayan.reserva_salao.entity.CasaEntity;
import br.com.nayan.reserva_salao.entity.CondominioEntity;
import br.com.nayan.reserva_salao.repository.CasaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CasaService {

    @Autowired
    private CasaRepository casaRepository;
    @Autowired
    private CondominioService condominioService;

    public CasaResponseDTO create(CasaRequestDTO casaDTO) {
        // validações basicas
        if (Objects.isNull(casaDTO.getNumero())) {
            throw new IllegalArgumentException("A casa deve estar associada a um numero");
        }
        if (Objects.isNull(casaDTO.getResponsavel())) {
            throw new IllegalArgumentException("A casa deve estar associada a um responsavel");
        }
        if (Objects.isNull(casaDTO.getCondominioNome())) {
            throw new IllegalArgumentException("A casa deve estar associada a um condominio");
        }

        //logica para criar casa
        if(casaRepository.existsByNumero(casaDTO.getNumero())){
            throw new IllegalArgumentException("Casa com esse numero ja existe.");
        }
        CondominioEntity condominioEntity = condominioService.getByName(casaDTO.getCondominioNome());

        CasaEntity novaCasaEntity = new CasaEntity();
        novaCasaEntity.setNumero(casaDTO.getNumero());
        novaCasaEntity.setResponsavel(casaDTO.getResponsavel());
        novaCasaEntity.setCondominioEntity(condominioEntity);
        CasaEntity casaEntitySalva = casaRepository.save(novaCasaEntity);

        return CasaResponseDTO.builder()
                .id(casaEntitySalva.getId())
                .numero(casaEntitySalva.getNumero())
                .responsavel(casaEntitySalva.getResponsavel())
                .condominioNome(casaEntitySalva.getCondominioEntity().getNome())
                .build();
    }

    public CasaResponseDTO getById(Long id) {
        Optional<CasaEntity> casaOpt = casaRepository.findById(id);
        if (casaOpt.isEmpty()) {
            throw new EntityNotFoundException("Casa não encontrada com o ID: " + id);
        }
        CasaEntity casaEntity = casaOpt.get();
        return CasaResponseDTO.builder()
                .id(casaEntity.getId())
                .numero(casaEntity.getNumero())
                .responsavel(casaEntity.getResponsavel())
                .condominioNome(casaEntity.getCondominioEntity().getNome())
                .build();
    }

    public void deleteCasaById(Long id){
//        if(!casaRepository.existsById(id)){
//            throw new EntityNotFoundException("Casa nao encontrada com o id: " + id);
//        }
        casaRepository.deleteById(id);
    }

    public CasaResponseDTO putById(Long id, CasaRequestDTO casaDTO){
        CasaEntity casaEntity = casaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Casa nao encontrada com o id: " + id)
        );

        casaEntity.setNumero(casaDTO.getNumero());
        casaEntity.setResponsavel(casaDTO.getResponsavel());
        CasaEntity casaEntityAtualizada = casaRepository.save(casaEntity);

        return CasaResponseDTO.builder()
                .id(casaEntityAtualizada.getId())
                .numero(casaEntityAtualizada.getNumero())
                .responsavel(casaEntityAtualizada.getResponsavel())
                .condominioNome(casaEntityAtualizada.getCondominioEntity().getNome())
                .build();
    }


}