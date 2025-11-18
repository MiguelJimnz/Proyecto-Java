package com.example.Proyecto.Controller;


import com.example.Proyecto.Model.HorasTrabajadas;
import com.example.Proyecto.Repository.HorasTrabajadasRepository;
import com.example.Proyecto.Service.HorasTrabajadasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/horas-trabajadas")
public class HorasTrabajadasController {
    @Autowired
    private HorasTrabajadasService horasService;

    @PostMapping
    public ResponseEntity<?> registrarHoras(@RequestBody HorasTrabajadas horas) {
        try {
            HorasTrabajadas horasRegistradas = horasService.registrarHoras(horas);
            return ResponseEntity.ok(horasRegistradas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<?> obtenerHorasPorEmpleado(@PathVariable Long empleadoId) {
        try {
            List<HorasTrabajadas> horas = horasService.obtenerHorasPorEmpleado(empleadoId);
            return ResponseEntity.ok(horas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/no-verificadas")
    public ResponseEntity<List<HorasTrabajadas>> obtenerHorasNoVerificadas() {
        return ResponseEntity.ok(horasService.obtenerHorasNoVerificadas());
    }

    @PutMapping("/{idHora}/verificar")
    public ResponseEntity<?> verificarHoras(@PathVariable Long idHora) {
        try {
            HorasTrabajadas horasVerificadas = horasService.verificarHoras(idHora);
            return ResponseEntity.ok(horasVerificadas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/resumen")
    public ResponseEntity<?> obtenerResumen(
            @RequestParam Long empleadoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        try {
            Map<String, Object> resumen = horasService.obtenerResumenHoras(empleadoId, inicio, fin);
            return ResponseEntity.ok(resumen);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/resumen-general")
    public ResponseEntity<?> obtenerResumenGeneral(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        try {
            Map<String, Object> resumen = horasService.obtenerResumenGeneral(inicio, fin);
            return ResponseEntity.ok(resumen);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<HorasTrabajadas>> obtenerHorasDeHoy() {
        return ResponseEntity.ok(horasService.obtenerHorasDeHoy());
    }

    @PutMapping("/{idHora}")
    public ResponseEntity<?> actualizarHoras(@PathVariable Long idHora, @RequestBody HorasTrabajadas horas) {
        try {
            HorasTrabajadas horasActualizadas = horasService.actualizarHoras(idHora, horas);
            return ResponseEntity.ok(horasActualizadas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{idHora}")
    public ResponseEntity<?> eliminarHoras(@PathVariable Long idHora) {
        try {
            horasService.eliminarHoras(idHora);
            return ResponseEntity.ok().body(Map.of("mensaje", "Horas eliminadas correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/tipos-tarea")
    public ResponseEntity<List<Map<String, String>>> obtenerTiposTarea() {
        return ResponseEntity.ok(horasService.obtenerTiposTarea());
    }
}
