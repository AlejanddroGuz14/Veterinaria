package control;

import bean.Administrador;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "controlMascotas", urlPatterns = {"/controlMascotas"})
public class controlMascotas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("CargarEspecies".equals(accion)) {
            Administrador a = new Administrador();
            a.setOpcion("CargarEspecies");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarRazas".equals(accion)) {
            Administrador a = new Administrador();
            a.setIdEspecie(parseInt(request, "idEspecie"));
            a.setOpcion("CargarRazasPorEspecie");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarSexos".equals(accion)) {
            Administrador a = new Administrador();
            a.setOpcion("CargarSexos");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarMotivos".equals(accion)) {
            Administrador a = new Administrador();
            a.setOpcion("CargarMotivosBaja");
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

        if ("CargarTodasMascotasCliente".equals(accion)) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usr"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("application/json;charset=UTF-8");
            if (idCliente == 0) { response.getWriter().println("[]"); return; }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarTodasMascotasPorCliente");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarUsuarios".equals(accion)) {
            Administrador a = new Administrador();
            a.setOpcion("CargarUsuarios");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // REGISTRAR MASCOTA
        if (request.getParameter("RegistrarMascotaA") != null) {
            Administrador a = new Administrador();
            String usuarioCliente = request.getParameter("usuarioCliente");
            a.setUsuario(usuarioCliente);
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setNombreMascota(request.getParameter("nombreMascota"));
            a.setIdEspecie(parseInt(request, "idEspecie"));
            a.setIdRaza(parseInt(request, "idRaza"));
            a.setIdSexo(parseInt(request, "idSexo"));
            a.setFechaNacimiento(request.getParameter("fechaNacimiento"));
            a.setPeso(request.getParameter("peso"));
            a.setColor(request.getParameter("color"));
            a.setObservaciones(request.getParameter("observaciones"));
            a.setOpcion("AltaMascota");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // BAJA MASCOTA
        if (request.getParameter("BajaMascotaA") != null) {
            Administrador a = new Administrador();
            a.setIdMascota(parseInt(request, "idMascota"));
            a.setIdMotivo(parseInt(request, "idMotivo"));
            a.setOpcion("BajaMascota");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // BUSCAR MASCOTA PARA MODIFICAR
        if (request.getParameter("BuscarMascotaA") != null) {
            Administrador a = new Administrador();
            a.setIdMascota(parseInt(request, "idMascota"));
            a.setOpcion("ValidarMascota");
            String existe = a.getRespuesta();
            if ("Mascota encontrada".equals(existe)) {
                a.setOpcion("CargarMascota");
                request.setAttribute("bean", a);
                request.getRequestDispatcher("ModificarMascota.jsp").forward(request, response);
            } else {
                response.sendRedirect("Error.jsp?respuesta=Mascota no encontrada");
            }
        }

        // MODIFICAR MASCOTA
        if (request.getParameter("ModificarMascotaA") != null) {
            Administrador a = new Administrador();
            a.setIdMascota(parseInt(request, "idMascota"));
            a.setNombreMascota(request.getParameter("nombreMascota"));
            a.setIdEspecie(parseInt(request, "idEspecie"));
            a.setIdRaza(parseInt(request, "idRaza"));
            a.setIdSexo(parseInt(request, "idSexo"));
            a.setFechaNacimiento(request.getParameter("fechaNacimiento"));
            a.setPeso(request.getParameter("peso"));
            a.setColor(request.getParameter("color"));
            a.setObservaciones(request.getParameter("observaciones"));
            a.setStatus(request.getParameter("status"));
            a.setOpcion("ModificarMascota");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // CONSULTA INDIVIDUAL
        if (request.getParameter("ConsultaIndividualM") != null) {
            Administrador a = new Administrador();
            a.setIdMascota(parseInt(request, "idMascota"));
            a.setOpcion("ValidarMascota");
            String existe = a.getRespuesta();
            if ("Mascota encontrada".equals(existe)) {
                a.setOpcion("ConsultaMascotaIndividual");
                request.setAttribute("titulos", a.getTitulos());
                request.setAttribute("datos", a.getDatos());
                request.getRequestDispatcher("ConsultarMascota.jsp").forward(request, response);
            } else {
                response.sendRedirect("Error.jsp?respuesta=Mascota no encontrada");
            }
        }

        // CONSULTA GENERAL
        if (request.getParameter("ConsultaGeneralM") != null) {
            Administrador a = new Administrador();
            a.setOpcion("ConsultaMascotasGeneral");
            request.setAttribute("titulos", a.getTitulos());
            request.setAttribute("datos", a.getDatos());
            request.setAttribute("respuesta", a.getRespuesta());
            request.getRequestDispatcher("ConsultarMascotas.jsp").forward(request, response);
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
