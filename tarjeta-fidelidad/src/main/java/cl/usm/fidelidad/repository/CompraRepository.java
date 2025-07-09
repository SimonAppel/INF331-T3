package cl.usm.fidelidad.repository;

import cl.usm.fidelidad.model.Compra;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CompraRepository {
    private final Map<String, Compra> compras;
    
    public CompraRepository() {
        this.compras = new ConcurrentHashMap<>();
    }
    
    public boolean guardar(Compra compra) {
        if (compra == null || compra.getIdCompra() == null) {
            return false;
        }
        
        if (compras.containsKey(compra.getIdCompra())) {
            return false; // Ya existe
        }
        
        compras.put(compra.getIdCompra(), compra);
        return true;
    }
    
    public Compra buscarPorId(String idCompra) {
        return compras.get(idCompra);
    }
    
    public List<Compra> obtenerTodas() {
        return new ArrayList<>(compras.values());
    }
    
    public boolean actualizar(Compra compra) {
        if (compra == null || compra.getIdCompra() == null) {
            return false;
        }
        
        if (!compras.containsKey(compra.getIdCompra())) {
            return false; // No existe
        }
        
        compras.put(compra.getIdCompra(), compra);
        return true;
    }
    
    public boolean eliminar(String idCompra) {
        return compras.remove(idCompra) != null;
    }
    
    public boolean existe(String idCompra) {
        return compras.containsKey(idCompra);
    }
    
    public int obtenerCantidad() {
        return compras.size();
    }
    
    public List<Compra> buscarPorCliente(String idCliente) {
        if (idCliente == null) {
            return new ArrayList<>();
        }
        
        return compras.values().stream()
                .filter(compra -> idCliente.equals(compra.getIdCliente()))
                .sorted(Comparator.comparing(Compra::getFecha).reversed())
                .collect(Collectors.toList());
    }
    
    public List<Compra> buscarPorFecha(LocalDate fecha) {
        if (fecha == null) {
            return new ArrayList<>();
        }
        
        return compras.values().stream()
                .filter(compra -> fecha.equals(compra.getFecha()))
                .sorted(Comparator.comparing(Compra::getIdCompra))
                .collect(Collectors.toList());
    }

    public List<Compra> buscarPorClienteYFecha(String idCliente, LocalDate fecha) {
        if (idCliente == null || fecha == null) {
            return new ArrayList<>();
        }
        
        return compras.values().stream()
                .filter(compra -> idCliente.equals(compra.getIdCliente()) && fecha.equals(compra.getFecha()))
                .sorted(Comparator.comparing(Compra::getIdCompra))
                .collect(Collectors.toList());
    }
    
    public int obtenerTotalPuntosCliente(String idCliente) {
        if (idCliente == null) {
            return 0;
        }
        
        return compras.values().stream()
                .filter(compra -> idCliente.equals(compra.getIdCliente()))
                .mapToInt(Compra::getPuntosGenerados)
                .sum();
    }
} 