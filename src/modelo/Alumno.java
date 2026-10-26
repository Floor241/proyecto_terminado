package modelo;

import java.util.ArrayList;

public class Alumno extends Usuario {

    private ArrayList<String> aprobados;

    public Alumno(String nombre, String apellido, String documento) {
        super(nombre, apellido, documento);
     
        this.aprobados = new ArrayList<>();
    }


    public ArrayList<String> getAprobados() {
        if (this.aprobados == null) {
            this.aprobados = new ArrayList<>();
        }
        return this.aprobados;
    }

    public void setAprobados(ArrayList<String> aprobados) {
        this.aprobados = aprobados;
    }
}
