<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Cliente"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Mascotas</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .tabla-mascotas { width:100%; border-collapse:collapse; margin-top:15px; }
        .tabla-mascotas th { background:#023e8a; color:white; padding:10px; font-size:13px; }
        .tabla-mascotas td { padding:8px; text-align:center; border-bottom:1px solid #e0f7fa; font-size:13px; }
        .tabla-mascotas tr:nth-child(even) { background:#e0f7fa; }
    </style>
</head>
<body class="body-bg body-center">

<%
    Cliente a = (Cliente) request.getAttribute("bean");
    String[][] mascotas = (String[][]) request.getAttribute("mascotas");
%>

<div class="container" style="max-width:600px;">

    <div class="icon"><i class="fa-solid fa-dog"></i></div>
    <h2 class="title">Mis Mascotas</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %></p>

    <% if (mascotas == null || mascotas.length == 0) { %>
        <p style="text-align:center; color:#555; margin-top:20px;">No tienes mascotas registradas.</p>
    <% } else { %>
        <table class="tabla-mascotas">
            <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Especie</th>
            </tr>
            <% for (int i = 0; i < mascotas.length; i++) { %>
            <tr>
                <td><%= (i+1) %></td>
                <td><%= mascotas[i][1] %></td>
                <td><%= mascotas[i][2] %></td>
            </tr>
            <% } %>
        </table>
    <% } %>

</div>

</body>
</html>
