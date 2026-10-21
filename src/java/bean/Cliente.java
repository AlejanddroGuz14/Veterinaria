package bean;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    // =========================
    // ATRIBUTOS
    // =========================
    private String nombre, paterno, materno, mail, telefono, usuario, tipo, status;
    private int idCliente, idMascota, idEspecie, idRaza, idSexo;
    private int idCita, idAcceso, idHorario;
    private String nombreMascota, fechaNacimiento, color, observaciones, peso;
    private String motivo, observacionesCita, estadoCita;
    private String opcion, respuesta;
    private String[] titulos;
    private String[][] datos;

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
    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }
    public int getIdEspecie() { return idEspecie; }
    public void setIdEspecie(int idEspecie) { this.idEspecie = idEspecie; }
    public int getIdRaza() { return idRaza; }
    public void setIdRaza(int idRaza) { this.idRaza = idRaza; }
    public int getIdSexo() { return idSexo; }
    public void setIdSexo(int idSexo) { this.idSexo = idSexo; }
    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }
    public int getIdAcceso() { return idAcceso; }
    public void setIdAcceso(int idAcceso) { this.idAcceso = idAcceso; }
    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }
    public String getNombreMascota() { return nombreMascota; }
    public void setNombreMascota(String nombreMascota) { this.nombreMascota = nombreMascota; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getObservacionesCita() { return observacionesCita; }
    public void setObservacionesCita(String observacionesCita) { this.observacionesCita = observacionesCita; }
    public String getEstadoCita() { return estadoCita; }
    public void setEstadoCita(String estadoCita) { this.estadoCita = estadoCita; }
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
    public String[] getTitulos() { return titulos; }
    public String[][] getDatos() { return datos; }

    public String getOpcion() { return opcion; }
    public void setOpcion(String opcion) {
        this.opcion = opcion;
        if ("CargarUsuario".equals(opcion)) CargarUsuario();
        if ("CargarMisMascotas".equals(opcion)) CargarMisMascotas();
        if ("RegistrarMascota".equals(opcion)) RegistrarMascota();
        if ("CargarMisCitas".equals(opcion)) CargarMisCitas();
        if ("AgendarCita".equals(opcion)) AgendarCita();
        if ("CancelarCita".equals(opcion)) CancelarCita();
        if ("ConsultarHistorial".equals(opcion)) ConsultarHistorial();
    }

    // =========================
    // CARGAR DATOS DEL CLIENTE
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
    // MIS MASCOTAS
    // =========================
    public void CargarMisMascotas() {
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                PreparedStatement ps = c.prepareStatement(
                    "SELECT M.idMascota, M.nombre, E.nombre AS especie " +
                    "FROM Mascotas M INNER JOIN Especie E ON M.idEspecie = E.idEspecie " +
                    "WHERE M.idCliente = ? AND M.status = 'A' ORDER BY M.nombre"
                );
                ps.setInt(1, idCliente);
                ResultSet rs = ps.executeQuery();
                List<String[]> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(new String[]{
                        rs.getString("idMascota"), rs.getString("nombre"), rs.getString("especie")
                    });
                }
                datos = lista.toArray(new String[0][]);
                rs.close(); ps.close(); c.close();
            }
        } catch (Exception e) { respuesta = "Error: " + e.getMessage(); }
    }

    // =========================
    // REGISTRAR MASCOTA
    // =========================
    public void RegistrarMascota() {
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                CallableStatement cs = c.prepareCall("{call AltaMascotaVeterinaria(?,?,?,?,?,?,?,?,?,?)}");
                cs.setInt(1, idCliente);
                cs.setInt(2, idEspecie);
                cs.setInt(3, idRaza);
                cs.setInt(4, idSexo);
                cs.setString(5, nombreMascota);
                if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) cs.setString(6, fechaNacimiento);
                else cs.setNull(6, java.sql.Types.DATE);
                try { cs.setBigDecimal(7, new java.math.BigDecimal(peso.trim())); }
                catch (Exception ep) { cs.setNull(7, java.sql.Types.DECIMAL); }
                cs.setString(8, color);
                cs.setString(9, observaciones);
                cs.registerOutParameter(10, java.sql.Types.VARCHAR);
                cs.execute();
                respuesta = cs.getString(10);
                cs.close(); c.close();
            }
        } catch (Exception e) { respuesta = "Error: " + e.getMessage(); }
    }

    // =========================
    // MIS CITAS PENDIENTES
    // =========================
    public void CargarMisCitas() {
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                PreparedStatement ps = c.prepareStatement(
                    "SELECT CI.idCita, ISNULL(M.nombre,'') AS mascota, " +
                    "C2.nombre + ' ' + C2.paterno AS veterinario, " +
                    "CONVERT(VARCHAR(10), H.fecha, 120) AS fecha, " +
                    "CONVERT(VARCHAR(8), H.hora, 108) AS hora, " +
                    "ISNULL(CI.motivo,'') AS motivo, CI.estado " +
                    "FROM Citas CI " +
                    "INNER JOIN Horarios H ON CI.idHorario = H.idHorario " +
                    "INNER JOIN Acceso A ON CI.idAcceso = A.idAcceso " +
                    "INNER JOIN Clientes C2 ON A.idCliente = C2.idCliente " +
                    "LEFT JOIN Mascotas M ON CI.idMascota = M.idMascota " +
                    "WHERE CI.idCliente = ? AND CI.estado = 'P' ORDER BY H.fecha, H.hora"
                );
                ps.setInt(1, idCliente);
                ResultSet rs = ps.executeQuery();
                List<String[]> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(new String[]{
                        String.valueOf(rs.getInt("idCita")), rs.getString("mascota"),
                        rs.getString("veterinario"), rs.getString("fecha"),
                        rs.getString("hora"), rs.getString("motivo"), rs.getString("estado")
                    });
                }
                datos = lista.toArray(new String[0][]);
                rs.close(); ps.close(); c.close();
            }
        } catch (Exception e) { respuesta = "Error: " + e.getMessage(); }
    }

    // =========================
    // AGENDAR CITA
    // =========================
    public void AgendarCita() {
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                CallableStatement cs = c.prepareCall("{call AgregarCitaVeterinaria(?,?,?,?,?,?,?)}");
                cs.setInt(1, idCliente);
                cs.setInt(2, idMascota);
                cs.setInt(3, idAcceso);
                cs.setInt(4, idHorario);
                cs.setString(5, motivo);
                cs.setString(6, observacionesCita);
                cs.registerOutParameter(7, java.sql.Types.VARCHAR);
                cs.execute();
                respuesta = cs.getString(7);
                cs.close(); c.close();
            }
        } catch (Exception e) { respuesta = "Error: " + e.getMessage(); }
    }

    // =========================
    // CANCELAR CITA
    // =========================
    public void CancelarCita() {
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                CallableStatement cs = c.prepareCall("{call CancelarCitaVeterinaria(?,?,?)}");
                cs.setInt(1, idCita);
                cs.setString(2, "C");
                cs.registerOutParameter(3, java.sql.Types.VARCHAR);
                cs.execute();
                respuesta = cs.getString(3);
                cs.close(); c.close();
            }
        } catch (Exception e) { respuesta = "Error: " + e.getMessage(); }
    }

    // =========================
    // CONSULTAR HISTORIAL POR MASCOTA
    // =========================
    public void ConsultarHistorial() {
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                CallableStatement cs = c.prepareCall("{call ConsultarHistorialPorMascotaVeterinaria(?)}");
                cs.setInt(1, idMascota);
                ResultSet rs = cs.executeQuery();
                List<String[]> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(new String[]{
                        String.valueOf(rs.getInt("idHistorial")),
                        rs.getString("fechaRegistro"), rs.getString("veterinario"),
                        rs.getString("peso"), rs.getString("temperatura"),
                        rs.getString("diagnostico"), rs.getString("tratamiento"),
                        rs.getString("medicamentos"), rs.getString("vacunas"),
                        rs.getString("observaciones"), rs.getString("proximaCita")
                    });
                }
                datos = lista.toArray(new String[0][]);
                rs.close(); cs.close(); c.close();
            }
        } catch (Exception e) { respuesta = "Error: " + e.getMessage(); }
    }
}
