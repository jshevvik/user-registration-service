package com.example.user;
import java.util.Optional;

public interface RepositorioUsuario {
    Optional<Usuario> findByEmail(String email);
    void save(Usuario usuario);
}
