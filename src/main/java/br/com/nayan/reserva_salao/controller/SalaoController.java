package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.CasaResponseDTO;
import br.com.nayan.reserva_salao.dto.SalaoRequestDTO;
import br.com.nayan.reserva_salao.dto.SalaoResponseDTO;
import br.com.nayan.reserva_salao.service.SalaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/salao")
public class SalaoController {

    private final SalaoService salaoService;

    public SalaoController(SalaoService salaoService) {
        this.salaoService = salaoService;
    }

    @PostMapping
    public ResponseEntity<SalaoResponseDTO> createSalao(@RequestBody SalaoRequestDTO salaoRequestDTO) {
        SalaoResponseDTO salaoResponseDTO = salaoService.create(salaoRequestDTO);
        return ResponseEntity.ok(salaoResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaoResponseDTO> getSalaoById(@PathVariable Long id){
        SalaoResponseDTO salaoResponseDTO = salaoService.getById(id);
        return ResponseEntity.ok(salaoResponseDTO);
    }
}
