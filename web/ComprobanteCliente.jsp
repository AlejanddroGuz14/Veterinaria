<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Cliente"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Descargar Comprobante</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .tabla-citas { width:100%; border-collapse:collapse; margin-top:15px; }
        .tabla-citas th { background:#023e8a; color:white; padding:10px; font-size:12px; }
        .tabla-citas td { padding:8px; text-align:center; border-bottom:1px solid #e0f7fa; font-size:12px; }
        .tabla-citas tr:nth-child(even) { background:#e0f7fa; }
        .btn-descargar { background:#0077b6; color:white; border:none; padding:6px 12px; border-radius:6px; cursor:pointer; font-weight:bold; font-size:12px; text-decoration:none; display:inline-block; }
        .btn-descargar:hover { background:#023e8a; }
    </style>
</head>
<body class="body-bg body-center">

<%
    Cliente a = (Cliente) request.getAttribute("bean");
    String[][] citas = (String[][]) request.getAttribute("citas");
%>

<div class="container" style="max-width:700px;">

    <div class="icon"><i class="fa-solid fa-file-pdf"></i></div>
    <h2 class="title">Descargar Comprobante</h2>
    <p class="subtitle">Selecciona la cita para descargar su comprobante</p>

    <% if (citas == null || citas.length == 0) { %>
        <p style="text-align:center; color:#555; margin-top:20px;">No tienes citas pendientes.</p>
    <% } else { %>
        <table class="tabla-citas">
            <tr>
                <th>ID</th>
                <th>Mascota</th>
                <th>Veterinario</th>
                <th>Fecha</th>
                <th>Motivo</th>
                <th>Comprobante</th>
            </tr>
            <% for (int i = 0; i < citas.length; i++) { %>
            <tr>
                <td><%= citas[i][0] %></td>
                <td><%= citas[i][1] %></td>
                <td><%= citas[i][2] %></td>
                <td><%= citas[i][3] %></td>
                <td><%= citas[i][5] %></td>
                <td>
                    <a href="controlCitas?accion=GenerarComprobanteCita&idCita=<%= citas[i][0] %>" class="btn-descargar" target="_blank">
                        <i class="fa-solid fa-download"></i> PDF
                    </a>
                </td>
            </tr>
            <% } %>
        </table>
    <% } %>

</div>

</body>
</html>
