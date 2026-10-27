package basededatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/instituto_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "24012002";


    public static Connection getConexion() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("🔌 [ÉXITO] ¡Java se conectó a MySQL Workbench desde el nuevo paquete!");
        } catch (ClassNotFoundException e) {
            System.out.println("⚠️ Error: No se encontró el driver en las librerías.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("⚠️ Error: Revisá la contraseña de MySQL.");
            e.printStackTrace();
        }
        return conexion;
    }
}
