<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Modificar Cita</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .hora-bloque { padding:6px 10px; font-size:12px; text-align:center; border-bottom:1px solid #e0f7fa; background:#f0f9ff; color:#023e8a; cursor:default; }
        .hora-bloque.disponible { background:#00b4d8; color:white; font-weight:bold; cursor:pointer; }
        .hora-bloque.disponible:hover { background:#023e8a; }
        .hora-bloque.ocupada { background:#e63946; color:white; font-weight:bold; cursor:not-allowed; }
        .hora-bloque.seleccionada { background:#023e8a; color:white; font-weight:bold; }
        .hora-bloque.fuera { background:#f0f9ff; color:#aaa; }
        .horario-col { display:inline-flex; flex-direction:column; align-items:center; margin-top:10px; }
        .horario-col .dia-titulo { background:#023e8a; color:white; padding:6px 20px; border-radius:8px 8px 0 0; font-weight:bold; font-size:13px; min-width:150px; text-align:center; }
        .horario-col .horas-lista { border:2px solid #90e0ef; border-top:none; border-radius:0 0 8px 8px; min-width:150px; overflow:hidden; }
    </style>
</head>

<body class="body-bg body-center">

<div class="container">

    <%
        Administrador a = (Administrador) request.getAttribute("bean");
        String[][] mascotas = (String[][]) request.getAttribute("mascotas");
    %>

    <div class="icon"><i class="fa-solid fa-pen-to-square"></i></div>
    <h2 class="title">Modificar Cita</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> <%= a.getMaterno() %> — <%= a.getUsuario() %></p>

    <!-- SELECTOR DE MASCOTA -->
    <label class="label">Mascota:</label>
    <select id="selMascota" class="select" onchange="cargarCitas()">
        <option value="">-- Selecciona una mascota --</option>
        <%
            if (mascotas != null) {
                for (String[] m : mascotas) {
        %>
        <option value="<%= m[0] %>"><%= m[1] %> (<%= m[2] %>)</option>
        <%
                }
            }
        %>
    </select>

    <!-- SELECTOR DE CITA -->
    <div id="seccionCita" style="display:none;">
        <label class="label">Cita pendiente:</label>
        <select id="selCita" class="select" onchange="mostrarFormulario()">
            <option value="">-- Selecciona una cita --</option>
        </select>
    </div>

    <div id="msgNada" style="display:none; color:#555; text-align:center; margin-top:15px;">
        Esta mascota no tiene citas pendientes.
    </div>

    <!-- FORMULARIO DE MODIFICACIÓN -->
    <div id="seccionForm" style="display:none;">
        <hr style="border-color:#90e0ef; margin:20px 0;">

        <form action="controlCitas" method="post" id="formModCita">
            <input type="hidden" name="idCita" id="hidIdCita">
            <input type="hidden" name="idHorario" id="hidIdHorario">

            <!-- Solo lectura -->
            <label class="label">No. Cita:</label>
            <input type="text" class="input" id="txtIdCita" readonly>

            <label class="label">Mascota:</label>
            <input type="text" class="input" id="txtMascota" readonly>

            <label class="label">Cliente:</label>
            <input type="text" class="input" value="<%= a.getNombre() %> <%= a.getPaterno() %> <%= a.getMaterno() %>" readonly>

            <!-- Editables -->
            <label class="label">Veterinario:</label>
            <select name="idAcceso" id="selVeterinario" class="select" required onchange="onVetChange()">
                <option value="">-- Selecciona un veterinario --</option>
            </select>

            <label class="label">Motivo:</label>
            <input type="text" name="motivo" id="txtMotivo" class="input" required>

            <label class="label">Observaciones:</label>
            <input type="text" name="observaciones" id="txtObs" class="input">

            <!-- Fecha -->
            <div id="seccionFecha" style="display:none;">
                <label class="label">Nueva fecha:</label>
                <input type="date" id="inputFecha" class="input" onchange="onFechaChange()">
            </div>

            <!-- Calendario -->
            <div id="seccionHorario" style="display:none;">
                <label class="label">Nueva hora:</label>
                <div id="horarioDia" class="horario-col"></div>
                <div id="horaSelTxt" style="margin-top:8px; font-size:13px; color:#023e8a; font-weight:bold;">
                    Hora seleccionada: sin cambios
                </div>
            </div>

            <br>
            <button type="submit" name="ModificarCitaA" value="1" class="btn btn-primary" onclick="return validar()"><i class="fa-solid fa-floppy-disk"></i> Guardar Cambios</button>
        </form>
    </div>

    <div class="back mt-15">
        <a href="ModificarCita.html" class="link">⬅ Nueva búsqueda</a>
    </div>

</div>

<script>
    var DIAS = ['','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'];
    var citasData = [];
    var horarioVet = [];
    var horasOcupadas = [];
    var idHorarioOriginal = "";

    window.onload = function() {
        cargarVeterinarios();
        var hoy = new Date().toISOString().split("T")[0];
        document.getElementById("inputFecha").setAttribute("min", hoy);
    };

    function cargarVeterinarios() {
        fetch("controlCitas?accion=CargarVeterinarios")
            .then(function(r) { return r.json(); })
            .then(function(data) {
                var sel = document.getElementById("selVeterinario");
                data.forEach(function(v) {
                    var opt = document.createElement("option");
                    opt.value = v[0];
                    opt.text  = v[1] + " — " + v[2];
                    sel.appendChild(opt);
                });
            });
    }

    function cargarCitas() {
        var idMascota = document.getElementById("selMascota").value;
        document.getElementById("seccionCita").style.display = "none";
        document.getElementById("seccionForm").style.display = "none";
        document.getElementById("msgNada").style.display     = "none";
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

    function mostrarFormulario() {
        var idCita = document.getElementById("selCita").value;
        document.getElementById("seccionForm").style.display = "none";
        if (!idCita) return;

        var c = citasData.find(function(x) { return x[0] === idCita; });
        if (!c) return;

        // c = [idCita, veterinario, fecha, hora, motivo]
        document.getElementById("hidIdCita").value = c[0];
        document.getElementById("txtIdCita").value = "Cita #" + c[0];

        var selMasc = document.getElementById("selMascota");
        document.getElementById("txtMascota").value = selMasc.options[selMasc.selectedIndex].text;

        document.getElementById("txtMotivo").value = c[4] || "";
        document.getElementById("txtObs").value    = "";

        // Preseleccionar el idHorario actual (se enviará si no cambia)
        document.getElementById("hidIdHorario").value = "";
        idHorarioOriginal = "";

        // Obtener idHorario actual de la cita
        fetch("controlCitas?accion=ObtenerIdHorario&fecha=" + c[2] + "&hora=" + encodeURIComponent(c[3]))
            .then(function(r) { return r.json(); })
            .then(function(data) {
                if (data && data[0] && data[0][0]) {
                    document.getElementById("hidIdHorario").value = data[0][0];
                    idHorarioOriginal = data[0][0];
                }
            });

        document.getElementById("seccionFecha").style.display   = "none";
        document.getElementById("seccionHorario").style.display  = "none";
        document.getElementById("horaSelTxt").textContent = "Hora seleccionada: sin cambios (" + c[2] + " " + c[3].substring(0,5) + ")";
        document.getElementById("seccionForm").style.display = "block";
    }

    function onVetChange() {
        var idAcceso = document.getElementById("selVeterinario").value;
        document.getElementById("seccionFecha").style.display   = "none";
        document.getElementById("seccionHorario").style.display  = "none";
        if (!idAcceso) return;

        fetch("controlCitas?accion=CargarHorarioVeterinario&idAcceso=" + idAcceso)
            .then(function(r) { return r.json(); })
            .then(function(data) {
                horarioVet = data;
                if (!data || data.length === 0) {
                    alert("Este veterinario no tiene horario asignado.");
                    return;
                }
                document.getElementById("seccionFecha").style.display = "block";
            });
    }

    function onFechaChange() {
        var fechaStr = document.getElementById("inputFecha").value;
        if (!fechaStr) return;

        var fecha = new Date(fechaStr + "T12:00:00");
        var jsDay = fecha.getDay();
        var diaSemana = jsDay === 0 ? 7 : jsDay;

        var diaHorario = horarioVet.find(function(h) { return parseInt(h[0]) === diaSemana; });
        if (!diaHorario) {
            document.getElementById("seccionHorario").style.display = "none";
            alert("El veterinario no trabaja el " + DIAS[diaSemana] + ".");
            return;
        }

        var idAcceso = document.getElementById("selVeterinario").value;
        fetch("controlCitas?accion=HorasOcupadas&idAcceso=" + idAcceso)
            .then(function(r) { return r.json(); })
            .then(function(data) {
                horasOcupadas = data || [];
                construirHorario(diaHorario, diaSemana, fechaStr);
                document.getElementById("seccionHorario").style.display = "block";
            });
    }

    function construirHorario(diaHorario, diaSemana, fechaStr) {
        var hEntrada = parseInt(diaHorario[2].substring(0,2));
        var hSalida  = parseInt(diaHorario[3].substring(0,2));

        var ocupadasSet = {};
        horasOcupadas.forEach(function(o) {
            if (o[0] === fechaStr) ocupadasSet[parseInt(o[1].substring(0,2))] = true;
        });

        var container = document.getElementById("horarioDia");
        container.innerHTML = "";

        var titulo = document.createElement("div");
        titulo.className = "dia-titulo";
        titulo.textContent = DIAS[diaSemana] + " — " + fechaStr;
        container.appendChild(titulo);

        var lista = document.createElement("div");
        lista.className = "horas-lista";

        for (var h = 6; h < 22; h++) {
            var trabajando = (h >= hEntrada && h < hSalida);
            var ocupada    = ocupadasSet[h] === true;
            var ampm = h >= 12 ? "PM" : "AM";
            var h12  = h > 12 ? h - 12 : (h === 0 ? 12 : h);
            var etiq = h12 + ":00 " + ampm;
            var horaStr = (h < 10 ? "0" : "") + h + ":00:00";

            var bloque = document.createElement("div");
            bloque.className = "hora-bloque";
            bloque.textContent = etiq;
            bloque.setAttribute("data-hora", horaStr);
            bloque.setAttribute("data-fecha", fechaStr);

            if (!trabajando) {
                bloque.className += " fuera";
            } else if (ocupada) {
                bloque.className += " ocupada";
            } else {
                bloque.className += " disponible";
                (function(b) {
                    b.onclick = function() { seleccionarHora(b); };
                })(bloque);
            }

            lista.appendChild(bloque);
        }
        container.appendChild(lista);
    }

    function seleccionarHora(bloque) {
        var todos = document.querySelectorAll(".hora-bloque.seleccionada");
        for (var i = 0; i < todos.length; i++) {
            todos[i].className = "hora-bloque disponible";
        }
        bloque.className = "hora-bloque seleccionada";

        var fechaSel = bloque.getAttribute("data-fecha");
        var horaSel  = bloque.getAttribute("data-hora");
        document.getElementById("horaSelTxt").textContent = "Hora seleccionada: " + fechaSel + " " + horaSel.substring(0,5);

        fetch("controlCitas?accion=ObtenerIdHorario&fecha=" + fechaSel + "&hora=" + encodeURIComponent(horaSel))
            .then(function(r) { return r.json(); })
            .then(function(data) {
                if (data && data[0] && data[0][0]) {
                    document.getElementById("hidIdHorario").value = data[0][0];
                }
            });
    }

    function validar() {
        if (!document.getElementById("hidIdHorario").value) {
            alert("Error: no se pudo obtener el horario. Intenta de nuevo.");
            return false;
        }
        return true;
    }
</script>

</body>
</html>
