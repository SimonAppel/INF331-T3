package cl.usm.fidelidad.model;

import java.time.LocalDate;
import java.util.Objects;

public class Compra {
    private String idCompra;
    private String idCliente;
    private double monto;
    private LocalDate fecha;
    private int puntosGenerados;
    
    public Compra(String idCompra, String idCliente, double monto, LocalDate fecha) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
        this.puntosGenerados = 0;
    }
    
    public Compra(String idCompra, String idCliente, double monto, LocalDate fecha, int puntosGenerados) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
        this.puntosGenerados = puntosGenerados;
    }
    
    public String getIdCompra() {
        return idCompra;
    }
    
    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }
    
    public String getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public int getPuntosGenerados() {
        return puntosGenerados;
    }
    
    public void setPuntosGenerados(int puntosGenerados) {
        this.puntosGenerados = puntosGenerados;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Compra compra = (Compra) obj;
        return Objects.equals(idCompra, compra.idCompra);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idCompra);
    }
    
    @Override
    public String toString() {
        return String.format("Compra{idCompra='%s', idCliente='%s', monto=%.2f, fecha=%s, puntosGenerados=%d}", 
                           idCompra, idCliente, monto, fecha, puntosGenerados);
    }
} 