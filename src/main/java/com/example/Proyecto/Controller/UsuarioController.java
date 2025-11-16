package com.example.Proyecto.Controller;


import com.example.Proyecto.Configuration.AuthResponse;
import com.example.Proyecto.Model.Usuarios;
import com.example.Proyecto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuarios usuario) {
        try {
            Usuarios usuarioRegistrado = usuarioService.registrar(usuario);
            return ResponseEntity.ok(usuarioRegistrado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Error interno del servidor")
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String correo = body.get("correo");
            String password = body.get("password");

            if (correo == null || password == null) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Correo y contraseña son obligatorios")
                );
            }

            AuthResponse auth = usuarioService.login(correo, password);
            return ResponseEntity.ok(auth);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Error interno del servidor")
            );
        }
    }
}
