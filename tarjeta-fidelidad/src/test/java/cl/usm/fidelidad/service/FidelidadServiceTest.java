package cl.usm.fidelidad.service;

import cl.usm.fidelidad.model.Cliente;
import cl.usm.fidelidad.model.Compra;
import cl.usm.fidelidad.model.Nivel;
import cl.usm.fidelidad.repository.ClienteRepository;
import cl.usm.fidelidad.repository.CompraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@DisplayName("Tests para el servicio FidelidadService")
class FidelidadServiceTest {
    
    private FidelidadService fidelidadService;
    private ClienteRepository clienteRepository;
    private CompraRepository compraRepository;
    
    @BeforeEach
    void setUp() {
        clienteRepository = new ClienteRepository();
        compraRepository = new CompraRepository();
        fidelidadService = new FidelidadService(clienteRepository, compraRepository);
    }
    
    @Test
    @DisplayName("Debería registrar cliente válido")
    void deberiaRegistrarClienteValido() {
        boolean resultado = fidelidadService.registrarCliente("CLI001", "Juan Pérez", "juan@email.com");
        
        assertTrue(resultado);
        
        Cliente cliente = fidelidadService.obtenerCliente("CLI001");
        assertNotNull(cliente);
        assertEquals("CLI001", cliente.getId());
        assertEquals("Juan Pérez", cliente.getNombre());
        assertEquals("juan@email.com", cliente.getCorreo());
        assertEquals(0, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel());
    }
    
    @Test
    @DisplayName("No debería registrar cliente con correo inválido")
    void noDeberiaRegistrarClienteConCorreoInvalido() {
        boolean resultado = fidelidadService.registrarCliente("CLI001", "Juan Pérez", "correo-invalido");
        
        assertFalse(resultado);
        
        Cliente cliente = fidelidadService.obtenerCliente("CLI001");
        assertNull(cliente);
    }
    
    @Test
    @DisplayName("No debería registrar cliente con datos vacíos")
    void noDeberiaRegistrarClienteConDatosVacios() {
        assertFalse(fidelidadService.registrarCliente("", "Juan Pérez", "juan@email.com"));
        assertFalse(fidelidadService.registrarCliente("CLI001", "", "juan@email.com"));
        assertFalse(fidelidadService.registrarCliente("CLI001", "Juan Pérez", ""));
        assertFalse(fidelidadService.registrarCliente(null, "Juan Pérez", "juan@email.com"));
    }
    
    @Test
    @DisplayName("Debería registrar compra y calcular puntos correctamente")
    void deberiaRegistrarCompraYCalcularPuntosCorrectamente() {
        fidelidadService.registrarCliente("CLI001", "Juan Pérez", "juan@email.com");
        
        int puntos = fidelidadService.registrarCompra("COMP001", "CLI001", 300.0, LocalDate.now());
        
        assertEquals(3, puntos);
        
        Cliente cliente = fidelidadService.obtenerCliente("CLI001");
        assertEquals(3, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel());
    }
    
    @Test
    @DisplayName("Debería aplicar multiplicador de nivel correctamente")
    void deberiaAplicarMultiplicadorDeNivelCorrectamente() {
        Cliente cliente = new Cliente("CLI001", "Juan Pérez", "juan@email.com", 500);
        clienteRepository.guardar(cliente);
        
        int puntos = fidelidadService.registrarCompra("COMP001", "CLI001", 100.0, LocalDate.now());
        
        assertEquals(1, puntos);
    }
    
    @Test
    @DisplayName("Debería aplicar bonus por compras seguidas")
    void deberiaAplicarBonusPorComprasSeguidas() {
        fidelidadService.registrarCliente("CLI001", "Juan Pérez", "juan@email.com");
        
        LocalDate fecha = LocalDate.now();
        
        int puntos1 = fidelidadService.registrarCompra("COMP001", "CLI001", 100.0, fecha);
        assertEquals(1, puntos1);

        int puntos2 = fidelidadService.registrarCompra("COMP002", "CLI001", 100.0, fecha);
        assertEquals(1, puntos2);
        
        int puntos3 = fidelidadService.registrarCompra("COMP003", "CLI001", 100.0, fecha);
        assertEquals(11, puntos3);
        
        Cliente cliente = fidelidadService.obtenerCliente("CLI001");
        assertEquals(13, cliente.getPuntos());
    }
    
    @Test
    @DisplayName("No debería registrar compra para cliente inexistente")
    void noDeberiaRegistrarCompraParaClienteInexistente() {
        int puntos = fidelidadService.registrarCompra("COMP001", "CLI001", 100.0, LocalDate.now());
        
        assertEquals(-1, puntos);
    }
    
    @Test
    @DisplayName("No debería registrar compra con datos inválidos")
    void noDeberiaRegistrarCompraConDatosInvalidos() {
        fidelidadService.registrarCliente("CLI001", "Juan Pérez", "juan@email.com");
        
        assertEquals(-1, fidelidadService.registrarCompra("", "CLI001", 100.0, LocalDate.now()));
        assertEquals(-1, fidelidadService.registrarCompra("COMP001", "", 100.0, LocalDate.now()));
        assertEquals(-1, fidelidadService.registrarCompra("COMP001", "CLI001", -100.0, LocalDate.now()));
        assertEquals(-1, fidelidadService.registrarCompra("COMP001", "CLI001", 100.0, null));
    }
    
    @Test
    @DisplayName("Debería obtener resumen de cliente")
    void deberiaObtenerResumenDeCliente() {
        fidelidadService.registrarCliente("CLI001", "Juan Pérez", "juan@email.com");
        fidelidadService.registrarCompra("COMP001", "CLI001", 500.0, LocalDate.now());
        
        String resumen = fidelidadService.obtenerResumenCliente("CLI001");
        
        assertTrue(resumen.contains("Juan Pérez"));
        assertTrue(resumen.contains("CLI001"));
        assertTrue(resumen.contains("juan@email.com"));
        assertTrue(resumen.contains("5"));
        assertTrue(resumen.contains("Bronce"));
        assertTrue(resumen.contains("1"));
        assertTrue(resumen.contains("500.00"));
    }
    
    @Test
    @DisplayName("Debería retornar mensaje de error para cliente no encontrado")
    void deberiaRetornarMensajeDeErrorParaClienteNoEncontrado() {
        String resumen = fidelidadService.obtenerResumenCliente("CLI001");
        
        assertEquals("Cliente no encontrado.", resumen);
    }
    
    @Test
    @DisplayName("Debería listar todos los clientes")
    void deberiaListarTodosLosClientes() {
        fidelidadService.registrarCliente("CLI001", "Juan Pérez", "juan@email.com");
        fidelidadService.registrarCliente("CLI002", "María García", "maria@email.com");
        
        List<Cliente> clientes = fidelidadService.obtenerTodosLosClientes();
        
        assertEquals(2, clientes.size());
        assertTrue(clientes.stream().anyMatch(c -> c.getId().equals("CLI001")));
        assertTrue(clientes.stream().anyMatch(c -> c.getId().equals("CLI002")));
    }
    
    @Test
    @DisplayName("Debería buscar clientes por nombre")
    void deberiaBuscarClientesPorNombre() {
        fidelidadService.registrarCliente("CLI001", "Juan Pérez", "juan@email.com");
        fidelidadService.registrarCliente("CLI002", "Juan Carlos", "juan.carlos@email.com");
        fidelidadService.registrarCliente("CLI003", "María García", "maria@email.com");
        
        List<Cliente> clientes = fidelidadService.buscarClientesPorNombre("Juan");
        
        assertEquals(2, clientes.size());
        assertTrue(clientes.stream().allMatch(c -> c.getNombre().contains("Juan")));
    }

    @Test
    @DisplayName("No debería permitir registrar dos clientes con el mismo ID")
    void noDeberiaPermitirRegistrarClientesDuplicados() {
        assertTrue(fidelidadService.registrarCliente("CLI100", "A", "a@email.com"));
        assertFalse(fidelidadService.registrarCliente("CLI100", "B", "b@email.com"));
    }

    @Test
    @DisplayName("No debería permitir registrar dos compras con el mismo ID")
    void noDeberiaPermitirRegistrarComprasDuplicadas() {
        fidelidadService.registrarCliente("CLI200", "A", "a@email.com");
        assertEquals(1, fidelidadService.registrarCompra("COMPX", "CLI200", 100.0, LocalDate.now()));
        assertEquals(-1, fidelidadService.registrarCompra("COMPX", "CLI200", 100.0, LocalDate.now()));
    }

    @Test
    @DisplayName("Debería eliminar cliente correctamente")
    void deberiaEliminarClienteCorrectamente() {
        fidelidadService.registrarCliente("CLI300", "A", "a@email.com");
        assertTrue(fidelidadService.eliminarCliente("CLI300"));
        assertNull(fidelidadService.obtenerCliente("CLI300"));
    }

    @Test
    @DisplayName("No debería actualizar cliente con correo inválido")
    void noDeberiaActualizarClienteConCorreoInvalido() {
        fidelidadService.registrarCliente("CLI400", "A", "a@email.com");
        Cliente c = fidelidadService.obtenerCliente("CLI400");
        c.setCorreo("invalido");
        assertFalse(fidelidadService.actualizarCliente(c));
    }

    @Test
    @DisplayName("Debería recalcular nivel tras varias compras")
    void deberiaRecalcularNivelTrasVariasCompras() {
        fidelidadService.registrarCliente("CLI500", "A", "a@email.com");
        int puntosAcumulados = 0;
        Cliente c = null;
        for (int i = 0; i < 10; i++) {
            int puntos = fidelidadService.registrarCompra("C"+i, "CLI500", 500.0, LocalDate.now());
            puntosAcumulados += puntos;
            c = fidelidadService.obtenerCliente("CLI500");
        }
        assertNotNull(c);
        Nivel nivelEsperado = Nivel.determinarNivel(puntosAcumulados);
        assertEquals(nivelEsperado, c.getNivel(), "El nivel del cliente debe coincidir con los puntos acumulados");
    }
} 