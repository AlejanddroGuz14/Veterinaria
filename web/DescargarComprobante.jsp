<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Descargar Comprobante de Cita</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg body-center">

<div class="container">

    <%
        Administrador a = (Administrador) request.getAttribute("bean");
        String[][] mascotas = (String[][]) request.getAttribute("mascotas");
    %>

    <div class="icon"><i class="fa-solid fa-file-lines"></i></div>
    <h2 class="title">Descargar Comprobante de Cita</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> <%= a.getMaterno() %> — <%= a.getUsuario() %></p>

    <!-- SELECTOR DE MASCOTA -->
    <label class="label">Mascota:</label>
    <select id="selMascota" class="select" onchange="cargarCitas()">
        <option value="">-- Selecciona una mascota --</option>
        <%
            if (mascotas != null) {
                for (String[] m : mascotas) {
        %>
        <option value="<%= m[0] %>" data-nombre="<%= m[1] %>"><%= m[1] %> (<%= m[2] %>)</option>
        <%
                }
            }
        %>
    </select>

    <!-- SELECTOR DE CITA -->
    <div id="seccionCita" style="display:none;">
        <label class="label">Cita pendiente:</label>
        <select id="selCita" class="select" onchange="habilitarBoton()">
            <option value="">-- Selecciona una cita --</option>
        </select>
    </div>

    <div id="msgNada" style="display:none; color:#555; text-align:center; margin-top:15px;">
        Esta mascota no tiene citas pendientes.
    </div>

    <!-- BOTÓN DESCARGAR -->
    <div id="seccionDescargar" style="display:none; margin-top:20px;">
        <button id="btnDescargar" class="btn btn-primary" onclick="descargarPDF()">
            <i class="fa-solid fa-download"></i> Descargar Comprobante
        </button>
    </div>

</div>

<script>
    var citasData = [];
    var usuarioCliente = "<%= a.getUsuario() %>";

    function cargarCitas() {
        var idMascota = document.getElementById("selMascota").value;
        document.getElementById("seccionCita").style.display     = "none";
        document.getElementById("seccionDescargar").style.display = "none";
        document.getElementById("msgNada").style.display          = "none";

        if (!idMascota) return;

        fetch("controlCitas?accion=CargarCitasMascota&idMascota=" + idMascota)
            .then(function(r) { return r.json(); })
            .then(function(data) {
                citasData = data || [];
                if (citasData.length === 0) {
                    document.getElementById("msgNada").style.display = "block";
                    return;
                }

                var sel = document.getElementById("selCita");
                sel.innerHTML = "<option value=''>-- Selecciona una cita --</option>";
                citasData.forEach(function(c) {
                    var opt = document.createElement("option");
                    opt.value = c[0];
                    opt.text  = "Cita #" + c[0] + " — " + c[2] + " " + c[3].substring(0,5) + " | " + c[4];
                    sel.appendChild(opt);
                });
                document.getElementById("seccionCita").style.display = "block";
            });
    }

    function habilitarBoton() {
        var idCita = document.getElementById("selCita").value;
        document.getElementById("seccionDescargar").style.display = idCita ? "block" : "none";
    }

    function descargarPDF() {
        var idCita = document.getElementById("selCita").value;
        if (!idCita) return;

        var selMascota = document.getElementById("selMascota");
        var nombreMascota = selMascota.options[selMascota.selectedIndex].getAttribute("data-nombre");

        fetch("controlCitas?accion=GenerarComprobanteCita&idCita=" + idCita)
            .then(function(res) {
                if (!res.ok) {
                    return res.text().then(function(msg) { throw new Error(msg); });
                }
                return res.blob();
            })
            .then(function(blob) {
                // Nombre del archivo: mascota_numeroCita_fechaDescarga
                var hoy = new Date();
                var fechaStr = hoy.getFullYear() + "-" +
                    ("0" + (hoy.getMonth()+1)).slice(-2) + "-" +
                    ("0" + hoy.getDate()).slice(-2);
                var filename = nombreMascota + "_Cita" + idCita + "_" + fechaStr + ".pdf";

                var url  = URL.createObjectURL(blob);
                var link = document.createElement("a");
                link.href     = url;
                link.download = filename;
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);

                // Abrir el PDF en nueva pestaña
                window.open(url, "_blank");
            })
            .catch(function(err) {
                alert("Error: " + err.message);
            });
    }
</script>

</body>
</html>
