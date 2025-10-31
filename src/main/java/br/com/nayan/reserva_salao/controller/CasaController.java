package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.CasaRequestDTO;
import br.com.nayan.reserva_salao.dto.CasaResponseDTO;
import br.com.nayan.reserva_salao.service.CasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/casa")
public class CasaController {

    private final CasaService casaService;

    public CasaController(CasaService casaService) {
        this.casaService = casaService;
    }

    @PostMapping
    public ResponseEntity<CasaResponseDTO> createCasa(@RequestBody CasaRequestDTO casaRequestDTO) {
        CasaResponseDTO casaResponseDTO = casaService.create(casaRequestDTO);
        return ResponseEntity.status(201).body(casaResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasaResponseDTO> getCasaById(@PathVariable Long id){
        CasaResponseDTO casaResponseDTO = casaService.getById(id);
        return ResponseEntity.ok(casaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCasaById(@PathVariable Long id){
        casaService.deleteCasaById(id);
        return ResponseEntity.noContent().build();
    }
}