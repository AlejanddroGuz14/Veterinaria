<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.EmpleadoVeterinario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Horario</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .semana-grid { display:grid; grid-template-columns:repeat(7,1fr); gap:6px; margin-top:10px; }
        .dia-col { border:2px solid #90e0ef; border-radius:10px; overflow:hidden; }
        .dia-header { background:#0077b6; color:white; text-align:center; padding:8px 4px; font-weight:bold; font-size:13px; }
        .dia-header.activo { background:#023e8a; }
        .dia-horas { display:flex; flex-direction:column; }
        .hora-bloque { padding:4px 6px; font-size:11px; text-align:center; border-top:1px solid #e0f7fa; background:#f0f9ff; color:#023e8a; }
        .hora-bloque.trabajando { background:#00b4d8; color:white; font-weight:bold; }
    </style>
</head>
<body class="body-bg body-center">
<div class="container-large">

    <%
        EmpleadoVeterinario a = (EmpleadoVeterinario) request.getAttribute("bean");
        String[][] horario = (String[][]) request.getAttribute("horarioSemanal");
    %>

    <div class="icon"><i class="fa-solid fa-calendar-days"></i></div>
    <h2 class="title">Mi Horario Semanal</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> — <%= a.getNombrePuesto() %> | <%= a.getNombreTurno() %></p>

    <%
        if (horario == null || horario.length == 0) {
    %>
        <p style="color:#555; text-align:center;">No tienes horario semanal asignado.</p>
    <%
        } else {
            String[] entradaPorDia = new String[8];
            String[] salidaPorDia  = new String[8];
            for (String[] fila : horario) {
                int dia = Integer.parseInt(fila[0]);
                entradaPorDia[dia] = fila[2];
                salidaPorDia[dia]  = fila[3];
            }
            String[] DIAS = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
    %>
    <div class="semana-grid">
        <%
            for (int d = 1; d <= 7; d++) {
                boolean tieneDia = entradaPorDia[d] != null;
        %>
        <div class="dia-col">
            <div class="dia-header <%= tieneDia ? "activo" : "" %>"><%= DIAS[d-1] %></div>
            <div class="dia-horas">
                <%
                    for (int h = 6; h < 22; h++) {
                        boolean trabajando = false;
                        if (tieneDia) {
                            int hE = Integer.parseInt(entradaPorDia[d].substring(0,2));
                            int hS = Integer.parseInt(salidaPorDia[d].substring(0,2));
                            trabajando = (h >= hE && h < hS);
                        }
                        String ampm = h >= 12 ? "PM" : "AM";
                        int h12 = h > 12 ? h - 12 : (h == 0 ? 12 : h);
                %>
                <div class="hora-bloque <%= trabajando ? "trabajando" : "" %>"><%= h12 %>:00 <%= ampm %></div>
                <%
                    }
                %>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <%
        }
    %>

</div>
</body>
</html>
