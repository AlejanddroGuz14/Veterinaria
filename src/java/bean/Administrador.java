package bean;

// =====================================================
// IMPORTS DE SQL (Base de datos)
// =====================================================
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// =====================================================
// IMPORTS DE JAVA UTIL (Listas, Fechas)
// =====================================================
import java.util.ArrayList;
import java.util.List;

// =====================================================
// IMPORTS DE JAVA MATH (BigDecimal para salarios)
// =====================================================
import java.math.BigDecimal;
import java.time.LocalDate;

public class Administrador {

    // =========================
    // ATRIBUTOS — USUARIOS
    // =========================
    private String nombre, paterno, materno, mail, telefono, tipo, usuario, pass, status;
    private int idCliente;

    // =========================
    // ATRIBUTOS — MASCOTAS
    // =========================
    private int idMascota, idEspecie, idRaza, idSexo, idMotivo;
    private String nombreMascota, fechaNacimiento, color, observaciones;
    private String peso; // String para facilitar el manejo desde el form

    // =========================
    // ATRIBUTOS — EMPLEADOS
    // =========================
    private int idEmpleado, idPuesto, idTurno, diaSemana;
    private String nombrePuesto, nombreTurno;
    private String horaEntrada, horaSalida, salario, fechaIngreso;
    private String fechaDesde, fechaHasta;
    private String opcion, respuesta;
    private String[] titulos;
    private String[][] datos;

    // =========================
    // ATRIBUTOS — CITAS
    // =========================
    private int idCita, idAcceso, idHorario;
    private String motivo, observacionesCita, estadoCita, fechaCita, horaCita;

    // =========================
    // GETTERS/SETTERS — MASCOTAS
    // =========================
    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public int getIdEspecie() { return idEspecie; }
    public void setIdEspecie(int idEspecie) { this.idEspecie = idEspecie; }

    public int getIdRaza() { return idRaza; }
    public void setIdRaza(int idRaza) { this.idRaza = idRaza; }

    public int getIdSexo() { return idSexo; }
    public void setIdSexo(int idSexo) { this.idSexo = idSexo; }

    public int getIdMotivo() { return idMotivo; }
    public void setIdMotivo(int idMotivo) { this.idMotivo = idMotivo; }

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

    public String[] getTitulos() {
        return titulos;
    }

    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
    }

    public String[][] getDatos() {
        return datos;
    }

    public void setDatos(String[][] datos) {
        this.datos = datos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
        if ("AltaUsuario".equals(opcion)) {
            AltaUsuarios();
        }
        if ("BuscaUsuario".equals(opcion)) {
            BuscaUsuarios();
        }
         if ("CargarUsuario".equals(opcion)) {
            CargarUsuario();
        }
         if ("ActualizarUsuario".equals(opcion)) {
            ActualizarUsuario();
        }
        if ("BajaUsuario".equals(opcion)) {
        BajaUsuario();
        }
        if ("MuestraTablaU".equals(opcion)) {
         MuestraTablaU();
        }
        if ("ConsultaUIndividual".equals(opcion)) {
        ConsultaUIndividual();
        }
        // =========================
        // MASCOTAS
        // =========================
        if ("AltaMascota".equals(opcion)) {
            AltaMascota();
        }
        if ("BajaMascota".equals(opcion)) {
            BajaMascota();
        }
        if ("ValidarMascota".equals(opcion)) {
            ValidarMascota();
        }
        if ("CargarMascota".equals(opcion)) {
            CargarMascota();
        }
        if ("ModificarMascota".equals(opcion)) {
            ModificarMascota();
        }
        if ("ConsultaMascotaIndividual".equals(opcion)) {
            ConsultaMascotaIndividual();
        }
        if ("ConsultaMascotasGeneral".equals(opcion)) {
            ConsultaMascotasGeneral();
        }
        if ("CargarEspecies".equals(opcion)) {
            CargarEspecies();
        }
        if ("CargarRazasPorEspecie".equals(opcion)) {
            CargarRazasPorEspecie();
        }
        if ("CargarSexos".equals(opcion)) {
            CargarSexos();
        }
        if ("CargarMotivosBaja".equals(opcion)) {
            CargarMotivosBaja();
        }
        if ("CargarUsuarios".equals(opcion)) {
            CargarUsuarios();
        }
        if ("CargarEmpleadosYAdmins".equals(opcion)) {
            CargarEmpleadosYAdmins();
        }
        // =========================
        // EMPLEADOS
        // =========================
        if ("CargarPuestos".equals(opcion)) {
            CargarPuestos();
        }
        if ("CargarTurnos".equals(opcion)) {
            CargarTurnos();
        }
        if ("AsignarDatosLaborales".equals(opcion)) {
            AsignarDatosLaborales();
        }
        if ("CargarEmpleado".equals(opcion)) {
            CargarEmpleado();
        }
        if ("ModificarDatosLaborales".equals(opcion)) {
            ModificarDatosLaborales();
        }
        if ("ConsultaDatosLaborales".equals(opcion)) {
            ConsultaDatosLaborales();
        }
        if ("AsignarDiaSemana".equals(opcion)) {
            AsignarDiaSemana();
        }
        if ("EliminarDiaSemana".equals(opcion)) {
            EliminarDiaSemana();
        }
        if ("ConsultaHorarioSemanal".equals(opcion)) {
            ConsultaHorarioSemanal();
        }
        if ("CargarUsuarioConPuesto".equals(opcion)) {
            CargarUsuarioConPuesto();
        }
        if ("CambiarContrasena".equals(opcion)) {
            CambiarContrasena();
        }
        // =========================
        // CITAS
        // =========================
        if ("CargarVeterinarios".equals(opcion)) {
            CargarVeterinarios();
        }
        if ("CargarHorarioVeterinario".equals(opcion)) {
            CargarHorarioVeterinario();
        }
        if ("CargarMascotasPorCliente".equals(opcion)) {
            CargarMascotasPorCliente();
        }
        if ("CargarTodasMascotasPorCliente".equals(opcion)) {
            CargarTodasMascotasPorCliente();
        }
        if ("RegistrarCita".equals(opcion)) {
            RegistrarCita();
        }
        if ("ModificarCita".equals(opcion)) {
            ModificarCita();
        }
        if ("CargarCitasPorCliente".equals(opcion)) {
            CargarCitasPorCliente();
        }
        if ("CancelarCita".equals(opcion)) {
            CancelarCita();
        }
        if ("CargarCitasPorMascota".equals(opcion)) {
            CargarCitasPorMascota();
        }
        if ("CargarTodasCitasMascota".equals(opcion)) {
            CargarTodasCitasMascota();
        }
        if ("ConsultaCitasCliente".equals(opcion)) {
            ConsultaCitasCliente();
        }
        if ("HorasOcupadasVeterinario".equals(opcion)) {
            HorasOcupadasVeterinario();
        }
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

   public void AltaUsuarios() {
    if (tipo == null || tipo.trim().isEmpty()) {
        tipo = "A";
    }

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
            cs.setString(7, EncriptarMD5.encripta(pass));
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
        pass = r;

        // Mostrar el hash MD5 que es la contraseña real guardada en BD
        String passMD5 = EncriptarMD5.encripta(pass);

        respuesta = "<h2>El usuario es " + usuario +
                "<br>El password es " + passMD5 + "</h2>";
    }

    public void BuscaUsuarios(){
        try {
        Connection c = Conexion.conectar();

        if (c != null) {
            CallableStatement cs = c.prepareCall(
                "SELECT COUNT(*) AS total FROM Acceso WHERE usuario = ?"
            );

            cs.setString(1, usuario);

            java.sql.ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");

                if (total > 0) {
                    respuesta = "ModificarDatos.jsp?usuario=" + usuario;
                } else {
                    respuesta = "Error.jsp?respuesta=Usuario no encontrado";
                }
            }

            rs.close();
            cs.close();
            c.close();
        } else {
            respuesta = "Error.jsp?respuesta=No hay conexión a la base";
        }

    } catch (Exception e) {
        respuesta = "Error.jsp?respuesta=Error al buscar " + e.getMessage();
    }
}
    
    public void CargarUsuario() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {
            System.out.println("Buscando usuario: " + usuario);

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
            } else {
                System.out.println(" Usuario no encontrado en BD");
            }

            rs.close();
            ps.close();
            c.close();
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
    
    public void ActualizarUsuario() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "UPDATE Clientes SET nombre=?, paterno=?, materno=?, mail=?, telefono=?, status=? WHERE idCliente=?"
            );

            ps.setString(1, nombre);
            ps.setString(2, paterno);
            ps.setString(3, materno);
            ps.setString(4, mail);
            ps.setString(5, telefono);
            ps.setString(6, status);
            ps.setInt(7, idCliente);

            ps.executeUpdate();

            CallableStatement cs = c.prepareCall(
                "{call ActualizaUsuarioVeterinaria(?,?,?)}"
            );

            cs.setString(1, usuario);
            cs.setString(2, tipo);
            cs.registerOutParameter(3, java.sql.Types.VARCHAR);

            cs.execute();

            respuesta = cs.getString(3);

            ps.close();
            cs.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la BD";
        }

    } catch (Exception e) {
        respuesta = "Error actualizar: " + e.getMessage();
    }
}
    
    public void BajaUsuario() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            CallableStatement cs = c.prepareCall(
                "{call BajaLogicaUsuarioVeterinaria(?,?)}"
            );

            cs.setString(1, usuario);
            cs.registerOutParameter(2, java.sql.Types.VARCHAR);

            cs.execute();

            respuesta = cs.getString(2);

            cs.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la BD";
        }

    } catch (Exception e) {
        respuesta = "Error en baja: " + e.getMessage();
    }
}
    
    public void MuestraTablaU() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            Statement ps = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = ps.executeQuery(
                "SELECT C.idCliente, A.usuario, " +
                "C.nombre + ' ' + C.paterno + ' ' + C.materno AS nombreCompleto, " +
                "C.mail, C.telefono, A.tipo, C.status " +
                "FROM Clientes C INNER JOIN Acceso A ON C.idCliente = A.idCliente"
            );

            int filas = 0, columnas = 0;

            if (rs.next()) {

                columnas = rs.getMetaData().getColumnCount();

                rs.last();
                filas = rs.getRow();

                titulos = new String[columnas];
                datos = new String[filas][columnas];

                for (int i = 0; i < columnas; i++) {
                    titulos[i] = rs.getMetaData().getColumnName(i + 1);
                }

                rs.first();

                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        datos[i][j] = rs.getString(j + 1);
                    }
                    rs.next();
                }

                crearTablaU(titulos, datos);

            } else {
                respuesta = "No hay usuarios registrados";
            }

            rs.close();
            ps.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error al mostrar tabla: " + e.getMessage();
    }
}
    
    public void crearTablaU(String t[], String d[][]) {

        String te = "<table style='width:100%; border-collapse:collapse;'>";

        te += "<tr style='background:#0077b6; color:white;'>";

        for (int i = 0; i < t.length; i++) {
            te += "<th style='padding:10px;'>" + t[i] + "</th>";
        }

        for (int i = 0; i < d.length; i++) {
            te += "<tr style='text-align:center;'>";

            for (int j = 0; j < d[i].length; j++) {
                te += "<td style='padding:8px; border-bottom:1px solid #ccc;'>" + d[i][j] + "</td>";
            }
            

            te += "</tr>";
        }

        te += "</table>";

        respuesta = te;
}
    
    public void ConsultaUIndividual() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            PreparedStatement ps = c.prepareStatement(
                "SELECT C.idCliente, A.usuario, " +
                "C.nombre + ' ' + C.paterno + ' ' + C.materno AS nombreCompleto, " +
                "C.mail, C.telefono, A.tipo, C.status " +
                "FROM Clientes C INNER JOIN Acceso A ON C.idCliente = A.idCliente " +
                "WHERE A.usuario = ?"
            );

            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                titulos = new String[]{
                    "ID", "Usuario", "Nombre", "Email", "Teléfono", "Tipo", "Status"
                };

                datos = new String[1][7];

                for (int i = 0; i < 7; i++) {
                    datos[0][i] = rs.getString(i + 1);
                }

                crearTablaU(titulos, datos);

            } else {
                respuesta = "<h3 style='color:red;'>Usuario no encontrado</h3>";
            }

            rs.close();
            ps.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la BD";
        }

    } catch (Exception e) {
        respuesta = "Error: " + e.getMessage();
    }
}
    
    // =========================
    // MASCOTAS — ALTA
    // =========================
    public void AltaMascota() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            CallableStatement cs = c.prepareCall(
                "{call AltaMascotaVeterinaria(?,?,?,?,?,?,?,?,?,?)}"
            );

            cs.setInt(1, idCliente);
            cs.setInt(2, idEspecie);
            cs.setInt(3, idRaza);
            cs.setInt(4, idSexo);
            cs.setString(5, nombreMascota);

            // fechaNacimiento puede venir vacía del form
            if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
                cs.setString(6, fechaNacimiento);
            } else {
                cs.setNull(6, java.sql.Types.DATE);
            }

            // peso puede venir vacío del form
            try {
                cs.setBigDecimal(7, new java.math.BigDecimal(peso.trim()));
            } catch (Exception ep) {
                cs.setNull(7, java.sql.Types.DECIMAL);
            }

            cs.setString(8, color);
            cs.setString(9, observaciones);
            cs.registerOutParameter(10, java.sql.Types.VARCHAR);

            cs.execute();

            respuesta = cs.getString(10);

            cs.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error en AltaMascota: " + e.getClass().getSimpleName() + " — " + e.getMessage();
        if (e.getCause() != null) respuesta += " | Causa: " + e.getCause().getMessage();
    }
}

    // =========================
    // MASCOTAS — BAJA LÓGICA
    // =========================
    public void BajaMascota() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            CallableStatement cs = c.prepareCall(
                "{call BajaMascotaVeterinaria(?,?,?)}"
            );

            cs.setInt(1, idMascota);
            cs.setInt(2, idMotivo);
            cs.registerOutParameter(3, java.sql.Types.VARCHAR);

            cs.execute();

            respuesta = cs.getString(3);

            cs.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error en BajaMascota: " + e.getMessage();
    }
}

    // =========================
    // MASCOTAS — VALIDAR EXISTENCIA
    // =========================
    public void ValidarMascota() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            PreparedStatement ps = c.prepareStatement(
                "SELECT COUNT(*) AS total FROM Mascotas WHERE idMascota = ?"
            );

            ps.setInt(1, idMascota);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");

                if (total > 0) {
                    respuesta = "Mascota encontrada";
                } else {
                    respuesta = "Error.jsp?respuesta=Mascota no encontrada";
                }
            }

            rs.close();
            ps.close();
            c.close();

        } else {
            respuesta = "Error.jsp?respuesta=No hay conexión a la base";
        }

    } catch (Exception e) {
        respuesta = "Error.jsp?respuesta=Error al buscar " + e.getMessage();
    }
}

    // =========================
    // MASCOTAS — CARGAR PARA EDITAR
    // =========================
    public void CargarMascota() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            PreparedStatement ps = c.prepareStatement(
                "SELECT M.idMascota, M.idCliente, M.idEspecie, M.idRaza, M.idSexo, " +
                "C.nombre + ' ' + C.paterno AS cliente, " +
                "M.nombre, M.fechaNacimiento, M.peso, M.color, M.observaciones, M.status " +
                "FROM Mascotas M INNER JOIN Clientes C ON M.idCliente = C.idCliente " +
                "WHERE M.idMascota = ?"
            );

            ps.setInt(1, idMascota);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idCliente       = rs.getInt("idCliente");
                idEspecie       = rs.getInt("idEspecie");
                idRaza          = rs.getInt("idRaza");
                idSexo          = rs.getInt("idSexo");
                nombreMascota   = rs.getString("nombre");
                fechaNacimiento = rs.getString("fechaNacimiento");
                peso            = rs.getString("peso");
                color           = rs.getString("color");
                observaciones   = rs.getString("observaciones");
                status          = rs.getString("status");
            } else {
                System.out.println("Mascota no encontrada en BD");
            }

            rs.close();
            ps.close();
            c.close();
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    // =========================
    // MASCOTAS — MODIFICAR
    // =========================
    public void ModificarMascota() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            CallableStatement cs = c.prepareCall(
                "{call ModificarMascotaVeterinaria(?,?,?,?,?,?,?,?,?,?,?)}"
            );

            cs.setInt(1, idMascota);
            cs.setInt(2, idEspecie);
            cs.setInt(3, idRaza);
            cs.setInt(4, idSexo);
            cs.setString(5, nombreMascota);
            cs.setString(6, fechaNacimiento);
            cs.setString(7, peso);
            cs.setString(8, color);
            cs.setString(9, observaciones);
            cs.setString(10, status);
            cs.registerOutParameter(11, java.sql.Types.VARCHAR);

            cs.execute();

            respuesta = cs.getString(11);

            cs.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error en ModificarMascota: " + e.getMessage();
    }
}

    // =========================
    // MASCOTAS — CONSULTA INDIVIDUAL
    // =========================
    public void ConsultaMascotaIndividual() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            PreparedStatement ps = c.prepareStatement(
                "SELECT idMascota, cliente, nombreMascota, especie, raza, " +
                "sexo, fechaNacimiento, peso, color, observaciones, status, fechaRegistro " +
                "FROM VistaMascotas WHERE idMascota = ?"
            );

            ps.setInt(1, idMascota);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                titulos = new String[]{
                    "ID", "Cliente", "Mascota", "Especie", "Raza",
                    "Sexo", "Fecha Nac.", "Peso", "Color", "Observaciones",
                    "Status", "Fecha Registro"
                };

                datos = new String[1][12];

                for (int i = 0; i < 12; i++) {
                    datos[0][i] = rs.getString(i + 1);
                }

                crearTablaU(titulos, datos);

            } else {
                respuesta = "<h3 style='color:red;'>Mascota no encontrada</h3>";
            }

            rs.close();
            ps.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la BD";
        }

    } catch (Exception e) {
        respuesta = "Error: " + e.getMessage();
    }
}

    // =========================
    // MASCOTAS — CONSULTA GENERAL
    // =========================
    public void ConsultaMascotasGeneral() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            Statement st = c.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );

            ResultSet rs = st.executeQuery(
                "SELECT idMascota, cliente, nombreMascota, especie, raza, " +
                "sexo, fechaNacimiento, peso, color, observaciones, status " +
                "FROM VistaMascotas WHERE status = 'A' " +
                "ORDER BY cliente, nombreMascota"
            );

            int filas = 0, columnas = 0;

            if (rs.next()) {

                columnas = rs.getMetaData().getColumnCount();

                rs.last();
                filas = rs.getRow();

                titulos = new String[columnas];
                datos   = new String[filas][columnas];

                for (int i = 0; i < columnas; i++) {
                    titulos[i] = rs.getMetaData().getColumnName(i + 1);
                }

                rs.first();

                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        datos[i][j] = rs.getString(j + 1);
                    }
                    rs.next();
                }

                crearTablaU(titulos, datos);

            } else {
                respuesta = "No hay mascotas registradas";
            }

            rs.close();
            st.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error al mostrar mascotas: " + e.getMessage();
    }
}

    // =========================
    // CATÁLOGOS — CARGAR ESPECIES
    // =========================
    public void CargarEspecies() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            Statement st = c.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );

            ResultSet rs = st.executeQuery(
                "SELECT idEspecie, nombre FROM Especie WHERE status = 'A' ORDER BY nombre"
            );

            int filas = 0, columnas = 0;

            if (rs.next()) {

                columnas = rs.getMetaData().getColumnCount();

                rs.last();
                filas = rs.getRow();

                titulos = new String[columnas];
                datos   = new String[filas][columnas];

                for (int i = 0; i < columnas; i++) {
                    titulos[i] = rs.getMetaData().getColumnName(i + 1);
                }

                rs.first();

                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        datos[i][j] = rs.getString(j + 1);
                    }
                    rs.next();
                }
            }

            rs.close();
            st.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error al cargar especies: " + e.getMessage();
    }
}

    // =========================
    // CATÁLOGOS — CARGAR RAZAS POR ESPECIE
    // =========================
    public void CargarRazasPorEspecie() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            PreparedStatement ps = c.prepareStatement(
                "SELECT idRaza, nombre FROM Raza " +
                "WHERE idEspecie = ? AND status = 'A' ORDER BY nombre"
            );

            ps.setInt(1, idEspecie);

            ResultSet rs = ps.executeQuery();

            int filas = 0, columnas = 0;

            // Usamos Statement scroll para poder contar filas
            Statement st = c.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );

            ResultSet rs2 = st.executeQuery(
                "SELECT idRaza, nombre FROM Raza " +
                "WHERE idEspecie = " + idEspecie + " AND status = 'A' ORDER BY nombre"
            );

            if (rs2.next()) {

                columnas = rs2.getMetaData().getColumnCount();

                rs2.last();
                filas = rs2.getRow();

                titulos = new String[columnas];
                datos   = new String[filas][columnas];

                for (int i = 0; i < columnas; i++) {
                    titulos[i] = rs2.getMetaData().getColumnName(i + 1);
                }

                rs2.first();

                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        datos[i][j] = rs2.getString(j + 1);
                    }
                    rs2.next();
                }
            }

            rs.close();
            ps.close();
            rs2.close();
            st.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error al cargar razas: " + e.getMessage();
    }
}

    // =========================
    // CATÁLOGOS — CARGAR SEXOS
    // =========================
    public void CargarSexos() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            Statement st = c.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );

            ResultSet rs = st.executeQuery(
                "SELECT idSexo, descripcion FROM SexoMascota ORDER BY idSexo"
            );

            int filas = 0, columnas = 0;

            if (rs.next()) {

                columnas = rs.getMetaData().getColumnCount();

                rs.last();
                filas = rs.getRow();

                titulos = new String[columnas];
                datos   = new String[filas][columnas];

                for (int i = 0; i < columnas; i++) {
                    titulos[i] = rs.getMetaData().getColumnName(i + 1);
                }

                rs.first();

                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        datos[i][j] = rs.getString(j + 1);
                    }
                    rs.next();
                }
            }

            rs.close();
            st.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error al cargar sexos: " + e.getMessage();
    }
}

    // =========================
    // CATÁLOGOS — CARGAR MOTIVOS DE BAJA
    // =========================
    public void CargarMotivosBaja() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            Statement st = c.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );

            ResultSet rs = st.executeQuery(
                "SELECT idMotivo, descripcion FROM MotivoBaja ORDER BY idMotivo"
            );

            int filas = 0, columnas = 0;

            if (rs.next()) {

                columnas = rs.getMetaData().getColumnCount();

                rs.last();
                filas = rs.getRow();

                titulos = new String[columnas];
                datos   = new String[filas][columnas];

                for (int i = 0; i < columnas; i++) {
                    titulos[i] = rs.getMetaData().getColumnName(i + 1);
                }

                rs.first();

                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        datos[i][j] = rs.getString(j + 1);
                    }
                    rs.next();
                }
            }

            rs.close();
            st.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error al cargar motivos: " + e.getMessage();
    }
}

    // =========================
    // CATÁLOGOS — CARGAR USUARIOS (para selector en RegistroMascota)
    // Devuelve idCliente, usuario y nombre completo de clientes activos
    // =========================
    public void CargarUsuarios() {
    try {
        Connection c = Conexion.conectar();

        if (c != null) {

            Statement st = c.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );

            ResultSet rs = st.executeQuery(
                "SELECT C.idCliente, A.usuario, C.nombre + ' ' + C.paterno AS nombreCompleto " +
                "FROM Clientes C INNER JOIN Acceso A ON C.idCliente = A.idCliente " +
                "WHERE C.status = 'A' AND A.tipo = 'C' " +
                "ORDER BY C.nombre"
            );

            int filas = 0, columnas = 0;

            if (rs.next()) {

                columnas = rs.getMetaData().getColumnCount();

                rs.last();
                filas = rs.getRow();

                titulos = new String[columnas];
                datos   = new String[filas][columnas];

                for (int i = 0; i < columnas; i++) {
                    titulos[i] = rs.getMetaData().getColumnName(i + 1);
                }

                rs.first();

                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        datos[i][j] = rs.getString(j + 1);
                    }
                    rs.next();
                }
            }

            rs.close();
            st.close();
            c.close();

        } else {
            respuesta = "No hay conexión a la base de datos";
        }

    } catch (Exception e) {
        respuesta = "Error al cargar usuarios: " + e.getMessage();
    }
}

    // =========================
    // CARGAR EMPLEADOS Y ADMINISTRADORES (tipo E y A)
    // =========================
    public void CargarEmpleadosYAdmins() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            Statement st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(
                "SELECT A.usuario, C.nombre + ' ' + C.paterno + ' ' + C.materno AS nombreCompleto, A.tipo " +
                "FROM Clientes C INNER JOIN Acceso A ON C.idCliente = A.idCliente " +
                "WHERE C.status = 'A' AND A.tipo IN ('E','A') " +
                "ORDER BY nombreCompleto"
            );
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("usuario"),
                    rs.getString("nombreCompleto"),
                    rs.getString("tipo")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            st.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar empleados: " + e.getMessage();
    }
}

    // =========================
    // GETTERS/SETTERS — EMPLEADOS
    // =========================
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

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

    public String getFechaDesde() { return fechaDesde; }
    public void setFechaDesde(String fechaDesde) { this.fechaDesde = fechaDesde; }

    public String getFechaHasta() { return fechaHasta; }
    public void setFechaHasta(String fechaHasta) { this.fechaHasta = fechaHasta; }

    // =========================
    // EMPLEADOS — CARGAR PUESTOS
    // =========================
    public void CargarPuestos() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            Statement st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT idPuesto, nombre FROM Puesto WHERE status = 'A' ORDER BY nombre");
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{ rs.getString("idPuesto"), rs.getString("nombre") });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            st.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar puestos: " + e.getMessage();
    }
}

    // =========================
    // EMPLEADOS — CARGAR TURNOS (con horaEntrada y horaSalida para el calendario)
    // =========================
    public void CargarTurnos() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            Statement st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(
                "SELECT idTurno, nombre, horaEntrada, horaSalida FROM Turno WHERE status = 'A' ORDER BY nombre"
            );
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("idTurno"),
                    rs.getString("nombre"),
                    rs.getString("horaEntrada"),
                    rs.getString("horaSalida")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            st.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar turnos: " + e.getMessage();
    }
}

    // =========================
    // EMPLEADOS — ASIGNAR DATOS LABORALES
    // =========================
    public void AsignarDatosLaborales() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call AsignarDatosLaboralesVeterinaria(?,?,?,?,?,?)}");
            cs.setInt(1, idCliente);
            cs.setInt(2, idPuesto);
            cs.setInt(3, idTurno);
            try {
                cs.setBigDecimal(4, new java.math.BigDecimal(salario.trim()));
            } catch (Exception es) {
                cs.setNull(4, java.sql.Types.DECIMAL);
            }
            cs.setString(5, fechaIngreso);
            cs.registerOutParameter(6, java.sql.Types.VARCHAR);
            cs.execute();
            respuesta = cs.getString(6);
            cs.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base de datos";
        }
    } catch (Exception e) {
        respuesta = "Error en AsignarDatosLaborales: " + e.getMessage();
    }
}

    // =========================
    // EMPLEADOS — CARGAR EMPLEADO (para modificar)
    // =========================
    public void CargarEmpleado() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT E.idEmpleado, E.idPuesto, E.idTurno, E.salario, E.fechaIngreso, E.status, " +
                "C.nombre, C.paterno, C.materno, A.usuario, P.nombre AS puesto, T.nombre AS turno " +
                "FROM Empleados E " +
                "INNER JOIN Clientes C ON E.idCliente = C.idCliente " +
                "INNER JOIN Acceso   A ON E.idCliente = A.idCliente " +
                "INNER JOIN Puesto   P ON E.idPuesto  = P.idPuesto " +
                "INNER JOIN Turno    T ON E.idTurno   = T.idTurno " +
                "WHERE E.idCliente = ?"
            );
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idEmpleado   = rs.getInt("idEmpleado");
                idPuesto     = rs.getInt("idPuesto");
                idTurno      = rs.getInt("idTurno");
                salario      = rs.getString("salario");
                fechaIngreso = rs.getString("fechaIngreso");
                status       = rs.getString("status");
                nombre       = rs.getString("nombre");
                paterno      = rs.getString("paterno");
                materno      = rs.getString("materno");
                usuario      = rs.getString("usuario");
                nombrePuesto = rs.getString("puesto");
                nombreTurno  = rs.getString("turno");
            } else {
                respuesta = "Empleado no encontrado";
            }
            rs.close();
            ps.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar empleado: " + e.getMessage();
    }
}

    // =========================
    // EMPLEADOS — MODIFICAR DATOS LABORALES
    // =========================
    public void ModificarDatosLaborales() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call ModificarDatosLaboralesVeterinaria(?,?,?,?,?,?)}");
            cs.setInt(1, idEmpleado);
            cs.setInt(2, idPuesto);
            cs.setInt(3, idTurno);
            try {
                cs.setBigDecimal(4, new java.math.BigDecimal(salario.trim()));
            } catch (Exception es) {
                cs.setNull(4, java.sql.Types.DECIMAL);
            }
            cs.setString(5, status);
            cs.registerOutParameter(6, java.sql.Types.VARCHAR);
            cs.execute();
            respuesta = cs.getString(6);
            cs.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base de datos";
        }
    } catch (Exception e) {
        respuesta = "Error en ModificarDatosLaborales: " + e.getMessage();
    }
}

    // =========================
    // EMPLEADOS — CONSULTA DATOS LABORALES
    // =========================
    public void ConsultaDatosLaborales() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call ConsultaDatosLaboralesVeterinaria(?)}");
            cs.setInt(1, idCliente);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                titulos = new String[]{
                    "ID Empleado", "Nombre", "Usuario", "Puesto",
                    "Turno", "Entrada", "Salida", "Salario", "Fecha Ingreso", "Status"
                };
                datos = new String[1][10];
                for (int i = 0; i < 10; i++) {
                    datos[0][i] = rs.getString(i + 1);
                }
                crearTablaU(titulos, datos);
            } else {
                respuesta = "<h3 style='color:red;'>Empleado no encontrado</h3>";
            }
            rs.close();
            cs.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base de datos";
        }
    } catch (Exception e) {
        respuesta = "Error en ConsultaDatosLaborales: " + e.getMessage();
    }
}

    // =========================
    // EMPLEADOS — ASIGNAR DÍA DE SEMANA
    // =========================
    public void AsignarDiaSemana() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call AsignarDiaSemanaVeterinaria(?,?,?,?,?)}");
            cs.setInt(1, idEmpleado);
            cs.setInt(2, diaSemana);
            cs.setString(3, horaEntrada);
            cs.setString(4, horaSalida);
            cs.registerOutParameter(5, java.sql.Types.VARCHAR);
            cs.execute();
            respuesta = cs.getString(5);
            cs.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base de datos";
        }
    } catch (Exception e) {
        respuesta = "Error en AsignarDiaSemana: " + e.getMessage();
    }
}

    // =========================
    // EMPLEADOS — ELIMINAR DÍA DE SEMANA
    // =========================
    public void EliminarDiaSemana() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call EliminarDiaSemanaVeterinaria(?,?,?)}");
            cs.setInt(1, idEmpleado);
            cs.setInt(2, diaSemana);
            cs.registerOutParameter(3, java.sql.Types.VARCHAR);
            cs.execute();
            respuesta = cs.getString(3);
            cs.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base de datos";
        }
    } catch (Exception e) {
        respuesta = "Error en EliminarDiaSemana: " + e.getMessage();
    }
}

    // =========================
    // EMPLEADOS — CONSULTA HORARIO SEMANAL
    // =========================
    public void ConsultaHorarioSemanal() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call ConsultaHorarioSemanalVeterinaria(?)}");
            cs.setInt(1, idEmpleado);
            ResultSet rs = cs.executeQuery();

            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("diaSemana"),
                    rs.getString("nombreDia"),
                    rs.getString("horaEntrada"),
                    rs.getString("horaSalida"),
                    rs.getString("empleado"),
                    rs.getString("puesto"),
                    rs.getString("turno")
                });
            }

            if (!lista.isEmpty()) {
                titulos = new String[]{ "Día#", "Día", "Entrada", "Salida", "Empleado", "Puesto", "Turno" };
                datos   = lista.toArray(new String[0][]);
                // No llamar crearTablaU para que datos quede disponible para los JSP
            } else {
                respuesta = "<h3 style='color:#555;'>No hay horario semanal asignado</h3>";
            }

            rs.close();
            cs.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base de datos";
        }
    } catch (Exception e) {
        respuesta = "Error en ConsultaHorarioSemanal: " + e.getMessage();
    }
}

    public int getDiaSemana() { return diaSemana; }
    public void setDiaSemana(int diaSemana) { this.diaSemana = diaSemana; }

    // =========================
    // GETTERS/SETTERS — CITAS
    // =========================
    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public int getIdAcceso() { return idAcceso; }
    public void setIdAcceso(int idAcceso) { this.idAcceso = idAcceso; }

    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getObservacionesCita() { return observacionesCita; }
    public void setObservacionesCita(String observacionesCita) { this.observacionesCita = observacionesCita; }

    public String getEstadoCita() { return estadoCita; }
    public void setEstadoCita(String estadoCita) { this.estadoCita = estadoCita; }

    public String getFechaCita() { return fechaCita; }
    public void setFechaCita(String fechaCita) { this.fechaCita = fechaCita; }

    public String getHoraCita() { return horaCita; }
    public void setHoraCita(String horaCita) { this.horaCita = horaCita; }

    // =========================
    // CITAS — CARGAR VETERINARIOS ACTIVOS
    // =========================
    public void CargarVeterinarios() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            Statement st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(
                "SELECT A.idAcceso, A.usuario, " +
                "C.nombre + ' ' + C.paterno + ' ' + C.materno AS nombreCompleto, " +
                "P.nombre AS puesto " +
                "FROM Empleados E " +
                "INNER JOIN Clientes C ON E.idCliente = C.idCliente " +
                "INNER JOIN Acceso   A ON E.idCliente = A.idCliente " +
                "INNER JOIN Puesto   P ON E.idPuesto  = P.idPuesto " +
                "WHERE E.status = 'A' AND P.nombre LIKE '%Veterinario%' " +
                "ORDER BY nombreCompleto"
            );
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("idAcceso"),
                    rs.getString("nombreCompleto"),
                    rs.getString("puesto"),
                    rs.getString("usuario")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            st.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar veterinarios: " + e.getMessage();
    }
}

    // =========================
    // CITAS — CARGAR HORARIO DE UN VETERINARIO
    // =========================
    public void CargarHorarioVeterinario() {
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
                "INNER JOIN Empleados E ON HL.idEmpleado = E.idEmpleado " +
                "INNER JOIN Acceso    A ON E.idCliente   = A.idCliente " +
                "WHERE A.idAcceso = ? " +
                "ORDER BY HL.diaSemana"
            );
            ps.setInt(1, idAcceso);
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
    // CITAS — CARGAR MASCOTAS DE UN CLIENTE
    // =========================
    public void CargarMascotasPorCliente() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT M.idMascota, M.nombre, E.nombre AS especie " +
                "FROM Mascotas M " +
                "INNER JOIN Especie E ON M.idEspecie = E.idEspecie " +
                "WHERE M.idCliente = ? AND M.status = 'A' " +
                "ORDER BY M.nombre"
            );
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("idMascota"),
                    rs.getString("nombre"),
                    rs.getString("especie")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            ps.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar mascotas: " + e.getMessage();
    }
}

    // =========================
    // CITAS — CARGAR TODAS LAS MASCOTAS DE UN CLIENTE (activas e inactivas)
    // =========================
    public void CargarTodasMascotasPorCliente() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT M.idMascota, M.nombre, E.nombre AS especie, M.status " +
                "FROM Mascotas M " +
                "INNER JOIN Especie E ON M.idEspecie = E.idEspecie " +
                "WHERE M.idCliente = ? " +
                "ORDER BY M.nombre"
            );
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("idMascota"),
                    rs.getString("nombre"),
                    rs.getString("especie"),
                    rs.getString("status")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            ps.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar mascotas: " + e.getMessage();
    }
}

    // =========================
    // CITAS — REGISTRAR CITA
    // =========================
    public void RegistrarCita() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall(
                "{call AgregarCitaVeterinaria(?,?,?,?,?,?,?)}"
            );
            cs.setInt(1, idCliente);
            cs.setInt(2, idMascota);
            cs.setInt(3, idAcceso);
            cs.setInt(4, idHorario);
            cs.setString(5, motivo);
            cs.setString(6, observacionesCita);
            cs.registerOutParameter(7, java.sql.Types.VARCHAR);
            cs.execute();
            respuesta = cs.getString(7);
            cs.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base de datos";
        }
    } catch (Exception e) {
        respuesta = "Error al registrar cita: " + e.getMessage();
    }
}

    // =========================
    // CITAS — CARGAR CITAS PENDIENTES DE UN CLIENTE
    // =========================
    public void CargarCitasPorCliente() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call CargarCitasPorClienteVeterinaria(?)}");
            cs.setInt(1, idCliente);
            ResultSet rs = cs.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("idCita"),
                    rs.getString("mascota"),
                    rs.getString("veterinario"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getString("estado")
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

    // =========================
    // CITAS — CANCELAR CITA (baja lógica)
    // =========================
    public void CancelarCita() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call CancelarCitaVeterinaria(?,?,?)}");
            cs.setInt(1, idCita);
            cs.setString(2, estadoCita);  // C=Cancelada, T=Terminada
            cs.registerOutParameter(3, java.sql.Types.VARCHAR);
            cs.execute();
            respuesta = cs.getString(3);
            cs.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base de datos";
        }
    } catch (Exception e) {
        respuesta = "Error al cancelar cita: " + e.getMessage();
    }
}

    // =========================
    // CITAS — CARGAR TODAS LAS CITAS DE UNA MASCOTA (todos los estados)
    // =========================
    public void CargarTodasCitasMascota() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT CI.idCita, " +
                "C2.nombre + ' ' + C2.paterno + ' ' + C2.materno AS veterinario, " +
                "CONVERT(VARCHAR(10), H.fecha, 120) AS fecha, " +
                "CONVERT(VARCHAR(8), H.hora, 108) AS hora, " +
                "ISNULL(CI.motivo, '') AS motivo, " +
                "ISNULL(CI.observaciones, '') AS observaciones, " +
                "CI.estado " +
                "FROM Citas CI " +
                "INNER JOIN Horarios  H  ON CI.idHorario = H.idHorario " +
                "INNER JOIN Acceso    A  ON CI.idAcceso  = A.idAcceso " +
                "INNER JOIN Clientes  C2 ON A.idCliente  = C2.idCliente " +
                "WHERE (CI.idMascota = ? OR (CI.idMascota IS NULL AND CI.idCliente = ?)) " +
                "ORDER BY H.fecha DESC, H.hora"
            );
            ps.setInt(1, idMascota);
            ps.setInt(2, idCliente);
            System.out.println("CargarTodasCitasMascota — idMascota: " + idMascota + " idCliente: " + idCliente);
            ResultSet rs = ps.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                // Calcular día de semana a partir de la fecha
                String fechaStr = rs.getString("fecha");
                String horaStr  = rs.getString("hora");
                // Obtener horaEntrada/horaSalida del veterinario para ese día
                String hEntrada = "06:00:00";
                String hSalida  = "22:00:00";
                String diaSem   = "1";
                try {
                    java.time.LocalDate ld = java.time.LocalDate.parse(fechaStr);
                    int dow = ld.getDayOfWeek().getValue(); // 1=Lunes...7=Domingo
                    diaSem = String.valueOf(dow);
                } catch (Exception ex) {}

                lista.add(new String[]{
                    String.valueOf(rs.getInt("idCita")),
                    rs.getString("veterinario"),
                    fechaStr,
                    horaStr,
                    rs.getString("motivo"),
                    rs.getString("observaciones"),
                    rs.getString("estado"),
                    diaSem,
                    hEntrada,
                    hSalida
                });
            }
            System.out.println("CargarTodasCitasMascota — filas: " + lista.size());
            datos = lista.toArray(new String[0][]);
            rs.close();
            ps.close();

            // Ahora para cada cita, buscar el horario laboral del veterinario
            if (datos != null && datos.length > 0) {
                for (String[] fila : datos) {
                    try {
                        int diaSemana = Integer.parseInt(fila[7]);
                        // Buscar en HorarioLaboral del veterinario de esta cita
                        PreparedStatement ps2 = c.prepareStatement(
                            "SELECT CONVERT(VARCHAR(8), HL.horaEntrada, 108) AS hE, " +
                            "CONVERT(VARCHAR(8), HL.horaSalida, 108) AS hS " +
                            "FROM HorarioLaboral HL " +
                            "INNER JOIN Empleados E ON HL.idEmpleado = E.idEmpleado " +
                            "INNER JOIN Acceso A ON E.idCliente = A.idCliente " +
                            "INNER JOIN Citas CI ON CI.idAcceso = A.idAcceso " +
                            "WHERE CI.idCita = ? AND HL.diaSemana = ?"
                        );
                        ps2.setInt(1, Integer.parseInt(fila[0]));
                        ps2.setInt(2, diaSemana);
                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            fila[8] = rs2.getString("hE");
                            fila[9] = rs2.getString("hS");
                        }
                        rs2.close();
                        ps2.close();
                    } catch (Exception ex) {}
                }
            }

            c.close();
        }
    } catch (Exception e) {
        System.out.println("ERROR CargarTodasCitasMascota: " + e.getMessage());
        respuesta = "Error al cargar citas de mascota: " + e.getMessage();
    }
}

    // =========================
    // CITAS — CONSULTAR TODAS LAS CITAS DE UN CLIENTE
    // =========================
    public void ConsultaCitasCliente() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT CI.idCita, M.nombre AS mascota, " +
                "C2.nombre + ' ' + C2.paterno + ' ' + C2.materno AS veterinario, " +
                "CONVERT(VARCHAR(10), H.fecha, 120) AS fecha, " +
                "CONVERT(VARCHAR(8), H.hora, 108) AS hora, " +
                "CI.motivo, CI.observaciones, CI.estado, " +
                "DATEPART(WEEKDAY, H.fecha) AS diaSemanaSQL, " +
                "CONVERT(VARCHAR(8), HL.horaEntrada, 108) AS horaEntrada, " +
                "CONVERT(VARCHAR(8), HL.horaSalida, 108) AS horaSalida " +
                "FROM Citas CI " +
                "INNER JOIN Horarios  H  ON CI.idHorario = H.idHorario " +
                "INNER JOIN Acceso    A  ON CI.idAcceso  = A.idAcceso " +
                "INNER JOIN Clientes  C2 ON A.idCliente  = C2.idCliente " +
                "LEFT  JOIN Mascotas  M  ON CI.idMascota = M.idMascota " +
                "LEFT  JOIN Empleados E  ON A.idCliente  = E.idCliente " +
                "LEFT  JOIN HorarioLaboral HL ON E.idEmpleado = HL.idEmpleado " +
                "   AND HL.diaSemana = ((DATEPART(WEEKDAY, H.fecha) + 5) % 7) + 1 " +
                "WHERE CI.idCliente = ? " +
                "ORDER BY H.fecha DESC, H.hora"
            );
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                // Convertir diaSemanaSQL (1=Dom...7=Sab) a 1=Lun...7=Dom
                int diaSQL = rs.getInt("diaSemanaSQL");
                int diaLun = ((diaSQL + 5) % 7) + 1;
                lista.add(new String[]{
                    rs.getString("idCita"),
                    rs.getString("mascota"),
                    rs.getString("veterinario"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getString("observaciones"),
                    rs.getString("estado"),
                    String.valueOf(diaLun),
                    rs.getString("horaEntrada") != null ? rs.getString("horaEntrada") : "06:00:00",
                    rs.getString("horaSalida")  != null ? rs.getString("horaSalida")  : "22:00:00"
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            ps.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al consultar citas: " + e.getMessage();
    }
}

    // =========================
    // CITAS — CARGAR CITAS PENDIENTES DE UNA MASCOTA
    // =========================
    public void CargarCitasPorMascota() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT CI.idCita, " +
                "C2.nombre + ' ' + C2.paterno + ' ' + C2.materno AS veterinario, " +
                "CONVERT(VARCHAR(10), H.fecha, 120) AS fecha, " +
                "CONVERT(VARCHAR(8), H.hora, 108) AS hora, " +
                "CI.motivo " +
                "FROM Citas CI " +
                "INNER JOIN Horarios H ON CI.idHorario = H.idHorario " +
                "INNER JOIN Acceso   A ON CI.idAcceso  = A.idAcceso " +
                "INNER JOIN Clientes C2 ON A.idCliente = C2.idCliente " +
                "WHERE CI.idMascota = ? AND CI.estado = 'P' " +
                "ORDER BY H.fecha, H.hora"
            );
            ps.setInt(1, idMascota);
            ResultSet rs = ps.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("idCita"),
                    rs.getString("veterinario"),
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
        respuesta = "Error al cargar citas por mascota: " + e.getMessage();
    }
}

    // =========================
    // CITAS — HORAS OCUPADAS DE UN VETERINARIO
    // =========================
    public void HorasOcupadasVeterinario() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call HorasOcupadasVeterinarioVeterinaria(?)}");
            cs.setInt(1, idAcceso);
            ResultSet rs = cs.executeQuery();
            List<String[]> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("idHorario")
                });
            }
            datos = lista.toArray(new String[0][]);
            rs.close();
            cs.close();
            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error al cargar horas ocupadas: " + e.getMessage();
    }
}

    // =========================
    // CARGAR USUARIO CON PUESTO (para menú de bienvenida)
    // =========================
    public void CargarUsuarioConPuesto() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            // Datos personales + tipo
            PreparedStatement ps = c.prepareStatement(
                "SELECT C.nombre, C.paterno, C.materno, A.tipo " +
                "FROM Clientes C INNER JOIN Acceso A ON C.idCliente = A.idCliente " +
                "WHERE A.usuario = ?"
            );
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre  = rs.getString("nombre");
                paterno = rs.getString("paterno");
                materno = rs.getString("materno");
                tipo    = rs.getString("tipo");
            }
            rs.close();
            ps.close();

            // Puesto (solo si es Administrador o Empleado)
            if ("A".equals(tipo) || "E".equals(tipo)) {
                PreparedStatement ps2 = c.prepareStatement(
                    "SELECT P.nombre AS puesto " +
                    "FROM Empleados E " +
                    "INNER JOIN Clientes C ON E.idCliente = C.idCliente " +
                    "INNER JOIN Acceso   A ON E.idCliente = A.idCliente " +
                    "INNER JOIN Puesto   P ON E.idPuesto  = P.idPuesto " +
                    "WHERE A.usuario = ?"
                );
                ps2.setString(1, usuario);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    nombrePuesto = rs2.getString("puesto");
                } else {
                    nombrePuesto = "Sin puesto";
                }
                rs2.close();
                ps2.close();
            }

            c.close();
        }
    } catch (Exception e) {
        respuesta = "Error en CargarUsuarioConPuesto: " + e.getMessage();
    }
}

    // =========================
    // CAMBIAR CONTRASEÑA (genera nuevo usuario y password)
    // =========================
    public void CambiarContrasena() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            PreparedStatement ps = c.prepareStatement(
                "SELECT C.idCliente, C.nombre, C.paterno, C.materno " +
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

                // Generar nuevo usuario y contraseña
                obtenerUsuario();

                // Actualizar en Acceso
                PreparedStatement ps2 = c.prepareStatement(
                    "UPDATE Acceso SET usuario = ?, password = ? WHERE idCliente = ?"
                );
                ps2.setString(1, usuario);
                ps2.setString(2, EncriptarMD5.encripta(pass));
                ps2.setInt(3, idCliente);
                ps2.execute();
                ps2.close();

                respuesta += "<br><h3 style='color:#023e8a;'>Contraseña cambiada correctamente</h3>";
            } else {
                respuesta = "<h3 style='color:red;'>Usuario no encontrado</h3>";
            }
            rs.close();
            ps.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base";
        }
    } catch (Exception e) {
        respuesta = "Error al cambiar contraseña: " + e.getMessage();
    }
}

    // =========================
    // CITAS — MODIFICAR CITA
    // =========================
    public void ModificarCita() {
    try {
        Connection c = Conexion.conectar();
        if (c != null) {
            CallableStatement cs = c.prepareCall("{call ModificarCitaVeterinaria(?,?,?,?,?,?)}");
            cs.setInt(1, idCita);
            cs.setInt(2, idAcceso);
            cs.setInt(3, idHorario);
            cs.setString(4, motivo);
            cs.setString(5, observacionesCita);
            cs.registerOutParameter(6, java.sql.Types.VARCHAR);
            cs.execute();
            respuesta = cs.getString(6);
            cs.close();
            c.close();
        } else {
            respuesta = "No hay conexión a la base de datos";
        }
    } catch (Exception e) {
        respuesta = "Error al modificar cita: " + e.getMessage();
    }
}

}
