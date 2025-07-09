package cl.usm.fidelidad.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

    
@DisplayName("Tests para la enumeración Nivel")
class NivelTest {
    
    @Test
    @DisplayName("Debería determinar nivel Bronce para 0 puntos")
    void deberiaDeterminarNivelBroncePara0Puntos() {
        Nivel nivel = Nivel.determinarNivel(0);
        assertEquals(Nivel.BRONCE, nivel);
    }
    
    @Test
    @DisplayName("Debería determinar nivel Bronce para 499 puntos")
    void deberiaDeterminarNivelBroncePara499Puntos() {
        Nivel nivel = Nivel.determinarNivel(499);
        assertEquals(Nivel.BRONCE, nivel);
    }
    
    @Test
    @DisplayName("Debería determinar nivel Plata para 500 puntos")
    void deberiaDeterminarNivelPlataPara500Puntos() {
        Nivel nivel = Nivel.determinarNivel(500);
        assertEquals(Nivel.PLATA, nivel);
    }
    
    @Test
    @DisplayName("Debería determinar nivel Plata para 1499 puntos")
    void deberiaDeterminarNivelPlataPara1499Puntos() {
        Nivel nivel = Nivel.determinarNivel(1499);
        assertEquals(Nivel.PLATA, nivel);
    }
    
    @Test
    @DisplayName("Debería determinar nivel Oro para 1500 puntos")
    void deberiaDeterminarNivelOroPara1500Puntos() {
        Nivel nivel = Nivel.determinarNivel(1500);
        assertEquals(Nivel.ORO, nivel);
    }
    
    @Test
    @DisplayName("Debería determinar nivel Oro para 2999 puntos")
    void deberiaDeterminarNivelOroPara2999Puntos() {
        Nivel nivel = Nivel.determinarNivel(2999);
        assertEquals(Nivel.ORO, nivel);
    }
    
    @Test
    @DisplayName("Debería determinar nivel Platino para 3000 puntos")
    void deberiaDeterminarNivelPlatinoPara3000Puntos() {
        Nivel nivel = Nivel.determinarNivel(3000);
        assertEquals(Nivel.PLATINO, nivel);
    }
    
    @Test
    @DisplayName("Debería determinar nivel Platino para 5000 puntos")
    void deberiaDeterminarNivelPlatinoPara5000Puntos() {
        Nivel nivel = Nivel.determinarNivel(5000);
        assertEquals(Nivel.PLATINO, nivel);
    }
    
    @Test
    @DisplayName("Debería tener multiplicador correcto para cada nivel")
    void deberiaTenerMultiplicadorCorrectoParaCadaNivel() {
        assertEquals(1.0, Nivel.BRONCE.getMultiplicador());
        assertEquals(1.2, Nivel.PLATA.getMultiplicador());
        assertEquals(1.5, Nivel.ORO.getMultiplicador());
        assertEquals(2.0, Nivel.PLATINO.getMultiplicador());
    }
    
    @Test
    @DisplayName("Debería tener umbral mínimo correcto para cada nivel")
    void deberiaTenerUmbralMinimoCorrectoParaCadaNivel() {
        assertEquals(0, Nivel.BRONCE.getUmbralMinimo());
        assertEquals(500, Nivel.PLATA.getUmbralMinimo());
        assertEquals(1500, Nivel.ORO.getUmbralMinimo());
        assertEquals(3000, Nivel.PLATINO.getUmbralMinimo());
    }
    
    @Test
    @DisplayName("Debería retornar nombre correcto para cada nivel")
    void deberiaRetornarNombreCorrectoParaCadaNivel() {
        assertEquals("Bronce", Nivel.BRONCE.getNombre());
        assertEquals("Plata", Nivel.PLATA.getNombre());
        assertEquals("Oro", Nivel.ORO.getNombre());
        assertEquals("Platino", Nivel.PLATINO.getNombre());
    }
    
    @Test
    @DisplayName("Debería retornar nombre en toString")
    void deberiaRetornarNombreEnToString() {
        assertEquals("Bronce", Nivel.BRONCE.toString());
        assertEquals("Plata", Nivel.PLATA.toString());
        assertEquals("Oro", Nivel.ORO.toString());
        assertEquals("Platino", Nivel.PLATINO.toString());
    }

    @Test
    @DisplayName("Debería retornar Bronce para puntos negativos")
    void deberiaRetornarBronceParaPuntosNegativos() {
        assertEquals(Nivel.BRONCE, Nivel.determinarNivel(-100));
        assertEquals(Nivel.BRONCE, Nivel.determinarNivel(-1));
    }

    @Test
    @DisplayName("Debería cambiar de nivel justo en los umbrales")
    void deberiaCambiarDeNivelEnUmbrales() {
        assertEquals(Nivel.BRONCE, Nivel.determinarNivel(499));
        assertEquals(Nivel.PLATA, Nivel.determinarNivel(500));
        assertEquals(Nivel.PLATA, Nivel.determinarNivel(1499));
        assertEquals(Nivel.ORO, Nivel.determinarNivel(1500));
        assertEquals(Nivel.ORO, Nivel.determinarNivel(2999));
        assertEquals(Nivel.PLATINO, Nivel.determinarNivel(3000));
    }
} 