package modelo;

import java.util.ArrayList;

public class Curso {
    private String id;
    private String nombre;
    private String descripcion;
    private int cupoMaximo;
    private String estado; // "Propuesto", "Habilitado", "Cerrado", "Finalizado", "Cancelado"
    private String documentoDocente;
    private ArrayList<String> alumnosInscritos;



    public Curso(String id, String nombre, String descripcion, int cupoMaximo, String documentoDocente) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cupoMaximo = cupoMaximo;
        this.documentoDocente = documentoDocente;
        this.estado = "Propuesto"; // Por defecto nace en estado Propuesto
        this.alumnosInscritos = new ArrayList<>();

    }

    // Getters para que los lea tu buscador del panel de administración
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEstado() { return estado; }
    public int getCupoMaximo() { return cupoMaximo; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getDescripcion() {
        return descripcion;
    }
    public String getDocumentoDocente() {
        return documentoDocente;
    }
    public ArrayList<String> getAlumnosInscritos() {
        if (this.alumnosInscritos == null) {
            this.alumnosInscritos = new ArrayList<>();
        }
        return this.alumnosInscritos;
    }

}
