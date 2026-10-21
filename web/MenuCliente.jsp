<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Cliente"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Menú Cliente</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg">

    <%
        Cliente a = (Cliente) request.getAttribute("bean");
        String nombreCompleto = a.getNombre() + " " + a.getPaterno() + " " + a.getMaterno();
        String usuarioLogin   = a.getUsuario();
    %>

    <!-- PANEL BLANCO -->
    <div class="main-panel" style="position:relative;">

        <!-- BOTÓN CERRAR SESIÓN -->
        <a href="index.html" style="position:absolute; top:15px; right:20px; background:#e63946; color:white; padding:8px 16px; border-radius:8px; text-decoration:none; font-weight:bold; font-size:13px; transition:0.3s;">
            <i class="fa-solid fa-right-from-bracket"></i> Cerrar sesión
        </a>

        <div class="admin-container">

            <!-- HEADER -->
            <div class="admin-header">
                <h1><i class="fa-solid fa-paw"></i> Menú Cliente</h1>
                <p>Sistema de gestión - Veterinaria Huellitas</p>

                <p style="color:#0077b6; font-weight:bold; font-size:16px; margin-top:10px;">
                    ¡Bienvenido, <%= nombreCompleto %>!
                </p>
                <p style="color:#023e8a; font-size:14px; margin:2px 0;">
                    Usuario: <strong><%= usuarioLogin %></strong>
                </p>
                <p style="color:#023e8a; font-size:14px; margin:2px 0;">
                    Rol: <strong>Cliente</strong>
                </p>
            </div>

            <hr class="admin-line">

            <!-- GRID -->
            <div class="admin-grid">

                <!-- MIS MASCOTAS -->
                <div class="admin-card">

                    <h2>Mis Mascotas</h2>

                    <a href="controlCliente?accion=RegistrarMascota&usr=<%= usuarioLogin %>" class="admin-btn">Registrar Mascota</a>
                    <a href="controlCliente?accion=MisMascotas&usr=<%= usuarioLogin %>" class="admin-btn">Consultar mis mascotas</a>

                </div>

                <!-- MIS CITAS -->
                <div class="admin-card">

                    <h2>Mis Citas</h2>

                    <a href="controlCliente?accion=AgendarCita&usr=<%= usuarioLogin %>" class="admin-btn">Agendar Cita</a>
                    <a href="controlCliente?accion=MisCitas&usr=<%= usuarioLogin %>" class="admin-btn">Consultar mis citas</a>
                    <a href="controlCliente?accion=CancelarMiCita&usr=<%= usuarioLogin %>" class="admin-btn">Cancelar Cita</a>
                    <a href="controlCliente?accion=MiComprobante&usr=<%= usuarioLogin %>" class="admin-btn">Descargar Comprobante</a>

                </div>

                <!-- MI PERFIL -->
                <div class="admin-card">

                    <h2>Mi Perfil</h2>

                    <a href="controlCliente?accion=MiPerfil&usr=<%= usuarioLogin %>" class="admin-btn">Ver mis datos</a>
                    <a href="controlCliente?accion=CambiarMiContrasena&usr=<%= usuarioLogin %>" class="admin-btn">Cambiar contraseña</a>

                </div>

            </div>

        </div>

    </div>

</body>
</html>
