package visual;

import modelo.Sistema;
import basededatos.ConexionDB;

public class Main {
    public static void main(String[] args) {
        ConexionDB.getConexion();

        System.out.println("🚀 [Sistema] Inicializando paneles académicos sin borrado de memoria...");

        loginadmin login = new loginadmin();
        login.setVisible(true);
    }
}
