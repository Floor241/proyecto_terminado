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
    private ArrayList<String> idsCorrelativas;
    private ArrayList<String> alumnosAprobados;
    private ArrayList<String> alumnosDesaprobados;
    private ArrayList<String> correlativas = new ArrayList<>();


    public Curso(String id, String nombre, String descripcion, int cupoMaximo, String documentoDocente) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cupoMaximo = cupoMaximo;
        this.documentoDocente = documentoDocente;
        this.estado = "Propuesto";
        this.alumnosInscritos = new ArrayList<>();
        this.idsCorrelativas = new ArrayList<>();
        this.alumnosAprobados = new ArrayList<>();
        this.alumnosDesaprobados = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEstado() { return estado; }
    public int getCupoMaximo() { return cupoMaximo; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getDescripcion() { return descripcion; }
    public String getDocumentoDocente() { return documentoDocente; }

    public ArrayList<String> getAlumnosInscritos() {
        if (this.alumnosInscritos == null) {
            this.alumnosInscritos = new ArrayList<>();
        }
        return this.alumnosInscritos;
    }

    public ArrayList<String> getAlumnosAprobados() {
        if (this.alumnosAprobados == null) {
            this.alumnosAprobados = new ArrayList<>();
        }
        return this.alumnosAprobados;
    }

    public ArrayList<String> getAlumnosDesaprobados() {
        if (this.alumnosDesaprobados == null) {
            this.alumnosDesaprobados = new ArrayList<>();
        }
        return this.alumnosDesaprobados;
    }

    public ArrayList<String> getIdsCorrelativas() {
        if (this.idsCorrelativas == null) {
            this.idsCorrelativas = new ArrayList<>();
        }
        return this.idsCorrelativas;
    }




    public ArrayList<String> getCorrelativas() {
        if (this.correlativas == null) {
            this.correlativas = new ArrayList<>();
        }
        return this.correlativas;
    }

    public void agregarCorrelativa(String idCorrelativa) {
        if (this.idsCorrelativas == null) {
            this.idsCorrelativas = new ArrayList<>();
        }
        this.idsCorrelativas.add(idCorrelativa);
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
