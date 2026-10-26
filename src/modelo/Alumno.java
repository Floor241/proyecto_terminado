package modelo;

import java.util.ArrayList;

public class Alumno extends Usuario {

    // 🌟 Declaramos la lista que guardará los códigos de los cursos aprobados
    private ArrayList<String> aprobados;

    public Alumno(String nombre, String apellido, String documento) {
        super(nombre, apellido, documento);
        // Inicializamos la lista limpia en memoria RAM
        this.aprobados = new ArrayList<>();
    }

    // 🌟 El método fundamental que el botón de inscripción estaba buscando:
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
