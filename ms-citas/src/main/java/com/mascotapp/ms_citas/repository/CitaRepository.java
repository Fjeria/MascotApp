package com.mascotapp.ms_citas.repository;

import com.mascotapp.ms_citas.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    // Para validar que el veterinario no tenga otra cita a esa misma hora
    Optional<Cita> findByFechaHoraAndIdVeterinario(LocalDateTime fechaHora, Long idVeterinario);
}