package modelo;

import java.util.ArrayList;

public class Instituto {

    private ArrayList<Usuario> usuarios;
    private ArrayList<Curso> cursos;

    public Instituto() {
        usuarios = new ArrayList<>();
        cursos = new ArrayList<>();

        cargarCursosDePrueba();
    }

    public void agregarUsuario(Usuario usuario) { usuarios.add(usuario); }

    public ArrayList<Usuario> getUsuarios() { return usuarios; }

    public int cantidadUsuarios() { return usuarios.size(); }


    public void agregarCurso(Curso curso) {
        this.cursos.add(curso);
    }

    public ArrayList<Curso> getCursos() {
        return this.cursos;
    }

    private void cargarCursosDePrueba() {

        cursos.add(new Curso("C1", "Bases de Datos", "Diseño relacional y SQL", 30, "Docente Gómez"));
        cursos.add(new Curso("C2", "Java Inicial", "Introducción a POO con Swing", 25, "Docente Ramírez"));

        // Forzamos que el segundo curso esté en otro estado para probar los filtros después
        cursos.get(1).setEstado("Habilitado");
    }
    public Usuario buscarUsuarioPorDni(String dni) {
        for (Usuario u : this.usuarios) {
            if (u.getDocumento().equals(dni)) {
                return u;
            }
        }
        return null;
    }


}
