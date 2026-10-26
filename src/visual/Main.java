package visual;

import modelo.Sistema;

public class Main {
    public static void main(String[] args) {
        // =====================================================================
        // 🌟 LA REPARACIÓN MAESTRA DEL NÚCLEO CENTRAL DE DATOS:
        // =====================================================================
        // Le ordenamos a la memoria estática central que cargue los cursos base
        if (modelo.Sistema.getInstituto() != null) {
            modelo.Sistema.getInstituto().cargarCursosDePrueba();
        }

        // Una vez poblada la RAM compartida, levantamos el Login de forma normal
        loginadmin login = new loginadmin();
        login.setVisible(true);
    }
}
