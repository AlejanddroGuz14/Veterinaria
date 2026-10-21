package control;

import bean.Administrador;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para generar PDF del horario semanal de un empleado.
 * Nombre del archivo: Horario + usuario del empleado.
 */
@WebServlet(name = "GenerarHorarioPDF", urlPatterns = {"/GenerarHorarioPDF"})
public class GenerarHorarioPDF extends HttpServlet {

    // ── Colores corporativos ──
    private static final BaseColor AZUL_OSCURO  = new BaseColor(2,   62, 138);  // #023e8a
    private static final BaseColor AZUL_MEDIO   = new BaseColor(0,  119, 182);  // #0077b6
    private static final BaseColor AZUL_CLARO   = new BaseColor(0,  180, 216);  // #00b4d8
    private static final BaseColor GRIS_FILA    = new BaseColor(240, 248, 255);  // #f0f8ff
    private static final BaseColor BLANCO       = BaseColor.WHITE;

    // ── Nombres de los días ──
    private static final String[] DIAS = {
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    };

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Administrador bean = (Administrador) request.getAttribute("bean");

        if (bean == null) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h3 style='color:red;'>Error: no se encontraron datos del empleado.</h3>");
            return;
        }

        // ── Nombre del archivo: Horario + usuario ──
        String usuario  = bean.getUsuario() != null ? bean.getUsuario() : "empleado";
        String filename = "Horario" + usuario + ".pdf";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try {
            // ── Fuentes ──
            Font fTitulo      = FontFactory.getFont(FontFactory.HELVETICA_BOLD,  20, AZUL_OSCURO);
            Font fSubtitulo   = FontFactory.getFont(FontFactory.HELVETICA_BOLD,  11, AZUL_MEDIO);
            Font fDato        = FontFactory.getFont(FontFactory.HELVETICA,        10, BaseColor.DARK_GRAY);
            Font fEncabezado  = FontFactory.getFont(FontFactory.HELVETICA_BOLD,  10, BLANCO);
            Font fDia         = FontFactory.getFont(FontFactory.HELVETICA_BOLD,  10, BLANCO);
            Font fHoraTrabaja = FontFactory.getFont(FontFactory.HELVETICA_BOLD,   9, BLANCO);
            Font fHoraLibre   = FontFactory.getFont(FontFactory.HELVETICA,        8, BaseColor.GRAY);
            Font fPie         = FontFactory.getFont(FontFactory.HELVETICA,         8, BaseColor.GRAY);

            // ── Documento A4 horizontal para que quepan 7 columnas ──
            Document doc = new Document(PageSize.A4.rotate(), 30, 30, 40, 30);
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();

            // ════════════════════════════════════════════
            // ENCABEZADO — TÍTULO
            // ════════════════════════════════════════════
            Paragraph titulo = new Paragraph("HORARIO LABORAL SEMANAL", fTitulo);
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
            lineaDec.setSpacingAfter(12);
            doc.add(lineaDec);

            // ════════════════════════════════════════════
            // DATOS DEL EMPLEADO
            // ════════════════════════════════════════════
            PdfPTable tDatos = new PdfPTable(2);
            tDatos.setWidthPercentage(60);
            tDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
            tDatos.setWidths(new float[]{1.4f, 2.6f});
            tDatos.setSpacingAfter(16);

            agregarFilaDato(tDatos, "Nombre completo:",
                bean.getNombre() + " " + bean.getPaterno() + " " + bean.getMaterno(),
                fSubtitulo, fDato);

            agregarFilaDato(tDatos, "Usuario:", bean.getUsuario(), fSubtitulo, fDato);

            agregarFilaDato(tDatos, "Puesto:", bean.getNombrePuesto(), fSubtitulo, fDato);

            agregarFilaDato(tDatos, "Turno:", bean.getNombreTurno(), fSubtitulo, fDato);

            agregarFilaDato(tDatos, "Salario:", "$" + bean.getSalario(), fSubtitulo, fDato);

            agregarFilaDato(tDatos, "Fecha de ingreso:", bean.getFechaIngreso(), fSubtitulo, fDato);

            doc.add(tDatos);

            // ════════════════════════════════════════════
            // TABLA SEMANAL — 7 COLUMNAS (una por día)
            // ════════════════════════════════════════════
            String[][] horarioSemanal = (String[][]) request.getAttribute("horarioSemanal");

            // Construir mapa diaSemana → {horaEntrada, horaSalida}
            // horarioSemanal[i] = { diaSemana, nombreDia, horaEntrada, horaSalida, ... }
            String[] entradaPorDia = new String[8]; // índice 1-7
            String[] salidaPorDia  = new String[8];

            if (horarioSemanal != null) {
                for (String[] fila : horarioSemanal) {
                    int dia = Integer.parseInt(fila[0]);
                    entradaPorDia[dia] = fila[2]; // horaEntrada
                    salidaPorDia[dia]  = fila[3]; // horaSalida
                }
            }

            // Rango de horas a mostrar (6:00 a 22:00)
            int H_INICIO = 6;
            int H_FIN    = 22;

            // Tabla: 1 fila de encabezado + (H_FIN - H_INICIO) filas de horas
            PdfPTable tSemana = new PdfPTable(7);
            tSemana.setWidthPercentage(100);
            tSemana.setSpacingBefore(5);

            // ── Encabezados de días ──
            for (int d = 0; d < 7; d++) {
                boolean tieneDia = entradaPorDia[d + 1] != null;
                PdfPCell cDia = new PdfPCell(new Phrase(DIAS[d], fDia));
                cDia.setBackgroundColor(tieneDia ? AZUL_OSCURO : AZUL_MEDIO);
                cDia.setHorizontalAlignment(Element.ALIGN_CENTER);
                cDia.setPadding(7);
                cDia.setBorderColor(BLANCO);
                tSemana.addCell(cDia);
            }

            // ── Filas de horas ──
            for (int h = H_INICIO; h < H_FIN; h++) {
                for (int d = 1; d <= 7; d++) {
                    boolean trabajando = false;

                    if (entradaPorDia[d] != null) {
                        int hEntrada = Integer.parseInt(entradaPorDia[d].substring(0, 2));
                        int hSalida  = Integer.parseInt(salidaPorDia[d].substring(0, 2));
                        trabajando = (h >= hEntrada && h < hSalida);
                    }

                    // Formato 12h
                    String ampm = h >= 12 ? "PM" : "AM";
                    int h12     = h > 12 ? h - 12 : (h == 0 ? 12 : h);
                    String etiq = h12 + ":00 " + ampm;

                    PdfPCell cHora;
                    if (trabajando) {
                        cHora = new PdfPCell(new Phrase(etiq, fHoraTrabaja));
                        cHora.setBackgroundColor(AZUL_CLARO);
                    } else {
                        cHora = new PdfPCell(new Phrase(etiq, fHoraLibre));
                        // Alternar gris claro / blanco
                        cHora.setBackgroundColor((h % 2 == 0) ? GRIS_FILA : BLANCO);
                    }

                    cHora.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cHora.setPadding(4);
                    cHora.setBorderColor(new BaseColor(200, 230, 240));
                    tSemana.addCell(cHora);
                }
            }

            doc.add(tSemana);

            // ── Leyenda ──
            PdfPTable tLeyenda = new PdfPTable(2);
            tLeyenda.setWidthPercentage(40);
            tLeyenda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tLeyenda.setSpacingBefore(8);

            PdfPCell cLeyAzul = new PdfPCell(new Phrase(" ", fHoraTrabaja));
            cLeyAzul.setBackgroundColor(AZUL_CLARO);
            cLeyAzul.setFixedHeight(12f);
            cLeyAzul.setBorder(Rectangle.NO_BORDER);
            tLeyenda.addCell(cLeyAzul);

            PdfPCell cLeyTexto = new PdfPCell(new Phrase("  Horas de trabajo", fPie));
            cLeyTexto.setBorder(Rectangle.NO_BORDER);
            cLeyTexto.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tLeyenda.addCell(cLeyTexto);

            doc.add(tLeyenda);

            // ════════════════════════════════════════════
            // PIE DE PÁGINA
            // ════════════════════════════════════════════
            String fechaGen = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            Paragraph pie = new Paragraph("Documento generado el " + fechaGen, fPie);
            pie.setAlignment(Element.ALIGN_RIGHT);
            pie.setSpacingBefore(10);
            doc.add(pie);

            doc.close();

        } catch (DocumentException e) {
            throw new IOException("Error al generar PDF: " + e.getMessage());
        }
    }

    // ── Agrega una fila de dos celdas (etiqueta | valor) a la tabla de datos ──
    private void agregarFilaDato(PdfPTable tabla, String etiqueta, String valor,
                                  Font fEtiqueta, Font fValor) {
        PdfPCell cEtiq = new PdfPCell(new Phrase(etiqueta, fEtiqueta));
        cEtiq.setBorder(Rectangle.NO_BORDER);
        cEtiq.setPaddingBottom(5);
        tabla.addCell(cEtiq);

        String v = (valor != null && !valor.trim().isEmpty()) ? valor : "—";
        PdfPCell cVal = new PdfPCell(new Phrase(v, fValor));
        cVal.setBorder(Rectangle.NO_BORDER);
        cVal.setPaddingBottom(5);
        tabla.addCell(cVal);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Genera PDF del horario semanal de un empleado";
    }
}
