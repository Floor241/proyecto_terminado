package modelo;

import java.util.ArrayList;

public class Instituto {

    private ArrayList<Usuario> usuarios;
    private ArrayList<Curso> cursos;


    public Instituto() {
        usuarios = new ArrayList<>();
        cursos = new ArrayList<>();


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

    public void cargarCursosDePrueba() {
        this.cursos.clear();
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
