package bean;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoVeterinario {

    // =========================
    // ATRIBUTOS
    // =========================
    private String nombre, paterno, materno, mail, telefono, usuario, tipo, status;
    private int idCliente, idEmpleado, idAcceso, idPuesto, idTurno;
    private String nombrePuesto, nombreTurno;
    private String horaEntrada, horaSalida, salario, fechaIngreso;
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

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdAcceso() { return idAcceso; }
    public void setIdAcceso(int idAcceso) { this.idAcceso = idAcceso; }

    public int getIdPuesto() { return idPuesto; }
    public void setIdPuesto(int idPuesto) { this.idPuesto = idPuesto; }

    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }

    public String getNombrePuesto() { return nombrePuesto; }
    public void setNombrePuesto(String nombrePuesto) { this.nombrePuesto = nombrePuesto; }

    public String getNombreTurno() { return nombreTurno; }
    public void setNombreTurno(String nombreTurno) { this.nombreTurno = nombreTurno; }

    public String getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(String horaEntrada) { this.horaEntrada = horaEntrada; }

    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }

    public String getSalario() { return salario; }
    public void setSalario(String salario) { this.salario = salario; }

    public String getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(String fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public String[] getTitulos() { return titulos; }
    public void setTitulos(String[] titulos) { this.titulos = titulos; }

    public String[][] getDatos() { return datos; }
    public void setDatos(String[][] datos) { this.datos = datos; }

    public String getOpcion() { return opcion; }
    public void setOpcion(String opcion) {
        this.opcion = opcion;
        if ("CargarDatosVeterinario".equals(opcion)) {
            CargarDatosVeterinario();
        }
        if ("CargarHorarioSemanal".equals(opcion)) {
            CargarHorarioSemanal();
        }
        if ("CargarMisCitas".equals(opcion)) {
            CargarMisCitas();
        }
        if ("CargarMisCitasTerminadas".equals(opcion)) {
            CargarMisCitasTerminadas();
        }
        if ("RegistrarHistorial".equals(opcion)) {
            RegistrarHistorial();
        }
        if ("ModificarHistorial".equals(opcion)) {
            ModificarHistorial();
        }
        if ("ConsultarHistorialPorMascota".equals(opcion)) {
            ConsultarHistorialPorMascota();
        }
        if ("CargarCitasTerminadasSinHistorial".equals(opcion)) {
            CargarCitasTerminadasSinHistorial();
        }
    }

    // =========================
    // CARGAR DATOS DEL VETERINARIO (para menú de bienvenida)
    // =========================
    public void CargarDatosVeterinario() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT C.idCliente, C.nombre, C.paterno, C.materno, C.mail, C.telefono, " +
                "A.tipo, A.idAcceso, C.status " +
                "FROM Clientes C INNER JOIN Acceso A ON C.idCliente = A.idCliente " +
                "WHERE A.usuario = ?"
            );
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idCliente = rs.getInt("idCliente");
                nombre    = rs.getString("nombre");
                paterno   = rs.getString("paterno");
                materno   = rs.getString("materno");
                mail      = rs.getString("mail");
                telefono  = rs.getString("telefono");
                tipo      = rs.getString("tipo");
                idAcceso  = rs.getInt("idAcceso");
                status    = rs.getString("status");
            }
            rs.close();
            ps.close();

            // Cargar datos laborales
            PreparedStatement ps2 = c.prepareStatement(
                "SELECT E.idEmpleado, E.idPuesto, E.idTurno, E.salario, E.fechaIngreso, " +
                "P.nombre AS puesto, T.nombre AS turno " +
                "FROM Empleados E " +
                "INNER JOIN Puesto P ON E.idPuesto = P.idPuesto " +
                "INNER JOIN Turno  T ON E.idTurno  = T.idTurno " +
                "WHERE E.idCliente = ?"
            );
            ps2.setInt(1, idCliente);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                idEmpleado   = rs2.getInt("idEmpleado");
                idPuesto     = rs2.getInt("idPuesto");
                idTurno      = rs2.getInt("idTurno");
                salario      = rs2.getString("salario");
                fechaIngreso = rs2.getString("fechaIngreso");
                nombrePuesto = rs2.getString("puesto");
                nombreTurno  = rs2.getString("turno");
            }
            rs2.close();
            ps2.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar datos: " + e.getMessage();
    }
}

    // =========================
    // CARGAR HORARIO SEMANAL DEL VETERINARIO
    // =========================
    public void CargarHorarioSemanal() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT HL.diaSemana, " +
                "CASE HL.diaSemana " +
                "WHEN 1 THEN 'Lunes' WHEN 2 THEN 'Martes' WHEN 3 THEN 'Miércoles' " +
                "WHEN 4 THEN 'Jueves' WHEN 5 THEN 'Viernes' WHEN 6 THEN 'Sábado' " +
                "WHEN 7 THEN 'Domingo' END AS nombreDia, " +
                "HL.horaEntrada, HL.horaSalida " +
                "FROM HorarioLaboral HL " +
                "WHERE HL.idEmpleado = ? " +
                "ORDER BY HL.diaSemana"
            );
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("diaSemana"),
                    rs.getString("nombreDia"),
                    rs.getString("horaEntrada"),
                    rs.getString("horaSalida")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            ps.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar horario: " + e.getMessage();
    }
}

    // =========================
    // CARGAR MIS CITAS PENDIENTES
    // =========================
    public void CargarMisCitas() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT CI.idCita, " +
                "CL.nombre + ' ' + CL.paterno + ' ' + CL.materno AS cliente, " +
                "ISNULL(M.nombre, '') AS mascota, " +
                "CONVERT(VARCHAR(10), H.fecha, 120) AS fecha, " +
                "CONVERT(VARCHAR(8), H.hora, 108) AS hora, " +
                "ISNULL(CI.motivo, '') AS motivo, " +
                "CI.estado " +
                "FROM Citas CI " +
                "INNER JOIN Clientes CL ON CI.idCliente = CL.idCliente " +
                "INNER JOIN Horarios H  ON CI.idHorario = H.idHorario " +
                "LEFT  JOIN Mascotas M  ON CI.idMascota = M.idMascota " +
                "WHERE CI.idAcceso = ? AND CI.estado = 'P' " +
                "ORDER BY H.fecha, H.hora"
            );
            ps.setInt(1, idAcceso);
            ResultSet rs = ps.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    String.valueOf(rs.getInt("idCita")),
                    rs.getString("cliente"),
                    rs.getString("mascota"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getString("estado")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            ps.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar citas: " + e.getMessage();
    }
}

    // =========================
    // CITAS — CARGAR MIS CITAS TERMINADAS (para historial)
    // =========================
    public void CargarMisCitasTerminadas() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT CI.idCita, " +
                "CL.nombre + ' ' + CL.paterno + ' ' + CL.materno AS cliente, " +
                "ISNULL(M.nombre, '') AS mascota, " +
                "M.idMascota, CI.idCliente, " +
                "CONVERT(VARCHAR(10), H.fecha, 120) AS fecha, " +
                "CONVERT(VARCHAR(8), H.hora, 108) AS hora, " +
                "ISNULL(CI.motivo, '') AS motivo " +
                "FROM Citas CI " +
                "INNER JOIN Clientes CL ON CI.idCliente = CL.idCliente " +
                "INNER JOIN Horarios H  ON CI.idHorario = H.idHorario " +
                "LEFT  JOIN Mascotas M  ON CI.idMascota = M.idMascota " +
                "WHERE CI.idAcceso = ? AND CI.estado = 'T' " +
                "AND NOT EXISTS(SELECT 1 FROM HistorialMedico HM WHERE HM.idCita = CI.idCita) " +
                "ORDER BY H.fecha DESC, H.hora"
            );
            ps.setInt(1, idAcceso);
            ResultSet rs = ps.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    String.valueOf(rs.getInt("idCita")),
                    rs.getString("cliente"),
                    rs.getString("mascota"),
                    String.valueOf(rs.getInt("idMascota")),
                    String.valueOf(rs.getInt("idCliente")),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            ps.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar citas terminadas: " + e.getMessage();
    }
}

    // =========================
    // HISTORIAL — ATRIBUTOS Y GETTERS/SETTERS
    // =========================
    private int idHistorial, idCita, idMascotaH, idClienteH;
    private String peso, temperatura, diagnostico, tratamiento, medicamentos, vacunas, observacionesH, proximaCita;

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }
    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }
    public int getIdMascotaH() { return idMascotaH; }
    public void setIdMascotaH(int idMascotaH) { this.idMascotaH = idMascotaH; }
    public int getIdClienteH() { return idClienteH; }
    public void setIdClienteH(int idClienteH) { this.idClienteH = idClienteH; }
    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }
    public String getTemperatura() { return temperatura; }
    public void setTemperatura(String temperatura) { this.temperatura = temperatura; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }
    public String getVacunas() { return vacunas; }
    public void setVacunas(String vacunas) { this.vacunas = vacunas; }
    public String getObservacionesH() { return observacionesH; }
    public void setObservacionesH(String observacionesH) { this.observacionesH = observacionesH; }
    public String getProximaCita() { return proximaCita; }
    public void setProximaCita(String proximaCita) { this.proximaCita = proximaCita; }

    // =========================
    // HISTORIAL — REGISTRAR
    // =========================
    public void RegistrarHistorial() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call RegistrarHistorialMedicoVeterinaria(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, idCita);
            cs.setInt(2, idMascotaH);
            cs.setInt(3, idClienteH);
            cs.setInt(4, idAcceso);
            try { cs.setBigDecimal(5, new java.math.BigDecimal(peso)); } catch (Exception e) { cs.setNull(5, java.sql.Types.DECIMAL); }
            try { cs.setBigDecimal(6, new java.math.BigDecimal(temperatura)); } catch (Exception e) { cs.setNull(6, java.sql.Types.DECIMAL); }
            cs.setString(7, diagnostico);
            cs.setString(8, tratamiento);
            cs.setString(9, medicamentos);
            cs.setString(10, vacunas);
            cs.setString(11, observacionesH);
            if (proximaCita != null && !proximaCita.trim().isEmpty()) cs.setString(12, proximaCita);
            else cs.setNull(12, java.sql.Types.DATE);
            cs.registerOutParameter(13, java.sql.Types.VARCHAR);
            cs.execute();
            respuesta = cs.getString(13);
            cs.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al registrar historial: " + e.getMessage();
    }
}

    // =========================
    // HISTORIAL — MODIFICAR
    // =========================
    public void ModificarHistorial() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call ModificarHistorialMedicoVeterinaria(?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, idHistorial);
            try { cs.setBigDecimal(2, new java.math.BigDecimal(peso)); } catch (Exception e) { cs.setNull(2, java.sql.Types.DECIMAL); }
            try { cs.setBigDecimal(3, new java.math.BigDecimal(temperatura)); } catch (Exception e) { cs.setNull(3, java.sql.Types.DECIMAL); }
            cs.setString(4, diagnostico);
            cs.setString(5, tratamiento);
            cs.setString(6, medicamentos);
            cs.setString(7, vacunas);
            cs.setString(8, observacionesH);
            if (proximaCita != null && !proximaCita.trim().isEmpty()) cs.setString(9, proximaCita);
            else cs.setNull(9, java.sql.Types.DATE);
            cs.registerOutParameter(10, java.sql.Types.VARCHAR);
            cs.execute();
            respuesta = cs.getString(10);
            cs.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al modificar historial: " + e.getMessage();
    }
}

    // =========================
    // HISTORIAL — CONSULTAR POR MASCOTA
    // =========================
    public void ConsultarHistorialPorMascota() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call ConsultarHistorialPorMascotaVeterinaria(?)}");
            cs.setInt(1, idMascotaH);
            ResultSet rs = cs.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    String.valueOf(rs.getInt("idHistorial")),
                    rs.getString("fechaRegistro"),
                    rs.getString("veterinario"),
                    rs.getString("peso"),
                    rs.getString("temperatura"),
                    rs.getString("diagnostico"),
                    rs.getString("tratamiento"),
                    rs.getString("medicamentos"),
                    rs.getString("vacunas"),
                    rs.getString("observaciones"),
                    rs.getString("proximaCita")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            cs.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al consultar historial: " + e.getMessage();
    }
}

    // =========================
    // HISTORIAL — CARGAR CITAS TERMINADAS SIN HISTORIAL
    // =========================
    public void CargarCitasTerminadasSinHistorial() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call CargarCitasTerminadasSinHistorialVeterinaria(?)}");
            cs.setInt(1, idMascotaH);
            ResultSet rs = cs.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    String.valueOf(rs.getInt("idCita")),
                    rs.getString("veterinario"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            cs.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar citas: " + e.getMessage();
    }
}

}
