package control;

import bean.Administrador;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "controlUsuarios", urlPatterns = {"/controlUsuarios"})
public class controlUsuarios extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        // =========================
        // MENÚ ADMINISTRADOR — carga datos de bienvenida
        // =========================
        if ("MenuAdmin".equals(accion)) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usr"));
            a.setOpcion("CargarUsuarioConPuesto");
            request.setAttribute("bean", a);
            request.getRequestDispatcher("MenuAdmin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // REGISTRO DE USUARIO
        if (request.getParameter("RegistrarUsuarioA") != null) {
            Administrador a = new Administrador();
            a.setNombre(request.getParameter("nombre"));
            a.setPaterno(request.getParameter("paterno"));
            a.setMaterno(request.getParameter("materno"));
            a.setMail(request.getParameter("mail"));
            a.setTelefono(request.getParameter("telefono"));
            a.setTipo(request.getParameter("tipo"));
            a.setOpcion("AltaUsuario");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // BAJA DE USUARIO
        if (request.getParameter("BajaUsuarioA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuario"));
            a.setOpcion("BajaUsuario");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // BUSCAR USUARIO PARA MODIFICAR
        if (request.getParameter("BuscarUsuarioA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuario"));
            a.setOpcion("BuscaUsuario");
            response.sendRedirect(a.getRespuesta());
        }

        // MODIFICAR USUARIO
        if (request.getParameter("ModificarUsuarioA") != null) {
            Administrador a = new Administrador();
            a.setIdCliente(parseInt(request, "idCliente"));
            a.setNombre(request.getParameter("nombre"));
            a.setPaterno(request.getParameter("paterno"));
            a.setMaterno(request.getParameter("materno"));
            a.setMail(request.getParameter("mail"));
            a.setTelefono(request.getParameter("telefono"));
            a.setStatus(request.getParameter("status"));
            a.setUsuario(request.getParameter("usuario"));
            a.setTipo(request.getParameter("tipo"));
            a.setOpcion("ActualizarUsuario");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // CONSULTA INDIVIDUAL
        if (request.getParameter("ConsultaIndividualA") != null) {
            Administrador a = new Administrador();
            String usuario = request.getParameter("usuario");
            a.setUsuario(usuario);
            a.setOpcion("BuscaUsuario");
            String existe = a.getRespuesta();
            if (existe != null && existe.startsWith("ModificarDatos")) {
                a.setOpcion("CargarUsuario");
                request.setAttribute("bean", a);
                request.getRequestDispatcher("ConsultarUsuario.jsp").forward(request, response);
            } else {
                response.sendRedirect("Error.jsp?respuesta=Usuario no encontrado");
            }
        }

        // CAMBIAR CONTRASEÑA
        if (request.getParameter("CambiarContrasenaA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuario"));
            a.setOpcion("CambiarContrasena");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // CONSULTA GENERAL
        if (request.getParameter("ConsultaGeneralA") != null) {
            Administrador a = new Administrador();
            a.setOpcion("MuestraTablaU");
            request.setAttribute("titulos", a.getTitulos());
            request.setAttribute("datos", a.getDatos());
            request.setAttribute("respuesta", a.getRespuesta());
            request.getRequestDispatcher("ConsultarUsuarios.jsp").forward(request, response);
        }
    }

    private int parseInt(HttpServletRequest request, String param) {
        String val = request.getParameter(param);
        if (val == null || val.trim().isEmpty()) return 0;
        try { return Integer.parseInt(val.trim()); } catch (NumberFormatException e) { return 0; }
    }
}
