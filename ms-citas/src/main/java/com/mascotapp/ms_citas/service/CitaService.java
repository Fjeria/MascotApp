package com.mascotapp.ms_citas.service;

import com.mascotapp.ms_citas.model.Cita;
import java.util.List;

public interface CitaService {
    Cita agendarCita(Cita cita);
    List<Cita> listarTodas();
}