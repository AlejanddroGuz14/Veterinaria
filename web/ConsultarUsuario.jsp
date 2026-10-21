<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Consulta de Usuario</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg body-center">

    <div class="container">

        <%
            Administrador a = (Administrador) request.getAttribute("bean");
        %>

        <div class="icon"><i class="fa-solid fa-paw"></i></div>

        <h2 class="title">Datos del Usuario</h2>
        <p class="subtitle">Información encontrada en el sistema</p>

        <label class="label">ID:</label>
        <input type="text" class="input" value="<%= a.getIdCliente() %>" readonly>

        <label class="label">Usuario:</label>
        <input type="text" class="input" value="<%= a.getUsuario() %>" readonly>

        <label class="label">Nombre:</label>
        <input type="text" class="input" value="<%= a.getNombre() %>" readonly>

        <label class="label">Paterno:</label>
        <input type="text" class="input" value="<%= a.getPaterno() %>" readonly>

        <label class="label">Materno:</label>
        <input type="text" class="input" value="<%= a.getMaterno() %>" readonly>

        <label class="label">Email:</label>
        <input type="text" class="input" value="<%= a.getMail() %>" readonly>

        <label class="label">Teléfono:</label>
        <input type="text" class="input" value="<%= a.getTelefono() %>" readonly>

        <label class="label">Tipo:</label>
        <input type="text" class="input" value="<%= a.getTipo() %>" readonly>

        <label class="label">Status:</label>
        <input type="text" class="input" value="<%= a.getStatus() %>" readonly>

        <div class="back mt-15">
            <a href="ConsultaUsuarios.html" class="link">⬅ Nueva búsqueda</a>
        </div>

    </div>

</body>

</html>
