<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.EmpleadoVeterinario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Modificar Historial Médico</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body class="body-bg body-center">
<div class="container">

    <%
        EmpleadoVeterinario a = (EmpleadoVeterinario) request.getAttribute("bean");
    %>

    <div class="icon"><i class="fa-solid fa-pen-to-square"></i></div>
    <h2 class="title">Modificar Historial Médico</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> — <%= a.getUsuario() %></p>
    <p class="subtitle">Ingresa el usuario del cliente para buscar historiales</p>

    <label class="label">Usuario del cliente:</label>
    <input type="text" id="usuarioCliente" class="input" placeholder="Ej. JuaPerLop045"
           oninput="buscarCliente()">

    <div id="seccionMascota" style="display:none;">
        <label class="label">Mascota:</label>
        <select id="selMascota" class="select" onchange="cargarHistoriales()">
            <option value="">-- Selecciona una mascota --</option>
        </select>
    </div>

    <div id="seccionHistorial" style="display:none;">
        <label class="label">Historial:</label>
        <select id="selHistorial" class="select" onchange="mostrarForm()">
            <option value="">-- Selecciona un historial --</option>
        </select>
    </div>

    <div id="seccionForm" style="display:none;">
        <form action="controlEmpleadoVeterinario" method="post">
            <input type="hidden" name="idHistorial" id="hidIdHistorial">

            <label class="label">Peso (kg):</label>
            <input type="text" name="peso" id="txtPeso" class="input">

            <label class="label">Temperatura (°C):</label>
            <input type="text" name="temperatura" id="txtTemp" class="input">

            <label class="label">Diagnóstico:</label>
            <input type="text" name="diagnostico" id="txtDiag" class="input" required>

            <label class="label">Tratamiento:</label>
            <input type="text" name="tratamiento" id="txtTrat" class="input">

            <label class="label">Medicamentos:</label>
            <input type="text" name="medicamentos" id="txtMed" class="input">

            <label class="label">Vacunas:</label>
            <input type="text" name="vacunas" id="txtVac" class="input">

            <label class="label">Observaciones:</label>
            <input type="text" name="observacionesH" id="txtObs" class="input">

            <label class="label">Próxima cita:</label>
            <input type="date" name="proximaCita" id="txtProx" class="input">

            <button type="submit" name="ModificarHistorialVet" value="1" class="btn btn-primary"><i class="fa-solid fa-floppy-disk"></i> Guardar Cambios</button>
        </form>
    </div>

    <div id="msgNada" style="display:none; color:#555; text-align:center; margin-top:15px;"></div>

</div>

<script>
    var historialesData = [];
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
                document.getElementById("seccionHistorial").style.display = "none";
                document.getElementById("seccionForm").style.display = "none";
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
        document.getElementById("seccionHistorial").style.display = "none";
        document.getElementById("seccionForm").style.display = "none";
        if (!idMascota) return;

        fetch("controlEmpleadoVeterinario?accion=ConsultarHistorialJSON&idMascota=" + idMascota)
            .then(function(r) { return r.json(); })
            .then(function(data) {
                historialesData = data || [];
                if (historialesData.length === 0) {
                    document.getElementById("msgNada").textContent = "Sin historiales para esta mascota.";
                    document.getElementById("msgNada").style.display = "block";
                    return;
                }
                document.getElementById("msgNada").style.display = "none";
                var sel = document.getElementById("selHistorial");
                sel.innerHTML = "<option value=''>-- Selecciona un historial --</option>";
                historialesData.forEach(function(h) {
                    var opt = document.createElement("option");
                    opt.value = h[0];
                    opt.text = "Historial #" + h[0] + " — " + h[1] + " | " + h[5];
                    sel.appendChild(opt);
                });
                document.getElementById("seccionHistorial").style.display = "block";
            });
    }

    function mostrarForm() {
        var id = document.getElementById("selHistorial").value;
        if (!id) { document.getElementById("seccionForm").style.display = "none"; return; }
        var h = historialesData.find(function(x) { return x[0] === id; });
        if (!h) return;
        // h = [idHistorial, fechaRegistro, veterinario, peso, temperatura, diagnostico, tratamiento, medicamentos, vacunas, observaciones, proximaCita]
        document.getElementById("hidIdHistorial").value = h[0];
        document.getElementById("txtPeso").value = h[3] || "";
        document.getElementById("txtTemp").value = h[4] || "";
        document.getElementById("txtDiag").value = h[5] || "";
        document.getElementById("txtTrat").value = h[6] || "";
        document.getElementById("txtMed").value  = h[7] || "";
        document.getElementById("txtVac").value  = h[8] || "";
        document.getElementById("txtObs").value  = h[9] || "";
        document.getElementById("txtProx").value = h[10] || "";
        document.getElementById("seccionForm").style.display = "block";
    }
</script>

</body>
</html>
