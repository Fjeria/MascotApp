package com.mascotapp.ms_citas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-mascotas")
public interface MascotaClient {
    @GetMapping("/api/mascotas/{id}")
    Object obtenerMascotaPorId(@PathVariable("id") Long id);
}