package com.example.Proyecto.Service;

import com.example.Proyecto.Model.DetallePedido;
import com.example.Proyecto.Repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository repo;

    public List<DetallePedido> listarPorPedido(Long pedidoId) {
        return repo.findByPedidoPedidoId(pedidoId);
    }

    public DetallePedido guardar(DetallePedido detalle) {
        return repo.save(detalle);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
