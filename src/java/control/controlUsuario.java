/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import bean.EncriptarMD5;
import bean.usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aleja
 */
@WebServlet(name = "controlUsuario", urlPatterns = {"/controlUsuario"})
public class controlUsuario extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet controlUsuario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet controlUsuario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// BOTONES
        String validar = request.getParameter("validarUsuario");
        String registrar = request.getParameter("registrarUsuario");

        // =========================
        // LOGIN
        // =========================
        if (validar != null) {

            String usuario = request.getParameter("usuario");
            String pass = request.getParameter("pass");

            usuarios u = new usuarios();
            u.setUsuario(usuario);
            u.setPass(pass);
            u.setOpcion("verifica");

            response.sendRedirect(u.getRespuesta());
        }

        // =========================
        // CONTINUAR COMO INVITADO
        // =========================
        String invitado = request.getParameter("continuarInvitado");
        if (invitado != null) {
            usuarios u = new usuarios();
            u.setOpcion("CrearInvitado");
            response.sendRedirect(u.getRespuesta());
        }

        // =========================
        // REGISTRO
        // =========================
        if (registrar != null) {

            usuarios u = new usuarios();

            u.setNombre(request.getParameter("nombre"));
            u.setPaterno(request.getParameter("paterno"));
            u.setMaterno(request.getParameter("materno"));
            u.setMail(request.getParameter("mail"));
            u.setTelefono(request.getParameter("telefono"));

            u.setOpcion("AltaUsuario");

            String respuesta = u.getRespuesta();

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='fontawesome-free-7.2.0-web/css/all.min.css'><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + respuesta + "</div></body></html>");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
