<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Cliente"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar Mascota</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body class="body-bg body-center">

<%
    Cliente a = (Cliente) request.getAttribute("bean");
%>

<div class="container">

    <div class="icon"><i class="fa-solid fa-paw"></i></div>
    <h2 class="title">Registrar Mascota</h2>
    <p class="subtitle">Ingresa los datos de tu mascota</p>

    <form action="controlCliente" method="post">
        <input type="hidden" name="usr" value="<%= a.getUsuario() %>">

        <label class="label">Nombre de la mascota:</label>
        <input type="text" name="nombreMascota" class="input" required>

        <label class="label">Especie:</label>
        <select name="idEspecie" id="selEspecie" class="select" required>
            <option value="">-- Selecciona especie --</option>
        </select>

        <label class="label">Raza:</label>
        <select name="idRaza" id="selRaza" class="select" required>
            <option value="">-- Selecciona raza --</option>
        </select>

        <label class="label">Sexo:</label>
        <select name="idSexo" id="selSexo" class="select" required>
            <option value="">-- Selecciona sexo --</option>
        </select>

        <label class="label">Fecha de nacimiento:</label>
        <input type="date" name="fechaNacimiento" class="input">

        <label class="label">Peso (kg):</label>
        <input type="text" name="peso" class="input" placeholder="Ej. 5.50">

        <label class="label">Color:</label>
        <input type="text" name="color" class="input">

        <label class="label">Observaciones:</label>
        <input type="text" name="observaciones" class="input">

        <input type="submit" name="RegistrarMascotaCliente" value="Registrar" class="btn btn-primary">
    </form>

</div>

<script>
    window.onload = function() {
        fetch("controlMascotas?accion=CargarEspecies")
            .then(function(r) { return r.json(); })
            .then(function(data) {
                var sel = document.getElementById("selEspecie");
                data.forEach(function(e) {
                    var opt = document.createElement("option");
                    opt.value = e[0]; opt.text = e[1];
                    sel.appendChild(opt);
                });
            });
        fetch("controlMascotas?accion=CargarSexos")
            .then(function(r) { return r.json(); })
            .then(function(data) {
                var sel = document.getElementById("selSexo");
                data.forEach(function(s) {
                    var opt = document.createElement("option");
                    opt.value = s[0]; opt.text = s[1];
                    sel.appendChild(opt);
                });
            });
    };

    document.getElementById("selEspecie").addEventListener("change", function() {
        var idEspecie = this.value;
        var selRaza = document.getElementById("selRaza");
        selRaza.innerHTML = "<option value=''>-- Selecciona raza --</option>";
        if (idEspecie) {
            fetch("controlMascotas?accion=CargarRazas&idEspecie=" + idEspecie)
                .then(function(r) { return r.json(); })
                .then(function(data) {
                    data.forEach(function(r) {
                        var opt = document.createElement("option");
                        opt.value = r[0]; opt.text = r[1];
                        selRaza.appendChild(opt);
                    });
                });
        }
    });
</script>

</body>
</html>
