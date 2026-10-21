<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Baja de Cita</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg body-center">

<div class="container">

    <%
        Administrador a = (Administrador) request.getAttribute("bean");
        String[][] mascotas = (String[][]) request.getAttribute("mascotas");
    %>

    <div class="icon"><i class="fa-solid fa-xmark"></i></div>
    <h2 class="title">Baja de Cita</h2>
    <p class="subtitle"><%= a.getNombre() %> <%= a.getPaterno() %> <%= a.getMaterno() %> — <%= a.getUsuario() %></p>

    <form action="controlCitas" method="post" id="formBaja">

        <!-- Usuario oculto -->
        <input type="hidden" name="usuarioCliente" value="<%= a.getUsuario() %>">

        <!-- SELECTOR DE MASCOTA -->
        <label class="label">Mascota:</label>
        <select name="idMascota" id="selMascota" class="select" required onchange="cargarCitasMascota()">
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

        <!-- SELECTOR DE CITAS (se llena por JS) -->
        <div id="seccionCitas" style="display:none;">

            <label class="label">Citas pendientes:</label>
            <select name="idCita" id="selCita" class="select" required onchange="mostrarDetalle()">
                <option value="">-- Selecciona una cita --</option>
            </select>

            <!-- DETALLE DE LA CITA -->
            <div id="detalleCita" style="display:none; margin-top:15px; padding:15px;
                 border:2px solid #90e0ef; border-radius:10px; background:#f0f9ff;">
                <p style="margin:4px 0; color:#023e8a;"><strong>Veterinario:</strong> <span id="detVet"></span></p>
                <p style="margin:4px 0; color:#023e8a;"><strong>Fecha:</strong> <span id="detFecha"></span></p>
                <p style="margin:4px 0; color:#023e8a;"><strong>Hora:</strong> <span id="detHora"></span></p>
                <p style="margin:4px 0; color:#023e8a;"><strong>Motivo:</strong> <span id="detMotivo"></span></p>
            </div>

            <!-- SELECTOR DE ACCIÓN -->
            <label class="label">Acción:</label>
            <select name="estadoCita" class="select" required>
                <option value="C"><i class="fa-solid fa-xmark"></i> Cancelar cita</option>
                <option value="T">✅ Marcar como terminada</option>
            </select>

            <br>
            <input type="submit" name="CancelarCitaA" value="Confirmar" class="btn btn-primary"
                   onclick="return confirmarBaja()">
        </div>

        <div id="msgNoCitas" style="display:none; color:#555; text-align:center; margin-top:15px;">
            Esta mascota no tiene citas pendientes.
        </div>

    </form>

    <div class="back mt-15">
        <a href="CancelarCita.html" class="link">⬅ Nueva búsqueda</a>
    </div>

</div>

<script>
    let citasData = [];
    const usuarioCliente = "<%= a.getUsuario() %>";

    function cargarCitasMascota() {
        const idMascota = document.getElementById("selMascota").value;
        const seccion   = document.getElementById("seccionCitas");
        const msgNada   = document.getElementById("msgNoCitas");
        const sel       = document.getElementById("selCita");

        sel.innerHTML = "<option value=''>-- Selecciona una cita --</option>";
        document.getElementById("detalleCita").style.display = "none";
        seccion.style.display = "none";
        msgNada.style.display = "none";

        if (!idMascota) return;

        fetch("controlCitas?accion=CargarCitasMascota&usr=" +
              encodeURIComponent(usuarioCliente) + "&idMascota=" + idMascota)
            .then(r => r.json())
            .then(data => {
                if (!data || data.length === 0) {
                    msgNada.style.display = "block";
                    return;
                }

                citasData = data;
                seccion.style.display = "block";

                data.forEach(c => {
                    // c = [idCita, veterinario, fecha, hora, motivo]
                    const opt = document.createElement("option");
                    opt.value = c[0];
                    opt.text  = "Cita #" + c[0] + " — " + c[2] + " " + c[3].substring(0,5) + " | " + c[4];
                    sel.appendChild(opt);
                });
            });
    }

    function mostrarDetalle() {
        const idCita = document.getElementById("selCita").value;
        if (!idCita) {
            document.getElementById("detalleCita").style.display = "none";
            return;
        }
        const cita = citasData.find(c => c[0] === idCita);
        if (!cita) return;

        document.getElementById("detVet").textContent    = cita[1] || "—";
        document.getElementById("detFecha").textContent  = cita[2] || "—";
        document.getElementById("detHora").textContent   = cita[3] ? cita[3].substring(0,5) : "—";
        document.getElementById("detMotivo").textContent = cita[4] || "—";
        document.getElementById("detalleCita").style.display = "block";
    }

    function confirmarBaja() {
        const sel    = document.getElementById("selCita");
        const estado = document.querySelector("select[name='estadoCita']").value;
        if (!sel.value) {
            alert("Selecciona una cita.");
            return false;
        }
        const accion = estado === "T" ? "marcar como terminada" : "cancelar";
        return confirm("¿Estás seguro de que deseas " + accion + " esta cita?");
    }
</script>

</body>
</html>
