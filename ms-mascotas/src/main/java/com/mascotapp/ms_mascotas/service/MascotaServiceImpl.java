package com.mascotapp.ms_mascotas.service;

import com.mascotapp.ms_mascotas.client.UsuarioClient;
import com.mascotapp.ms_mascotas.model.Mascota;
import com.mascotapp.ms_mascotas.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    // Inyectamos el repositorio para guardar en MySQL
    private final MascotaRepository mascotaRepository;

    // Inyectamos el cliente Feign para "llamar por teléfono" a ms-usuarios
    private final UsuarioClient usuarioClient;

    @Override
    public Mascota registrarMascota(Mascota mascota) {
        try {
            // 1. Intentamos buscar al dueño en el ms-usuarios
            // Si el dueño no existe (retorna 404), Feign lanzará un error y saltará al 'catch'
            usuarioClient.obtenerUsuarioPorId(mascota.getIdDueno());

            // 2. Si pasa de la línea anterior, significa que el dueño SÍ existe. ¡Guardamos la mascota!
            return mascotaRepository.save(mascota);

        } catch (Exception e) {
            // Si hubo un error capturamos la excepción y devolvemos un mensaje claro
            throw new RuntimeException("Error: El dueño con ID " + mascota.getIdDueno() + " no existe o el servicio de usuarios no está disponible.");
        }
    }

    @Override
    public List<Mascota> obtenerTodas() {
        return mascotaRepository.findAll();
    }

    @Override
    public Optional<Mascota> obtenerPorId(Long id) {
        return mascotaRepository.findById(id);
    }

    @Override
    public List<Mascota> obtenerMascotasPorDueno(Long idDueno) {
        // Llama al método personalizado que creamos en tu Repository
        return mascotaRepository.findByIdDueno(idDueno);
    }

    @Override
    public void eliminarMascota(Long id) {
        mascotaRepository.deleteById(id);
    }
}