<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg body-center">

<div class="container">

    <div class="icon">⚠️</div>

    <h2 class="title">Ocurrió un problema</h2>

    <%
        String mensaje = request.getParameter("respuesta");

        if (mensaje == null || mensaje.isEmpty()) {
            mensaje = "Ha ocurrido un error inesperado";
        }
    %>

    <p class="subtitle"><%= mensaje %></p>

</div>

</body>
</html>