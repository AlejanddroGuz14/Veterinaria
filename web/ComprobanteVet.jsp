<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.EmpleadoVeterinario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Descargar Comprobante</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body class="body-bg body-center">
<div class="container">

    <%
        EmpleadoVeterinario a = (EmpleadoVeterinario) request.getAttribute("bean");
        String[][] citas = (String[][]) request.getAttribute("citas");
    %>

    <div class="icon"><i class="fa-solid fa-file-lines"></i></div>
    <h2 class="title">Descargar Comprobante de Cita</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> — <%= a.getUsuario() %></p>

    <%
        if (citas == null || citas.length == 0) {
    %>
        <p style="color:#555; text-align:center; margin-top:20px;">No tienes citas pendientes.</p>
    <%
        } else {
    %>

    <label class="label">Selecciona la cita:</label>
    <select id="selCita" class="select">
        <option value="">-- Selecciona una cita --</option>
        <%
            for (String[] c : citas) {
        %>
        <option value="<%= c[0] %>">Cita #<%= c[0] %> — <%= c[1] %> | <%= c[3] %> <%= c[4].substring(0,5) %></option>
        <%
            }
        %>
    </select>

    <br>
    <button class="btn btn-primary" onclick="descargarPDF()"><i class="fa-solid fa-download"></i> Descargar Comprobante</button>

    <%
        }
    %>

</div>

<script>
    function descargarPDF() {
        var idCita = document.getElementById("selCita").value;
        if (!idCita) { alert("Selecciona una cita."); return; }

        fetch("controlCitas?accion=GenerarComprobanteCita&idCita=" + idCita)
            .then(function(res) {
                if (!res.ok) return res.text().then(function(msg) { throw new Error(msg); });
                return res.blob();
            })
            .then(function(blob) {
                var hoy = new Date();
                var fechaStr = hoy.getFullYear() + "-" + ("0"+(hoy.getMonth()+1)).slice(-2) + "-" + ("0"+hoy.getDate()).slice(-2);
                var filename = "Comprobante_Cita" + idCita + "_" + fechaStr + ".pdf";
                var url = URL.createObjectURL(blob);
                var link = document.createElement("a");
                link.href = url; link.download = filename;
                document.body.appendChild(link); link.click(); document.body.removeChild(link);
                window.open(url, "_blank");
            })
            .catch(function(err) { alert("Error: " + err.message); });
    }
</script>

</body>
</html>
