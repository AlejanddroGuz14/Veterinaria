<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Consulta de Mascota</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg body-center">

<div class="container">

    <%
        String[] titulos = (String[]) request.getAttribute("titulos");
        String[][] datos = (String[][]) request.getAttribute("datos");
    %>

    <div class="icon">&#128062;</div>
    <h2 class="title">Datos de la Mascota</h2>
    <p class="subtitle">Información encontrada en el sistema</p>

    <% if (titulos != null && datos != null && datos.length > 0) {
           String[] fila = datos[0];
    %>

    <label class="label">ID:</label>
    <input type="text" class="input" value="<%= fila[0] != null ? fila[0] : "" %>" readonly>

    <label class="label">Cliente:</label>
    <input type="text" class="input" value="<%= fila[1] != null ? fila[1] : "" %>" readonly>

    <label class="label">Mascota:</label>
    <input type="text" class="input" value="<%= fila[2] != null ? fila[2] : "" %>" readonly>

    <label class="label">Especie:</label>
    <input type="text" class="input" value="<%= fila[3] != null ? fila[3] : "" %>" readonly>

    <label class="label">Raza:</label>
    <input type="text" class="input" value="<%= fila[4] != null ? fila[4] : "" %>" readonly>

    <label class="label">Sexo:</label>
    <input type="text" class="input" value="<%= fila[5] != null ? fila[5] : "" %>" readonly>

    <label class="label">Fecha de nacimiento:</label>
    <input type="text" class="input" value="<%= fila[6] != null ? fila[6] : "" %>" readonly>

    <label class="label">Peso (kg):</label>
    <input type="text" class="input" value="<%= fila[7] != null ? fila[7] : "" %>" readonly>

    <label class="label">Color:</label>
    <input type="text" class="input" value="<%= fila[8] != null ? fila[8] : "" %>" readonly>

    <label class="label">Observaciones:</label>
    <input type="text" class="input" value="<%= fila[9] != null ? fila[9] : "" %>" readonly>

    <label class="label">Status:</label>
    <input type="text" class="input" value="<%= fila[10] != null ? fila[10] : "" %>" readonly>

    <label class="label">Fecha de registro:</label>
    <input type="text" class="input" value="<%= fila[11] != null ? fila[11] : "" %>" readonly>

    <% } else { %>
    <p class="subtitle" style="color:red;">No se encontraron datos de la mascota.</p>
    <% } %>

    <div class="back mt-15">
        <a href="ConsultaMascota.html" class="link">&#8592; Nueva búsqueda</a>
    </div>

</div>

</body>
</html>
