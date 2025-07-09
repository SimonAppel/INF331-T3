package cl.usm.fidelidad.ui;

import cl.usm.fidelidad.model.Cliente;
import cl.usm.fidelidad.model.Compra;
import cl.usm.fidelidad.service.FidelidadService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private final FidelidadService fidelidadService;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;
    
    public CLI(FidelidadService fidelidadService) {
        this.fidelidadService = fidelidadService;
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public void iniciar() {
        System.out.println("=== SISTEMA DE TARJETA DE FIDELIDAD GAMIFICADA ===");
        System.out.println("Bienvenido al sistema de gestión de fidelidad\n");
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    gestionarClientes();
                    break;
                case 2:
                    gestionarCompras();
                    break;
                case 3:
                    mostrarPuntosNivel();
                    break;
                case 4:
                    continuar = false;
                    System.out.println("¡Gracias por usar el sistema de fidelidad!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
            
            if (continuar) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Gestión de Clientes");
        System.out.println("2. Gestión de Compras");
        System.out.println("3. Mostrar Puntos / Nivel de un Cliente");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void gestionarClientes() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== GESTIÓN DE CLIENTES ===");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar Cliente");
            System.out.println("4. Actualizar Cliente");
            System.out.println("5. Eliminar Cliente");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarCliente();
                    break;
                case 4:
                    actualizarCliente();
                    break;
                case 5:
                    eliminarCliente();
                    break;
                case 6:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
    
    private void agregarCliente() {
        System.out.println("\n--- AGREGAR CLIENTE ---");
        
        System.out.print("ID del cliente: ");
        String id = scanner.nextLine().trim();
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine().trim();
        
        if (fidelidadService.registrarCliente(id, nombre, correo)) {
            System.out.println("Cliente registrado exitosamente.");
        } else {
            System.out.println("Error al registrar cliente. Verifique los datos ingresados.");
        }
    }
    
    private void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        List<Cliente> clientes = fidelidadService.obtenerTodosLosClientes();
        
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.printf("%-10s %-20s %-25s %-10s %-10s%n", 
                            "ID", "Nombre", "Correo", "Puntos", "Nivel");
            System.out.println("-".repeat(75));
            
            for (Cliente cliente : clientes) {
                System.out.printf("%-10s %-20s %-25s %-10d %-10s%n",
                                cliente.getId(),
                                cliente.getNombre(),
                                cliente.getCorreo(),
                                cliente.getPuntos(),
                                cliente.getNivel().getNombre());
            }
        }
    }
    
    private void buscarCliente() {
        System.out.println("\n--- BUSCAR CLIENTE ---");
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine().trim();
        
        List<Cliente> clientes = fidelidadService.buscarClientesPorNombre(nombre);
        
        if (clientes.isEmpty()) {
            System.out.println("No se encontraron clientes con ese nombre.");
        } else {
            System.out.println("Clientes encontrados:");
            for (Cliente cliente : clientes) {
                System.out.printf("- %s (%s) - %s - %d puntos - %s%n",
                                cliente.getNombre(), cliente.getId(),
                                cliente.getCorreo(), cliente.getPuntos(),
                                cliente.getNivel().getNombre());
            }
        }
    }
    
    private void actualizarCliente() {
        System.out.println("\n--- ACTUALIZAR CLIENTE ---");
        System.out.print("ID del cliente a actualizar: ");
        String id = scanner.nextLine().trim();
        
        Cliente cliente = fidelidadService.obtenerCliente(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        
        System.out.println("Cliente actual: " + cliente.getNombre());
        System.out.print("Nuevo nombre (Enter para mantener actual): ");
        String nuevoNombre = scanner.nextLine().trim();
        if (!nuevoNombre.isEmpty()) {
            cliente.setNombre(nuevoNombre);
        }
        
        System.out.print("Nuevo correo (Enter para mantener actual): ");
        String nuevoCorreo = scanner.nextLine().trim();
        if (!nuevoCorreo.isEmpty()) {
            cliente.setCorreo(nuevoCorreo);
        }
        
        if (fidelidadService.actualizarCliente(cliente)) {
            System.out.println("Cliente actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar cliente.");
        }
    }
    
    private void eliminarCliente() {
        System.out.println("\n--- ELIMINAR CLIENTE ---");
        System.out.print("ID del cliente a eliminar: ");
        String id = scanner.nextLine().trim();
        
        System.out.print("¿Está seguro? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        
        if (confirmacion.equals("s") || confirmacion.equals("si")) {
            if (fidelidadService.eliminarCliente(id)) {
                System.out.println("Cliente eliminado exitosamente.");
            } else {
                System.out.println("Error al eliminar cliente o cliente no encontrado.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    
    private void gestionarCompras() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== GESTIÓN DE COMPRAS ===");
            System.out.println("1. Registrar Compra");
            System.out.println("2. Listar Compras");
            System.out.println("3. Buscar Compras por Cliente");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    registrarCompra();
                    break;
                case 2:
                    listarCompras();
                    break;
                case 3:
                    buscarComprasPorCliente();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
    
    private void registrarCompra() {
        System.out.println("\n--- REGISTRAR COMPRA ---");
        
        System.out.print("ID de la compra: ");
        String idCompra = scanner.nextLine().trim();
        
        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine().trim();
        
        System.out.print("Monto de la compra: ");
        double monto;
        try {
            monto = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
            return;
        }
        
        System.out.print("Fecha (dd/MM/yyyy) o Enter para hoy: ");
        String fechaStr = scanner.nextLine().trim();
        LocalDate fecha;
        
        if (fechaStr.isEmpty()) {
            fecha = LocalDate.now();
        } else {
            try {
                fecha = LocalDate.parse(fechaStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use dd/MM/yyyy");
                return;
            }
        }
        
        int puntos = fidelidadService.registrarCompra(idCompra, idCliente, monto, fecha);
        
        if (puntos >= 0) {
            System.out.printf("Compra registrada exitosamente. Puntos generados: %d%n", puntos);
        } else {
            System.out.println("Error al registrar compra. Verifique los datos ingresados.");
        }
    }
    
    private void listarCompras() {
        System.out.println("\n--- LISTA DE COMPRAS ---");
        List<Compra> compras = fidelidadService.obtenerTodasLasCompras();
        
        if (compras.isEmpty()) {
            System.out.println("No hay compras registradas.");
        } else {
            System.out.printf("%-15s %-10s %-10s %-12s %-10s%n",
                            "ID Compra", "ID Cliente", "Monto", "Fecha", "Puntos");
            System.out.println("-".repeat(60));
            
            for (Compra compra : compras) {
                System.out.printf("%-15s %-10s %-10.2f %-12s %-10d%n",
                                compra.getIdCompra(),
                                compra.getIdCliente(),
                                compra.getMonto(),
                                compra.getFecha().format(dateFormatter),
                                compra.getPuntosGenerados());
            }
        }
    }
    
    private void buscarComprasPorCliente() {
        System.out.println("\n--- BUSCAR COMPRAS POR CLIENTE ---");
        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine().trim();
        
        List<Compra> compras = fidelidadService.obtenerComprasCliente(idCliente);
        
        if (compras.isEmpty()) {
            System.out.println("No se encontraron compras para este cliente.");
        } else {
            System.out.printf("Compras del cliente %s:%n", idCliente);
            System.out.printf("%-15s %-10s %-12s %-10s%n",
                            "ID Compra", "Monto", "Fecha", "Puntos");
            System.out.println("-".repeat(50));
            
            for (Compra compra : compras) {
                System.out.printf("%-15s %-10.2f %-12s %-10d%n",
                                compra.getIdCompra(),
                                compra.getMonto(),
                                compra.getFecha().format(dateFormatter),
                                compra.getPuntosGenerados());
            }
        }
    }
    
    private void mostrarPuntosNivel() {
        System.out.println("\n--- MOSTRAR PUNTOS Y NIVEL ---");
        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine().trim();
        
        String resumen = fidelidadService.obtenerResumenCliente(idCliente);
        System.out.println("\n" + resumen);
    }
} 