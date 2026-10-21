package control;

import bean.Cliente;
import bean.Administrador;
import bean.Conexion;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "controlCliente", urlPatterns = {"/controlCliente"})
public class controlCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String usr    = request.getParameter("usr");

        // =========================
        // MENÚ CLIENTE
        // =========================
        if ("MenuCliente".equals(accion)) {
            Cliente a = new Cliente();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            request.setAttribute("bean", a);
            request.getRequestDispatcher("MenuCliente.jsp").forward(request, response);
        }

        // =========================
        // MIS MASCOTAS — REGISTRAR (muestra formulario)
        // =========================
        if ("RegistrarMascota".equals(accion)) {
            Cliente a = new Cliente();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            request.setAttribute("bean", a);
            request.getRequestDispatcher("RegistrarMascotaCliente.jsp").forward(request, response);
        }

        // =========================
        // MIS MASCOTAS — CONSULTAR
        // =========================
        if ("MisMascotas".equals(accion)) {
            Cliente a = new Cliente();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            a.setOpcion("CargarMisMascotas");
            request.setAttribute("bean", a);
            request.setAttribute("mascotas", a.getDatos());
            request.getRequestDispatcher("MisMascotasCliente.jsp").forward(request, response);
        }

        // =========================
        // MIS CITAS — AGENDAR
        // =========================
        if ("AgendarCita".equals(accion)) {
            Cliente a = new Cliente();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            a.setOpcion("CargarMisMascotas");
            request.setAttribute("bean", a);
            request.setAttribute("mascotas", a.getDatos());
            request.getRequestDispatcher("AgendarCitaCliente.jsp").forward(request, response);
        }

        // =========================
        // MIS CITAS — CONSULTAR
        // =========================
        if ("MisCitas".equals(accion)) {
            Cliente a = new Cliente();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            a.setOpcion("CargarMisCitas");
            request.setAttribute("bean", a);
            request.setAttribute("citas", a.getDatos());
            request.getRequestDispatcher("MisCitasCliente.jsp").forward(request, response);
        }

        // =========================
        // MIS CITAS — CANCELAR
        // =========================
        if ("CancelarMiCita".equals(accion)) {
            Cliente a = new Cliente();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            a.setOpcion("CargarMisCitas");
            request.setAttribute("bean", a);
            request.setAttribute("citas", a.getDatos());
            request.getRequestDispatcher("CancelarCitaCliente.jsp").forward(request, response);
        }

        // =========================
        // MIS CITAS — COMPROBANTE
        // =========================
        if ("MiComprobante".equals(accion)) {
            Cliente a = new Cliente();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            a.setOpcion("CargarMisCitas");
            request.setAttribute("bean", a);
            request.setAttribute("citas", a.getDatos());
            request.getRequestDispatcher("ComprobanteCliente.jsp").forward(request, response);
        }

        // =========================
        // MI HISTORIAL
        // =========================
        if ("MiHistorial".equals(accion)) {
            Cliente a = new Cliente();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            a.setOpcion("CargarMisMascotas");
            request.setAttribute("bean", a);
            request.setAttribute("mascotas", a.getDatos());
            request.getRequestDispatcher("MiHistorialCliente.jsp").forward(request, response);
        }

        // =========================
        // MI PERFIL — CAMBIAR MI CONTRASEÑA
        // =========================
        if ("CambiarMiContrasena".equals(accion)) {
            Administrador adm = new Administrador();
            adm.setUsuario(usr);
            adm.setOpcion("CambiarContrasena");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + adm.getRespuesta() + "</div></body></html>");
        }

        // =========================
        // MI PERFIL
        // =========================
        if ("MiPerfil".equals(accion)) {
            Cliente a = new Cliente();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            request.setAttribute("bean", a);
            request.getRequestDispatcher("MiPerfilCliente.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // =========================
        // REGISTRAR MASCOTA
        // =========================
        if (request.getParameter("RegistrarMascotaCliente") != null) {
            Cliente a = new Cliente();
            a.setUsuario(request.getParameter("usr"));
            a.setOpcion("CargarUsuario");
            response.setContentType("text/html;charset=UTF-8");
            if (a.getIdCliente() == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Error de sesión.</h3><div class='back mt-15'><a href='index.html' class='link'>Volver al inicio</a></div></div></body></html>");
                return;
            }
            a.setNombreMascota(request.getParameter("nombreMascota"));
            a.setIdEspecie(parseInt(request, "idEspecie"));
            a.setIdRaza(parseInt(request, "idRaza"));
            a.setIdSexo(parseInt(request, "idSexo"));
            a.setFechaNacimiento(request.getParameter("fechaNacimiento"));
            a.setPeso(request.getParameter("peso"));
            a.setColor(request.getParameter("color"));
            a.setObservaciones(request.getParameter("observaciones"));
            a.setOpcion("RegistrarMascota");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // =========================
        // CANCELAR CITA
        // =========================
        if (request.getParameter("CancelarCitaCliente") != null) {
            Cliente a = new Cliente();
            a.setIdCita(parseInt(request, "idCita"));
            a.setOpcion("CancelarCita");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // =========================
        // AGENDAR CITA
        // =========================
        if (request.getParameter("AgendarCitaCliente") != null) {
            Cliente a = new Cliente();
            a.setUsuario(request.getParameter("usr"));
            a.setOpcion("CargarUsuario");
            response.setContentType("text/html;charset=UTF-8");
            if (a.getIdCliente() == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Error de sesión.</h3><div class='back mt-15'><a href='index.html' class='link'>Volver al inicio</a></div></div></body></html>");
                return;
            }
            a.setIdMascota(parseInt(request, "idMascota"));
            a.setIdAcceso(parseInt(request, "idAcceso"));
            a.setIdHorario(parseInt(request, "idHorario"));
            a.setMotivo(request.getParameter("motivo"));
            a.setObservacionesCita(request.getParameter("observaciones"));
            a.setOpcion("AgendarCita");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }
    }

    private int parseInt(HttpServletRequest request, String param) {
        String val = request.getParameter(param);
        if (val == null || val.trim().isEmpty()) return 0;
        try { return Integer.parseInt(val.trim()); } catch (NumberFormatException e) { return 0; }
    }
}
