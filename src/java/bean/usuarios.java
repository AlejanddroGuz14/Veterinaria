package bean;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class usuarios {

    private String nombre, paterno, materno, mail, telefono;
    private String usuario, pass, tipo;
    private String opcion, respuesta;

    // =========================
    // GETTERS Y SETTERS
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

    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public String getOpcion() { return opcion; }
    public void setOpcion(String opcion) {
        this.opcion = opcion;

        if (opcion.equals("AltaUsuario")) {
            AltaUsuarios();
        } else if (opcion.equals("verifica")) {
            buscarUsuario();
        } else if (opcion.equals("CrearInvitado")) {
            CrearInvitado();
        }
    }

    // =========================
    // REGISTRO DE USUARIO
    // =========================
    public void AltaUsuarios() {
        tipo = "C"; // Cliente por defecto
        obtenerUsuario();

        try {
            Connection c = Conexion.conectar();

            if (c != null) {
                CallableStatement cs = c.prepareCall("{call dbo.AgregaUsuarioVeterinaria(?,?,?,?,?,?,?,?,?)}");

                cs.setString(1, nombre);
                cs.setString(2, paterno);
                cs.setString(3, materno);
                cs.setString(4, mail);
                cs.setString(5, telefono);
                cs.setString(6, usuario);
                cs.setString(7, pass);
                cs.setString(8, tipo);
                cs.registerOutParameter(9, java.sql.Types.VARCHAR);

                cs.execute();

                respuesta += "<br><br><h3 style='color:#023e8a;'>" + cs.getString(9) + "</h3>";

                cs.close();
                c.close();

            } else {
                respuesta = "No hay conexión a la base de datos";
            }

        } catch (Exception e) {
            respuesta = "Error en AltaUsuarios: " + e.getMessage();
        }
    }

    // =========================
    // GENERAR USUARIO Y PASSWORD
    // =========================
    public int aleatorio() {
        return (int) (Math.random() * 100);
    }

    public void obtenerUsuario() {
        int y = aleatorio();

        String r = nombre.substring(0, Math.min(3, nombre.length()));
        r += paterno.substring(0, Math.min(3, paterno.length()));
        r += materno.substring(0, Math.min(3, materno.length()));

        if (y < 10) {
            r += "00" + y;
        } else if (y < 100) {
            r += "0" + y;
        } else {
            r += y;
        }

        usuario = r;
        pass = EncriptarMD5.encripta(r);

        respuesta = "<h2>Tu usuario es " + usuario +
                "<br>Tu password es " + pass + "</h2>";
    }

    // =========================
    // CREAR INVITADO TEMPORAL
    // =========================
    public void CrearInvitado() {
        try {
            // Generar usuario temporal único
            String temp = "INV" + System.currentTimeMillis() % 100000;
            usuario = temp;
            pass = EncriptarMD5.encripta(temp);
            tipo = "I";

            Connection c = Conexion.conectar();
            if (c != null) {
                CallableStatement cs = c.prepareCall("{call dbo.AgregaUsuarioVeterinaria(?,?,?,?,?,?,?,?,?)}");
                cs.setString(1, "Invitado");
                cs.setString(2, "Temporal");
                cs.setString(3, "");
                cs.setString(4, "invitado@temp.com");
                cs.setString(5, "0000000000");
                cs.setString(6, usuario);
                cs.setString(7, pass);
                cs.setString(8, "I");
                cs.registerOutParameter(9, java.sql.Types.VARCHAR);
                cs.execute();
                String msg = cs.getString(9);
                cs.close();
                c.close();

                // Redirigir al menú invitado
                respuesta = "controlInvitado?accion=MenuInvitado&usr=" + usuario;
            } else {
                respuesta = "Error.jsp?respuesta=No hay conexión a la base";
            }
        } catch (Exception e) {
            respuesta = "Error.jsp?respuesta=Error al crear invitado: " + e.getMessage();
        }
    }

    // =========================
    // LOGIN
    // =========================
    public void buscarUsuario() {
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                PreparedStatement ps = c.prepareStatement(
                        "SELECT tipo FROM Acceso WHERE usuario=? AND password=?");
                ps.setString(1, usuario);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String tipo = rs.getString("tipo");

                    if (tipo.equals("A")) {
                        respuesta = "controlUsuarios?accion=MenuAdmin&usr=" + usuario;
                    } else if (tipo.equals("C")) {
                        respuesta = "controlCliente?accion=MenuCliente&usr=" + usuario;
                    } else if (tipo.equals("I")) {
                        respuesta = "controlInvitado?accion=MenuInvitado&usr=" + usuario;
                    } else if (tipo.equals("E")) {
                        respuesta = "controlEmpleadoVeterinario?accion=MenuVeterinario&usr=" + usuario;
                    }
                } else {
                    respuesta = "Error.jsp?respuesta=Usuario o contraseña incorrectos";
                }

                rs.close();
                ps.close();
                c.close();
            } else {
                respuesta = "Error.jsp?respuesta=No hay conexion en la base";
            }
        } catch (Exception e) {
            respuesta = "Error.jsp?respuesta=Error al buscar " + e.getMessage();
        }
    }
}
