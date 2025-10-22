package br.com.nayan.reserva_salao.service;

import br.com.nayan.reserva_salao.dto.CasaResponseDTO;
import br.com.nayan.reserva_salao.dto.CondominioRequestDTO;
import br.com.nayan.reserva_salao.dto.CondominioResponseDTO;
import br.com.nayan.reserva_salao.dto.SalaoResponseDTO;
import br.com.nayan.reserva_salao.entity.Casa;
import br.com.nayan.reserva_salao.entity.Condominio;
import br.com.nayan.reserva_salao.entity.Salao;
import br.com.nayan.reserva_salao.repository.CasaRepository;
import br.com.nayan.reserva_salao.repository.CondominioRepository;
import br.com.nayan.reserva_salao.repository.SalaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        Condominio novoCondominio = new Condominio();
        novoCondominio.setNome(condominioDTO.getNome());
        novoCondominio.setSalao(new ArrayList<Salao>());
        novoCondominio.setCasa(new ArrayList<Casa>());

        Condominio condominioSalvo = condominioRepository.save(novoCondominio);

        List<SalaoResponseDTO> list_salao = condominioSalvo.getSalao().stream().map(salao ->{
            return SalaoResponseDTO.builder()
                    .id(salao.getId())
                    .area(salao.getArea())
                    .build();
        }).toList();

        List<CasaResponseDTO> list_casa = condominioSalvo.getCasa().stream().map(casa ->{
            return CasaResponseDTO.builder()
                    .id(casa.getId())
                    .numero(casa.getNumero())
                    .responsavel(casa.getResponsavel())
                    .build();
        }).toList();

        return CondominioResponseDTO.builder()
                .id(condominioSalvo.getId())
                .nome(condominioSalvo.getNome())
                .salao(list_salao)
                .casa(list_casa)
                .build();
    }

    public Condominio getByName(String nome) {
        return condominioRepository.findByNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Condominio nao encontrado."));
    }
}

