<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.EmpleadoVeterinario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Citas</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .cita-card { border:2px solid #90e0ef; border-radius:12px; padding:15px; margin-top:15px; background:#f0f9ff; }
        .cita-card h4 { color:#023e8a; margin:0 0 8px 0; }
        .cita-info { display:grid; grid-template-columns:1fr 1fr; gap:4px 20px; font-size:13px; color:#023e8a; }
        .cita-info p { margin:3px 0; }
        .cita-info strong { color:#0077b6; }
    </style>
</head>
<body class="body-bg body-center">
<div class="container">

    <%
        EmpleadoVeterinario a = (EmpleadoVeterinario) request.getAttribute("bean");
        String[][] citas = (String[][]) request.getAttribute("citas");
    %>

    <div class="icon"><i class="fa-solid fa-clipboard-list"></i></div>
    <h2 class="title">Mis Citas Pendientes</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> — <%= a.getUsuario() %></p>

    <%
        if (citas == null || citas.length == 0) {
    %>
        <p style="color:#555; text-align:center; margin-top:20px;">No tienes citas pendientes.</p>
    <%
        } else {
            for (String[] c : citas) {
    %>
    <div class="cita-card">
        <h4>Cita #<%= c[0] %></h4>
        <div class="cita-info">
            <p><strong>Cliente:</strong> <%= c[1] %></p>
            <p><strong>Mascota:</strong> <%= c[2] %></p>
            <p><strong>Fecha:</strong> <%= c[3] %></p>
            <p><strong>Hora:</strong> <%= c[4].substring(0,5) %></p>
            <p><strong>Motivo:</strong> <%= c[5] %></p>
        </div>
    </div>
    <%
            }
        }
    %>

</div>
</body>
</html>
