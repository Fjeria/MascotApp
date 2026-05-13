package com.mascotapp.ms_mascotas.service;

import com.mascotapp.ms_mascotas.model.Mascota;
import java.util.List;
import java.util.Optional;

public interface MascotaService {

    Mascota registrarMascota(Mascota mascota);

    List<Mascota> obtenerTodas();

    Optional<Mascota> obtenerPorId(Long id);

    List<Mascota> obtenerMascotasPorDueno(Long idDueno);

    void eliminarMascota(Long id);
}