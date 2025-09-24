package com.example.user;

import java.util.*;

public class ServicioUsuario {

    public final RepositorioUsuario repositorio;
    public final PasswordHasher hasher;
    public final EnvioEmail envioEmail;

    public ServicioUsuario(RepositorioUsuario repositorio, PasswordHasher hasher, EnvioEmail envioEmail){
        this.repositorio = repositorio;
        this.hasher = hasher;
        this.envioEmail = envioEmail;
    }

    public Usuario registro(String email, String rawPassword){
        validar(email, rawPassword);

        Optional<Usuario> existing = repositorio.findByEmail(email);
        if (existing.isPresent()){
            throw new IllegalArgumentException("Email ya registrado");
        }

        String hash = hasher.hash(rawPassword);
        Usuario usuario = new Usuario(UUID.randomUUID().toString(), email, hash);

        repositorio.save(usuario);
        envioEmail.enviarBienbenidoEmail(email);

        return usuario;
    }

    private void validar(String email, String rawPassword) {
        if (email == null || !email.contains("@")){
            throw new IllegalArgumentException("Email invalido");
        }
        if (rawPassword == null || rawPassword.length()< 6){
            throw new IllegalArgumentException("Contraseña demasiado corta");
        }
    }
}
