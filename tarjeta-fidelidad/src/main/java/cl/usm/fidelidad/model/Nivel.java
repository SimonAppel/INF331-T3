package cl.usm.fidelidad.model;

public enum Nivel {
    BRONCE(0, 1.0, "Bronce"),
    PLATA(500, 1.2, "Plata"),
    ORO(1500, 1.5, "Oro"),
    PLATINO(3000, 2.0, "Platino");
    
    private final int umbralMinimo;
    private final double multiplicador;
    private final String nombre;
    
    Nivel(int umbralMinimo, double multiplicador, String nombre) {
        this.umbralMinimo = umbralMinimo;
        this.multiplicador = multiplicador;
        this.nombre = nombre;
    }
    
    public int getUmbralMinimo() {
        return umbralMinimo;
    }
    
    public double getMultiplicador() {
        return multiplicador;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public static Nivel determinarNivel(int puntosTotales) {
        if (puntosTotales >= PLATINO.umbralMinimo) {
            return PLATINO;
        } else if (puntosTotales >= ORO.umbralMinimo) {
            return ORO;
        } else if (puntosTotales >= PLATA.umbralMinimo) {
            return PLATA;
        } else {
            return BRONCE;
        }
    }
    
    @Override
    public String toString() {
        return nombre;
    }
} 