<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.EmpleadoVeterinario"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Menú Veterinario</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg">

    <%
        EmpleadoVeterinario a = (EmpleadoVeterinario) request.getAttribute("bean");
        String nombreCompleto = a.getNombre() + " " + a.getPaterno() + " " + a.getMaterno();
        String usuarioLogin   = a.getUsuario();
        String puesto         = a.getNombrePuesto();
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
                <h1><i class="fa-solid fa-stethoscope"></i> Menú Veterinario</h1>
                <p>Sistema de gestión - Veterinaria Huellitas</p>

                <p style="color:#0077b6; font-weight:bold; font-size:16px; margin-top:10px;">
                    ¡Bienvenido, <%= nombreCompleto %>!
                </p>
                <p style="color:#023e8a; font-size:14px; margin:2px 0;">
                    Usuario: <strong><%= usuarioLogin %></strong>
                </p>
                <p style="color:#023e8a; font-size:14px; margin:2px 0;">
                    Rol: <strong>Veterinario</strong>
                </p>
                <p style="color:#023e8a; font-size:14px; margin:2px 0;">
                    Descripción: <strong><%= puesto != null ? puesto : "Sin puesto" %></strong>
                </p>
            </div>

            <hr class="admin-line">

            <!-- GRID -->
            <div class="admin-grid">

                <!-- CITAS -->
                <div class="admin-card">

                    <h2>Citas</h2>

                    <a href="controlEmpleadoVeterinario?accion=MisCitas&usr=<%= usuarioLogin %>" class="admin-btn">Consultar mis citas</a>
                    <a href="controlEmpleadoVeterinario?accion=MisCitasBaja&usr=<%= usuarioLogin %>" class="admin-btn">Cancelar / Terminar cita</a>
                    <a href="controlEmpleadoVeterinario?accion=MisCitasComprobante&usr=<%= usuarioLogin %>" class="admin-btn">Descargar comprobante</a>
                    <a href="controlReportes?reporte=MisCitasExcel&usr=<%= usuarioLogin %>" class="admin-btn" target="_blank">📗 Excel — Mis citas atendidas</a>

                </div>

                <!-- MASCOTAS -->
                <div class="admin-card">

                    <h2>Mascotas</h2>

                    <a href="ConsultaMascota.html" class="admin-btn">Consultar Mascota</a>

                </div>

                <!-- HISTORIAL MÉDICO -->
                <div class="admin-card">

                    <h2>Historial Médico</h2>

                    <a href="controlEmpleadoVeterinario?accion=RegistrarHistorial&usr=<%= usuarioLogin %>" class="admin-btn">Registrar Historial</a>
                    <a href="controlEmpleadoVeterinario?accion=ModificarHistorial&usr=<%= usuarioLogin %>" class="admin-btn">Modificar Historial</a>
                    <a href="controlEmpleadoVeterinario?accion=ConsultarHistorial&usr=<%= usuarioLogin %>" class="admin-btn">Consultar Historial</a>

                </div>

                <!-- MI PERFIL -->
                <div class="admin-card">

                    <h2>Mi Perfil</h2>

                    <a href="controlEmpleadoVeterinario?accion=MiHorario&usr=<%= usuarioLogin %>" class="admin-btn">Mi horario</a>
                    <a href="controlEmpleadoVeterinario?accion=ImprimirMiHorario&usr=<%= usuarioLogin %>" class="admin-btn">Imprimir mi horario</a>
                    <a href="controlEmpleadoVeterinario?accion=CambiarMiContrasena&usr=<%= usuarioLogin %>" class="admin-btn">Cambiar contraseña</a>

                </div>

            </div>

        </div>

    </div>

</body>
</html>
