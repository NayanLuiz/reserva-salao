package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.SalaoRequestDTO;
import br.com.nayan.reserva_salao.dto.SalaoResponseDTO;
import br.com.nayan.reserva_salao.entity.SalaoEntity;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SalaoService {

    @Autowired
    private CondominioService condominioService;
    @Autowired
    private SalaoRepository salaoRepository;

    public SalaoResponseDTO create(SalaoRequestDTO salaoDTO){
        // validações
        if(Objects.isNull(salaoDTO.getArea()) || salaoDTO.getArea().isBlank()){
            throw new IllegalArgumentException("A área do salão não pode ser nula ou vazia");
        }
        if (Objects.isNull(salaoDTO.getCondominio())) {
            throw new IllegalArgumentException("O salão deve estar associado a um condomínio");
    }
        condominioService.getByName(salaoDTO.getCondominio());

        SalaoEntity salao = new SalaoEntity();
        salao.setArea(salaoDTO.getArea());
        salao.setCondominioEntity(condominioService.getByName(salaoDTO.getCondominio()));
        SalaoEntity salaoSalvo = salaoRepository.save(salao);

        return SalaoResponseDTO.builder()
                .id(salaoSalvo.getId())
                .area(salaoSalvo.getArea())
                .condominio(salaoSalvo.getCondominioEntity().getNome())
                .build();
    }

    public SalaoResponseDTO getById(Long id) {
        SalaoEntity salao = salaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salão com ID " + id + " não encontrado."));

        return SalaoResponseDTO.builder()
                .id(salao.getId())
                .area(salao.getArea())
                .condominio(salao.getCondominioEntity().getNome())
                .build();
    }





    public void deleteSalaoById(Long id){
//        if(!salaoRepository.existsById(id)){
//            throw new EntityNotFoundException("Salão com ID " + id + " não encontrado.");
//        }
        salaoRepository.deleteById(id);
    }

//    public SalaoResponseDTO putById(Long id, SalaoRequestDTO requestDTO) {
//
//    }
}
