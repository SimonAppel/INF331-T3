package cl.usm.fidelidad;

import cl.usm.fidelidad.repository.ClienteRepository;
import cl.usm.fidelidad.repository.CompraRepository;
import cl.usm.fidelidad.service.FidelidadService;
import cl.usm.fidelidad.ui.CLI;

public class App {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Tarjeta de Fidelidad Gamificada...");
        
        ClienteRepository clienteRepository = new ClienteRepository();
        CompraRepository compraRepository = new CompraRepository();
        
        FidelidadService fidelidadService = new FidelidadService(clienteRepository, compraRepository);
        
        CLI cli = new CLI(fidelidadService);
        cli.iniciar();
    }
}
