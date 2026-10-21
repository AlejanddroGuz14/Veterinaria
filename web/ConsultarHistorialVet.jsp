<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.EmpleadoVeterinario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Consultar Historial Médico</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .historial-card { border:2px solid #90e0ef; border-radius:12px; padding:15px; margin-top:15px; background:#f0f9ff; }
        .historial-card h4 { color:#023e8a; margin:0 0 8px 0; }
        .historial-info { font-size:13px; color:#023e8a; }
        .historial-info p { margin:4px 0; }
        .historial-info strong { color:#0077b6; }
    </style>
</head>
<body class="body-bg body-center">
<div class="container">

    <%
        EmpleadoVeterinario a = (EmpleadoVeterinario) request.getAttribute("bean");
    %>

    <div class="icon"><i class="fa-solid fa-magnifying-glass"></i></div>
    <h2 class="title">Consultar Historial Médico</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> — <%= a.getUsuario() %></p>
    <p class="subtitle">Ingresa el usuario del cliente</p>

    <label class="label">Usuario del cliente:</label>
    <input type="text" id="usuarioCliente" class="input" placeholder="Ej. JuaPerLop045"
           oninput="buscarCliente()">

    <div id="seccionMascota" style="display:none;">
        <label class="label">Mascota:</label>
        <select id="selMascota" class="select" onchange="cargarHistoriales()">
            <option value="">-- Selecciona una mascota --</option>
        </select>
    </div>

    <div id="contenidoHistorial"></div>

    <div id="msgNada" style="display:none; color:#555; text-align:center; margin-top:15px;"></div>

</div>

<script>
    var timerBuscar = null;

    function buscarCliente() {
        clearTimeout(timerBuscar);
        timerBuscar = setTimeout(function() { cargarMascotas(); }, 400);
    }

    function cargarMascotas() {
        var usr = document.getElementById("usuarioCliente").value.trim();
        if (!usr) return;
        fetch("controlMascotas?accion=CargarMascotasPorCliente&usr=" + encodeURIComponent(usr))
            .then(function(r) { return r.json(); })
            .then(function(data) {
                var sel = document.getElementById("selMascota");
                sel.innerHTML = "<option value=''>-- Selecciona una mascota --</option>";
                document.getElementById("contenidoHistorial").innerHTML = "";
                if (!data || data.length === 0) {
                    document.getElementById("seccionMascota").style.display = "none";
                    document.getElementById("msgNada").textContent = "Sin mascotas.";
                    document.getElementById("msgNada").style.display = "block";
                    return;
                }
                document.getElementById("msgNada").style.display = "none";
                data.forEach(function(m) {
                    var opt = document.createElement("option");
                    opt.value = m[0]; opt.text = m[1] + " (" + m[2] + ")";
                    sel.appendChild(opt);
                });
                document.getElementById("seccionMascota").style.display = "block";
            });
    }

    function cargarHistoriales() {
        var idMascota = document.getElementById("selMascota").value;
        document.getElementById("contenidoHistorial").innerHTML = "";
        if (!idMascota) return;

        fetch("controlEmpleadoVeterinario?accion=ConsultarHistorialJSON&idMascota=" + idMascota)
            .then(function(r) { return r.json(); })
            .then(function(data) {
                if (!data || data.length === 0) {
                    document.getElementById("msgNada").textContent = "Sin historiales para esta mascota.";
                    document.getElementById("msgNada").style.display = "block";
                    return;
                }
                document.getElementById("msgNada").style.display = "none";
                var html = "";
                data.forEach(function(h) {
                    // h = [idHistorial, fechaRegistro, veterinario, peso, temperatura, diagnostico, tratamiento, medicamentos, vacunas, observaciones, proximaCita]
                    html += "<div class='historial-card'>";
                    html += "<h4>Historial #" + h[0] + " — " + h[1] + "</h4>";
                    html += "<div class='historial-info'>";
                    html += "<p><strong>Veterinario:</strong> " + (h[2]||"—") + "</p>";
                    html += "<p><strong>Peso:</strong> " + (h[3]||"—") + " kg</p>";
                    html += "<p><strong>Temperatura:</strong> " + (h[4]||"—") + " °C</p>";
                    html += "<p><strong>Diagnóstico:</strong> " + (h[5]||"—") + "</p>";
                    html += "<p><strong>Tratamiento:</strong> " + (h[6]||"—") + "</p>";
                    html += "<p><strong>Medicamentos:</strong> " + (h[7]||"—") + "</p>";
                    html += "<p><strong>Vacunas:</strong> " + (h[8]||"—") + "</p>";
                    html += "<p><strong>Observaciones:</strong> " + (h[9]||"—") + "</p>";
                    html += "<p><strong>Próxima cita:</strong> " + (h[10]||"—") + "</p>";
                    html += "</div></div>";
                });
                document.getElementById("contenidoHistorial").innerHTML = html;
            });
    }
</script>

</body>
</html>
