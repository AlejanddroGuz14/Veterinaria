package control;

import bean.Invitado;
import bean.Administrador;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "controlInvitado", urlPatterns = {"/controlInvitado"})
public class controlInvitado extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String usr    = request.getParameter("usr");

        // =========================
        // MENÚ INVITADO
        // =========================
        if ("MenuInvitado".equals(accion)) {
            Invitado a = new Invitado();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            request.setAttribute("bean", a);
            request.setAttribute("usr", usr);
            request.getRequestDispatcher("MenuInvitado.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // =========================
        // REGISTRARSE (UPDATE invitado → cliente)
        // =========================
        if (request.getParameter("RegistrarseInvitado") != null) {
            String usr = request.getParameter("usr");
            Invitado a = new Invitado();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");

            response.setContentType("text/html;charset=UTF-8");
            if (a.getIdCliente() == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Error de sesión.</h3><div class='back mt-15'><a href='index.html' class='link'>Volver al inicio</a></div></div></body></html>");
                return;
            }

            a.setNombre(request.getParameter("nombre"));
            a.setPaterno(request.getParameter("paterno"));
            a.setMaterno(request.getParameter("materno"));
            a.setMail(request.getParameter("mail"));
            a.setTelefono(request.getParameter("telefono"));
            a.setOpcion("Registrarse");

            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>");
            response.getWriter().println("<h3 style='color:#023e8a;'>¡Registro exitoso! Ahora eres cliente.</h3>");
            response.getWriter().println("<p style='text-align:center;'>Tu usuario es: <strong>" + usr + "</strong></p>");
            response.getWriter().println("<div class='back mt-15'><a href='index.html' class='link'>Iniciar sesión</a></div>");
            response.getWriter().println("</div></body></html>");
        }
    }
}
