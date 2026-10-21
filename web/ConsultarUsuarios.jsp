<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Consulta General de Usuarios</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg">

    <div class="main-panel">

        <div class="admin-container">

            <div class="admin-header">
                <h1 class="title">Lista de Usuarios</h1>
            </div>

            <%
                String[][] datos   = (String[][]) request.getAttribute("datos");
                String[]  titulos  = (String[])   request.getAttribute("titulos");
                String    respuesta = (String)     request.getAttribute("respuesta");

                if (datos != null && titulos != null) {
            %>

            <table class="table">
                <tr>
                    <% for (int i = 0; i < titulos.length; i++) { %>
                        <th><%= titulos[i] %></th>
                    <% } %>
                </tr>
                <% for (int i = 0; i < datos.length; i++) { %>
                <tr>
                    <% for (int j = 0; j < datos[i].length; j++) { %>
                        <td><%= datos[i][j] %></td>
                    <% } %>
                </tr>
                <% } %>
            </table>

            <% } else { %>
                <p class="text-muted text-center mt-20">
                    <%= (respuesta != null) ? respuesta : "No hay datos para mostrar." %>
                </p>
            <% } %>

        </div>

    </div>

</body>

</html>
