<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Cliente"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Perfil</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .perfil-info { margin-top:20px; text-align:left; }
        .perfil-info p { margin:8px 0; font-size:14px; color:#023e8a; }
        .perfil-info strong { color:#0077b6; }
    </style>
</head>
<body class="body-bg body-center">

<%
    Cliente a = (Cliente) request.getAttribute("bean");
%>

<div class="container" style="max-width:500px;">

    <div class="icon"><i class="fa-solid fa-user"></i></div>
    <h2 class="title">Mi Perfil</h2>
    <p class="subtitle">Información de tu cuenta</p>

    <div class="perfil-info">
        <p><strong>Nombre:</strong> <%= a.getNombre() %> <%= a.getPaterno() %> <%= a.getMaterno() %></p>
        <p><strong>Usuario:</strong> <%= a.getUsuario() %></p>
        <p><strong>Email:</strong> <%= a.getMail() != null ? a.getMail() : "—" %></p>
        <p><strong>Teléfono:</strong> <%= a.getTelefono() != null ? a.getTelefono() : "—" %></p>
        <p><strong>Tipo:</strong> Cliente</p>
        <p><strong>Status:</strong> <%= "A".equals(a.getStatus()) ? "Activo" : a.getStatus() %></p>
    </div>

</div>

</body>
</html>
