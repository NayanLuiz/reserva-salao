package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.SalaoRequestDTO;
import br.com.nayan.reserva_salao.dto.SalaoResponseDTO;
import br.com.nayan.reserva_salao.entity.Salao;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
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
        // Validações básicas
        if(Objects.isNull(salaoDTO.getArea()) || salaoDTO.getArea().isBlank()){
            throw new IllegalArgumentException("A área do salão não pode ser nula ou vazia");
        }
        if (Objects.isNull(salaoDTO.getCondominio())) {
            throw new IllegalArgumentException("O salão deve estar associado a um condomínio");
    }
        condominioService.getByName(salaoDTO.getCondominio());

        Salao salao = new Salao();
        salao.setArea(salaoDTO.getArea());
        salao.setCondominio(condominioService.getByName(salaoDTO.getCondominio()));
        Salao salaoSalvo = salaoRepository.save(salao);

        return SalaoResponseDTO.builder()
                .id(salaoSalvo.getId())
                .area(salaoSalvo.getArea())
                .condominio(salaoSalvo.getCondominio().getNome())
                .build();
    }
}
