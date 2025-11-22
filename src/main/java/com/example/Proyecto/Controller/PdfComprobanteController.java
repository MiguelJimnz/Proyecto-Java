package com.example.Proyecto.Controller;


import com.example.Proyecto.Model.Pago;
import com.example.Proyecto.Service.PagoService;
import com.example.Proyecto.Service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comprobantes")
public class PdfComprobanteController {
    @Autowired
    private PagoService pagoService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping("/pago/{pagoId}")
    public ResponseEntity<byte[]> generarComprobantePago(@PathVariable Long pagoId) {
        try {
            System.out.println("🎯 Generando comprobante PDF para pago: " + pagoId);

            // Verificar que el pago existe
            Pago pago = pagoService.findById(pagoId);
            if (pago == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] pdfBytes = pdfGeneratorService.generarPdfPago(pagoId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("comprobante-pago-" + pagoId + ".pdf")
                    .build());

            System.out.println("✅ PDF generado exitosamente - " + pdfBytes.length + " bytes");
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("❌ Error generando PDF: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
