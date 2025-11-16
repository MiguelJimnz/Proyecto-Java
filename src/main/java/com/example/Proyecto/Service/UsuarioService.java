package com.example.Proyecto.Service;


import com.example.Proyecto.Configuration.AuthResponse;
import com.example.Proyecto.Configuration.JwtUtil;
import com.example.Proyecto.Model.Usuarios;
import com.example.Proyecto.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public Usuarios registrar(Usuarios usuario) {
        // Verificar si el correo ya existe
        if (usuariosRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Validar campos obligatorios
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new RuntimeException("El correo es obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        // Asignar rol por defecto si no viene
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            usuario.setRol("USER");
        }

        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Guardar usuario
        return usuariosRepository.save(usuario);
    }

    // MÉTODO LOGIN - ACTUALIZADO PARA INCLUIR TOKEN
    public AuthResponse login(String correo, String password) {
        Usuarios usuario = usuariosRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generar token JWT
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());

        return new AuthResponse(token, usuario);
    }
}
