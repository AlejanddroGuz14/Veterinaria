<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Modificar Mascota</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg body-center">

<div class="container">

    <%
        Administrador a = (Administrador) request.getAttribute("bean");
    %>

    <div class="icon"><i class="fa-solid fa-pen-to-square"></i></div>
    <h2 class="title">Modificar Mascota</h2>
    <p class="subtitle">Actualiza la información de la mascota</p>

    <form action="controlMascotas" method="post">

        <!-- ID oculto -->
        <input type="hidden" name="idMascota" value="<%= a.getIdMascota() %>">

        <!-- Nombre -->
        <label class="label">Nombre:</label>
        <input type="text" name="nombreMascota" class="input" value="<%= a.getNombreMascota() %>" required>

        <!-- Especie -->
        <label class="label">Especie:</label>
        <select name="idEspecie" class="select">
        </select>

        <!-- Raza -->
        <label class="label">Raza:</label>
        <select name="idRaza" class="select">
        </select>

        <!-- Sexo -->
        <label class="label">Sexo:</label>
        <select name="idSexo" class="select">
        </select>

        <!-- Fecha nacimiento -->
        <label class="label">Fecha de nacimiento:</label>
        <input type="date" name="fechaNacimiento" class="input" value="<%= a.getFechaNacimiento() != null ? a.getFechaNacimiento() : "" %>">

        <!-- Peso -->
        <label class="label">Peso (kg):</label>
        <input type="text" name="peso" class="input" value="<%= a.getPeso() != null ? a.getPeso() : "" %>">

        <!-- Color -->
        <label class="label">Color:</label>
        <input type="text" name="color" class="input" value="<%= a.getColor() != null ? a.getColor() : "" %>">

        <!-- Observaciones -->
        <label class="label">Observaciones:</label>
        <input type="text" name="observaciones" class="input" value="<%= a.getObservaciones() != null ? a.getObservaciones() : "" %>">

        <!-- Status -->
        <label class="label">Status:</label>
        <select name="status" class="select">
            <option value="A" <%= "A".equals(a.getStatus()) ? "selected" : "" %>>Activo</option>
            <option value="I" <%= "I".equals(a.getStatus()) ? "selected" : "" %>>Inactivo</option>
        </select>

        <input type="hidden" name="opcion" value="ModificarMascota">

        <input type="submit" name="ModificarMascotaA" value="Guardar Cambios" class="btn btn-primary">

    </form>

</div>

<script>
    const idEspecieActual = "<%= a.getIdEspecie() %>";
    const idRazaActual    = "<%= a.getIdRaza() %>";
    const idSexoActual    = "<%= a.getIdSexo() %>";

    window.onload = function () {
        cargarEspecies();
        cargarSexos();
    };

    function cargarEspecies() {
        fetch("controlMascotas?accion=CargarEspecies")
            .then(r => r.json())
            .then(data => {
                const sel = document.querySelector("select[name='idEspecie']");
                data.forEach(e => {
                    const opt = document.createElement("option");
                    opt.value = e[0];
                    opt.text  = e[1];
                    if (e[0] === idEspecieActual) opt.selected = true;
                    sel.appendChild(opt);
                });
                // Carga razas de la especie actual
                cargarRazas(idEspecieActual);
            });
    }

    function cargarRazas(idEspecie) {
        fetch("controlMascotas?accion=CargarRazas&idEspecie=" + idEspecie)
            .then(r => r.json())
            .then(data => {
                const sel = document.querySelector("select[name='idRaza']");
                sel.innerHTML = "";
                data.forEach(r => {
                    const opt = document.createElement("option");
                    opt.value = r[0];
                    opt.text  = r[1];
                    if (r[0] === idRazaActual) opt.selected = true;
                    sel.appendChild(opt);
                });
            });
    }

    function cargarSexos() {
        fetch("controlMascotas?accion=CargarSexos")
            .then(r => r.json())
            .then(data => {
                const sel = document.querySelector("select[name='idSexo']");
                data.forEach(s => {
                    const opt = document.createElement("option");
                    opt.value = s[0];
                    opt.text  = s[1];
                    if (s[0] === idSexoActual) opt.selected = true;
                    sel.appendChild(opt);
                });
            });
    }

    document.querySelector("select[name='idEspecie']").addEventListener("change", function () {
        cargarRazas(this.value);
    });
</script>

</body>
</html>
