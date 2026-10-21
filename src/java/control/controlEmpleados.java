package control;

import bean.Administrador;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "controlEmpleados", urlPatterns = {"/controlEmpleados"})
public class controlEmpleados extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("CargarPuestos".equals(accion)) {
            Administrador a = new Administrador();
            a.setOpcion("CargarPuestos");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarTurnos".equals(accion)) {
            Administrador a = new Administrador();
            a.setOpcion("CargarTurnos");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(arrayToJson(a.getDatos()));
        }

        if ("CargarEmpleadosYAdmins".equals(accion)) {
            Administrador a = new Administrador();
            a.setOpcion("CargarEmpleadosYAdmins");
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

        if ("DescargarHorarioPDF".equals(accion)) {
            String usr = request.getParameter("usr");
            Administrador a = new Administrador();
            a.setUsuario(usr);
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            if (idCliente == 0) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("Usuario no encontrado");
                return;
            }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarEmpleado");
            int idEmpleado = a.getIdEmpleado();
            if (idEmpleado == 0) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("Sin datos laborales");
                return;
            }
            a.setIdEmpleado(idEmpleado);
            a.setOpcion("ConsultaHorarioSemanal");
            request.setAttribute("bean", a);
            request.setAttribute("horarioSemanal", a.getDatos());
            request.getRequestDispatcher("GenerarHorarioPDF").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ASIGNAR DATOS LABORALES
        if (request.getParameter("AsignarDatosLaboralesA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioEmpleado"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setIdPuesto(parseInt(request, "idPuesto"));
            a.setIdTurno(parseInt(request, "idTurno"));
            a.setSalario(request.getParameter("salario"));
            a.setFechaIngreso(request.getParameter("fechaIngreso"));
            a.setOpcion("AsignarDatosLaborales");
            String respuesta = a.getRespuesta();
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + respuesta);

            if (respuesta != null && respuesta.contains("correctamente")) {
                a.setOpcion("CargarEmpleado");
                int idEmpleado = a.getIdEmpleado();
                String diasStr     = request.getParameter("diasLaborales");
                String horaEntrada = request.getParameter("horaEntradaTurno");
                String horaSalida  = request.getParameter("horaSalidaTurno");
                if (diasStr != null && !diasStr.trim().isEmpty() && idEmpleado > 0) {
                    String[] dias = diasStr.split(",");
                    StringBuilder sbDias = new StringBuilder();
                    for (String diaStr : dias) {
                        int dia = Integer.parseInt(diaStr.trim());
                        a.setIdEmpleado(idEmpleado);
                        a.setDiaSemana(dia);
                        a.setHoraEntrada(horaEntrada);
                        a.setHoraSalida(horaSalida);
                        a.setOpcion("AsignarDiaSemana");
                        sbDias.append("<br>Día ").append(dia).append(": ").append(a.getRespuesta());
                    }
                    response.getWriter().println(sbDias.toString());
                }
            }
            response.getWriter().println("</div></body></html>");
        }

        // BUSCAR EMPLEADO PARA MODIFICAR
        if (request.getParameter("BuscarEmpleadoA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioEmpleado"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarEmpleado");
            int idEmpleado = a.getIdEmpleado();
            if (idEmpleado == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Sin datos laborales.</h3></div></body></html>");
                return;
            }
            a.setIdEmpleado(idEmpleado);
            a.setOpcion("ConsultaHorarioSemanal");
            String[][] horarioSemanal = a.getDatos();
            request.setAttribute("bean", a);
            request.setAttribute("horarioSemanal", horarioSemanal);
            request.getRequestDispatcher("ModificarDatosLaborales.jsp").forward(request, response);
        }

        // MODIFICAR DATOS LABORALES
        if (request.getParameter("ModificarDatosLaboralesA") != null) {
            Administrador a = new Administrador();
            a.setIdEmpleado(parseInt(request, "idEmpleado"));
            a.setIdPuesto(parseInt(request, "idPuesto"));
            a.setIdTurno(parseInt(request, "idTurno"));
            a.setSalario(request.getParameter("salario"));
            a.setStatus(request.getParameter("status"));
            a.setOpcion("ModificarDatosLaborales");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'>" + a.getRespuesta());

            String diasStr     = request.getParameter("diasLaborales");
            String horaEntrada = request.getParameter("horaEntradaTurno");
            String horaSalida  = request.getParameter("horaSalidaTurno");
            if (diasStr != null && !diasStr.trim().isEmpty()
                    && horaEntrada != null && horaSalida != null) {
                int idEmpleado = parseInt(request, "idEmpleado");
                for (int d = 1; d <= 7; d++) {
                    a.setIdEmpleado(idEmpleado);
                    a.setDiaSemana(d);
                    a.setOpcion("EliminarDiaSemana");
                }
                StringBuilder sbDias = new StringBuilder();
                for (String diaStr : diasStr.split(",")) {
                    int dia = Integer.parseInt(diaStr.trim());
                    a.setIdEmpleado(idEmpleado);
                    a.setDiaSemana(dia);
                    a.setHoraEntrada(horaEntrada);
                    a.setHoraSalida(horaSalida);
                    a.setOpcion("AsignarDiaSemana");
                    sbDias.append("<br>Día ").append(dia).append(": ").append(a.getRespuesta());
                }
                response.getWriter().println(sbDias.toString());
            }
            response.getWriter().println("</div></body></html>");
        }

        // CONSULTAR DATOS LABORALES
        if (request.getParameter("ConsultaEmpleadoA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioEmpleado"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarEmpleado");
            int idEmpleado = a.getIdEmpleado();
            if (idEmpleado == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Sin datos laborales.</h3></div></body></html>");
                return;
            }
            a.setIdEmpleado(idEmpleado);
            a.setOpcion("ConsultaHorarioSemanal");
            String[][] horarioSemanal = a.getDatos();
            request.setAttribute("bean", a);
            request.setAttribute("horarioSemanal", horarioSemanal);
            request.getRequestDispatcher("ConsultarDatosLaborales.jsp").forward(request, response);
        }

        // ASIGNAR HORARIO
        if (request.getParameter("AsignarHorarioA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioEmpleado"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarEmpleado");
            int idEmpleado = a.getIdEmpleado();
            if (idEmpleado == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Sin datos laborales.</h3></div></body></html>");
                return;
            }
            String diasStr     = request.getParameter("diasLaborales");
            String horaEntrada = request.getParameter("horaEntradaTurno");
            String horaSalida  = request.getParameter("horaSalidaTurno");
            if (diasStr == null || diasStr.trim().isEmpty()) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Selecciona al menos un día.</h3></div></body></html>");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (String diaStr : diasStr.split(",")) {
                int dia = Integer.parseInt(diaStr.trim());
                a.setIdEmpleado(idEmpleado);
                a.setDiaSemana(dia);
                a.setHoraEntrada(horaEntrada);
                a.setHoraSalida(horaSalida);
                a.setOpcion("AsignarDiaSemana");
                sb.append("<br>").append(a.getRespuesta());
            }
            response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:#023e8a;'>Horario asignado:</h3>" + sb.toString() + "</div></body></html>");
        }

        // IMPRIMIR HORARIO
        if (request.getParameter("ImprimirHorarioA") != null) {
            Administrador a = new Administrador();
            a.setUsuario(request.getParameter("usuarioEmpleado"));
            a.setOpcion("CargarUsuario");
            int idCliente = a.getIdCliente();
            response.setContentType("text/html;charset=UTF-8");
            if (idCliente == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Usuario no encontrado.</h3></div></body></html>");
                return;
            }
            a.setIdCliente(idCliente);
            a.setOpcion("CargarEmpleado");
            int idEmpleado = a.getIdEmpleado();
            if (idEmpleado == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Sin datos laborales.</h3></div></body></html>");
                return;
            }
            a.setIdEmpleado(idEmpleado);
            a.setOpcion("ConsultaHorarioSemanal");
            String[][] horarioSemanal = a.getDatos();
            if (horarioSemanal == null || horarioSemanal.length == 0) {
                response.getWriter().println("<!DOCTYPE html><html><head><link rel='stylesheet' href='style.css'></head><body class='body-bg body-center'><div class='container' style='max-width:500px;'><h3 style='color:red; text-align:center;'>Sin horario asignado.</h3></div></body></html>");
                return;
            }
            request.setAttribute("bean", a);
            request.setAttribute("horarioSemanal", horarioSemanal);
            request.getRequestDispatcher("GenerarHorarioPDF").forward(request, response);
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
