package com.mascotapp.ms_mascotas.controller;

import com.mascotapp.ms_mascotas.model.Mascota;
import com.mascotapp.ms_mascotas.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<?> registrarMascota(@Valid @RequestBody Mascota mascota) {
        try {
            Mascota nuevaMascota = mascotaService.registrarMascota(mascota);
            return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Mascota>> listarMascotas() {
        log.debug("Listando todas las mascotas");
        return ResponseEntity.ok(mascotaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMascotaPorId(@PathVariable Long id) {
        log.debug("Buscando mascota ID: {}", id);
        return mascotaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMascota(@PathVariable Long id, @Valid @RequestBody Mascota mascotaDetalles) {
        java.util.Optional<Mascota> optionalMascota = mascotaService.obtenerPorId(id);

        if (optionalMascota.isPresent()) {
            Mascota mascotaExistente = optionalMascota.get();
            mascotaExistente.setNombre(mascotaDetalles.getNombre());
            mascotaExistente.setEspecie(mascotaDetalles.getEspecie());
            mascotaExistente.setRaza(mascotaDetalles.getRaza());
            mascotaExistente.setEdad(mascotaDetalles.getEdad());

            Mascota mascotaActualizada = mascotaService.registrarMascota(mascotaExistente);
            return ResponseEntity.ok(mascotaActualizada);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mascota no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMascota(@PathVariable Long id) {
        java.util.Optional<Mascota> optionalMascota = mascotaService.obtenerPorId(id);

        if (optionalMascota.isPresent()) {
            mascotaService.eliminarMascota(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mascota no encontrada");
        }
    }
}