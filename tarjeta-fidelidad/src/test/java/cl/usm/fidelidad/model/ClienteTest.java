package cl.usm.fidelidad.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la clase Cliente")
class ClienteTest {
    
    private Cliente cliente;
    
    @BeforeEach
    void setUp() {
        cliente = new Cliente("CLI001", "Juan Pérez", "juan.perez@email.com");
    }
    
    @Test
    @DisplayName("Debería crear cliente con valores por defecto")
    void deberiaCrearClienteConValoresPorDefecto() {
        assertEquals("CLI001", cliente.getId());
        assertEquals("Juan Pérez", cliente.getNombre());
        assertEquals("juan.perez@email.com", cliente.getCorreo());
        assertEquals(0, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel());
    }
    
    @Test
    @DisplayName("Debería crear cliente con puntos específicos")
    void deberiaCrearClienteConPuntosEspecificos() {
        Cliente clienteConPuntos = new Cliente("CLI002", "María García", "maria@email.com", 750);
        
        assertEquals("CLI002", clienteConPuntos.getId());
        assertEquals("María García", clienteConPuntos.getNombre());
        assertEquals("maria@email.com", clienteConPuntos.getCorreo());
        assertEquals(750, clienteConPuntos.getPuntos());
        assertEquals(Nivel.PLATA, clienteConPuntos.getNivel());
    }
    
    @Test
    @DisplayName("Debería validar correo electrónico válido")
    void deberiaValidarCorreoElectronicoValido() {
        assertTrue(cliente.esCorreoValido());
        
        cliente.setCorreo("test@domain.com");
        assertTrue(cliente.esCorreoValido());
        
        cliente.setCorreo("usuario@empresa.cl");
        assertTrue(cliente.esCorreoValido());
    }
    
    @Test
    @DisplayName("Debería validar correo electrónico inválido")
    void deberiaValidarCorreoElectronicoInvalido() {
        cliente.setCorreo("correo-sin-arroba.com");
        assertFalse(cliente.esCorreoValido());
        
        cliente.setCorreo("solo-texto");
        assertFalse(cliente.esCorreoValido());
        
        cliente.setCorreo("");
        assertFalse(cliente.esCorreoValido());
        
        cliente.setCorreo(null);
        assertFalse(cliente.esCorreoValido());
    }
    
    @Test
    @DisplayName("Debería agregar puntos y recalcular nivel")
    void deberiaAgregarPuntosYRecalcularNivel() {
        cliente.agregarPuntos(500);
        assertEquals(500, cliente.getPuntos());
        assertEquals(Nivel.PLATA, cliente.getNivel());
        
        cliente.agregarPuntos(1000);
        assertEquals(1500, cliente.getPuntos());
        assertEquals(Nivel.ORO, cliente.getNivel());
        
        cliente.agregarPuntos(1500);
        assertEquals(3000, cliente.getPuntos());
        assertEquals(Nivel.PLATINO, cliente.getNivel());
    }
    
    @Test
    @DisplayName("Debería actualizar puntos y recalcular nivel")
    void deberiaActualizarPuntosYRecalcularNivel() {
        cliente.setPuntos(2000);
        assertEquals(2000, cliente.getPuntos());
        assertEquals(Nivel.ORO, cliente.getNivel());
        
        cliente.setPuntos(100);
        assertEquals(100, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel());
    }
    
    @Test
    @DisplayName("Debería actualizar información del cliente")
    void deberiaActualizarInformacionDelCliente() {
        cliente.setNombre("Juan Carlos Pérez");
        assertEquals("Juan Carlos Pérez", cliente.getNombre());
        
        cliente.setCorreo("juan.carlos@nuevo.com");
        assertEquals("juan.carlos@nuevo.com", cliente.getCorreo());
        
        cliente.setId("CLI001-NUEVO");
        assertEquals("CLI001-NUEVO", cliente.getId());
    }
    
    @Test
    @DisplayName("Debería comparar clientes por ID")
    void deberiaCompararClientesPorId() {
        Cliente cliente1 = new Cliente("CLI001", "Juan", "juan@email.com");
        Cliente cliente2 = new Cliente("CLI001", "Juan Carlos", "juan.carlos@email.com");
        Cliente cliente3 = new Cliente("CLI002", "Juan", "juan@email.com");
        
        assertEquals(cliente1, cliente2);
        assertNotEquals(cliente1, cliente3);
        assertNotEquals(cliente2, cliente3);
    }
    
    @Test
    @DisplayName("Debería generar hashCode consistente")
    void deberiaGenerarHashCodeConsistente() {
        Cliente cliente1 = new Cliente("CLI001", "Juan", "juan@email.com");
        Cliente cliente2 = new Cliente("CLI001", "Juan Carlos", "juan.carlos@email.com");
        
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }
    
    @Test
    @DisplayName("Debería generar toString con información completa")
    void deberiaGenerarToStringConInformacionCompleta() {
        String toString = cliente.toString();
        
        assertTrue(toString.contains("CLI001"));
        assertTrue(toString.contains("Juan Pérez"));
        assertTrue(toString.contains("juan.perez@email.com"));
        assertTrue(toString.contains("0"));
        assertTrue(toString.contains("Bronce"));
    }

    @Test
    @DisplayName("Debería permitir setNivel manualmente")
    void deberiaPermitirSetNivelManualmente() {
        cliente.setNivel(Nivel.PLATINO);
        assertEquals(Nivel.PLATINO, cliente.getNivel());
    }

    @Test
    @DisplayName("Clientes con mismo ID pero distinto correo son iguales")
    void clientesConMismoIdPeroDistintoCorreoSonIguales() {
        Cliente otro = new Cliente("CLI001", "Otro", "otro@email.com");
        assertEquals(cliente, otro);
    }

    @Test
    @DisplayName("toString incluye todos los campos relevantes")
    void toStringIncluyeTodosLosCampos() {
        String s = cliente.toString();
        assertTrue(s.contains("CLI001"));
        assertTrue(s.contains("Juan Pérez"));
        assertTrue(s.contains("juan.perez@email.com"));
        assertTrue(s.contains("Bronce"));
    }
} 