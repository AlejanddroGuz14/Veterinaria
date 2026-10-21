package control;

import bean.Conexion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

@WebServlet(name = "controlReportes", urlPatterns = {"/controlReportes"})
public class controlReportes extends HttpServlet {

    // Colores corporativos
    private static final BaseColor AZUL_OSCURO = new BaseColor(2, 62, 138);
    private static final BaseColor AZUL_MEDIO  = new BaseColor(0, 119, 182);
    private static final BaseColor AZUL_CLARO  = new BaseColor(0, 180, 216);

    // Paleta de colores para barras
    private static final java.awt.Color[] COLORES_BARRAS = {
        new java.awt.Color(0, 119, 182),
        new java.awt.Color(230, 57, 70),
        new java.awt.Color(42, 157, 143),
        new java.awt.Color(233, 196, 106),
        new java.awt.Color(244, 162, 97),
        new java.awt.Color(38, 70, 83),
        new java.awt.Color(231, 111, 81),
        new java.awt.Color(2, 62, 138),
        new java.awt.Color(0, 180, 216),
        new java.awt.Color(144, 224, 239),
        new java.awt.Color(72, 202, 228),
        new java.awt.Color(168, 218, 220)
    };

    private void aplicarColoresBarras(JFreeChart grafico) {
        CategoryPlot plot = grafico.getCategoryPlot();
        plot.setRenderer(new BarRenderer3D() {
            public java.awt.Paint getItemPaint(int row, int column) {
                return COLORES_BARRAS[column % COLORES_BARRAS.length];
            }
        });
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Evitar caché del navegador
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        String reporte = request.getParameter("reporte");

        // ═══════════════════════════════════════════
        // USUARIOS
        // ═══════════════════════════════════════════
        if ("UsuariosExcel".equals(reporte)) {
            generarExcelUsuarios(response);
        }
        if ("UsuariosPie".equals(reporte)) {
            generarPieUsuarios(response);
        }

        // ═══════════════════════════════════════════
        // MASCOTAS
        // ═══════════════════════════════════════════
        if ("MascotasExcel".equals(reporte)) {
            generarExcelMascotas(response);
        }
        if ("MascotasBarras".equals(reporte)) {
            generarBarrasMascotas(response);
        }
        if ("MascotasBajaPie".equals(reporte)) {
            generarPieBajasMascotas(response);
        }

        // ═══════════════════════════════════════════
        // EMPLEADOS
        // ═══════════════════════════════════════════
        if ("EmpleadosExcel".equals(reporte)) {
            generarExcelEmpleados(response);
        }

        // ═══════════════════════════════════════════
        // CITAS
        // ═══════════════════════════════════════════
        if ("CitasEstadoPie".equals(reporte)) {
            generarPieCitasEstado(response);
        }
        if ("CitasVetBarras".equals(reporte)) {
            generarBarrasCitasVet(response);
        }
        if ("CitasMesBarras".equals(reporte)) {
            generarBarrasCitasMes(response);
        }
        if ("CitasExcel".equals(reporte)) {
            generarExcelCitas(response);
        }

        // ═══════════════════════════════════════════
        // VETERINARIO — MIS CITAS ATENDIDAS
        // ═══════════════════════════════════════════
        if ("MisCitasExcel".equals(reporte)) {
            generarExcelMisCitas(response, request.getParameter("usr"));
        }
    }

    // ═══════════════════════════════════════════════════════
    // EXCEL — USUARIOS ACTIVOS
    // ═══════════════════════════════════════════════════════
    private void generarExcelUsuarios(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"Usuarios_Activos.xls\"");
        PrintWriter pw = response.getWriter();
        pw.write("ID\tUsuario\tNombre Completo\tEmail\tTeléfono\tTipo\tStatus\n");
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT C.idCliente, A.usuario, C.nombre+' '+C.paterno+' '+C.materno AS nombre, " +
                    "C.mail, C.telefono, A.tipo, C.status FROM Clientes C " +
                    "INNER JOIN Acceso A ON C.idCliente=A.idCliente WHERE C.status='A'"
                );
                while (rs.next()) {
                    pw.write(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+
                             rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\n");
                }
                rs.close(); st.close(); c.close();
            }
        } catch (Exception e) { pw.write("Error: "+e.getMessage()); }
    }

    // ═══════════════════════════════════════════════════════
    // PIE — USUARIOS POR TIPO
    // ═══════════════════════════════════════════════════════
    private void generarPieUsuarios(HttpServletResponse response) throws IOException {
        try {
            DefaultPieDataset datos = new DefaultPieDataset();
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT A.tipo, COUNT(*) AS total FROM Acceso A " +
                    "INNER JOIN Clientes C ON A.idCliente=C.idCliente WHERE C.status='A' GROUP BY A.tipo"
                );
                while (rs.next()) {
                    String tipo = rs.getString("tipo");
                    String nombre = "A".equals(tipo)?"Administrador":"E".equals(tipo)?"Empleado":"C".equals(tipo)?"Cliente":"Invitado";
                    datos.setValue(nombre, rs.getInt("total"));
                }
                rs.close(); st.close(); c.close();
            }
            JFreeChart grafico = ChartFactory.createPieChart3D("Usuarios por Tipo", datos, true, true, false);
            grafico.setBackgroundPaint(java.awt.Color.WHITE);

            // Generar imagen
            ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(imgStream, grafico, 600, 400);
            byte[] imgBytes = imgStream.toByteArray();

            // Crear PDF con la gráfica
            generarPdfConGrafica(response, "Reporte_Usuarios_PorTipo.pdf", "Usuarios por Tipo", imgBytes,
                "SELECT A.tipo, COUNT(*) AS total FROM Acceso A INNER JOIN Clientes C ON A.idCliente=C.idCliente WHERE C.status='A' GROUP BY A.tipo",
                new String[]{"Tipo","Total"}, 2);

        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Error: "+e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════
    // EXCEL — MASCOTAS ACTIVAS
    // ═══════════════════════════════════════════════════════
    private void generarExcelMascotas(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"Mascotas_Activas.xls\"");
        PrintWriter pw = response.getWriter();
        pw.write("ID\tCliente\tMascota\tEspecie\tRaza\tSexo\tColor\tStatus\n");
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT idMascota, cliente, nombreMascota, especie, raza, sexo, color, status FROM VistaMascotas WHERE status='A'"
                );
                while (rs.next()) {
                    pw.write(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+
                             rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+
                             rs.getString(7)+"\t"+rs.getString(8)+"\n");
                }
                rs.close(); st.close(); c.close();
            }
        } catch (Exception e) { pw.write("Error: "+e.getMessage()); }
    }

    // ═══════════════════════════════════════════════════════
    // BARRAS — MASCOTAS POR ESPECIE
    // ═══════════════════════════════════════════════════════
    private void generarBarrasMascotas(HttpServletResponse response) throws IOException {
        try {
            DefaultCategoryDataset datos = new DefaultCategoryDataset();
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT E.nombre AS especie, COUNT(*) AS total FROM Mascotas M " +
                    "INNER JOIN Especie E ON M.idEspecie=E.idEspecie WHERE M.status='A' GROUP BY E.nombre ORDER BY total DESC"
                );
                while (rs.next()) {
                    datos.addValue(rs.getInt("total"), "Mascotas", rs.getString("especie"));
                }
                rs.close(); st.close(); c.close();
            }
            JFreeChart grafico = ChartFactory.createBarChart3D("Mascotas por Especie", "Especie", "Cantidad",
                datos, PlotOrientation.HORIZONTAL, true, true, false);
            grafico.setBackgroundPaint(java.awt.Color.WHITE);
            aplicarColoresBarras(grafico);

            ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(imgStream, grafico, 700, 400);
            byte[] imgBytes = imgStream.toByteArray();

            generarPdfConGrafica(response, "Reporte_Mascotas_PorEspecie.pdf", "Mascotas por Especie", imgBytes,
                "SELECT E.nombre, COUNT(*) FROM Mascotas M INNER JOIN Especie E ON M.idEspecie=E.idEspecie WHERE M.status='A' GROUP BY E.nombre",
                new String[]{"Especie","Total"}, 2);

        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Error: "+e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════
    // PIE — MASCOTAS DADAS DE BAJA POR MOTIVO
    // ═══════════════════════════════════════════════════════
    private void generarPieBajasMascotas(HttpServletResponse response) throws IOException {
        try {
            DefaultPieDataset datos = new DefaultPieDataset();
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT MB.descripcion, COUNT(*) AS total FROM BajaMascota BM " +
                    "INNER JOIN MotivoBaja MB ON BM.idMotivo=MB.idMotivo GROUP BY MB.descripcion"
                );
                while (rs.next()) {
                    datos.setValue(rs.getString(1), rs.getInt(2));
                }
                rs.close(); st.close(); c.close();
            }
            JFreeChart grafico = ChartFactory.createPieChart3D("Bajas de Mascotas por Motivo", datos, true, true, false);
            grafico.setBackgroundPaint(java.awt.Color.WHITE);

            ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(imgStream, grafico, 600, 400);
            byte[] imgBytes = imgStream.toByteArray();

            generarPdfConGrafica(response, "Reporte_Bajas_Mascotas.pdf", "Bajas de Mascotas por Motivo", imgBytes,
                "SELECT MB.descripcion, COUNT(*) FROM BajaMascota BM INNER JOIN MotivoBaja MB ON BM.idMotivo=MB.idMotivo GROUP BY MB.descripcion",
                new String[]{"Motivo","Total"}, 2);

        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Error: "+e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════
    // EXCEL — EMPLEADOS ACTIVOS
    // ═══════════════════════════════════════════════════════
    private void generarExcelEmpleados(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"Empleados_Activos.xls\"");
        PrintWriter pw = response.getWriter();
        pw.write("ID\tNombre\tUsuario\tPuesto\tTurno\tSalario\tFecha Ingreso\tStatus\n");
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM VistaEmpleados WHERE status='A'");
                while (rs.next()) {
                    pw.write(rs.getInt("idEmpleado")+"\t"+rs.getString("nombreCompleto")+"\t"+
                             rs.getString("usuario")+"\t"+rs.getString("puesto")+"\t"+
                             rs.getString("turno")+"\t"+rs.getString("salario")+"\t"+
                             rs.getString("fechaIngreso")+"\t"+rs.getString("status")+"\n");
                }
                rs.close(); st.close(); c.close();
            }
        } catch (Exception e) { pw.write("Error: "+e.getMessage()); }
    }

    // ═══════════════════════════════════════════════════════
    // PIE — CITAS POR ESTADO
    // ═══════════════════════════════════════════════════════
    private void generarPieCitasEstado(HttpServletResponse response) throws IOException {
        try {
            DefaultPieDataset datos = new DefaultPieDataset();
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT estado, COUNT(*) AS total FROM Citas GROUP BY estado");
                while (rs.next()) {
                    String est = rs.getString("estado");
                    String nombre = "P".equals(est)?"Pendiente":"C".equals(est)?"Cancelada":"Terminada";
                    datos.setValue(nombre, rs.getInt("total"));
                }
                rs.close(); st.close(); c.close();
            }
            JFreeChart grafico = ChartFactory.createPieChart3D("Citas por Estado", datos, true, true, false);
            grafico.setBackgroundPaint(java.awt.Color.WHITE);

            ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(imgStream, grafico, 600, 400);
            byte[] imgBytes = imgStream.toByteArray();

            generarPdfConGrafica(response, "Reporte_Citas_PorEstado.pdf", "Citas por Estado", imgBytes,
                "SELECT estado, COUNT(*) FROM Citas GROUP BY estado",
                new String[]{"Estado","Total"}, 2);

        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Error: "+e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════
    // BARRAS — CITAS POR VETERINARIO
    // ═══════════════════════════════════════════════════════
    private void generarBarrasCitasVet(HttpServletResponse response) throws IOException {
        try {
            DefaultCategoryDataset datos = new DefaultCategoryDataset();
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT C2.nombre+' '+C2.paterno AS vet, COUNT(*) AS total FROM Citas CI " +
                    "INNER JOIN Acceso A ON CI.idAcceso=A.idAcceso " +
                    "INNER JOIN Clientes C2 ON A.idCliente=C2.idCliente GROUP BY C2.nombre, C2.paterno ORDER BY total DESC"
                );
                while (rs.next()) {
                    datos.addValue(rs.getInt("total"), "Citas", rs.getString("vet"));
                }
                rs.close(); st.close(); c.close();
            }
            JFreeChart grafico = ChartFactory.createBarChart3D("Citas por Veterinario", "Veterinario", "Cantidad",
                datos, PlotOrientation.HORIZONTAL, true, true, false);
            grafico.setBackgroundPaint(java.awt.Color.WHITE);
            aplicarColoresBarras(grafico);

            ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(imgStream, grafico, 700, 400);
            byte[] imgBytes = imgStream.toByteArray();

            generarPdfConGrafica(response, "Reporte_Citas_PorVeterinario.pdf", "Citas por Veterinario", imgBytes,
                "SELECT C2.nombre+' '+C2.paterno, COUNT(*) FROM Citas CI INNER JOIN Acceso A ON CI.idAcceso=A.idAcceso INNER JOIN Clientes C2 ON A.idCliente=C2.idCliente GROUP BY C2.nombre, C2.paterno",
                new String[]{"Veterinario","Total"}, 2);

        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Error: "+e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════
    // BARRAS — CITAS POR MES
    // ═══════════════════════════════════════════════════════
    private void generarBarrasCitasMes(HttpServletResponse response) throws IOException {
        try {
            DefaultCategoryDataset datos = new DefaultCategoryDataset();
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT FORMAT(H.fecha,'yyyy-MM') AS mes, COUNT(*) AS total FROM Citas CI " +
                    "INNER JOIN Horarios H ON CI.idHorario=H.idHorario GROUP BY FORMAT(H.fecha,'yyyy-MM') ORDER BY mes"
                );
                while (rs.next()) {
                    datos.addValue(rs.getInt("total"), "Citas", rs.getString("mes"));
                }
                rs.close(); st.close(); c.close();
            }
            JFreeChart grafico = ChartFactory.createBarChart3D("Citas por Mes", "Mes", "Cantidad",
                datos, PlotOrientation.VERTICAL, true, true, false);
            grafico.setBackgroundPaint(java.awt.Color.WHITE);
            aplicarColoresBarras(grafico);

            ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(imgStream, grafico, 700, 400);
            byte[] imgBytes = imgStream.toByteArray();

            generarPdfConGrafica(response, "Reporte_Citas_PorMes.pdf", "Citas por Mes", imgBytes,
                "SELECT FORMAT(H.fecha,'yyyy-MM'), COUNT(*) FROM Citas CI INNER JOIN Horarios H ON CI.idHorario=H.idHorario GROUP BY FORMAT(H.fecha,'yyyy-MM')",
                new String[]{"Mes","Total"}, 2);

        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Error: "+e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════
    // EXCEL — CITAS
    // ═══════════════════════════════════════════════════════
    private void generarExcelCitas(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"Citas.xls\"");
        PrintWriter pw = response.getWriter();
        pw.write("ID\tCliente\tMascota\tVeterinario\tFecha\tMotivo\tEstado\n");
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM VistaCitas ORDER BY fecha DESC");
                while (rs.next()) {
                    pw.write(rs.getInt("idCita")+"\t"+rs.getString("cliente")+"\t"+
                             rs.getString("mascota")+"\t"+rs.getString("veterinario")+"\t"+
                             rs.getString("fecha")+"\t"+
                             rs.getString("motivo")+"\t"+rs.getString("estado")+"\n");
                }
                rs.close(); st.close(); c.close();
            }
        } catch (Exception e) { pw.write("Error: "+e.getMessage()); }
    }

    // ═══════════════════════════════════════════════════════
    // EXCEL — MIS CITAS ATENDIDAS (veterinario)
    // ═══════════════════════════════════════════════════════
    private void generarExcelMisCitas(HttpServletResponse response, String usr) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"Mis_Citas_Atendidas.xls\"");
        PrintWriter pw = response.getWriter();
        pw.write("ID Cita\tCliente\tMascota\tFecha\tHora\tMotivo\tEstado\n");
        try {
            Connection c = Conexion.conectar();
            if (c != null) {
                PreparedStatement ps = c.prepareStatement(
                    "SELECT CI.idCita, CL.nombre+' '+CL.paterno+' '+CL.materno AS cliente, " +
                    "ISNULL(M.nombre,'') AS mascota, CONVERT(VARCHAR(10),H.fecha,120) AS fecha, " +
                    "CONVERT(VARCHAR(8),H.hora,108) AS hora, ISNULL(CI.motivo,'') AS motivo, CI.estado " +
                    "FROM Citas CI " +
                    "INNER JOIN Clientes CL ON CI.idCliente=CL.idCliente " +
                    "INNER JOIN Horarios H ON CI.idHorario=H.idHorario " +
                    "INNER JOIN Acceso A ON CI.idAcceso=A.idAcceso " +
                    "LEFT JOIN Mascotas M ON CI.idMascota=M.idMascota " +
                    "WHERE A.usuario=? AND CI.estado='T' ORDER BY H.fecha DESC, H.hora"
                );
                ps.setString(1, usr);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    pw.write(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+
                             rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\n");
                }
                rs.close(); ps.close(); c.close();
            }
        } catch (Exception e) { pw.write("Error: "+e.getMessage()); }
    }

    // ═══════════════════════════════════════════════════════
    // MÉTODO GENÉRICO — PDF CON GRÁFICA + TABLA
    // ═══════════════════════════════════════════════════════
    private void generarPdfConGrafica(HttpServletResponse response, String filename, String titulo,
            byte[] imgBytes, String query, String[] columnas, int numCols) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try {
            Document doc = new Document(PageSize.A4, 30, 30, 40, 30);
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();

            // Título
            Font fTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, AZUL_OSCURO);
            Paragraph pTitulo = new Paragraph(titulo, fTitulo);
            pTitulo.setAlignment(Element.ALIGN_CENTER);
            pTitulo.setSpacingAfter(5);
            doc.add(pTitulo);

            // Subtítulo con fecha
            Font fSub = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
            Paragraph pSub = new Paragraph("Generado el " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()), fSub);
            pSub.setAlignment(Element.ALIGN_CENTER);
            pSub.setSpacingAfter(15);
            doc.add(pSub);

            // Línea decorativa
            PdfPTable linea = new PdfPTable(1);
            linea.setWidthPercentage(100);
            PdfPCell lineaCell = new PdfPCell();
            lineaCell.setBackgroundColor(AZUL_CLARO);
            lineaCell.setFixedHeight(3f);
            lineaCell.setBorder(Rectangle.NO_BORDER);
            linea.addCell(lineaCell);
            linea.setSpacingAfter(15);
            doc.add(linea);

            // Imagen de la gráfica
            Image img = Image.getInstance(imgBytes);
            img.setAlignment(Image.ALIGN_CENTER);
            img.scaleToFit(500, 350);
            doc.add(img);

            // Espacio
            doc.add(new Paragraph("\n"));

            // Tabla de datos
            Font fHead = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
            Font fCell = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.DARK_GRAY);

            PdfPTable table = new PdfPTable(numCols);
            table.setWidthPercentage(60);
            table.setSpacingBefore(10);

            for (String col : columnas) {
                PdfPCell cell = new PdfPCell(new Phrase(col, fHead));
                cell.setBackgroundColor(AZUL_MEDIO);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }

            Connection c = Conexion.conectar();
            if (c != null) {
                try {
                    Statement st = c.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    boolean alt = false;
                    while (rs.next()) {
                        for (int i = 1; i <= numCols; i++) {
                            PdfPCell cell = new PdfPCell(new Phrase(rs.getString(i), fCell));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setPadding(6);
                            if (alt) cell.setBackgroundColor(new BaseColor(240, 248, 255));
                            table.addCell(cell);
                        }
                        alt = !alt;
                    }
                    rs.close(); st.close(); c.close();
                } catch (Exception ex) { try { c.close(); } catch (Exception e2) {} }
            }

            doc.add(table);

            // Pie
            Font fPie = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY);
            Paragraph pie = new Paragraph("Veterinaria Huellitas — Sistema de gestión", fPie);
            pie.setAlignment(Element.ALIGN_CENTER);
            pie.setSpacingBefore(20);
            doc.add(pie);

            doc.close();

        } catch (DocumentException e) {
            throw new IOException("Error PDF: " + e.getMessage());
        }
    }
}
