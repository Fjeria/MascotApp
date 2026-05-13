package com.mascotapp.ms_citas.service;

import com.mascotapp.ms_citas.client.MascotaClient;
import com.mascotapp.ms_citas.client.UsuarioClient;
import com.mascotapp.ms_citas.model.Cita;
import com.mascotapp.ms_citas.repository.CitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final MascotaClient mascotaClient;
    private final UsuarioClient usuarioClient;

    @Override
    public Cita agendarCita(Cita cita) {
        // 1. Validar que la mascota existe
        mascotaClient.obtenerMascotaPorId(cita.getIdMascota());

        // 2. Validar que el veterinario existe
        usuarioClient.obtenerUsuarioPorId(cita.getIdVeterinario());

        // 3. Lógica de los 30 minutos: Validar que el minuto sea 00 o 30
        int minutos = cita.getFechaHora().getMinute();
        if (minutos != 0 && minutos != 30) {
            throw new RuntimeException("Las citas solo pueden ser en bloques de 30 minutos (ej: 10:00 o 10:30)");
        }

        // 4. Validar disponibilidad del veterinario
        if (citaRepository.findByFechaHoraAndIdVeterinario(cita.getFechaHora(), cita.getIdVeterinario()).isPresent()) {
            throw new RuntimeException("El veterinario ya tiene una cita agendada para esa hora.");
        }

        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }
}