package control;

import bean.EmpleadoVeterinario;
import bean.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "controlEmpleadoVeterinario", urlPatterns = {"/controlEmpleadoVeterinario"})
public class controlEmpleadoVeterinario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String usr    = request.getParameter("usr");

        // =========================
        // MENÚ VETERINARIO — carga datos de bienvenida
        // =========================
        if ("MenuVeterinario".equals(accion)) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setUsuario(usr);
            a.setOpcion("CargarDatosVeterinario");
            request.setAttribute("bean", a);
            request.getRequestDispatcher("MenuVeterinario.jsp").forward(request, response);
        }

        // =========================
        // CITAS — CONSULTAR MIS CITAS
        // =========================
        if ("MisCitas".equals(accion)) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setUsuario(usr);
            a.setOpcion("CargarDatosVeterinario");
            a.setOpcion("CargarMisCitas");
            request.setAttribute("bean", a);
            request.setAttribute("citas", a.getDatos());
            request.getRequestDispatcher("MisCitasVet.jsp").forward(request, response);
        }

        // =========================
        // CITAS — CANCELAR/TERMINAR
        // =========================
        if ("MisCitasBaja".equals(accion)) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setUsuario(usr);
            a.setOpcion("CargarDatosVeterinario");
            a.setOpcion("CargarMisCitas");
            request.setAttribute("bean", a);
            request.setAttribute("citas", a.getDatos());
            request.getRequestDispatcher("BajaCitaVet.jsp").forward(request, response);
        }

        // =========================
        // CITAS — DESCARGAR COMPROBANTE
        // =========================
        if ("MisCitasComprobante".equals(accion)) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setUsuario(usr);
            a.setOpcion("CargarDatosVeterinario");
            a.setOpcion("CargarMisCitas");
            request.setAttribute("bean", a);
            request.setAttribute("citas", a.getDatos());
            request.getRequestDispatcher("ComprobanteVet.jsp").forward(request, response);
        }

        // =========================
        // MI PERFIL — MI HORARIO
        // =========================
        if ("MiHorario".equals(accion)) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setUsuario(usr);
            a.setOpcion("CargarDatosVeterinario");
            a.setOpcion("CargarHorarioSemanal");
            request.setAttribute("bean", a);
            request.setAttribute("horarioSemanal", a.getDatos());
            request.getRequestDispatcher("MiHorarioVet.jsp").forward(request, response);
        }

        // =========================
        // HISTORIAL — REGISTRAR (muestra citas terminadas sin historial)
        // =========================
        if ("RegistrarHistorial".equals(accion)) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setUsuario(usr);
            a.setOpcion("CargarDatosVeterinario");
            a.setOpcion("CargarMisCitasTerminadas");
            request.setAttribute("bean", a);
            request.setAttribute("citasTerminadas", a.getDatos());
            request.getRequestDispatcher("RegistrarHistorialVet.jsp").forward(request, response);
        }

        // =========================
        // HISTORIAL — MODIFICAR (muestra citas terminadas con historial)
        // =========================
        if ("ModificarHistorial".equals(accion)) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setUsuario(usr);
            a.setOpcion("CargarDatosVeterinario");
            request.setAttribute("bean", a);
            request.getRequestDispatcher("ModificarHistorialVet.jsp").forward(request, response);
        }

        // =========================
        // HISTORIAL — CONSULTAR
        // =========================
        if ("ConsultarHistorial".equals(accion)) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setUsuario(usr);
            a.setOpcion("CargarDatosVeterinario");
            request.setAttribute("bean", a);
            request.getRequestDispatcher("ConsultarHistorialVet.jsp").forward(request, response);
        }

        // =========================
        // HISTORIAL — JSON (para fetch desde JSP)
        // =========================
        if ("ConsultarHistorialJSON".equals(accion)) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            int idMascota = 0;
            try { idMascota = Integer.parseInt(request.getParameter("idMascota")); } catch (Exception e) {}
            a.setIdMascotaH(idMascota);
            a.setOpcion("ConsultarHistorialPorMascota");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        // =========================
        // MI PERFIL — CAMBIAR MI CONTRASEÑA
        // =========================
        if ("CambiarMiContrasena".equals(accion)) {
            bean.Administrador adm = new bean.Administrador();
            adm.setUsuario(usr);
            adm.setOpcion("CambiarContrasena");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + adm.getRespuesta() + "</div></body></html>");
        }

        // =========================
        // MI PERFIL — IMPRIMIR MI HORARIO (PDF)
        // =========================
        if ("ImprimirMiHorario".equals(accion)) {
            // Reutiliza el servlet GenerarHorarioPDF del admin
            bean.Administrador adm = new bean.Administrador();
            adm.setUsuario(usr);
            adm.setOpcion("CargarUsuario");
            int idCliente = adm.getIdCliente();
            adm.setIdCliente(idCliente);
            adm.setOpcion("CargarEmpleado");
            int idEmpleado = adm.getIdEmpleado();
            adm.setIdEmpleado(idEmpleado);
            adm.setOpcion("ConsultaHorarioSemanal");
            String[][] horario = adm.getDatos();
            request.setAttribute("bean", adm);
            request.setAttribute("horarioSemanal", horario);
            request.getRequestDispatcher("GenerarHorarioPDF").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // =========================
        // CANCELAR/TERMINAR CITA
        // =========================
        if (request.getParameter("CancelarCitaVet") != null) {
            int idCita = 0;
            try { idCita = Integer.parseInt(request.getParameter("idCita")); } catch (Exception e) {}
            String estado = request.getParameter("estadoCita");

            response.setContentType("text/html;charset=UTF-8");
            try {
                java.sql.Connection c = Conexion.conectar();
                if (c != null) {
                    java.sql.CallableStatement cs = c.prepareCall("{call CancelarCitaVeterinaria(?,?,?)}");
                    cs.setInt(1, idCita);
                    cs.setString(2, estado);
                    cs.registerOutParameter(3, java.sql.Types.VARCHAR);
                    cs.execute();
                    response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + cs.getString(3) + "</div></body></html>");
                    cs.close();
                    c.close();
                }
            } catch (Exception e) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Error: " + e.getMessage() + "</h3></div></body></html>");
            }
        }

        // =========================
        // REGISTRAR HISTORIAL
        // =========================
        if (request.getParameter("RegistrarHistorialVet") != null) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setUsuario(request.getParameter("usr"));
            a.setOpcion("CargarDatosVeterinario");
            a.setIdCita(parseInt(request, "idCita"));
            a.setIdMascotaH(parseInt(request, "idMascota"));
            a.setIdClienteH(parseInt(request, "idCliente"));
            a.setPeso(request.getParameter("peso"));
            a.setTemperatura(request.getParameter("temperatura"));
            a.setDiagnostico(request.getParameter("diagnostico"));
            a.setTratamiento(request.getParameter("tratamiento"));
            a.setMedicamentos(request.getParameter("medicamentos"));
            a.setVacunas(request.getParameter("vacunas"));
            a.setObservacionesH(request.getParameter("observacionesH"));
            a.setProximaCita(request.getParameter("proximaCita"));
            a.setOpcion("RegistrarHistorial");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // =========================
        // MODIFICAR HISTORIAL
        // =========================
        if (request.getParameter("ModificarHistorialVet") != null) {
            EmpleadoVeterinario a = new EmpleadoVeterinario();
            a.setIdHistorial(parseInt(request, "idHistorial"));
            a.setPeso(request.getParameter("peso"));
            a.setTemperatura(request.getParameter("temperatura"));
            a.setDiagnostico(request.getParameter("diagnostico"));
            a.setTratamiento(request.getParameter("tratamiento"));
            a.setMedicamentos(request.getParameter("medicamentos"));
            a.setVacunas(request.getParameter("vacunas"));
            a.setObservacionesH(request.getParameter("observacionesH"));
            a.setProximaCita(request.getParameter("proximaCita"));
            a.setOpcion("ModificarHistorial");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta() + "</div></body></html>");
        }

        // =========================
        // CONSULTAR MASCOTA
        // =========================
        if (request.getParameter("ConsultaMascotaVet") != null) {
            bean.Administrador adm = new bean.Administrador();
            int idMascota = 0;
            try { idMascota = Integer.parseInt(request.getParameter("idMascota")); } catch (Exception e) {}
            adm.setIdMascota(idMascota);
            adm.setOpcion("ValidarMascota");
            String existe = adm.getRespuesta();

            if ("Mascota encontrada".equals(existe)) {
                adm.setOpcion("ConsultaMascotaIndividual");
                request.setAttribute("titulos", adm.getTitulos());
                request.setAttribute("datos", adm.getDatos());
                request.getRequestDispatcher("ConsultarMascota.jsp").forward(request, response);
            } else {
                response.sendRedirect("Error.jsp?respuesta=Mascota no encontrada");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Controlador del menú veterinario";
    }

    private int parseInt(HttpServletRequest request, String param) {
        String val = request.getParameter(param);
        if (val == null || val.trim().isEmpty()) return 0;
        try { return Integer.parseInt(val.trim()); } catch (NumberFormatException e) { return 0; }
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
}
