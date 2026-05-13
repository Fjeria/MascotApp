package com.mascotapp.ms_usuarios.service;

import com.mascotapp.ms_usuarios.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario) throws Exception;
    List<Usuario> listarUsuarios();
    Optional<Usuario> obtenerPorId(Long id);
}