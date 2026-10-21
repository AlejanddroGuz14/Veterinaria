<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Consulta de Citas</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <style>
        .cita-card {
            border: 2px solid #90e0ef;
            border-radius: 12px;
            padding: 20px;
            margin-top: 20px;
            background: #f0f9ff;
        }
        .cita-card h4 { color: #023e8a; margin: 0 0 10px 0; }
        .cita-info {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 6px 20px;
            font-size: 14px;
            color: #023e8a;
        }
        .cita-info p { margin: 3px 0; }
        .cita-info strong { color: #0077b6; }
        .hora-col {
            display: inline-flex;
            flex-direction: column;
            align-items: center;
            margin-top: 10px;
        }
        .hora-col .dia-titulo {
            background: #023e8a;
            color: white;
            padding: 6px 20px;
            border-radius: 8px 8px 0 0;
            font-weight: bold;
            font-size: 13px;
            text-align: center;
            min-width: 120px;
        }
        .hora-col .horas-lista {
            border: 2px solid #90e0ef;
            border-top: none;
            border-radius: 0 0 8px 8px;
            min-width: 120px;
            overflow: hidden;
        }
        .hora-item {
            padding: 5px 10px;
            font-size: 12px;
            text-align: center;
            border-bottom: 1px solid #e0f7fa;
            background: #f0f9ff;
            color: #aaa;
        }
        .hora-item.activa {
            background: #00b4d8;
            color: white;
            font-weight: bold;
        }
    </style>
</head>

<body class="body-bg body-center">

<div class="container">

    <%
        Administrador a = (Administrador) request.getAttribute("bean");
        String[][] mascotas = (String[][]) request.getAttribute("mascotas");
    %>

    <div class="icon"><i class="fa-solid fa-magnifying-glass"></i></div>
    <h2 class="title">Consultar Citas</h2>
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
        <select id="selCita" class="select" onchange="mostrarCita()">
            <option value="">-- Selecciona una cita --</option>
        </select>
    </div>

    <div id="msgNada" style="display:none; color:#555; text-align:center; margin-top:15px;">
        Esta mascota no tiene citas pendientes.
    </div>

    <!-- DETALLE DE LA CITA -->
    <div id="detalleCita" style="display:none;"></div>

    <div class="back mt-15">
        <a href="ConsultaCita.html" class="link">⬅ Nueva búsqueda</a>
    </div>

</div>

<script>
    const DIAS = ['','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'];
    const usuarioCliente = "<%= a.getUsuario() %>";
    let citasData = [];

    function cargarCitas() {
        const idMascota = document.getElementById("selMascota").value;
        document.getElementById("seccionCita").style.display = "none";
        document.getElementById("detalleCita").style.display = "none";
        document.getElementById("msgNada").style.display     = "none";

        if (!idMascota) return;

        // Usa el mismo endpoint que la baja — solo trae pendientes
        fetch("controlCitas?accion=CargarCitasMascota&idMascota=" + idMascota)
            .then(r => r.json())
            .then(data => {
                citasData = data || [];
                if (citasData.length === 0) {
                    document.getElementById("msgNada").style.display = "block";
                    return;
                }

                const sel = document.getElementById("selCita");
                sel.innerHTML = "<option value=''>-- Selecciona una cita --</option>";
                // data[i] = [idCita, veterinario, fecha, hora, motivo]
                citasData.forEach(c => {
                    const opt = document.createElement("option");
                    opt.value = c[0];
                    opt.text  = "Cita #" + c[0] + " — " + c[2] + " " + c[3].substring(0,5) + " | " + c[4];
                    sel.appendChild(opt);
                });
                document.getElementById("seccionCita").style.display = "block";
            });
    }

    function mostrarCita() {
        const idCita = document.getElementById("selCita").value;
        document.getElementById("detalleCita").style.display = "none";

        if (!idCita) return;

        const c = citasData.find(x => x[0] === idCita);
        if (!c) return;

        const veterinario = c[1] || "—";
        const fecha       = c[2] || "—";
        const hora        = c[3] || "00:00:00";
        const motivo      = c[4] || "—";
        const horaCita    = parseInt(hora.substring(0,2));

        // Calcular día de la semana desde la fecha
        const fechaObj = new Date(fecha + "T12:00:00");
        const jsDay    = fechaObj.getDay();
        const diaSemana = jsDay === 0 ? 7 : jsDay;
        const nombreDia = DIAS[diaSemana];

        var horasHTML = "";
        for (var h = 6; h < 22; h++) {
            var ampm = h >= 12 ? "PM" : "AM";
            var h12  = h > 12 ? h - 12 : (h === 0 ? 12 : h);
            var etiq = h12 + ":00 " + ampm;
            var activa = (h === horaCita) ? "activa" : "";
            horasHTML += "<div class='hora-item " + activa + "'>" + etiq + "</div>";
        }

        var html = "<div class='cita-card'>" +
            "<h4>Cita #" + c[0] + " — <span style='color:#0077b6; font-weight:bold;'>Pendiente</span></h4>" +
            "<div class='cita-info'>" +
            "<p><strong>Veterinario:</strong> " + veterinario + "</p>" +
            "<p><strong>Fecha:</strong> " + fecha + "</p>" +
            "<p><strong>Motivo:</strong> " + motivo + "</p>" +
            "</div>" +
            "<div class='hora-col'>" +
            "<div class='dia-titulo'>" + nombreDia + " — " + fecha + "</div>" +
            "<div class='horas-lista'>" + horasHTML + "</div>" +
            "</div>" +
            "</div>";

        document.getElementById("detalleCita").innerHTML = html;
        document.getElementById("detalleCita").style.display = "block";
    }
</script>

</body>
</html>
