<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Consulta de Empleado</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .semana-grid {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            gap: 6px;
            margin-top: 10px;
        }
        .dia-col {
            border: 2px solid #90e0ef;
            border-radius: 10px;
            overflow: hidden;
        }
        .dia-header {
            background: #0077b6;
            color: white;
            text-align: center;
            padding: 8px 4px;
            font-weight: bold;
            font-size: 13px;
        }
        .dia-header.activo { background: #023e8a; }
        .dia-horas {
            display: flex;
            flex-direction: column;
        }
        .hora-bloque {
            padding: 4px 6px;
            font-size: 11px;
            text-align: center;
            border-top: 1px solid #e0f7fa;
            background: #f0f9ff;
            color: #023e8a;
        }
        .hora-bloque.trabajando {
            background: #00b4d8;
            color: white;
            font-weight: bold;
        }
        .leyenda {
            display: flex;
            gap: 15px;
            margin-top: 10px;
            font-size: 12px;
            align-items: center;
        }
        .leyenda-item { display: flex; align-items: center; gap: 5px; }
        .leyenda-color { width: 16px; height: 16px; border-radius: 4px; }
    </style>
</head>

<body class="body-bg body-center">

<div class="container-large">

    <%
        Administrador a = (Administrador) request.getAttribute("bean");
        String[][] horario = (String[][]) request.getAttribute("horarioSemanal");
    %>

    <div class="icon"><i class="fa-solid fa-briefcase"></i></div>
    <h2 class="title">Datos Laborales del Empleado</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> <%= a.getMaterno() %> — <%= a.getUsuario() %></p>

    <label class="label">Puesto:</label>
    <input type="text" class="input" value="<%= a.getNombrePuesto() != null ? a.getNombrePuesto() : "—" %>" readonly>

    <label class="label">Turno:</label>
    <input type="text" class="input" value="<%= a.getNombreTurno() != null ? a.getNombreTurno() : "—" %>" readonly>

    <label class="label">Salario:</label>
    <input type="text" class="input" value="$<%= a.getSalario() != null ? a.getSalario() : "—" %>" readonly>

    <label class="label">Fecha de ingreso:</label>
    <input type="text" class="input" value="<%= a.getFechaIngreso() != null ? a.getFechaIngreso() : "—" %>" readonly>

    <label class="label">Status:</label>
    <input type="text" class="input" value="<%= "A".equals(a.getStatus()) ? "Activo" : "Inactivo" %>" readonly>

    <!-- ── CALENDARIO SEMANAL ── -->
    <hr style="border-color:#90e0ef; margin:20px 0;">
    <h3 class="title" style="font-size:18px;"><i class="fa-solid fa-calendar-days"></i> Horario Semanal</h3>

    <%
        if (horario == null || horario.length == 0) {
    %>
        <p style="color:#555; text-align:center;">No tiene horario semanal asignado.</p>
    <%
        } else {
            // Construir arreglos de entrada/salida por día (índice 1-7)
            String[] entradaPorDia = new String[8];
            String[] salidaPorDia  = new String[8];
            for (String[] fila : horario) {
                int dia = Integer.parseInt(fila[0]);
                entradaPorDia[dia] = fila[2]; // horaEntrada
                salidaPorDia[dia]  = fila[3]; // horaSalida
            }
            String[] DIAS = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
            int H_INICIO = 6;
            int H_FIN    = 22;
    %>
    <div class="semana-grid">
        <%
            for (int d = 1; d <= 7; d++) {
                boolean tieneDia = entradaPorDia[d] != null;
        %>
        <div class="dia-col">
            <div class="dia-header <%= tieneDia ? "activo" : "" %>">
                <%= DIAS[d - 1] %>
            </div>
            <div class="dia-horas">
                <%
                    for (int h = H_INICIO; h < H_FIN; h++) {
                        boolean trabajando = false;
                        if (tieneDia) {
                            int hEntrada = Integer.parseInt(entradaPorDia[d].substring(0, 2));
                            int hSalida  = Integer.parseInt(salidaPorDia[d].substring(0, 2));
                            trabajando = (h >= hEntrada && h < hSalida);
                        }
                        String ampm = h >= 12 ? "PM" : "AM";
                        int h12 = h > 12 ? h - 12 : (h == 0 ? 12 : h);
                        String etiq = h12 + ":00 " + ampm;
                %>
                <div class="hora-bloque <%= trabajando ? "trabajando" : "" %>">
                    <%= etiq %>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <%
            }
        %>
    </div>

    <div class="leyenda">
        <div class="leyenda-item">
            <div class="leyenda-color" style="background:#00b4d8;"></div>
            <span>Horas de trabajo</span>
        </div>
        <div class="leyenda-item">
            <div class="leyenda-color" style="background:#f0f9ff; border:1px solid #90e0ef;"></div>
            <span>Fuera de turno</span>
        </div>
        <div class="leyenda-item">
            <div class="leyenda-color" style="background:#0077b6;"></div>
            <span>Día sin laborar</span>
        </div>
    </div>

    <%
        }
    %>

    <div class="back mt-15">
        <a href="ConsultaDatosLaborales.html" class="link">⬅ Nueva búsqueda</a>
    </div>

</div>

</body>
</html>
