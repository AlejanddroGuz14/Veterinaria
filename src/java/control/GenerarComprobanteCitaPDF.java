package control;

import bean.Administrador;
import bean.Conexion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GenerarComprobanteCitaPDF", urlPatterns = {"/GenerarComprobanteCitaPDF"})
public class GenerarComprobanteCitaPDF extends HttpServlet {

    private static final BaseColor AZUL_OSCURO = new BaseColor(2, 62, 138);
    private static final BaseColor AZUL_MEDIO  = new BaseColor(0, 119, 182);
    private static final BaseColor AZUL_CLARO  = new BaseColor(0, 180, 216);
    private static final BaseColor BLANCO      = BaseColor.WHITE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idCita = 0;
        try { idCita = Integer.parseInt(request.getParameter("idCita")); } catch (Exception e) {}

        if (idCita == 0) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Cita no válida");
            return;
        }

        try {
            Connection c = Conexion.conectar();
            if (c == null) {
                response.getWriter().println("No hay conexión a la BD");
                return;
            }

            PreparedStatement ps = c.prepareStatement(
                "SELECT CI.idCita, " +
                "CL.nombre + ' ' + CL.paterno + ' ' + CL.materno AS cliente, " +
                "A.usuario AS usuarioCliente, " +
                "M.nombre AS mascota, " +
                "C2.nombre + ' ' + C2.paterno + ' ' + C2.materno AS veterinario, " +
                "CONVERT(VARCHAR(10), H.fecha, 120) AS fecha, " +
                "CONVERT(VARCHAR(8), H.hora, 108) AS hora, " +
                "ISNULL(CI.motivo, '') AS motivo, " +
                "ISNULL(CI.observaciones, '') AS observaciones, " +
                "CI.estado, " +
                "CONVERT(VARCHAR(19), CI.fechaRegistro, 120) AS fechaRegistro " +
                "FROM Citas CI " +
                "INNER JOIN Clientes  CL ON CI.idCliente = CL.idCliente " +
                "INNER JOIN Horarios  H  ON CI.idHorario = H.idHorario " +
                "INNER JOIN Acceso    A2 ON CI.idAcceso  = A2.idAcceso " +
                "INNER JOIN Clientes  C2 ON A2.idCliente = C2.idCliente " +
                "INNER JOIN Acceso    A  ON CI.idCliente = A.idCliente " +
                "LEFT  JOIN Mascotas  M  ON CI.idMascota = M.idMascota " +
                "WHERE CI.idCita = ?"
            );
            ps.setInt(1, idCita);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("Cita no encontrada");
                rs.close(); ps.close(); c.close();
                return;
            }

            String cliente       = rs.getString("cliente");
            String mascota       = rs.getString("mascota") != null ? rs.getString("mascota") : "—";
            String veterinario   = rs.getString("veterinario");
            String fecha         = rs.getString("fecha");
            String hora          = rs.getString("hora");
            String motivo        = rs.getString("motivo");
            String observaciones = rs.getString("observaciones");
            String estado        = rs.getString("estado");
            String fechaRegistro = rs.getString("fechaRegistro");

            String estadoTexto = "Pendiente";
            if ("C".equals(estado)) estadoTexto = "Cancelada";
            if ("T".equals(estado)) estadoTexto = "Terminada";

            rs.close();
            ps.close();
            c.close();

            // ── Generar PDF ──
            String filename = mascota + "_Cita" + idCita + "_" +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".pdf";

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            Font fTitulo    = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, AZUL_OSCURO);
            Font fSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, AZUL_MEDIO);
            Font fDato      = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);
            Font fPie       = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY);

            Document doc = new Document(PageSize.A4, 40, 40, 40, 30);
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();

            // Título
            Paragraph titulo = new Paragraph("COMPROBANTE DE CITA", fTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(4);
            doc.add(titulo);

            // Línea decorativa
            PdfPTable lineaDec = new PdfPTable(1);
            lineaDec.setWidthPercentage(100);
            PdfPCell lineaCell = new PdfPCell();
            lineaCell.setBackgroundColor(AZUL_CLARO);
            lineaCell.setFixedHeight(3f);
            lineaCell.setBorder(Rectangle.NO_BORDER);
            lineaDec.addCell(lineaCell);
            lineaDec.setSpacingAfter(20);
            doc.add(lineaDec);

            // Datos
            PdfPTable tDatos = new PdfPTable(2);
            tDatos.setWidthPercentage(80);
            tDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
            tDatos.setWidths(new float[]{1.5f, 2.5f});
            tDatos.setSpacingAfter(20);

            agregarFila(tDatos, "No. de Cita:", String.valueOf(idCita), fSubtitulo, fDato);
            agregarFila(tDatos, "Cliente:", cliente, fSubtitulo, fDato);
            agregarFila(tDatos, "Mascota:", mascota, fSubtitulo, fDato);
            agregarFila(tDatos, "Veterinario:", veterinario, fSubtitulo, fDato);
            agregarFila(tDatos, "Fecha de cita:", fecha, fSubtitulo, fDato);
            agregarFila(tDatos, "Hora:", hora.substring(0, 5), fSubtitulo, fDato);
            agregarFila(tDatos, "Motivo:", motivo, fSubtitulo, fDato);
            agregarFila(tDatos, "Observaciones:", observaciones, fSubtitulo, fDato);
            agregarFila(tDatos, "Estado:", estadoTexto, fSubtitulo, fDato);
            agregarFila(tDatos, "Fecha de registro:", fechaRegistro, fSubtitulo, fDato);

            doc.add(tDatos);

            // Pie
            Paragraph pie = new Paragraph(
                "Documento generado el " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
                fPie
            );
            pie.setAlignment(Element.ALIGN_RIGHT);
            pie.setSpacingBefore(30);
            doc.add(pie);

            Paragraph nota = new Paragraph(
                "Veterinaria Huellitas — Sistema de gestión",
                fPie
            );
            nota.setAlignment(Element.ALIGN_CENTER);
            nota.setSpacingBefore(10);
            doc.add(nota);

            doc.close();

        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Error al generar PDF: " + e.getMessage());
        }
    }

    private void agregarFila(PdfPTable tabla, String etiqueta, String valor,
                              Font fEtiqueta, Font fValor) {
        PdfPCell cEtiq = new PdfPCell(new Phrase(etiqueta, fEtiqueta));
        cEtiq.setBorder(Rectangle.NO_BORDER);
        cEtiq.setPaddingBottom(8);
        tabla.addCell(cEtiq);

        String v = (valor != null && !valor.trim().isEmpty()) ? valor : "—";
        PdfPCell cVal = new PdfPCell(new Phrase(v, fValor));
        cVal.setBorder(Rectangle.NO_BORDER);
        cVal.setPaddingBottom(8);
        tabla.addCell(cVal);
    }
}
