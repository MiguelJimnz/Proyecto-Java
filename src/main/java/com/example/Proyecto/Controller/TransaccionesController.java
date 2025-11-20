package com.example.Proyecto.Controller;


import com.example.Proyecto.Model.Transacciones;
import com.example.Proyecto.Repository.TransaccionRepository;
import com.example.Proyecto.Service.PdfGeneratorService;
import com.example.Proyecto.Service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionesController {
    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping
    public List<Transacciones> obtenerTodasLasTransacciones() {
        return transaccionService.obtenerTodasLasTransacciones();
    }

    @PostMapping
    public Transacciones registrarTransaccion(@RequestBody Transacciones transaccion) {
        return transaccionService.registrarTransaccion(transaccion);
    }

    @GetMapping("/resumen")
    public Map<String, Object> obtenerResumen(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return transaccionService.obtenerResumenTransacciones(inicio, fin);
    }

    // GENERAR PDF - Endpoint principal
    @GetMapping("/reporte-pdf")
    public ResponseEntity<byte[]> generarReportePdf(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        try {
            byte[] pdfBytes = pdfGeneratorService.generarReporteTransacciones(inicio, fin);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("reporte-transacciones-" + LocalDate.now() + ".pdf")
                    .build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
