package com.mascotapp.ms_usuarios.repository;

import com.mascotapp.ms_usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Para validar que no se repitan correos
    boolean existsByEmail(String email);
}