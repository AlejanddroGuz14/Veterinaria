<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.EmpleadoVeterinario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Cancelar / Terminar Cita</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body class="body-bg body-center">
<div class="container">

    <%
        EmpleadoVeterinario a = (EmpleadoVeterinario) request.getAttribute("bean");
        String[][] citas = (String[][]) request.getAttribute("citas");
    %>

    <div class="icon"><i class="fa-solid fa-xmark"></i></div>
    <h2 class="title">Cancelar / Terminar Cita</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> — <%= a.getUsuario() %></p>

    <%
        if (citas == null || citas.length == 0) {
    %>
        <p style="color:#555; text-align:center; margin-top:20px;">No tienes citas pendientes.</p>
    <%
        } else {
    %>

    <form action="controlEmpleadoVeterinario" method="post">

        <label class="label">Selecciona la cita:</label>
        <select name="idCita" class="select" required>
            <option value="">-- Selecciona una cita --</option>
            <%
                for (String[] c : citas) {
            %>
            <option value="<%= c[0] %>">Cita #<%= c[0] %> — <%= c[1] %> | <%= c[3] %> <%= c[4].substring(0,5) %></option>
            <%
                }
            %>
        </select>

        <label class="label">Acción:</label>
        <select name="estadoCita" class="select" required>
            <option value="C"><i class="fa-solid fa-xmark"></i> Cancelar cita</option>
            <option value="T">✅ Marcar como terminada</option>
        </select>

        <input type="submit" name="CancelarCitaVet" value="Confirmar" class="btn btn-primary"
               onclick="return confirm('¿Estás seguro?');">

    </form>

    <%
        }
    %>

</div>
</body>
</html>
