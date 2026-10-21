<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Administrador"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Menú Administrador</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <link rel="stylesheet" href="style.css">
</head>

<body class="body-bg">

    <%
        Administrador a = (Administrador) request.getAttribute("bean");
        String nombreCompleto = a.getNombre() + " " + a.getPaterno() + " " + a.getMaterno();
        String usuarioLogin   = a.getUsuario();
        String tipo           = a.getTipo();
        String rolTexto       = "A".equals(tipo) ? "Administrador" : "Empleado";
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
                <h1><i class="fa-solid fa-paw"></i> Menú Administrador</h1>
                <p>Sistema de gestión - Veterinaria Huellitas</p>

                <!-- BIENVENIDA -->
                <p style="color:#0077b6; font-weight:bold; font-size:16px; margin-top:10px;">
                    ¡Bienvenido, <%= nombreCompleto %>!
                </p>
                <p style="color:#023e8a; font-size:14px; margin:2px 0;">
                    Usuario: <strong><%= usuarioLogin %></strong>
                </p>
                <p style="color:#023e8a; font-size:14px; margin:2px 0;">
                    Rol: <strong><%= rolTexto %></strong>
                </p>
                <p style="color:#023e8a; font-size:14px; margin:2px 0;">
                    Descripción: <strong><%= puesto != null ? puesto : "Sin puesto" %></strong>
                </p>
            </div>

            <hr class="admin-line">

            <!-- GRID -->
            <div class="admin-grid">

                <!-- USUARIOS -->
                <div class="admin-card">

                    <h2>Usuarios</h2>

                    <a href="RegistroUsuarios.html" class="admin-btn">Registro de Usuario</a>
                    <a href="BajaUsuario.html" class="admin-btn">Eliminar Usuario</a>
                    <a href="ModificaDatos.html" class="admin-btn">Modificar Datos</a>
                    <a href="CambiarContrasena.html" class="admin-btn">Cambiar contraseña</a>

                    <!-- DROPDOWN -->
                    <div class="dropdown">

                        <button class="admin-btn" onclick="toggleMenu(event)">
                            Consultar Usuario ▸
                        </button>

                        <div class="dropdown-menu" id="menuConsulta">
                            <form action="controlUsuarios" method="post" style="margin:0;">
                                <input type="hidden" name="ConsultaGeneralA" value="1">
                                <button type="submit" style="background:none; border:none; width:100%; text-align:left; padding:10px 15px; color:#023e8a; font-weight:bold; cursor:pointer;">
                                    Consulta general
                                </button>
                            </form>
                            <a href="ConsultaUsuarios.html">Consulta individual</a>
                        </div>

                    </div>

                </div>

                <!-- MASCOTAS -->
                <div class="admin-card">

                    <h2>Mascotas</h2>

                    <a href="RegistroMascota.html" class="admin-btn">Registrar Mascota</a>
                    <a href="BajaMascota.html" class="admin-btn">Baja de Mascota</a>
                    <a href="ModificaMascota.html" class="admin-btn">Modificar Mascota</a>

                    <!-- DROPDOWN -->
                    <div class="dropdown">

                        <button class="admin-btn" onclick="toggleMenuM(event)">
                            Consultar Mascota ▸
                        </button>

                        <div class="dropdown-menu" id="menuConsultaM">
                            <form action="controlMascotas" method="post" style="margin:0;">
                                <input type="hidden" name="ConsultaGeneralM" value="1">
                                <button type="submit">Consulta general</button>
                            </form>
                            <a href="ConsultaMascota.html">Consulta individual</a>
                        </div>

                    </div>

                </div>

                <!-- EMPLEADOS -->
                <div class="admin-card">

                    <h2>Empleados</h2>

                    <a href="AsignarDatosLaborales.html" class="admin-btn">Asignar Datos Laborales</a>
                    <a href="ModificarDatosLaborales.html" class="admin-btn">Modificar Datos Laborales</a>
                    <a href="ConsultaDatosLaborales.html" class="admin-btn">Consultar Datos Laborales</a>
                    <a href="ImprimirHorario.html" class="admin-btn">Imprimir Horario</a>

                </div>

                <!-- CITAS -->
                <div class="admin-card">

                    <h2>Citas</h2>

                    <a href="RegistrarCita.html" class="admin-btn">Registrar Cita</a>
                    <a href="CancelarCita.html" class="admin-btn">Cancelar Cita</a>
                    <a href="ModificarCita.html" class="admin-btn">Modificar Cita</a>

                    <!-- DROPDOWN -->
                    <div class="dropdown">

                        <button class="admin-btn" onclick="toggleMenuC(event)">
                            Consultar Cita ▸
                        </button>

                        <div class="dropdown-menu" id="menuConsultaC">
                            <a href="ConsultaCita.html">Consultar Cita</a>
                            <a href="DescargarComprobante.html">Descargar Comprobante de Cita</a>
                        </div>

                    </div>

                    <!-- DROPDOWN HORARIOS -->
                    <div class="dropdown">

                        <button class="admin-btn" onclick="toggleMenuH(event)">
                            Horarios ▸
                        </button>

                        <div class="dropdown-menu" id="menuHorarios">
                            <a href="HorarioGeneral.html">Horario general de atención</a>
                            <a href="HorarioDisponible.html">Consultar horario disponible</a>
                        </div>

                    </div>

                </div>

                <!-- REPORTES -->
                <div class="admin-card">

                    <h2>Reportes</h2>

                    <a href="Reportes.html" class="admin-btn"><i class="fa-solid fa-chart-bar"></i> Generar Reportes</a>

                </div>

            </div>

        </div>

    </div>

    <!-- SCRIPT -->
    <script>
        function toggleMenu(event) {
            event.stopPropagation();
            const menu = document.getElementById("menuConsulta");
            menu.style.display = menu.style.display === "block" ? "none" : "block";
        }

        function toggleMenuM(event) {
            event.stopPropagation();
            const menu = document.getElementById("menuConsultaM");
            menu.style.display = menu.style.display === "block" ? "none" : "block";
        }

        function toggleMenuC(event) {
            event.stopPropagation();
            const menu = document.getElementById("menuConsultaC");
            menu.style.display = menu.style.display === "block" ? "none" : "block";
        }

        function toggleMenuH(event) {
            event.stopPropagation();
            const menu = document.getElementById("menuHorarios");
            menu.style.display = menu.style.display === "block" ? "none" : "block";
        }

        function toggleMenuHL(event) {
            event.stopPropagation();
            const menu = document.getElementById("menuHL");
            menu.style.display = menu.style.display === "block" ? "none" : "block";
        }

        function toggleMenuCatE(event) {
            event.stopPropagation();
            const menu = document.getElementById("menuCatE");
            menu.style.display = menu.style.display === "block" ? "none" : "block";
        }

        window.onclick = function () {
            document.getElementById("menuConsulta").style.display  = "none";
            document.getElementById("menuConsultaM").style.display = "none";
            document.getElementById("menuConsultaC").style.display = "none";
            document.getElementById("menuHorarios").style.display  = "none";
            document.getElementById("menuHL").style.display        = "none";
            document.getElementById("menuCatE").style.display      = "none";
        }
    </script>

</body>

</html>
