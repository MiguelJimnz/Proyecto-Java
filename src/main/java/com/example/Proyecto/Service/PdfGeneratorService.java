package com.example.Proyecto.Service;

import com.example.Proyecto.Model.TipoTransaccion;
import com.example.Proyecto.Model.Transacciones;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class PdfGeneratorService {
    @Autowired
    private TransaccionService transaccionService;

    public byte[] generarReporteTransacciones(LocalDate inicio, LocalDate fin) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Configurar página
            pdfDoc.setDefaultPageSize(PageSize.A4);

            // TÍTULO
            Paragraph titulo = new Paragraph("Reporte de Transacciones - Sastrería Shalome")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(titulo);

            // PERIODO
            if (inicio != null && fin != null) {
                Paragraph periodo = new Paragraph("Período: " + inicio + " a " + fin)
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(15);
                document.add(periodo);
            }

            // OBTENER DATOS
            Map<String, Object> resumen = transaccionService.obtenerResumenTransacciones(
                    inicio != null ? inicio : LocalDate.now().minusMonths(1),
                    fin != null ? fin : LocalDate.now()
            );

            @SuppressWarnings("unchecked")
            List<Transacciones> transacciones = (List<Transacciones>) resumen.get("transacciones");

            // RESUMEN FINANCIERO
            agregarResumenFinanciero(document, resumen);

            // TABLA DE TRANSACCIONES
            if (!transacciones.isEmpty()) {
                agregarTablaTransacciones(document, transacciones);
            } else {
                document.add(new Paragraph("No hay transacciones en el período seleccionado")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontColor(ColorConstants.RED));
            }

            // PIE DE PÁGINA
            document.add(new Paragraph("\n\nGenerado el: " + LocalDateTime.now())
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.RIGHT));

            document.close();
            return baos.toByteArray();
        }
    }

    private void agregarResumenFinanciero(Document document, Map<String, Object> resumen) {
        // Crear tabla de resumen - CORREGIDO
        Table tablaResumen = new Table(UnitValue.createPercentArray(new float[]{60, 40}));
        tablaResumen.setWidth(UnitValue.createPercentValue(80));
        tablaResumen.setHorizontalAlignment(HorizontalAlignment.CENTER);
        tablaResumen.setMarginBottom(20);

        // Encabezados - CORREGIDO
        Cell headerConcepto = new Cell()
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .add(new Paragraph("Concepto").setBold());
        tablaResumen.addHeaderCell(headerConcepto);

        Cell headerMonto = new Cell()
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .add(new Paragraph("Monto").setBold());
        tablaResumen.addHeaderCell(headerMonto);

        // Datos - CORREGIDO
        agregarFilaResumen(tablaResumen, "Total Ingresos", (Double) resumen.get("totalIngresos"), ColorConstants.GREEN);
        agregarFilaResumen(tablaResumen, "Total Egresos", (Double) resumen.get("totalEgresos"), ColorConstants.RED);
        agregarFilaResumen(tablaResumen, "Balance Final", (Double) resumen.get("balance"),
                ((Double) resumen.get("balance")) >= 0 ? ColorConstants.BLUE : ColorConstants.RED);

        document.add(tablaResumen);
    }

    private void agregarFilaResumen(Table tabla, String concepto, Double monto, Color color) {
        // Celda concepto - CORREGIDO
        Cell cellConcepto = new Cell().add(new Paragraph(concepto));
        tabla.addCell(cellConcepto);

        // Celda monto - CORREGIDO
        Cell cellMonto = new Cell()
                .add(new Paragraph(String.format("$%,.2f", monto)))
                .setFontColor(color)
                .setTextAlignment(TextAlignment.RIGHT);
        tabla.addCell(cellMonto);
    }

    private void agregarTablaTransacciones(Document document, List<Transacciones> transacciones) {
        document.add(new Paragraph("Detalle de Transacciones:")
                .setBold()
                .setMarginBottom(10));

        // Crear tabla - CORREGIDO
        Table tabla = new Table(UnitValue.createPercentArray(new float[]{15, 10, 25, 15, 15, 10}));
        tabla.setWidth(UnitValue.createPercentValue(100));
        tabla.setMarginBottom(20);

        // Encabezados - CORREGIDO
        String[] headers = {"Fecha", "Tipo", "Descripción", "Empleado", "Monto", "Pago ID"};
        for (String header : headers) {
            Cell cell = new Cell()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(header).setBold());
            tabla.addHeaderCell(cell);
        }

        // Datos - CORREGIDO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Transacciones trans : transacciones) {
            // Fecha
            tabla.addCell(new Cell().add(new Paragraph(trans.getFecha().format(formatter))));

            // Tipo
            Cell cellTipo = new Cell()
                    .add(new Paragraph(trans.getTipo().toString()))
                    .setFontColor(trans.getTipo() == TipoTransaccion.INGRESO ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER);
            tabla.addCell(cellTipo);

            // Descripción
            tabla.addCell(new Cell().add(new Paragraph(trans.getDescripcion())));

            // Empleado
            tabla.addCell(new Cell().add(new Paragraph(
                    trans.getEmpleado() != null ? trans.getEmpleado().getNombre() : "N/A"
            )));

            // Monto
            Cell cellMonto = new Cell()
                    .add(new Paragraph(String.format("$%,.2f", trans.getMonto())))
                    .setTextAlignment(TextAlignment.RIGHT);
            tabla.addCell(cellMonto);

            // Pago ID
            tabla.addCell(new Cell().add(new Paragraph(
                    trans.getPago() != null ? trans.getPago().getPagoId().toString() : "N/A"
            )));
        }

        document.add(tabla);
    }
}
