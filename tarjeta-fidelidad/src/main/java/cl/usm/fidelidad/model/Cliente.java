package cl.usm.fidelidad.model;

import java.util.Objects;

public class Cliente {
    private String id;
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;
    
    public Cliente(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
    }
    
    public Cliente(String id, String nombre, String correo, int puntos) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = puntos;
        this.nivel = Nivel.determinarNivel(puntos);
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public void setPuntos(int puntos) {
        this.puntos = puntos;
        this.nivel = Nivel.determinarNivel(puntos);
    }
    
    public Nivel getNivel() {
        return nivel;
    }
    
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
    
    public void agregarPuntos(int puntosAAgregar) {
        this.puntos += puntosAAgregar;
        this.nivel = Nivel.determinarNivel(this.puntos);
    }
    
    public boolean esCorreoValido() {
        return correo != null && correo.contains("@");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return Objects.equals(id, cliente.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Cliente{id='%s', nombre='%s', correo='%s', puntos=%d, nivel=%s}", 
                           id, nombre, correo, puntos, nivel);
    }
} 