<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.EmpleadoVeterinario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar Historial Médico</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body class="body-bg body-center">
<div class="container">

    <%
        EmpleadoVeterinario a = (EmpleadoVeterinario) request.getAttribute("bean");
        String[][] citas = (String[][]) request.getAttribute("citasTerminadas");
    %>

    <div class="icon"><i class="fa-solid fa-clipboard-list"></i></div>
    <h2 class="title">Registrar Historial Médico</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> — <%= a.getUsuario() %></p>

    <%
        if (citas == null || citas.length == 0) {
    %>
        <p style="color:#555; text-align:center; margin-top:20px;">No tienes citas terminadas pendientes de historial.</p>
    <%
        } else {
    %>

    <form action="controlEmpleadoVeterinario" method="post">
        <input type="hidden" name="usr" value="<%= a.getUsuario() %>">

        <label class="label">Cita atendida:</label>
        <select name="idCita" id="selCita" class="select" required onchange="llenarDatos()">
            <option value="">-- Selecciona una cita --</option>
            <%
                for (String[] c : citas) {
                    // c = [idCita, cliente, mascota, idMascota, idCliente, fecha, hora, motivo]
            %>
            <option value="<%= c[0] %>" data-mascota="<%= c[3] %>" data-cliente="<%= c[4] %>">
                Cita #<%= c[0] %> — <%= c[1] %> | <%= c[2] %> | <%= c[5] %> <%= c[6].substring(0,5) %>
            </option>
            <%
                }
            %>
        </select>

        <input type="hidden" name="idMascota" id="hidMascota">
        <input type="hidden" name="idCliente" id="hidCliente">

        <div id="seccionForm" style="display:none;">
            <label class="label">Peso (kg):</label>
            <input type="text" name="peso" class="input" placeholder="Ej. 5.2">

            <label class="label">Temperatura (°C):</label>
            <input type="text" name="temperatura" class="input" placeholder="Ej. 38.5">

            <label class="label">Diagnóstico:</label>
            <input type="text" name="diagnostico" class="input" required placeholder="Diagnóstico del paciente">

            <label class="label">Tratamiento:</label>
            <input type="text" name="tratamiento" class="input" placeholder="Tratamiento indicado">

            <label class="label">Medicamentos:</label>
            <input type="text" name="medicamentos" class="input" placeholder="Medicamentos recetados">

            <label class="label">Vacunas aplicadas:</label>
            <input type="text" name="vacunas" class="input" placeholder="Vacunas (si aplica)">

            <label class="label">Observaciones:</label>
            <input type="text" name="observacionesH" class="input" placeholder="Notas adicionales">

            <label class="label">Próxima cita recomendada:</label>
            <input type="date" name="proximaCita" class="input">

            <button type="submit" name="RegistrarHistorialVet" value="1" class="btn btn-primary"><i class="fa-solid fa-floppy-disk"></i> Registrar Historial</button>
        </div>
    </form>

    <%
        }
    %>

</div>

<script>
    function llenarDatos() {
        var sel = document.getElementById("selCita");
        var opt = sel.options[sel.selectedIndex];
        if (!opt.value) {
            document.getElementById("seccionForm").style.display = "none";
            return;
        }
        document.getElementById("hidMascota").value = opt.getAttribute("data-mascota");
        document.getElementById("hidCliente").value = opt.getAttribute("data-cliente");
        document.getElementById("seccionForm").style.display = "block";
    }
</script>

</body>
</html>
