package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.CasaRequestDTO;
import br.com.nayan.reserva_salao.dto.CasaResponseDTO;
import br.com.nayan.reserva_salao.service.CasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(casaResponseDTO);
    }
}