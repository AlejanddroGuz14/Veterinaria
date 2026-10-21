<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Cliente"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agendar Cita</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .horario-dia { display:flex; flex-direction:column; align-items:center; margin-top:10px; }
        .horario-dia .dia-titulo { background:#023e8a; color:white; padding:10px 30px; border-radius:10px 10px 0 0; font-weight:bold; font-size:15px; text-align:center; width:250px; }
        .horario-dia .horas-lista { border:2px solid #90e0ef; border-top:none; border-radius:0 0 10px 10px; width:250px; overflow:hidden; }
        .hora-bloque { padding:8px 10px; font-size:13px; text-align:center; border-bottom:1px solid #e0f7fa; background:#f0f9ff; color:#023e8a; cursor:default; }
        .hora-bloque.disponible { background:#00b4d8; color:white; font-weight:bold; cursor:pointer; }
        .hora-bloque.disponible:hover { background:#023e8a; }
        .hora-bloque.ocupada { background:#e63946; color:white; font-weight:bold; cursor:not-allowed; }
        .hora-bloque.seleccionada { background:#023e8a; color:white; font-weight:bold; }
        .hora-bloque.fuera { background:#f0f9ff; color:#aaa; }
        .leyenda { display:flex; gap:15px; margin-top:12px; font-size:12px; align-items:center; justify-content:center; }
        .leyenda-item { display:flex; align-items:center; gap:5px; }
        .leyenda-color { width:16px; height:16px; border-radius:4px; }
    </style>
</head>
<body class="body-bg body-center">

<%
    Cliente a = (Cliente) request.getAttribute("bean");
    String[][] mascotas = (String[][]) request.getAttribute("mascotas");
%>

<div class="container">

    <div class="icon"><i class="fa-solid fa-calendar-days"></i></div>
    <h2 class="title">Agendar Cita</h2>
    <p class="subtitle">Completa los datos para agendar tu cita</p>

    <form action="controlCliente" method="post" id="formCita">
        <input type="hidden" name="usr" value="<%= a.getUsuario() %>">

        <label class="label">Mascota:</label>
        <select name="idMascota" class="select" required>
            <option value="">-- Selecciona una mascota --</option>
            <% if (mascotas != null) { for (int i = 0; i < mascotas.length; i++) { %>
            <option value="<%= mascotas[i][0] %>"><%= mascotas[i][1] %> (<%= mascotas[i][2] %>)</option>
            <% } } %>
        </select>

        <label class="label">Veterinario:</label>
        <select name="idAcceso" id="selVeterinario" class="select" required onchange="onVeterinarioChange()">
            <option value="">-- Selecciona un veterinario --</option>
        </select>

        <div id="seccionFecha" style="display:none;">
            <label class="label">Fecha de la cita:</label>
            <input type="date" name="fechaCita" id="inputFecha" class="input" required onchange="onFechaChange()">
        </div>

        <label class="label">Motivo de consulta:</label>
        <input type="text" name="motivo" class="input" placeholder="Ej. Revisión general, vacuna, etc." required>

        <label class="label">Observaciones:</label>
        <input type="text" name="observaciones" class="input" placeholder="Notas adicionales (opcional)">

        <div id="seccionHorario" style="display:none;">
            <hr style="border-color:#90e0ef; margin:20px 0;">
            <h3 class="title" style="font-size:18px;"><i class="fa-solid fa-clock"></i> Horario Disponible</h3>
            <p class="subtitle">Haz clic en una hora disponible para seleccionarla</p>
            <div id="horarioDia" class="horario-dia"></div>
            <div class="leyenda">
                <div class="leyenda-item"><div class="leyenda-color" style="background:#00b4d8;"></div><span>Disponible</span></div>
                <div class="leyenda-item"><div class="leyenda-color" style="background:#e63946;"></div><span>Ocupada</span></div>
                <div class="leyenda-item"><div class="leyenda-color" style="background:#023e8a;"></div><span>Seleccionada</span></div>
            </div>
            <div id="horaSeleccionada" style="margin-top:10px; font-size:14px; color:#023e8a; font-weight:bold; text-align:center;">
                Hora seleccionada: ninguna
            </div>
        </div>

        <input type="hidden" name="idHorario" id="idHorario" value="">
        <br>
        <button type="submit" name="AgendarCitaCliente" value="1" class="btn btn-primary" onclick="return validarFormulario()">
            <i class="fa-solid fa-clipboard-list"></i> Agendar Cita
        </button>
    </form>

</div>

<script>
    var DIAS = ['Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'];
    var horarioVeterinario = [];
    var horasOcupadas = [];

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
                    opt.value = v[0]; opt.text = v[1] + " — " + v[2];
                    sel.appendChild(opt);
                });
            });
    }

    function onVeterinarioChange() {
        var idAcceso = document.getElementById("selVeterinario").value;
        document.getElementById("seccionHorario").style.display = "none";
        document.getElementById("inputFecha").value = "";
        limpiarSeleccion();
        if (!idAcceso) { document.getElementById("seccionFecha").style.display = "none"; return; }
        fetch("controlCitas?accion=CargarHorarioVeterinario&idAcceso=" + idAcceso)
            .then(function(r) { return r.json(); })
            .then(function(data) {
                horarioVeterinario = data;
                if (!data || data.length === 0) {
                    document.getElementById("seccionFecha").style.display = "none";
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
        var diaHorario = null;
        for (var i = 0; i < horarioVeterinario.length; i++) {
            if (parseInt(horarioVeterinario[i][0]) === diaSemana) { diaHorario = horarioVeterinario[i]; break; }
        }
        if (!diaHorario) {
            document.getElementById("seccionHorario").style.display = "none";
            alert("El veterinario no trabaja el " + DIAS[diaSemana - 1] + ". Selecciona otra fecha.");
            return;
        }
        var idAcceso = document.getElementById("selVeterinario").value;
        fetch("controlCitas?accion=HorasOcupadas&idAcceso=" + idAcceso)
            .then(function(r) { return r.json(); })
            .then(function(data) {
                horasOcupadas = data || [];
                construirHorarioDia(diaHorario, diaSemana, fechaStr);
                document.getElementById("seccionHorario").style.display = "block";
                limpiarSeleccion();
            });
    }

    function construirHorarioDia(diaHorario, diaSemana, fechaStr) {
        var hEntrada = parseInt(diaHorario[2].substring(0,2));
        var hSalida = parseInt(diaHorario[3].substring(0,2));
        var ocupadasSet = {};
        horasOcupadas.forEach(function(o) { if (o[0] === fechaStr) ocupadasSet[parseInt(o[1].substring(0,2))] = true; });
        var container = document.getElementById("horarioDia");
        container.innerHTML = "";
        var titulo = document.createElement("div");
        titulo.className = "dia-titulo";
        titulo.textContent = DIAS[diaSemana - 1] + " — " + fechaStr;
        container.appendChild(titulo);
        var lista = document.createElement("div");
        lista.className = "horas-lista";
        for (var h = 6; h < 22; h++) {
            var trabajando = (h >= hEntrada && h < hSalida);
            var ocupada = ocupadasSet[h] === true;
            var ampm = h >= 12 ? "PM" : "AM";
            var h12 = h > 12 ? h - 12 : (h === 0 ? 12 : h);
            var etiq = h12 + ":00 " + ampm;
            var horaStr = (h < 10 ? "0" : "") + h + ":00:00";
            var bloque = document.createElement("div");
            bloque.className = "hora-bloque";
            bloque.textContent = etiq;
            bloque.setAttribute("data-hora", horaStr);
            bloque.setAttribute("data-fecha", fechaStr);
            bloque.setAttribute("data-etiq", DIAS[diaSemana - 1] + " " + fechaStr + " " + etiq);
            if (!trabajando) { bloque.className += " fuera"; }
            else if (ocupada) { bloque.className += " ocupada"; bloque.textContent = etiq + " ⛔"; }
            else { bloque.className += " disponible"; bloque.onclick = (function(b) { return function() { seleccionarHora(b); }; })(bloque); }
            lista.appendChild(bloque);
        }
        container.appendChild(lista);
    }

    function seleccionarHora(bloque) {
        var todos = document.querySelectorAll(".hora-bloque.seleccionada");
        for (var i = 0; i < todos.length; i++) { todos[i].className = "hora-bloque disponible"; }
        bloque.className = "hora-bloque seleccionada";
        document.getElementById("horaSeleccionada").textContent = "Hora seleccionada: " + bloque.getAttribute("data-etiq");
        fetch("controlCitas?accion=ObtenerIdHorario&fecha=" + bloque.getAttribute("data-fecha") + "&hora=" + encodeURIComponent(bloque.getAttribute("data-hora")))
            .then(function(r) { return r.json(); })
            .then(function(data) {
                if (data && data[0] && data[0][0]) document.getElementById("idHorario").value = data[0][0];
                else document.getElementById("idHorario").value = "";
            });
    }

    function limpiarSeleccion() {
        document.getElementById("idHorario").value = "";
        document.getElementById("horaSeleccionada").textContent = "Hora seleccionada: ninguna";
    }

    function validarFormulario() {
        if (!document.getElementById("idHorario").value) { alert("Debes seleccionar una hora disponible."); return false; }
        return true;
    }
</script>

</body>
</html>
