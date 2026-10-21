package bean;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public static Connection conectar() {
        Connection c = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=VeterinariaDB";

            String user = "sa";
            String password = "sasa";

            c = DriverManager.getConnection(url, user, password);

            System.out.println("Conectado correctamente");

        } catch (Exception e) {
            System.out.println("Error de conexión:");
            e.printStackTrace(); 
        }

        return c;
    }
}