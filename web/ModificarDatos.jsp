<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>

<jsp:useBean id="a" class="bean.Administrador" scope="request">
    <jsp:setProperty name="a" property="usuario" param="usuario"/>
    <jsp:setProperty name="a" property="opcion" value="CargarUsuario"/>
</jsp:useBean>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modificar Usuario</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg body-center">

<div class="container">

    <div class="icon"><i class="fa-solid fa-pen-to-square"></i></div>
    <h2 class="title">Modificar Usuario</h2>
    <p class="subtitle">Actualiza la información del usuario</p>

    <form action="controlUsuarios" method="post">

        <!-- ID -->
        <input type="hidden" name="idCliente" value="<%=a.getIdCliente()%>">

        <!-- Usuario -->
        <label class="label">Usuario:</label>
        <input type="text" name="usuario" class="input" value="<%=a.getUsuario()%>" readonly>

        <!-- Nombre -->
        <label class="label">Nombre:</label>
        <input type="text" name="nombre" class="input" value="<%=a.getNombre()%>">

        <!-- Paterno -->
        <label class="label">Paterno:</label>
        <input type="text" name="paterno" class="input" value="<%=a.getPaterno()%>">

        <!-- Materno -->
        <label class="label">Materno:</label>
        <input type="text" name="materno" class="input" value="<%=a.getMaterno()%>">

        <!-- Email -->
        <label class="label">Email:</label>
        <input type="email" name="mail" class="input" value="<%=a.getMail()%>">

        <!-- Teléfono -->
        <label class="label">Teléfono:</label>
        <input type="text" name="telefono" class="input" value="<%=a.getTelefono()%>">

        <!-- Tipo -->
        <label class="label">Tipo:</label>
        <select name="tipo" class="select">
            <option value="A" <%= "A".equals(a.getTipo()) ? "selected" : "" %>>Administrador</option>
            <option value="C" <%= "C".equals(a.getTipo()) ? "selected" : "" %>>Cliente</option>
            <option value="E" <%= "E".equals(a.getTipo()) ? "selected" : "" %>>Empleado</option>
            <option value="I" <%= "I".equals(a.getTipo()) ? "selected" : "" %>>Invitado</option>
        </select>

        <!-- Status -->
        <label class="label">Status:</label>
        <select name="status" class="select">
            <option value="A" <%= "A".equals(a.getStatus()) ? "selected" : "" %>>Activo</option>
            <option value="I" <%= "I".equals(a.getStatus()) ? "selected" : "" %>>Inactivo</option>
        </select>

        <!-- OPCIÓN -->
        <input type="hidden" name="opcion" value="ActualizarUsuario">

        <!-- BOTÓN -->
        <input type="submit" name="ModificarUsuarioA" value="Guardar Cambios" class="btn btn-primary">

    </form>

</div>

</body>
</html>