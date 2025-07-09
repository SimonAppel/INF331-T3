package cl.usm.fidelidad.repository;

import cl.usm.fidelidad.model.Cliente;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClienteRepository {
    private final Map<String, Cliente> clientes;
    
    public ClienteRepository() {
        this.clientes = new ConcurrentHashMap<>();
    }
    
    public boolean guardar(Cliente cliente) {
        if (cliente == null || cliente.getId() == null) {
            return false;
        }
        
        if (clientes.containsKey(cliente.getId())) {
            return false; // Ya existe
        }
        
        clientes.put(cliente.getId(), cliente);
        return true;
    }
    
    public Cliente buscarPorId(String id) {
        return clientes.get(id);
    }
    
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes.values());
    }
    
    public boolean actualizar(Cliente cliente) {
        if (cliente == null || cliente.getId() == null) {
            return false;
        }
        
        if (!clientes.containsKey(cliente.getId())) {
            return false; // No existe
        }
        
        clientes.put(cliente.getId(), cliente);
        return true;
    }
    
    public boolean eliminar(String id) {
        return clientes.remove(id) != null;
    }
    
    public boolean existe(String id) {
        return clientes.containsKey(id);
    }
    
    public int obtenerCantidad() {
        return clientes.size();
    }
    
    public List<Cliente> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String nombreBusqueda = nombre.toLowerCase().trim();
        return clientes.values().stream()
                .filter(cliente -> cliente.getNombre().toLowerCase().contains(nombreBusqueda))
                .toList();
    }
} 