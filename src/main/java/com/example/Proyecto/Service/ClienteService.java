package com.example.Proyecto.Service;

import com.example.Proyecto.Model.Cliente;
import com.example.Proyecto.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;


    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente actualizar(Long id, Cliente clienteNuevo) {
        Cliente existente = buscarPorId(id);
        if (existente != null) {
            existente.setNombre(clienteNuevo.getNombre());
            existente.setTelefono(clienteNuevo.getTelefono());
            existente.setDireccion(clienteNuevo.getDireccion());
            existente.setCorreoElectronico(clienteNuevo.getCorreoElectronico());
            return clienteRepository.save(existente);
        }
        return null;
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

}
