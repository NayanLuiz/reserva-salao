package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.CondominioRequestDTO;
import br.com.nayan.reserva_salao.dto.CondominioResponseDTO;
import br.com.nayan.reserva_salao.service.CondominioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<CondominioResponseDTO> getCondominioById(@PathVariable Long id){
        CondominioResponseDTO condominioResponseDTO = condominioService.getById(id);
        return ResponseEntity.ok(condominioResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CondominioResponseDTO> deleteCondominioById(@PathVariable Long id){
        condominioService.deleteCondominioById(id);
        return ResponseEntity.noContent().build();
    }
}
