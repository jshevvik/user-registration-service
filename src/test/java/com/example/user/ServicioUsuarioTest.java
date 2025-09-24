package com.example.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioUsuarioTest{

    @Mock RepositorioUsuario repositorio;
    @Mock PasswordHasher hasher;
    @Mock EnvioEmail envioEmail;

    @InjectMocks ServicioUsuario servicio;

    @Captor ArgumentCaptor<Usuario> usuarioCaptor;

    @Test
    void  registro_creaUsuario_y_enviaEmail(){
        String email = "test@example.com";
        String raw = "secret";
        when(repositorio.findByEmail(email)).thenReturn(Optional.empty());
        when(hasher.hash(raw)).thenReturn("HASH");

        Usuario u = servicio.registro(email, raw);

        verify(repositorio).save(usuarioCaptor.capture());
        verify(envioEmail).enviarBienbenidoEmail(email);

        Usuario saved = usuarioCaptor.getValue();
        assertThat(saved.getEmail()).isEqualTo(email);
        assertThat(saved.getPassword()).isEqualTo("HASH");
        assertThat(u.getId()).isNotBlank();
    }

    @Test
    void registro_falla_siEmailDuplicado(){
        String email = "algo@mail.com";
        when(repositorio.findByEmail(email)).thenReturn(Optional.of(new Usuario("x", email, "hash")));

        assertThrows(IllegalArgumentException.class, () -> servicio.registro(email, "secret"));
        verify(repositorio, never()).save(any());
        verify(envioEmail, never()).enviarBienbenidoEmail(any());

    }

    @Test
    void registro_validaEntrada(){
        assertThrows(IllegalArgumentException.class, () -> servicio.registro("mal_email", "secret"));
        assertThrows(IllegalArgumentException.class, () -> servicio.registro("test@example.com", "1234"));

    }

    @Test
    void registrar_falla_siEmailNull() {
        assertThrows(IllegalArgumentException.class, () -> servicio.registro(null, "secreto"));
    }

    @Test
    void registrar_falla_siPasswordNull() {
        assertThrows(IllegalArgumentException.class, () -> servicio.registro("ok@example.com", null));
    }


}
