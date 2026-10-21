package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Invitado {

    // =========================
    // ATRIBUTOS
    // =========================
    private String nombre, paterno, materno, mail, telefono, usuario, tipo, status;
    private int idCliente;
    private String opcion, respuesta;

    // =========================
    // GETTERS/SETTERS
    // =========================
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPaterno() { return paterno; }
    public void setPaterno(String paterno) { this.paterno = paterno; }
    public String getMaterno() { return materno; }
    public void setMaterno(String materno) { this.materno = materno; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public String getOpcion() { return opcion; }
    public void setOpcion(String opcion) {
        this.opcion = opcion;
        if ("CargarUsuario".equals(opcion)) CargarUsuario();
        if ("Registrarse".equals(opcion)) Registrarse();
    }

    // =========================
    // CARGAR DATOS DEL INVITADO
    // =========================
    public void CargarUsuario() {
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                PreparedStatement ps = c.prepareStatement(
                    "SELECT C.idCliente, C.nombre, C.paterno, C.materno, C.mail, C.telefono, C.status, A.tipo " +
                    "FROM Clientes C INNER JOIN Acceso A ON C.idCliente = A.idCliente WHERE A.usuario = ?"
                );
                ps.setString(1, usuario);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    idCliente = rs.getInt("idCliente");
                    nombre = rs.getString("nombre");
                    paterno = rs.getString("paterno");
                    materno = rs.getString("materno");
                    mail = rs.getString("mail");
                    telefono = rs.getString("telefono");
                    tipo = rs.getString("tipo");
                    status = rs.getString("status");
                }
                rs.close(); ps.close(); c.close();
            }
        } catch (Exception e) { respuesta = "Error: " + e.getMessage(); }
    }

    // =========================
    // REGISTRARSE (UPDATE invitado → cliente)
    // =========================
    public void Registrarse() {
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                // Actualizar datos personales en Clientes
                PreparedStatement ps = c.prepareStatement(
                    "UPDATE Clientes SET nombre=?, paterno=?, materno=?, mail=?, telefono=?, status='A' WHERE idCliente=?"
                );
                ps.setString(1, nombre);
                ps.setString(2, paterno);
                ps.setString(3, materno);
                ps.setString(4, mail);
                ps.setString(5, telefono);
                ps.setInt(6, idCliente);
                ps.executeUpdate();
                ps.close();

                // Cambiar tipo a C en Acceso
                PreparedStatement ps2 = c.prepareStatement(
                    "UPDATE Acceso SET tipo='C' WHERE idCliente=?"
                );
                ps2.setInt(1, idCliente);
                ps2.executeUpdate();
                ps2.close();

                // Actualizar tipo en Clientes
                PreparedStatement ps3 = c.prepareStatement(
                    "UPDATE Clientes SET tipo='C' WHERE idCliente=?"
                );
                ps3.setInt(1, idCliente);
                ps3.executeUpdate();
                ps3.close();

                c.close();
                respuesta = "Registro exitoso";
            } else {
                respuesta = "No hay conexión a la base";
            }
        } catch (Exception e) { respuesta = "Error: " + e.getMessage(); }
    }
}
