package cl.usm.fidelidad.service;

import cl.usm.fidelidad.model.Cliente;
import cl.usm.fidelidad.model.Compra;
import cl.usm.fidelidad.model.Nivel;
import cl.usm.fidelidad.repository.ClienteRepository;
import cl.usm.fidelidad.repository.CompraRepository;

import java.time.LocalDate;
import java.util.List;

public class FidelidadService {
    private final ClienteRepository clienteRepository;
    private final CompraRepository compraRepository;
    
    private static final double MONTO_POR_PUNTO = 100.0;
    private static final int BONUS_COMPRAS_SEGUIDAS = 10;
    private static final int COMPRAS_PARA_BONUS = 3;
    
    public FidelidadService(ClienteRepository clienteRepository, CompraRepository compraRepository) {
        this.clienteRepository = clienteRepository;
        this.compraRepository = compraRepository;
    }
    
    public boolean registrarCliente(String id, String nombre, String correo) {
        if (id == null || nombre == null || correo == null || 
            id.trim().isEmpty() || nombre.trim().isEmpty() || correo.trim().isEmpty()) {
            return false;
        }
        
        Cliente cliente = new Cliente(id.trim(), nombre.trim(), correo.trim());
        
        if (!cliente.esCorreoValido()) {
            return false;
        }
        
        return clienteRepository.guardar(cliente);
    }
    
    public int registrarCompra(String idCompra, String idCliente, double monto, LocalDate fecha) {
        if (idCompra == null || idCliente == null || monto <= 0 || fecha == null
            || idCompra.trim().isEmpty() || idCliente.trim().isEmpty()) {
            return -1;
        }
        
        Cliente cliente = clienteRepository.buscarPorId(idCliente);
        if (cliente == null) {
            return -1;
        }
        
        if (compraRepository.existe(idCompra)) {
            return -1;
        }
        
        int puntosBase = calcularPuntosBase(monto);
        
        double multiplicador = cliente.getNivel().getMultiplicador();
        int puntosConMultiplicador = (int) Math.round(puntosBase * multiplicador);
        
        int bonus = calcularBonusComprasSeguidas(idCliente, fecha);
        
        int puntosTotales = puntosConMultiplicador + bonus;
        
        Compra compra = new Compra(idCompra, idCliente, monto, fecha, puntosTotales);
        if (!compraRepository.guardar(compra)) {
            return -1;
        }
        
        cliente.agregarPuntos(puntosTotales);
        clienteRepository.actualizar(cliente);
        
        return puntosTotales;
    }
    
    private int calcularPuntosBase(double monto) {
        return (int) (monto / MONTO_POR_PUNTO);
    }
    
    private int calcularBonusComprasSeguidas(String idCliente, LocalDate fecha) {
        List<Compra> comprasDelDia = compraRepository.buscarPorClienteYFecha(idCliente, fecha);
        
        if (comprasDelDia.size() == COMPRAS_PARA_BONUS - 1) {
            return BONUS_COMPRAS_SEGUIDAS;
        }
        
        return 0;
    }
    
    public Cliente obtenerCliente(String idCliente) {
        return clienteRepository.buscarPorId(idCliente);
    }
    
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.obtenerTodos();
    }
    
    public boolean actualizarCliente(Cliente cliente) {
        if (cliente == null || !cliente.esCorreoValido()) {
            return false;
        }
        
        return clienteRepository.actualizar(cliente);
    }
    
    public boolean eliminarCliente(String idCliente) {
        return clienteRepository.eliminar(idCliente);
    }
    
    public List<Compra> obtenerComprasCliente(String idCliente) {
        return compraRepository.buscarPorCliente(idCliente);
    }
    
    public List<Compra> obtenerTodasLasCompras() {
        return compraRepository.obtenerTodas();
    }
    
    public List<Cliente> buscarClientesPorNombre(String nombre) {
        return clienteRepository.buscarPorNombre(nombre);
    }
    
    public String obtenerResumenCliente(String idCliente) {
        Cliente cliente = clienteRepository.buscarPorId(idCliente);
        if (cliente == null) {
            return "Cliente no encontrado.";
        }
        
        List<Compra> compras = compraRepository.buscarPorCliente(idCliente);
        int totalCompras = compras.size();
        double totalGastado = compras.stream().mapToDouble(Compra::getMonto).sum();
        
        return String.format("Cliente: %s (%s)\n" +
                           "Correo: %s\n" +
                           "Puntos actuales: %d\n" +
                           "Nivel: %s\n" +
                           "Total de compras: %d\n" +
                           "Total gastado: $%.2f",
                           cliente.getNombre(), cliente.getId(),
                           cliente.getCorreo(),
                           cliente.getPuntos(),
                           cliente.getNivel().getNombre(),
                           totalCompras,
                           totalGastado);
    }
} 