package control;

import bean.Administrador;
import bean.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "controlCitas", urlPatterns = {"/controlCitas"})
public class controlCitas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("CargarVeterinarios".equals(accion)) {
            Administrador a = new Administrador();
            a.setOpcion("CargarVeterinarios");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarHorarioVeterinario".equals(accion)) {
            Administrador a = new Administrador();
            a.setIdAcceso(parseInt(request, "idAcceso"));
            a.setOpcion("CargarHorarioVeterinario");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarMascotasPorCliente".equals(accion)) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usr"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("application/json;charset=UTF-8");
            if (idCliente == 0) { response.getWriter().println("[]"); return; }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarMascotasPorCliente");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("HorasOcupadas".equals(accion)) {
            Administrador a = new Administrador();
            a.setIdAcceso(parseInt(request, "idAcceso"));
            a.setOpcion("HorasOcupadasVeterinario");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarCitasMascota".equals(accion)) {
            Administrador a = new Administrador();
            a.setIdMascota(parseInt(request, "idMascota"));
            a.setOpcion("CargarCitasPorMascota");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarCitasCliente".equals(accion)) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usr"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("application/json;charset=UTF-8");
            if (idCliente == 0) { response.getWriter().println("[]"); return; }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarCitasPorCliente");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("ObtenerIdHorario".equals(accion)) {
            String fecha = request.getParameter("fecha");
            String hora  = request.getParameter("hora");
            response.setContentType("application/json;charset=UTF-8");
            try {
                java.sql.Connection c = Conexion.conectar();
                if (c != null) {
                    java.sql.PreparedStatement ps = c.prepareStatement(
                        "SELECT idHorario FROM Horarios WHERE fecha = ? AND hora = ?"
                    );
                    ps.setString(1, fecha);
                    ps.setString(2, hora);
                    java.sql.ResultSet rs = ps.executeQuery();
                    int idHorario = 0;
                    if (rs.next()) {
                        idHorario = rs.getInt("idHorario");
                    } else {
                        java.sql.PreparedStatement ins = c.prepareStatement(
                            "INSERT INTO Horarios(fecha, hora, disponible) VALUES(?,?,1)",
                            java.sql.Statement.RETURN_GENERATED_KEYS
                        );
                        ins.setString(1, fecha);
                        ins.setString(2, hora);
                        ins.executeUpdate();
                        java.sql.ResultSet keys = ins.getGeneratedKeys();
                        if (keys.next()) idHorario = keys.getInt(1);
                        keys.close();
                        ins.close();
                    }
                    rs.close(); ps.close(); c.close();
                    response.getWriter().println("[[\"" + idHorario + "\"]]");
                } else {
                    response.getWriter().println("[]");
                }
            } catch (Exception e) {
                response.getWriter().println("[]");
            }
        }

        if ("GenerarComprobanteCita".equals(accion)) {
            request.getRequestDispatcher("GenerarComprobanteCitaPDF?idCita=" + request.getParameter("idCita"))
                   .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // REGISTRAR CITA
        if (request.getParameter("RegistrarCitaA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioCliente"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario del cliente no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setIdMascota(parseInt(request, "idMascota"));
            a.setIdAcceso(parseInt(request, "idAcceso"));
            a.setIdHorario(parseInt(request, "idHorario"));
            a.setMotivo(request.getParameter("motivo"));
            a.setObservacionesCita(request.getParameter("observaciones"));
            a.setOpcion("RegistrarCita");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // CANCELAR/TERMINAR CITA
        if (request.getParameter("CancelarCitaA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioCliente"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCita(parseInt(request, "idCita"));
            a.setEstadoCita(request.getParameter("estadoCita"));
            a.setOpcion("CancelarCita");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // MODIFICAR CITA
        if (request.getParameter("ModificarCitaA") != null) {
            Administrador a = new Administrador();
            a.setIdCita(parseInt(request, "idCita"));
            a.setIdAcceso(parseInt(request, "idAcceso"));
            a.setIdHorario(parseInt(request, "idHorario"));
            a.setMotivo(request.getParameter("motivo"));
            a.setObservacionesCita(request.getParameter("observaciones"));
            a.setOpcion("ModificarCita");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // BUSCAR CITAS PARA BAJA
        if (request.getParameter("BuscarCitasA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioCliente"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarMascotasPorCliente");
            String[][] mascotas = a.getDatos();
            request.setAttribute("bean", a);
            request.setAttribute("mascotas", mascotas);
            request.getRequestDispatcher("CancelarCita.jsp").forward(request, response);
        }

        // BUSCAR PARA MODIFICAR CITA
        if (request.getParameter("BuscarModificarCitaA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioCliente"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarMascotasPorCliente");
            String[][] mascotas = a.getDatos();
            request.setAttribute("bean", a);
            request.setAttribute("mascotas", mascotas);
            request.getRequestDispatcher("ModificarCita.jsp").forward(request, response);
        }

        // CONSULTAR CITAS
        if (request.getParameter("ConsultaCitasA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioCliente"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarMascotasPorCliente");
            String[][] mascotas = a.getDatos();
            request.setAttribute("bean", a);
            request.setAttribute("mascotas", mascotas);
            request.getRequestDispatcher("ConsultarCitas.jsp").forward(request, response);
        }

        // BUSCAR PARA COMPROBANTE
        if (request.getParameter("BuscarComprobanteA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioCliente"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarMascotasPorCliente");
            String[][] mascotas = a.getDatos();
            request.setAttribute("bean", a);
            request.setAttribute("mascotas", mascotas);
            request.getRequestDispatcher("DescargarComprobante.jsp").forward(request, response);
        }
    }

    private String arrayToJson(String[][] datos) {
        if (datos == null) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < datos.length; i++) {
            sb.append("[");
            for (int j = 0; j < datos[i].length; j++) {
                String val = datos[i][j] != null ? datos[i][j] : "";
                sb.append("\"").append(val).append("\"");
                if (j < datos[i].length - 1) sb.append(",");
            }
            sb.append("]");
            if (i < datos.length - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private int parseInt(HttpServletRequest request, String param) {
        String val = request.getParameter(param);
        if (val == null || val.trim().isEmpty()) return 0;
        try { return Integer.parseInt(val.trim()); } catch (NumberFormatException e) { return 0; }
    }
}
