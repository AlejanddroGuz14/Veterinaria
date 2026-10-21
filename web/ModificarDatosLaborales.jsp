<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Modificar Datos Laborales</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg body-center">

<div class="container-large">

    <%
        Administrador a = (Administrador) request.getAttribute("bean");
    %>

    <div class="icon"><i class="fa-solid fa-pen-to-square"></i></div>
    <h2 class="title">Modificar Datos Laborales</h2>
    <p class="subtitle">Actualiza la información laboral del empleado</p>

    <form action="controlEmpleados" method="post" id="formModificar">

        <!-- IDs ocultos -->
        <input type="hidden" name="idEmpleado" value="<%= a.getIdEmpleado() %>">

        <!-- Empleado (solo lectura) -->
        <label class="label">Empleado:</label>
        <input type="text" class="input" value="<%= a.getNombre() %> <%= a.getPaterno() %> <%= a.getMaterno() %>" readonly>

        <label class="label">Usuario:</label>
        <input type="text" class="input" value="<%= a.getUsuario() %>" readonly>

        <!-- Puesto -->
        <label class="label">Puesto:</label>
        <select name="idPuesto" class="select" required>
        </select>

        <!-- Turno -->
        <label class="label">Turno:</label>
        <select name="idTurno" class="select" required>
        </select>

        <!-- Salario -->
        <label class="label">Salario:</label>
        <input type="text" name="salario" class="input"
               value="<%= a.getSalario() != null ? a.getSalario() : "" %>" required>

        <!-- Status -->
        <label class="label">Status:</label>
        <select name="status" class="select">
            <option value="A" <%= "A".equals(a.getStatus()) ? "selected" : "" %>>Activo</option>
            <option value="I" <%= "I".equals(a.getStatus()) ? "selected" : "" %>>Inactivo</option>
        </select>

    </form>

    <!-- ── CALENDARIO SEMANAL ── -->
    <%
        String[][] horario = (String[][]) request.getAttribute("horarioSemanal");
        String diasActivosStr = "";
        String horaEntradaActual = "08:00";
        String horaSalidaActual  = "16:00";
        if (horario != null && horario.length > 0) {
            StringBuilder sbDias = new StringBuilder();
            for (String[] fila : horario) {
                if (sbDias.length() > 0) sbDias.append(",");
                sbDias.append(fila[0]);
            }
            diasActivosStr   = sbDias.toString();
            horaEntradaActual = horario[0][2].substring(0, 5);
            horaSalidaActual  = horario[0][3].substring(0, 5);
        }
    %>
    <hr style="border-color:#90e0ef; margin:20px 0;">
    <h3 class="title" style="font-size:18px;"><i class="fa-solid fa-calendar-days"></i> Horario Semanal</h3>
    <p class="subtitle">Haz clic en un día para seleccionarlo o deseleccionarlo</p>

    <div id="semanaGrid" style="display:grid; grid-template-columns:repeat(7,1fr); gap:6px; margin-top:10px;"></div>

    <div style="display:flex; gap:15px; margin-top:10px; font-size:12px; align-items:center;">
        <div style="display:flex; align-items:center; gap:5px;">
            <div style="width:16px; height:16px; border-radius:4px; background:#00b4d8;"></div>
            <span>Horas de trabajo</span>
        </div>
        <div style="display:flex; align-items:center; gap:5px;">
            <div style="width:16px; height:16px; border-radius:4px; background:#f0f9ff; border:1px solid #90e0ef;"></div>
            <span>Fuera de turno</span>
        </div>
    </div>

    <div id="diasSeleccionados" style="margin-top:10px; font-size:13px; color:#023e8a; font-weight:bold;">
        Días seleccionados: ninguno
    </div>

    <input type="hidden" name="diasLaborales"    id="diasLaborales"    form="formModificar">
    <input type="hidden" name="horaEntradaTurno" id="horaEntradaTurno" form="formModificar" value="<%= horaEntradaActual %>">
    <input type="hidden" name="horaSalidaTurno"  id="horaSalidaTurno"  form="formModificar" value="<%= horaSalidaActual %>">

    <br>
    <input type="submit" name="ModificarDatosLaboralesA" value="Guardar Cambios" class="btn btn-primary" form="formModificar">

    <div class="back mt-15">
        <a href="ModificarDatosLaborales.html" class="link">⬅ Nueva búsqueda</a>
    </div>

</div>

<script>
    // Valores actuales del empleado para preseleccionar los selects
    const idPuestoActual = "<%= a.getIdPuesto() %>";
    const idTurnoActual  = "<%= a.getIdTurno() %>";

    window.onload = function () {
        cargarPuestos();
        cargarTurnos();
    };

    function cargarPuestos() {
        fetch("controlEmpleados?accion=CargarPuestos")
            .then(r => r.json())
            .then(data => {
                const sel = document.querySelector("select[name='idPuesto']");
                data.forEach(p => {
                    const opt = document.createElement("option");
                    opt.value = p[0];
                    opt.text  = p[1];
                    if (p[0] === idPuestoActual) opt.selected = true;
                    sel.appendChild(opt);
                });
            });
    }

    var turnosData = {};

    function cargarTurnos() {
        fetch("controlEmpleados?accion=CargarTurnos")
            .then(r => r.json())
            .then(data => {
                const sel = document.querySelector("select[name='idTurno']");
                data.forEach(t => {
                    const opt = document.createElement("option");
                    opt.value = t[0];
                    opt.text  = t[1];
                    opt.dataset.entrada = t[2];
                    opt.dataset.salida  = t[3];
                    if (t[0] === idTurnoActual) opt.selected = true;
                    turnosData[t[0]] = { entrada: t[2], salida: t[3] };
                    sel.appendChild(opt);
                });
                // Agregar evento de cambio de turno
                sel.addEventListener("change", onTurnoChange);
            });
    }

    function onTurnoChange() {
        const sel = document.querySelector("select[name='idTurno']");
        const opt = sel.options[sel.selectedIndex];
        if (opt && opt.dataset.entrada) {
            var entrada = opt.dataset.entrada.substring(0, 5);
            var salida  = opt.dataset.salida.substring(0, 5);
            document.getElementById('horaEntradaTurno').value = entrada;
            document.getElementById('horaSalidaTurno').value  = salida;
            // Reconstruir calendario con nuevas horas
            reconstruirCalendario(entrada, salida);
        }
    }

    function reconstruirCalendario(entrada, salida) {
        var hEntrada = parseInt(entrada.split(':')[0]);
        var hSalida  = parseInt(salida.split(':')[0]);
        var H_INICIO = 6;
        var H_FIN    = 22;

        var grid = document.getElementById('semanaGrid');
        grid.innerHTML = '';

        DIAS.forEach(function(dia, idx) {
            var numDia = idx + 1;
            var activo = diasActivos.has(numDia);

            var col = document.createElement('div');
            col.id = 'dia-' + numDia;
            col.style.cssText = 'border:2px solid #90e0ef; border-radius:10px; overflow:hidden;';

            var header = document.createElement('div');
            header.id = 'header-' + numDia;
            header.textContent = dia;
            header.style.cssText = 'background:' + (activo ? '#023e8a' : '#0077b6') + '; color:white; text-align:center; padding:8px 4px; font-weight:bold; font-size:13px; cursor:pointer; user-select:none;';
            header.onclick = (function(n, hE, hS, hI, hF) { return function() { toggleDia(n, hE, hS, hI, hF); }; })(numDia, hEntrada, hSalida, H_INICIO, H_FIN);

            var horas = document.createElement('div');
            horas.id = 'horas-' + numDia;
            horas.style.cssText = 'display:' + (activo ? 'flex' : 'none') + '; flex-direction:column;';

            for (var h = H_INICIO; h < H_FIN; h++) {
                var trabajando = (h >= hEntrada && h < hSalida);
                var ampm = h >= 12 ? 'PM' : 'AM';
                var h12  = h > 12 ? h - 12 : (h === 0 ? 12 : h);
                var bloque = document.createElement('div');
                bloque.textContent = h12 + ':00 ' + ampm;
                bloque.style.cssText = 'padding:4px 6px; font-size:11px; text-align:center; border-top:1px solid #e0f7fa;' +
                    'background:' + (trabajando ? '#00b4d8' : (h % 2 === 0 ? '#f0f9ff' : '#fff')) + ';' +
                    'color:' + (trabajando ? 'white' : '#023e8a') + ';' +
                    'font-weight:' + (trabajando ? 'bold' : 'normal') + ';';
                horas.appendChild(bloque);
            }

            col.appendChild(header);
            col.appendChild(horas);
            grid.appendChild(col);
        });

        actualizarResumen();
    }

    // ── CALENDARIO INTERACTIVO ──
    const DIAS = ['Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'];
    let diasActivos = new Set();

    // Días y horas precargados desde el servidor
    const diasPrecargados = "<%= diasActivosStr %>".trim();
    const horaEntradaBase = "<%= horaEntradaActual %>";
    const horaSalidaBase  = "<%= horaSalidaActual %>";

    window.onload = function () {
        cargarPuestos();
        cargarTurnos();
        construirCalendario();
    };

    function construirCalendario() {
        var hEntrada = parseInt(horaEntradaBase.split(':')[0]);
        var hSalida  = parseInt(horaSalidaBase.split(':')[0]);
        var H_INICIO = 6;
        var H_FIN    = 22;

        // Precargar días activos
        if (diasPrecargados !== "") {
            diasPrecargados.split(',').forEach(d => diasActivos.add(parseInt(d)));
        }

        var grid = document.getElementById('semanaGrid');
        grid.innerHTML = '';

        DIAS.forEach(function(dia, idx) {
            var numDia = idx + 1;
            var activo = diasActivos.has(numDia);

            var col = document.createElement('div');
            col.id = 'dia-' + numDia;
            col.style.cssText = 'border:2px solid #90e0ef; border-radius:10px; overflow:hidden;';

            var header = document.createElement('div');
            header.id = 'header-' + numDia;
            header.textContent = dia;
            header.style.cssText = 'background:' + (activo ? '#023e8a' : '#0077b6') + '; color:white; text-align:center; padding:8px 4px; font-weight:bold; font-size:13px; cursor:pointer; user-select:none;';
            header.onclick = (function(n, hE, hS, hI, hF) { return function() { toggleDia(n, hE, hS, hI, hF); }; })(numDia, hEntrada, hSalida, H_INICIO, H_FIN);

            var horas = document.createElement('div');
            horas.id = 'horas-' + numDia;
            horas.style.cssText = 'display:' + (activo ? 'flex' : 'none') + '; flex-direction:column;';

            for (var h = H_INICIO; h < H_FIN; h++) {
                var trabajando = (h >= hEntrada && h < hSalida);
                var ampm = h >= 12 ? 'PM' : 'AM';
                var h12  = h > 12 ? h - 12 : (h === 0 ? 12 : h);
                var bloque = document.createElement('div');
                bloque.textContent = h12 + ':00 ' + ampm;
                bloque.style.cssText = 'padding:4px 6px; font-size:11px; text-align:center; border-top:1px solid #e0f7fa;' +
                    'background:' + (trabajando ? '#00b4d8' : (h % 2 === 0 ? '#f0f9ff' : '#fff')) + ';' +
                    'color:' + (trabajando ? 'white' : '#023e8a') + ';' +
                    'font-weight:' + (trabajando ? 'bold' : 'normal') + ';';
                horas.appendChild(bloque);
            }

            col.appendChild(header);
            col.appendChild(horas);
            grid.appendChild(col);
        });

        actualizarResumen();
    }

    function toggleDia(numDia, hEntrada, hSalida, H_INICIO, H_FIN) {
        const header = document.getElementById('header-' + numDia);
        const horas  = document.getElementById('horas-' + numDia);

        if (diasActivos.has(numDia)) {
            diasActivos.delete(numDia);
            header.style.background = '#0077b6';
            horas.style.display = 'none';
        } else {
            diasActivos.add(numDia);
            header.style.background = '#023e8a';
            horas.style.display = 'flex';
        }
        actualizarResumen();
    }

    function actualizarResumen() {
        const nombres = [...diasActivos].sort().map(d => DIAS[d - 1]);
        const resumen = document.getElementById('diasSeleccionados');
        resumen.textContent = diasActivos.size === 0
            ? 'Días seleccionados: ninguno'
            : 'Días seleccionados: ' + nombres.join(', ');
        document.getElementById('diasLaborales').value = [...diasActivos].sort().join(',');
    }
</script>

</body>
</html>
