package br.com.nayan.reserva_salao.controller;

import br.com.nayan.reserva_salao.dto.ReservaRequestDTO;
import br.com.nayan.reserva_salao.dto.ReservaResponseDTO;
import br.com.nayan.reserva_salao.service.ReservaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reserva")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> createReserva(@RequestBody ReservaRequestDTO reservaRequestDTO) {
        ReservaResponseDTO reservaResponseDTO = reservaService.create(reservaRequestDTO);
        return ResponseEntity.ok(reservaResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> getReservaById(@PathVariable Long id){
        ReservaResponseDTO reservaResponseDTO = reservaService.getById(id);
        return ResponseEntity.ok(reservaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservaById(@PathVariable Long id) {
        reservaService.deleteReservaById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> updateReserva(@PathVariable Long id, ReservaRequestDTO requestDTO){
        ReservaResponseDTO reservaResponseDTO = reservaService.putById(id, requestDTO);
        return ResponseEntity.ok(reservaResponseDTO);
    }

}
