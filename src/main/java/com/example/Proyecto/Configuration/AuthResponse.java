package com.example.Proyecto.Configuration;

import com.example.Proyecto.Model.Usuarios;

public class AuthResponse {

    private String token;
    private Usuarios usuario;

    public AuthResponse(String token, Usuarios usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
