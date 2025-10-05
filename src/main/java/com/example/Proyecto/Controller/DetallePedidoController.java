package com.example.Proyecto.Controller;

import com.example.Proyecto.Model.DetallePedido;
import com.example.Proyecto.Repository.DetallePedidoRepository;
import com.example.Proyecto.Service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetallePedidoController {
    @Autowired
    private DetallePedidoService service;

    @GetMapping("/pedido/{pedidoId}")
    public List<DetallePedido> porPedido(@PathVariable Long pedidoId) {
        return service.listarPorPedido(pedidoId);
    }

    @PostMapping
    public DetallePedido crear(@RequestBody DetallePedido detalle) {
        return service.guardar(detalle);
    }

    @PutMapping("/{id}")
    public DetallePedido actualizar(@PathVariable Long id, @RequestBody DetallePedido detalle) {
        detalle.setDetalleId(id);
        return service.guardar(detalle);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
