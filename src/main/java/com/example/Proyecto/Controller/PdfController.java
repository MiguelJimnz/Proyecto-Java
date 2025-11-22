package com.example.Proyecto.Controller;


import com.example.Proyecto.Model.Pago;
import com.example.Proyecto.Service.PagoService;
import com.example.Proyecto.Service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "*")
public class PdfController {

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private PagoService pagoService; // ✅ Inyectar el servicio

    @GetMapping("/pago/{pagoId}")
    public ResponseEntity<byte[]> generarComprobantePago(@PathVariable Long pagoId) {
        try {
            System.out.println("📄 Generando PDF para pago: " + pagoId);

            // ✅ CORREGIDO: Usar pagoService en lugar de PagoService.findById
            Pago pago = pagoService.findById(pagoId);
            if (pago == null) {
                System.out.println("❌ Pago no encontrado: " + pagoId);
                return ResponseEntity.notFound().build();
            }

            // Generar PDF
            byte[] pdfBytes = pdfGeneratorService.generarPdfPago(pagoId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.inline() // inline para visualizar, attachment para descargar
                            .filename("comprobante-pago-" + pagoId + ".pdf")
                            .build()
            );
            headers.setCacheControl("no-cache, no-store, must-revalidate");
            headers.setPragma("no-cache");
            headers.setExpires(0);

            System.out.println("✅ PDF generado: " + pdfBytes.length + " bytes");
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("❌ Error generando PDF: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/transacciones")
    public ResponseEntity<byte[]> generarReporteTransacciones(
            @RequestParam(required = false) String inicio,
            @RequestParam(required = false) String fin) {
        try {
            System.out.println("📄 Generando reporte de transacciones");

            LocalDate fechaInicio = inicio != null ? LocalDate.parse(inicio) : LocalDate.now().minusMonths(1);
            LocalDate fechaFin = fin != null ? LocalDate.parse(fin) : LocalDate.now();

            byte[] pdfBytes = pdfGeneratorService.generarReporteTransacciones(fechaInicio, fechaFin);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.attachment()
                            .filename("reporte-transacciones.pdf")
                            .build()
            );

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("❌ Error generando reporte: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}