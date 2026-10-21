<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.Invitado"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Veterinaria Huellitas Felices</title>
    <link rel="stylesheet" href="fontawesome-free-7.2.0-web/css/all.min.css">
    <style>
        *{ margin:0; padding:0; box-sizing:border-box; font-family:Arial,sans-serif; }
        body{ background-color:#f4f9f9; color:#333; }
        header{ background:linear-gradient(90deg,#2ec4b6,#0096c7); color:white; padding:20px; text-align:center; }
        header h1{ font-size:40px; margin-bottom:10px; }
        header p{ font-size:18px; }
        nav{ background-color:#023e8a; padding:15px; text-align:center; }
        nav a{ color:white; text-decoration:none; margin:0 15px; font-weight:bold; transition:0.3s; }
        nav a:hover{ color:#90e0ef; }
        .hero{ background-image:url('https://images.unsplash.com/photo-1517849845537-4d257902454a'); background-size:cover; background-position:center; height:400px; display:flex; align-items:center; justify-content:center; text-align:center; color:white; }
        .hero .contenido{ background-color:rgba(0,0,0,0.5); padding:30px; border-radius:15px; }
        .hero h2{ font-size:40px; margin-bottom:15px; }
        .hero p{ font-size:18px; margin-bottom:20px; }
        .btn-hero{ display:inline-block; background-color:#ffb703; color:#333; padding:12px 25px; text-decoration:none; border-radius:10px; font-weight:bold; transition:0.3s; }
        .btn-hero:hover{ background-color:#fb8500; color:white; }
        section{ padding:50px 20px; text-align:center; }
        section h2{ font-size:30px; margin-bottom:25px; color:#0077b6; }
        .servicios{ display:flex; flex-wrap:wrap; justify-content:center; gap:20px; }
        .card{ background:white; width:260px; padding:20px; border-radius:15px; box-shadow:0 4px 10px rgba(0,0,0,0.1); transition:0.3s; }
        .card:hover{ transform:translateY(-5px); }
        .card h3{ color:#0096c7; margin-bottom:10px; }
        .contacto{ background-color:#caf0f8; border-radius:20px; max-width:700px; margin:auto; padding:30px; }
        .contacto p{ font-size:16px; margin:8px 0; }
        footer{ background-color:#023e8a; color:white; text-align:center; padding:15px; margin-top:20px; }
        /* Formulario registro */
        .registro-section{ max-width:500px; margin:auto; text-align:left; }
        .registro-section label{ display:block; font-weight:bold; color:#023e8a; margin-top:12px; }
        .registro-section input{ width:100%; padding:10px; border:2px solid #90e0ef; border-radius:8px; margin-top:5px; font-size:14px; }
        .registro-section button{ margin-top:20px; width:100%; padding:12px; background:#0077b6; color:white; border:none; border-radius:10px; font-size:16px; font-weight:bold; cursor:pointer; }
        .registro-section button:hover{ background:#023e8a; }
    </style>
</head>
<body>

    <%
        Invitado a = (Invitado) request.getAttribute("bean");
        String usr = (String) request.getAttribute("usr");
        if (usr == null) usr = request.getParameter("usr");
    %>

    <header>
        <h1><i class="fa-solid fa-paw"></i> Veterinaria Huellitas Felices</h1>
        <p>Cuidamos a tus mascotas con amor y profesionalismo</p>
    </header>

    <nav>
        <a href="#inicio">Inicio</a>
        <a href="#servicios">Servicios</a>
        <a href="HorarioDisponible.html">Horarios</a>
        <a href="#registro">Registrarse</a>
        <a href="#contacto">Contacto</a>
        <a href="index.html">Cerrar sesión</a>
    </nav>

    <section class="hero" id="inicio">
        <div class="contenido">
            <h2>Bienvenido, estás como invitado</h2>
            <p>Regístrate para acceder a todas las funciones: agendar citas, registrar mascotas y más</p>
            <a href="#registro" class="btn-hero">Registrarme ahora</a>
        </div>
    </section>

    <section id="servicios">
        <h2>Nuestros Servicios</h2>
        <div class="servicios">
            <div class="card">
                <h3>🐶 Consulta General</h3>
                <p>Revisión médica completa para perros, gatos y otras mascotas.</p>
            </div>
            <div class="card">
                <h3>💉 Vacunación</h3>
                <p>Aplicación de vacunas y control preventivo para su bienestar.</p>
            </div>
            <div class="card">
                <h3>🛁 Estética Canina</h3>
                <p>Baño, corte de pelo, limpieza y cuidado especial.</p>
            </div>
            <div class="card">
                <h3><i class="fa-solid fa-stethoscope"></i> Cirugías</h3>
                <p>Procedimientos quirúrgicos con equipo especializado.</p>
            </div>
            <div class="card">
                <h3>🐱 Desparasitación</h3>
                <p>Tratamientos para mantener a tu mascota sana.</p>
            </div>
            <div class="card">
                <h3>🚑 Emergencias</h3>
                <p>Atención rápida en casos urgentes.</p>
            </div>
        </div>
    </section>

    <section id="registro">
        <h2>Registrarse como Cliente</h2>
        <p style="color:#555; margin-bottom:20px;">Completa tus datos para convertirte en cliente y acceder a todas las funciones</p>

        <div class="registro-section">
            <form action="controlInvitado" method="post">
                <input type="hidden" name="usr" value="<%= usr %>">

                <label>Nombre:</label>
                <input type="text" name="nombre" required>

                <label>Apellido Paterno:</label>
                <input type="text" name="paterno" required>

                <label>Apellido Materno:</label>
                <input type="text" name="materno" required>

                <label>Email:</label>
                <input type="email" name="mail" required>

                <label>Teléfono:</label>
                <input type="text" name="telefono" maxlength="10">

                <button type="submit" name="RegistrarseInvitado">✅ Registrarme</button>
            </form>
        </div>
    </section>

    <section id="contacto">
        <h2>Contáctanos</h2>
        <div class="contacto">
            <p><strong>📍 Dirección:</strong> Av. Principal #123, Ciudad</p>
            <p><strong>📞 Teléfono:</strong> 555-123-4567</p>
            <p><strong>📧 Correo:</strong> contacto@huellitasfelices.com</p>
            <p><strong>🕒 Horario:</strong> Lunes a Sábado de 9:00 AM a 7:00 PM</p>
        </div>
    </section>

    <footer>
        <p>&copy; 2026 Veterinaria Huellitas Felices | Todos los derechos reservados</p>
    </footer>

</body>
</html>
