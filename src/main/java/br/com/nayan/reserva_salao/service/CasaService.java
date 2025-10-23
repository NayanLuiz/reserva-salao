package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.CasaRequestDTO;
import br.com.nayan.reserva_salao.dto.CasaResponseDTO;
import br.com.nayan.reserva_salao.entity.Casa;
import br.com.nayan.reserva_salao.entity.Condominio;
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
        // Validações básicas
        if (Objects.isNull(casaDTO.getNumero())) {
            throw new IllegalArgumentException("A casa deve estar associada a um numero");
        }
        if (Objects.isNull(casaDTO.getResponsavel())) {
            throw new IllegalArgumentException("A casa deve estar associada a um responsavel");
        }
        if (Objects.isNull(casaDTO.getCondominioNome())) {
            throw new IllegalArgumentException("A casa deve estar associada a um condominio");
        }

        //Logica para criar casa
        if(casaRepository.existsByNumero(casaDTO.getNumero())){
            throw new EntityNotFoundException("Casa com esse numero ja existe.");
        }
        Condominio condominio = condominioService.getByName(casaDTO.getCondominioNome());

        Casa novaCasa = new Casa();
        novaCasa.setNumero(casaDTO.getNumero());
        novaCasa.setResponsavel(casaDTO.getResponsavel());
        novaCasa.setCondominio(condominio);
        Casa casaSalva = casaRepository.save(novaCasa);

        return CasaResponseDTO.builder()
                .id(casaSalva.getId())
                .numero(casaSalva.getNumero())
                .responsavel(casaDTO.getResponsavel())
                .condominioNome(casaSalva.getCondominio().getNome())
                .build();
    }

    public CasaResponseDTO getById(Long id) {
        Optional<Casa> casaOpt = casaRepository.findById(id);
        if (casaOpt.isEmpty()) {
            throw new EntityNotFoundException("Casa não encontrada com o ID: " + id);
        }
        Casa casa = casaOpt.get();
        return CasaResponseDTO.builder()
                .id(casa.getId())
                .numero(casa.getNumero())
                .responsavel(casa.getResponsavel())
                .condominioNome(casa.getCondominio().getNome())
                .build();
    }

    public void deleteCasaById(Long id){
//        if(!casaRepository.existsById(id)){
//            throw new EntityNotFoundException("Casa nao encontrada com o id: " + id);
//        }
        casaRepository.deleteById(id);
    }

    public CasaResponseDTO putById(Long id, CasaRequestDTO casaDTO){
        Casa casa = casaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Casa nao encontrada com o id: " + id)
        );

        casa.setNumero(casaDTO.getNumero());
        casa.setResponsavel(casaDTO.getResponsavel());
        Casa casaAtualizada = casaRepository.save(casa);

        return CasaResponseDTO.builder()
                .id(casaAtualizada.getId())
                .numero(casaAtualizada.getNumero())
                .responsavel(casaAtualizada.getResponsavel())
                .condominioNome(casaAtualizada.getCondominio().getNome())
                .build();
    }


}
