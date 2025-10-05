package com.example.Proyecto.Service;

import com.example.Proyecto.Model.Cliente;
import com.example.Proyecto.Model.Empleado;
import com.example.Proyecto.Model.Pedido;
import com.example.Proyecto.Repository.ClienteRepository;
import com.example.Proyecto.Repository.EmpleadoRepository;
import com.example.Proyecto.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repo;
    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private EmpleadoRepository empleadoRepo;

    public List<Pedido> findAll() { return repo.findAll(); }

    public Pedido save(Pedido p) {

        if (p.getFechaPedido() == null || p.getFechaEntregaEstimada() == null ||
                p.getEstado() == null || p.getEstado().isEmpty()) {
            throw new IllegalArgumentException("Datos del pedido incompletos");
        }


        if (p.getCliente() != null && p.getCliente().getClienteId() != null) {
            Cliente c = clienteRepo.findById(p.getCliente().getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
            p.setCliente(c);
        } else {
            throw new IllegalArgumentException("Cliente es requerido");
        }

        if (p.getEmpleado() != null && p.getEmpleado().getEmpleadoId() != null) {
            Empleado e = empleadoRepo.findById(p.getEmpleado().getEmpleadoId())
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
            p.setEmpleado(e);
        } else {
            throw new IllegalArgumentException("Empleado es requerido");
        }

        return repo.save(p);
    }
    public Pedido findById(Long id) { return repo.findById(id).orElse(null); }
    public void delete(Long id) { repo.deleteById(id); }
    public List<Pedido> findByEstado(String estado) {
        return repo.findByEstado(estado);
    }
    public List<Pedido> obtenerPedidosCompletos() {
        return repo.findAllWithClienteAndEmpleado();
    }
}
