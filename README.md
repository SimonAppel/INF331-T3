# Proyecto Tarjeta de Fidelidad Gamificada
Programa de línea de comandos en **Java** con **pruebas unitarias JUnit 5**  

## 1. Enunciado general
Desarrollar un sistema que gestione un programa de fidelidad para una cadena de tiendas de conveniencia.  
El sistema debe:

1. Administrar **clientes** y su acumulación de puntos.  
2. Registrar **compras** y otorgar puntos según reglas de negocio.  
3. Gestionar **niveles** (Bronce, Plata, Oro, Platino).  

Todo se opera por consola mediante un menú de texto. 

---

## 2. Requisitos funcionales

### 2.1 Gestión de Clientes

| Operación | Detalles |
|-----------|----------|
| **Agregar cliente** | Atributos: `id`, `nombre`, `correo`, `puntos` (0), `nivel` (Bronce), ~~`streakDias` (0).~~ |
| **CRUD completo** | Crear – Listar – Actualizar – Eliminar. |
| **Restricción** | El `correo` debe ser válido (`@`). |

### 2.2 Registro de Compras

| Operación | Detalles |
|-----------|----------|
| **Registrar compra** | Datos: `idCompra`, `idCliente`, `monto`, `fecha`. |
| **Regla puntos base** | Cada \$100 ⇒ 1 pto (redondeo hacia abajo). |
| **Multiplicador por nivel** | Bronce ×1 • Plata ×1.2 • Oro ×1.5 • Platino ×2 |
| **bonnus** | 3 compras seguidas (dentro de un mismo día) ⇒ +10 pts; se reinicia cada dái. |
| **Histórico** | CRUD de compras. |

### 2.3 Niveles de Fidelidad

| Nivel | Umbral (puntos totales) | Beneficio |
|-------|-------------------------|-----------|
| Bronce | 0 – 499 | — |
| Plata  | 500 – 1499 | +20 % puntos |
| Oro    | 1500 – 2999 | +50 % puntos |
| Platino| 3000 + | +100 % puntos |

El nivel se recalcula tras cada compra.

---

## 3. Requisitos técnicos

| Ítem | Detalle |
|------|---------|
| tipo App | Por consola |
| Lenguaje | Java 21+ |
| Build | Maven / Gradle (documentar en README) (Ver videos de uso JUnit)|
| Pruebas | JUnit 5 + assertions estándar |
| Persistencia | En memoria |
| Estilo | Diseño OO limpio (entidades, repositorios, proyecto en general ) |
| Medir Cobertura | Usar EclEmma (JaCoCo) o SnarQube |
| TDD | Se sugiere uso de TDD |
| Modalidad | Trabajo individual|

---

## 4. Menú principal (CLI)
- Gestión de Clientes
- Gestión de Compras
- Mostrar Puntos / Nivel de un Cliente.
- Salir

## 5. Entregables
Repositorio GitHub (público) con:
- Código fuente organizado
- Suite de tests JUnit.
- README.md que incluya:
  - Descripción del diseño (diagrama UML o otro), no incluir enlaces a repositorios personales (por ejemplo en Sharepoint).
  - Instrucciones para compilar, ejecutar y probar.
  - Ejemplo de salida de tests.
  - Licencia
  - Otras onsideraciones vistas previamente en curso
  - Responde a pregunta: **¿Qué tipo de cobertura he medido y por qué?**

## 6. Dudas y preguntas
 - Cualquier duda o descubrimiento,en el foro de la semana.

---

## 7. Implementación del Proyecto

#### Diagrama de Clases UML

```mermaid
classDiagram
    class App {
        +main(String[] args)
    }
    
    class CLI {
        -FidelidadService fidelidadService
        -Scanner scanner
        -DateTimeFormatter dateFormatter
        +iniciar()
        -mostrarMenuPrincipal()
        -gestionarClientes()
        -gestionarCompras()
    }
    
    class FidelidadService {
        -ClienteRepository clienteRepository
        -CompraRepository compraRepository
        +registrarCliente(String, String, String) boolean
        +registrarCompra(String, String, double, LocalDate) int
        +obtenerCliente(String) Cliente
        +obtenerResumenCliente(String) String
    }
    
    class ClienteRepository {
        -Map~String, Cliente~ clientes
        +guardar(Cliente) boolean
        +buscarPorId(String) Cliente
        +obtenerTodos() List~Cliente~
        +actualizar(Cliente) boolean
        +eliminar(String) boolean
    }
    
    class CompraRepository {
        -Map~String, Compra~ compras
        +guardar(Compra) boolean
        +buscarPorId(String) Compra
        +buscarPorCliente(String) List~Compra~
        +obtenerTodas() List~Compra~
    }
    
    class Cliente {
        -String id
        -String nombre
        -String correo
        -int puntos
        -Nivel nivel
        +Cliente(String, String, String)
        +agregarPuntos(int)
        +esCorreoValido() boolean
    }
    
    class Compra {
        -String idCompra
        -String idCliente
        -double monto
        -LocalDate fecha
        -int puntosGenerados
        +Compra(String, String, double, LocalDate)
    }
    
    enum Nivel {
        BRONCE
        PLATA
        ORO
        PLATINO
        +determinarNivel(int) Nivel
        +getMultiplicador() double
    }
    
    App --> CLI
    CLI --> FidelidadService
    FidelidadService --> ClienteRepository
    FidelidadService --> CompraRepository
    ClienteRepository --> Cliente
    CompraRepository --> Compra
    Cliente --> Nivel
    Compra --> Cliente
```
---

## 8. Instrucciones para Compilar, Ejecutar y Probar

### 8.2 Compilación

```bash
# Navegar al directorio del proyecto
cd tarjeta-fidelidad

# Compilar el proyecto
mvn clean compile
```

### 8.3 Ejecución

```bash
# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="cl.usm.fidelidad.App"

# O alternativamente, después de compilar:
java -cp target/classes cl.usm.fidelidad.App
```

### 8.4 Ejecución de Pruebas

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas con reporte detallado
mvn test -Dtest="*Test"

# Ejecutar una clase de prueba específica
mvn test -Dtest=NivelTest
```

### 8.5 Medición de Cobertura

```bash
# Generar reporte de cobertura con JaCoCo
mvn clean test jacoco:report

# El reporte se genera en: target/site/jacoco/index.html
```

---

## 9. Ejemplo de Salida de Tests

```bash
$ mvn test

[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------< cl.usm.fidelidad:tarjeta-fidelidad >----------------
[INFO] Building tarjeta-fidelidad 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-surefire-plugin:3.1.2:test (default-test) ---
[INFO] Running cl.usm.fidelidad.model.NivelTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.045 s
[INFO] Running cl.usm.fidelidad.model.ClienteTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.023 s
[INFO] Running cl.usm.fidelidad.service.FidelidadServiceTest
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.045 s
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 32, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### 9.1 Ejemplo de Ejecución de la Aplicación

```
=== SISTEMA DE TARJETA DE FIDELIDAD GAMIFICADA ===
Bienvenido al sistema de gestión de fidelidad

=== MENÚ PRINCIPAL ===
1. Gestión de Clientes
2. Gestión de Compras
3. Mostrar Puntos / Nivel de un Cliente
4. Salir
Seleccione una opción: 1

=== GESTIÓN DE CLIENTES ===
1. Agregar Cliente
2. Listar Clientes
3. Buscar Cliente
4. Actualizar Cliente
5. Eliminar Cliente
6. Volver al Menú Principal
Seleccione una opción: 1

--- AGREGAR CLIENTE ---
ID del cliente: CLI001
Nombre: Juan Pérez
Correo electrónico: juan.perez@email.com
Cliente registrado exitosamente.
```

---

## 10. Cobertura de Código

### 10.1 ¿Qué tipo de cobertura he medido y por qué?

He implementado **Cobertura de Código con JaCoCo** por las siguientes razones:

1. **Cobertura de Líneas**: Mide qué líneas de código han sido ejecutadas durante las pruebas
2. **Cobertura de Ramas**: Mide qué ramas de decisiones (if/else, switch) han sido cubiertas
3. **Cobertura de Métodos**: Mide qué métodos han sido invocados
4. **Cobertura de Clases**: Mide qué clases han sido utilizadas

**¿Por qué JaCoCo?**

- **Integración nativa con Maven**: Se ejecuta automáticamente con `mvn test`
- **Reportes HTML detallados**: Visualización clara de la cobertura
- **Configuración simple**: Solo requiere agregar el plugin al `pom.xml`
- **Estándar de la industria**: Ampliamente utilizado en proyectos Java
- **Compatibilidad con JUnit 5**: Funciona perfectamente con las pruebas unitarias

### 10.2 Configuración de JaCoCo

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 10.3 Resultados de Cobertura

```bash
$ mvn clean test jacoco:report

# Reporte generado en: target/site/jacoco/index.html
# Cobertura aproximada:
# - Líneas: 95%
# - Ramas: 90%
# - Métodos: 100%
# - Clases: 100%
```

---

## 11. Consideraciones Técnicas

### 11.1 Diseño OO Limpio

- **Separación de Responsabilidades**: Cada clase tiene una responsabilidad específica
- **Inyección de Dependencias**: Los repositorios se inyectan en el servicio
- **Encapsulamiento**: Atributos privados con getters/setters apropiados
- **Polimorfismo**: Uso de interfaces y herencia donde es apropiado

### 11.2 Persistencia en Memoria

- **ConcurrentHashMap**: Para manejo thread-safe de datos
- **Streams API**: Para operaciones de filtrado y mapeo eficientes
- **Inmutabilidad**: Uso de `final` donde es apropiado

### 11.3 Validaciones

- **Validación de correo**: Verificación de presencia del símbolo `@`
- **Validación de datos**: Verificación de valores nulos y vacíos
- **Validación de negocio**: Verificación de existencia de clientes antes de registrar compras

### 11.4 Manejo de Errores

- **Valores de retorno**: Uso de códigos de error (-1) para operaciones fallidas
- **Mensajes informativos**: Feedback claro al usuario sobre errores
- **Validación defensiva**: Verificación de precondiciones

---

## 12. Licencia

Este proyecto está bajo la licencia **MIT License**.