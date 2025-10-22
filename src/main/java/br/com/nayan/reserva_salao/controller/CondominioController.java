package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.CondominioRequestDTO;
import br.com.nayan.reserva_salao.dto.CondominioResponseDTO;
import br.com.nayan.reserva_salao.dto.ReservaRequestDTO;
import br.com.nayan.reserva_salao.dto.ReservaResponseDTO;
import br.com.nayan.reserva_salao.service.CondominioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/condominio")
public class CondominioController{

    private final CondominioService condominioService;

    public CondominioController(CondominioService condominioService) {
        this.condominioService = condominioService;
    }

    @PostMapping
    public ResponseEntity<CondominioResponseDTO> createReserva(@RequestBody CondominioRequestDTO condominioRequestDTO) {
        CondominioResponseDTO condominioResponseDTO = condominioService.create(condominioRequestDTO);
        return ResponseEntity.ok(condominioResponseDTO);
    }

}
