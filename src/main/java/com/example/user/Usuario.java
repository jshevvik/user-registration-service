package com.example.user;
import java.util.Objects;

public class Usuario {
    private final String id;
    private final String email;
    private final String password;

    public Usuario(String id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Usuario))
            return false;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(email, usuario.email) &&
                Objects.equals(password, usuario.password);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, email, password);
    }
}
