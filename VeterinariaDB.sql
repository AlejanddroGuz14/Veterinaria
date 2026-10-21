-- =====================================================
-- BASE DE DATOS: VeterinariaDB
-- =====================================================

CREATE DATABASE VeterinariaDB;
GO
USE VeterinariaDB;
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 1: TABLAS BASE (sin dependencias)
-- ═══════════════════════════════════════════════════════

CREATE TABLE Clientes (
    idCliente INT IDENTITY(1,1) PRIMARY KEY,
    nombre    VARCHAR(30) NOT NULL,
    paterno   VARCHAR(30) NOT NULL,
    materno   VARCHAR(30) NOT NULL,
    mail      VARCHAR(80) NOT NULL,
    telefono  CHAR(10),
    tipo      CHAR(1) NOT NULL DEFAULT 'C',
    status    CHAR(1) NOT NULL DEFAULT 'A'
);
GO

CREATE TABLE Especie (
    idEspecie INT IDENTITY(1,1) PRIMARY KEY,
    nombre    VARCHAR(40) NOT NULL UNIQUE,
    status    CHAR(1) NOT NULL DEFAULT 'A'
);
GO

CREATE TABLE SexoMascota (
    idSexo      INT IDENTITY(1,1) PRIMARY KEY,
    descripcion VARCHAR(20) NOT NULL UNIQUE
);
GO

CREATE TABLE MotivoBaja (
    idMotivo    INT IDENTITY(1,1) PRIMARY KEY,
    descripcion VARCHAR(200) NOT NULL UNIQUE
);
GO

CREATE TABLE Horarios (
    idHorario  INT IDENTITY(1,1) PRIMARY KEY,
    fecha      DATE NOT NULL,
    hora       TIME NOT NULL,
    disponible BIT DEFAULT 1,
    UNIQUE (fecha, hora)
);
GO

CREATE TABLE Puesto (
    idPuesto    INT IDENTITY(1,1) PRIMARY KEY,
    nombre      VARCHAR(50)  NOT NULL UNIQUE,
    descripcion VARCHAR(150) NULL,
    status      CHAR(1)      NOT NULL DEFAULT 'A'
);
GO

CREATE TABLE Turno (
    idTurno     INT IDENTITY(1,1) PRIMARY KEY,
    nombre      VARCHAR(30) NOT NULL UNIQUE,
    horaEntrada TIME        NOT NULL,
    horaSalida  TIME        NOT NULL,
    status      CHAR(1)     NOT NULL DEFAULT 'A'
);
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 2: TABLAS CON FK SIMPLES
-- ═══════════════════════════════════════════════════════

CREATE TABLE Acceso (
    idAcceso  INT IDENTITY(1,1) PRIMARY KEY,
    idCliente INT NOT NULL,
    usuario   VARCHAR(20) NOT NULL UNIQUE,
    password  VARCHAR(32) NOT NULL,
    tipo      CHAR(1) NOT NULL CHECK (tipo IN ('A','C','E','I')),
    FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente)
);
GO

CREATE TABLE Empleados (
    idEmpleado   INT IDENTITY(1,1) PRIMARY KEY,
    idCliente    INT            NOT NULL,
    idPuesto     INT            NOT NULL,
    idTurno      INT            NOT NULL,
    salario      DECIMAL(10,2)  NOT NULL,
    fechaIngreso DATE           NOT NULL DEFAULT GETDATE(),
    status       CHAR(1)        NOT NULL DEFAULT 'A',
    FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente),
    FOREIGN KEY (idPuesto)  REFERENCES Puesto(idPuesto),
    FOREIGN KEY (idTurno)   REFERENCES Turno(idTurno)
);
GO

CREATE TABLE Raza (
    idRaza    INT IDENTITY(1,1) PRIMARY KEY,
    idEspecie INT NOT NULL,
    nombre    VARCHAR(50) NOT NULL,
    status    CHAR(1) NOT NULL DEFAULT 'A',
    CONSTRAINT FK_Raza_Especie FOREIGN KEY(idEspecie) REFERENCES Especie(idEspecie),
    CONSTRAINT UQ_Raza UNIQUE(idEspecie, nombre)
);
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 3: TABLAS CON FK MÚLTIPLES
-- ═══════════════════════════════════════════════════════

CREATE TABLE Mascotas (
    idMascota       INT IDENTITY(1,1) PRIMARY KEY,
    idCliente       INT NOT NULL,
    idEspecie       INT NOT NULL,
    idRaza          INT NOT NULL,
    idSexo          INT NOT NULL,
    nombre          VARCHAR(50) NOT NULL,
    fechaNacimiento DATE NULL,
    peso            DECIMAL(8,2) NULL,
    color           VARCHAR(30) NULL,
    observaciones   VARCHAR(150) NULL,
    status          CHAR(1) NOT NULL DEFAULT 'A',
    fechaRegistro   DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente),
    FOREIGN KEY (idEspecie) REFERENCES Especie(idEspecie),
    FOREIGN KEY (idRaza)    REFERENCES Raza(idRaza),
    FOREIGN KEY (idSexo)    REFERENCES SexoMascota(idSexo)
);
GO

CREATE TABLE HorarioLaboral (
    idHorarioLaboral INT IDENTITY(1,1) PRIMARY KEY,
    idEmpleado       INT     NOT NULL,
    diaSemana        TINYINT NOT NULL,
    horaEntrada      TIME    NOT NULL,
    horaSalida       TIME    NOT NULL,
    CONSTRAINT UQ_HorarioLaboral UNIQUE (idEmpleado, diaSemana),
    FOREIGN KEY (idEmpleado) REFERENCES Empleados(idEmpleado)
);
GO

CREATE TABLE Citas (
    idCita        INT IDENTITY(1,1) PRIMARY KEY,
    idCliente     INT NOT NULL,
    idMascota     INT NULL,
    idAcceso      INT NOT NULL,
    idHorario     INT NOT NULL,
    motivo        VARCHAR(150),
    observaciones VARCHAR(200) NULL,
    estado        CHAR(1) DEFAULT 'P',
    fechaRegistro DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente),
    FOREIGN KEY (idMascota) REFERENCES Mascotas(idMascota),
    FOREIGN KEY (idAcceso)  REFERENCES Acceso(idAcceso),
    FOREIGN KEY (idHorario) REFERENCES Horarios(idHorario)
);
GO

CREATE TABLE BajaMascota (
    idBaja    INT IDENTITY(1,1) PRIMARY KEY,
    idMascota INT NOT NULL,
    idMotivo  INT NOT NULL,
    fechaBaja DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (idMascota) REFERENCES Mascotas(idMascota),
    FOREIGN KEY (idMotivo)  REFERENCES MotivoBaja(idMotivo)
);
GO

CREATE TABLE HistorialMedico (
    idHistorial    INT IDENTITY(1,1) PRIMARY KEY,
    idCita         INT NOT NULL,
    idMascota      INT NOT NULL,
    idCliente      INT NOT NULL,
    idAcceso       INT NOT NULL,
    peso           DECIMAL(8,2) NULL,
    temperatura    DECIMAL(4,1) NULL,
    diagnostico    VARCHAR(300) NOT NULL,
    tratamiento    VARCHAR(300) NULL,
    medicamentos   VARCHAR(300) NULL,
    vacunas        VARCHAR(200) NULL,
    observaciones  VARCHAR(300) NULL,
    proximaCita    DATE NULL,
    fechaRegistro  DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (idCita)    REFERENCES Citas(idCita),
    FOREIGN KEY (idMascota) REFERENCES Mascotas(idMascota),
    FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente),
    FOREIGN KEY (idAcceso)  REFERENCES Acceso(idAcceso)
);
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 4: VISTAS
-- ═══════════════════════════════════════════════════════

CREATE VIEW VistaCitas AS
SELECT
    CI.idCita, CI.idCliente, CI.idAcceso, CI.idMascota,
    CL.nombre + ' ' + CL.paterno + ' ' + CL.materno AS cliente,
    M.nombre AS mascota,
    A.usuario AS veterinario,
    P.nombre AS puesto,
    H.fecha, H.hora,
    CI.motivo, CI.observaciones, CI.estado, CI.fechaRegistro
FROM Citas CI
INNER JOIN Clientes CL ON CI.idCliente = CL.idCliente
INNER JOIN Acceso   A  ON CI.idAcceso  = A.idAcceso
INNER JOIN Horarios H  ON CI.idHorario = H.idHorario
LEFT  JOIN Mascotas M  ON CI.idMascota = M.idMascota
LEFT  JOIN Empleados E ON A.idCliente  = E.idCliente
LEFT  JOIN Puesto   P  ON E.idPuesto   = P.idPuesto;
GO

CREATE VIEW VistaMascotas AS
SELECT
    M.idMascota, M.idCliente, M.idEspecie, M.idRaza, M.idSexo,
    C.nombre + ' ' + C.paterno + ' ' + C.materno AS cliente,
    M.nombre AS nombreMascota,
    E.nombre AS especie,
    R.nombre AS raza,
    S.descripcion AS sexo,
    M.fechaNacimiento, M.peso, M.color,
    M.observaciones, M.status, M.fechaRegistro
FROM Mascotas M
INNER JOIN Clientes    C ON M.idCliente = C.idCliente
INNER JOIN Especie     E ON M.idEspecie = E.idEspecie
INNER JOIN Raza        R ON M.idRaza    = R.idRaza
INNER JOIN SexoMascota S ON M.idSexo    = S.idSexo;
GO

CREATE VIEW VistaEmpleados AS
SELECT
    E.idEmpleado, E.idCliente,
    C.nombre + ' ' + C.paterno + ' ' + C.materno AS nombreCompleto,
    A.usuario,
    P.nombre AS puesto,
    T.nombre AS turno,
    T.horaEntrada, T.horaSalida,
    E.salario, E.fechaIngreso, E.status
FROM Empleados E
INNER JOIN Clientes C ON E.idCliente = C.idCliente
INNER JOIN Acceso   A ON E.idCliente = A.idCliente
INNER JOIN Puesto   P ON E.idPuesto  = P.idPuesto
INNER JOIN Turno    T ON E.idTurno   = T.idTurno;
GO

CREATE VIEW VistaHistorialMedico AS
SELECT
    HM.idHistorial, HM.idCita, HM.idMascota, HM.idCliente, HM.idAcceso,
    M.nombre AS mascota,
    CL.nombre + ' ' + CL.paterno + ' ' + CL.materno AS cliente,
    C2.nombre + ' ' + C2.paterno + ' ' + C2.materno AS veterinario,
    HM.peso, HM.temperatura, HM.diagnostico, HM.tratamiento,
    HM.medicamentos, HM.vacunas, HM.observaciones, HM.proximaCita,
    HM.fechaRegistro,
    CONVERT(VARCHAR(10), H.fecha, 120) AS fechaCita,
    CONVERT(VARCHAR(8), H.hora, 108) AS horaCita
FROM HistorialMedico HM
INNER JOIN Mascotas M  ON HM.idMascota = M.idMascota
INNER JOIN Clientes CL ON HM.idCliente = CL.idCliente
INNER JOIN Acceso   A  ON HM.idAcceso  = A.idAcceso
INNER JOIN Clientes C2 ON A.idCliente  = C2.idCliente
INNER JOIN Citas    CI ON HM.idCita    = CI.idCita
INNER JOIN Horarios H  ON CI.idHorario = H.idHorario;
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 5: PROCEDIMIENTOS — USUARIOS
-- ═══════════════════════════════════════════════════════

CREATE PROCEDURE AgregaUsuarioVeterinaria
    @nombre    VARCHAR(30),
    @paterno   VARCHAR(30),
    @materno   VARCHAR(30),
    @mail      VARCHAR(80),
    @telefono  CHAR(10),
    @usuario   VARCHAR(20),
    @password  VARCHAR(32),
    @tipo      CHAR(1),
    @respuesta VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF EXISTS (SELECT 1 FROM Acceso WHERE usuario = @usuario)
    BEGIN
        SET @respuesta = 'El usuario generado ya existe, intenta nuevamente';
    END
    ELSE
    BEGIN
        INSERT INTO Clientes(nombre, paterno, materno, mail, telefono, tipo, status)
        VALUES(@nombre, @paterno, @materno, @mail, @telefono, @tipo, 'A');

        DECLARE @idCliente INT;
        SET @idCliente = SCOPE_IDENTITY();

        INSERT INTO Acceso(idCliente, usuario, password, tipo)
        VALUES(@idCliente, @usuario, @password, @tipo);

        SET @respuesta = 'Usuario registrado correctamente';
    END
END;
GO

CREATE PROCEDURE ActualizaUsuarioVeterinaria
    @usuario   VARCHAR(20),
    @tipo      CHAR(1),
    @respuesta VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF EXISTS (SELECT 1 FROM Acceso WHERE usuario = @usuario)
    BEGIN
        DECLARE @idCliente INT;
        SELECT @idCliente = idCliente FROM Acceso WHERE usuario = @usuario;
        UPDATE Acceso   SET tipo = @tipo WHERE usuario = @usuario;
        UPDATE Clientes SET tipo = @tipo WHERE idCliente = @idCliente;
        SET @respuesta = 'Usuario actualizado correctamente';
    END
    ELSE
    BEGIN
        SET @respuesta = 'Usuario no encontrado';
    END
END;
GO

CREATE PROCEDURE BajaLogicaUsuarioVeterinaria
    @usuario   VARCHAR(20),
    @respuesta VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF EXISTS (SELECT 1 FROM Acceso WHERE usuario = @usuario)
    BEGIN
        DECLARE @idCliente INT;
        SELECT @idCliente = idCliente FROM Acceso WHERE usuario = @usuario;
        UPDATE Clientes SET status = 'I' WHERE idCliente = @idCliente;
        SET @respuesta = 'Usuario dado de baja correctamente';
    END
    ELSE
    BEGIN
        SET @respuesta = 'Usuario no encontrado';
    END
END;
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 6: PROCEDIMIENTOS — EMPLEADOS
-- ═══════════════════════════════════════════════════════

CREATE PROCEDURE AsignarDatosLaboralesVeterinaria
    @idCliente    INT,
    @idPuesto     INT,
    @idTurno      INT,
    @salario      DECIMAL(10,2),
    @fechaIngreso DATE,
    @respuesta    VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF EXISTS(SELECT 1 FROM Empleados WHERE idCliente = @idCliente AND status = 'A')
    BEGIN
        SET @respuesta = 'El empleado ya tiene datos laborales asignados';
        RETURN;
    END
    IF NOT EXISTS(SELECT 1 FROM Clientes WHERE idCliente = @idCliente AND status = 'A')
    BEGIN
        SET @respuesta = 'Empleado no encontrado';
        RETURN;
    END
    INSERT INTO Empleados(idCliente, idPuesto, idTurno, salario, fechaIngreso, status)
    VALUES(@idCliente, @idPuesto, @idTurno, @salario, @fechaIngreso, 'A');
    SET @respuesta = 'Datos laborales asignados correctamente';
END;
GO

CREATE PROCEDURE ModificarDatosLaboralesVeterinaria
    @idEmpleado INT,
    @idPuesto   INT,
    @idTurno    INT,
    @salario    DECIMAL(10,2),
    @status     CHAR(1),
    @respuesta  VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS(SELECT 1 FROM Empleados WHERE idEmpleado = @idEmpleado)
    BEGIN
        SET @respuesta = 'Empleado no encontrado';
        RETURN;
    END
    UPDATE Empleados
    SET idPuesto = @idPuesto, idTurno = @idTurno, salario = @salario, status = @status
    WHERE idEmpleado = @idEmpleado;
    SET @respuesta = 'Datos laborales modificados correctamente';
END;
GO

CREATE PROCEDURE ConsultaDatosLaboralesVeterinaria
    @idCliente INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT E.idEmpleado,
           C.nombre + ' ' + C.paterno + ' ' + C.materno AS nombreCompleto,
           A.usuario,
           P.nombre AS puesto,
           T.nombre AS turno,
           T.horaEntrada, T.horaSalida,
           E.salario, E.fechaIngreso, E.status
    FROM Empleados E
    INNER JOIN Clientes C ON E.idCliente = C.idCliente
    INNER JOIN Acceso   A ON E.idCliente = A.idCliente
    INNER JOIN Puesto   P ON E.idPuesto  = P.idPuesto
    INNER JOIN Turno    T ON E.idTurno   = T.idTurno
    WHERE E.idCliente = @idCliente;
END;
GO

CREATE PROCEDURE AsignarDiaSemanaVeterinaria
    @idEmpleado  INT,
    @diaSemana   TINYINT,
    @horaEntrada TIME,
    @horaSalida  TIME,
    @respuesta   VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS(SELECT 1 FROM Empleados WHERE idEmpleado = @idEmpleado AND status = 'A')
    BEGIN
        SET @respuesta = 'Empleado no encontrado o inactivo';
        RETURN;
    END
    IF @diaSemana < 1 OR @diaSemana > 7
    BEGIN
        SET @respuesta = 'Día de semana inválido';
        RETURN;
    END
    IF EXISTS(SELECT 1 FROM HorarioLaboral WHERE idEmpleado = @idEmpleado AND diaSemana = @diaSemana)
        UPDATE HorarioLaboral
        SET horaEntrada = @horaEntrada, horaSalida = @horaSalida
        WHERE idEmpleado = @idEmpleado AND diaSemana = @diaSemana;
    ELSE
        INSERT INTO HorarioLaboral(idEmpleado, diaSemana, horaEntrada, horaSalida)
        VALUES(@idEmpleado, @diaSemana, @horaEntrada, @horaSalida);
    SET @respuesta = 'Día asignado correctamente';
END;
GO

CREATE PROCEDURE EliminarDiaSemanaVeterinaria
    @idEmpleado INT,
    @diaSemana  TINYINT,
    @respuesta  VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS(SELECT 1 FROM HorarioLaboral WHERE idEmpleado = @idEmpleado AND diaSemana = @diaSemana)
    BEGIN
        SET @respuesta = 'No existe ese día asignado';
        RETURN;
    END
    DELETE FROM HorarioLaboral
    WHERE idEmpleado = @idEmpleado AND diaSemana = @diaSemana;
    SET @respuesta = 'Día eliminado correctamente';
END;
GO

CREATE PROCEDURE ConsultaHorarioSemanalVeterinaria
    @idEmpleado INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT
        HL.diaSemana,
        CASE HL.diaSemana
            WHEN 1 THEN 'Lunes'
            WHEN 2 THEN 'Martes'
            WHEN 3 THEN 'Miércoles'
            WHEN 4 THEN 'Jueves'
            WHEN 5 THEN 'Viernes'
            WHEN 6 THEN 'Sábado'
            WHEN 7 THEN 'Domingo'
        END AS nombreDia,
        HL.horaEntrada, HL.horaSalida,
        C.nombre + ' ' + C.paterno AS empleado,
        P.nombre AS puesto,
        T.nombre AS turno
    FROM HorarioLaboral HL
    INNER JOIN Empleados E ON HL.idEmpleado = E.idEmpleado
    INNER JOIN Clientes  C ON E.idCliente   = C.idCliente
    INNER JOIN Puesto    P ON E.idPuesto    = P.idPuesto
    INNER JOIN Turno     T ON E.idTurno     = T.idTurno
    WHERE HL.idEmpleado = @idEmpleado
    ORDER BY HL.diaSemana;
END;
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 7: PROCEDIMIENTOS — MASCOTAS
-- ═══════════════════════════════════════════════════════

CREATE PROCEDURE AltaMascotaVeterinaria
    @idCliente       INT,
    @idEspecie       INT,
    @idRaza          INT,
    @idSexo          INT,
    @nombre          VARCHAR(50),
    @fechaNacimiento DATE,
    @peso            DECIMAL(8,2),
    @color           VARCHAR(30),
    @observaciones   VARCHAR(150),
    @respuesta       VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS(SELECT 1 FROM Clientes WHERE idCliente = @idCliente AND status = 'A')
    BEGIN
        SET @respuesta = 'Cliente no válido';
        RETURN;
    END
    IF NOT EXISTS(SELECT 1 FROM Especie WHERE idEspecie = @idEspecie AND status = 'A')
    BEGIN
        SET @respuesta = 'Especie no válida';
        RETURN;
    END
    IF NOT EXISTS(SELECT 1 FROM Raza WHERE idRaza = @idRaza AND idEspecie = @idEspecie AND status = 'A')
    BEGIN
        SET @respuesta = 'Raza no corresponde a la especie';
        RETURN;
    END
    INSERT INTO Mascotas(idCliente, idEspecie, idRaza, idSexo, nombre,
                         fechaNacimiento, peso, color, observaciones, status)
    VALUES(@idCliente, @idEspecie, @idRaza, @idSexo, @nombre,
           @fechaNacimiento, @peso, @color, @observaciones, 'A');
    SET @respuesta = 'Mascota registrada correctamente';
END;
GO

CREATE PROCEDURE ModificarMascotaVeterinaria
    @idMascota       INT,
    @idEspecie       INT,
    @idRaza          INT,
    @idSexo          INT,
    @nombre          VARCHAR(50),
    @fechaNacimiento DATE,
    @peso            DECIMAL(8,2),
    @color           VARCHAR(30),
    @observaciones   VARCHAR(150),
    @status          CHAR(1),
    @respuesta       VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS(SELECT 1 FROM Mascotas WHERE idMascota = @idMascota)
    BEGIN
        SET @respuesta = 'Mascota no encontrada';
        RETURN;
    END
    UPDATE Mascotas
    SET idEspecie = @idEspecie, idRaza = @idRaza, idSexo = @idSexo,
        nombre = @nombre, fechaNacimiento = @fechaNacimiento,
        peso = @peso, color = @color, observaciones = @observaciones, status = @status
    WHERE idMascota = @idMascota;
    SET @respuesta = 'Mascota modificada correctamente';
END;
GO

CREATE PROCEDURE BajaMascotaVeterinaria
    @idMascota INT,
    @idMotivo  INT,
    @respuesta VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS(SELECT 1 FROM Mascotas WHERE idMascota = @idMascota)
    BEGIN
        SET @respuesta = 'Mascota no encontrada';
        RETURN;
    END
    IF EXISTS(SELECT 1 FROM Mascotas WHERE idMascota = @idMascota AND status = 'I')
    BEGIN
        SET @respuesta = 'La mascota ya se encuentra inactiva';
        RETURN;
    END
    UPDATE Mascotas SET status = 'I' WHERE idMascota = @idMascota;
    INSERT INTO BajaMascota(idMascota, idMotivo) VALUES(@idMascota, @idMotivo);
    SET @respuesta = 'Mascota dada de baja correctamente';
END;
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 8: PROCEDIMIENTOS — CATÁLOGOS
-- ═══════════════════════════════════════════════════════

CREATE PROCEDURE CargarEspecies
AS
BEGIN
    SET NOCOUNT ON;
    SELECT idEspecie, nombre FROM Especie WHERE status = 'A' ORDER BY nombre;
END;
GO

CREATE PROCEDURE CargarRazasPorEspecie
    @idEspecie INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT idRaza, nombre FROM Raza WHERE idEspecie = @idEspecie AND status = 'A' ORDER BY nombre;
END;
GO

CREATE PROCEDURE CargarSexos
AS
BEGIN
    SET NOCOUNT ON;
    SELECT idSexo, descripcion FROM SexoMascota ORDER BY idSexo;
END;
GO

CREATE PROCEDURE CargarMotivosBaja
AS
BEGIN
    SET NOCOUNT ON;
    SELECT idMotivo, descripcion FROM MotivoBaja ORDER BY idMotivo;
END;
GO

CREATE PROCEDURE CargarUsuarios
AS
BEGIN
    SET NOCOUNT ON;
    SELECT C.idCliente, A.usuario,
           C.nombre + ' ' + C.paterno + ' ' + C.materno AS nombreCompleto
    FROM Clientes C
    INNER JOIN Acceso A ON C.idCliente = A.idCliente
    WHERE C.status = 'A' AND C.tipo IN ('C','E')
    ORDER BY nombreCompleto;
END;
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 9: PROCEDIMIENTOS — CITAS
-- ═══════════════════════════════════════════════════════

CREATE PROCEDURE AgregarCitaVeterinaria
    @idCliente     INT,
    @idMascota     INT,
    @idAcceso      INT,
    @idHorario     INT,
    @motivo        VARCHAR(150),
    @observaciones VARCHAR(200),
    @respuesta     VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS(SELECT 1 FROM Clientes WHERE idCliente = @idCliente AND status = 'A')
    BEGIN
        SET @respuesta = 'Cliente no encontrado';
        RETURN;
    END
    IF NOT EXISTS(SELECT 1 FROM Mascotas WHERE idMascota = @idMascota AND idCliente = @idCliente AND status = 'A')
    BEGIN
        SET @respuesta = 'Mascota no válida para este cliente';
        RETURN;
    END
    IF NOT EXISTS(SELECT 1 FROM Acceso WHERE idAcceso = @idAcceso AND tipo IN ('A','E'))
    BEGIN
        SET @respuesta = 'Veterinario no válido';
        RETURN;
    END
    IF NOT EXISTS(SELECT 1 FROM Horarios WHERE idHorario = @idHorario AND disponible = 1)
    BEGIN
        SET @respuesta = 'Horario no disponible';
        RETURN;
    END
    INSERT INTO Citas(idCliente, idMascota, idAcceso, idHorario, motivo, observaciones, estado)
    VALUES(@idCliente, @idMascota, @idAcceso, @idHorario, @motivo, @observaciones, 'P');
    UPDATE Horarios SET disponible = 0 WHERE idHorario = @idHorario;
    SET @respuesta = 'Cita registrada correctamente';
END;
GO

CREATE PROCEDURE CancelarCitaVeterinaria
    @idCita    INT,
    @estado    CHAR(1),
    @respuesta VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @idHorario INT;
    IF NOT EXISTS(SELECT 1 FROM Citas WHERE idCita = @idCita)
    BEGIN
        SET @respuesta = 'Cita no encontrada';
        RETURN;
    END
    IF @estado NOT IN ('C','T')
    BEGIN
        SET @respuesta = 'Estado no válido';
        RETURN;
    END
    SELECT @idHorario = idHorario FROM Citas WHERE idCita = @idCita;
    UPDATE Citas    SET estado     = @estado WHERE idCita    = @idCita;
    UPDATE Horarios SET disponible = 1       WHERE idHorario = @idHorario;
    SET @respuesta = CASE @estado
        WHEN 'C' THEN 'Cita cancelada correctamente'
        WHEN 'T' THEN 'Cita terminada correctamente'
    END;
END;
GO

CREATE PROCEDURE ModificarCitaVeterinaria
    @idCita        INT,
    @idAcceso      INT,
    @idHorario     INT,
    @motivo        VARCHAR(150),
    @observaciones VARCHAR(200),
    @respuesta     VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @idHorarioAnterior INT;
    IF NOT EXISTS(SELECT 1 FROM Citas WHERE idCita = @idCita AND estado = 'P')
    BEGIN
        SET @respuesta = 'Cita no encontrada o no está pendiente';
        RETURN;
    END
    SELECT @idHorarioAnterior = idHorario FROM Citas WHERE idCita = @idCita;
    IF @idHorario <> @idHorarioAnterior
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM Horarios WHERE idHorario = @idHorario AND disponible = 1)
        BEGIN
            SET @respuesta = 'El nuevo horario no está disponible';
            RETURN;
        END
        UPDATE Horarios SET disponible = 1 WHERE idHorario = @idHorarioAnterior;
        UPDATE Horarios SET disponible = 0 WHERE idHorario = @idHorario;
    END
    UPDATE Citas
    SET idAcceso = @idAcceso, idHorario = @idHorario,
        motivo = @motivo, observaciones = @observaciones
    WHERE idCita = @idCita;
    SET @respuesta = 'Cita modificada correctamente';
END;
GO

CREATE PROCEDURE CargarVeterinariosVeterinaria
AS
BEGIN
    SET NOCOUNT ON;
    SELECT A.idAcceso, A.usuario,
           C.nombre + ' ' + C.paterno + ' ' + C.materno AS nombreCompleto,
           P.nombre AS puesto
    FROM Empleados E
    INNER JOIN Clientes C ON E.idCliente = C.idCliente
    INNER JOIN Acceso   A ON E.idCliente = A.idCliente
    INNER JOIN Puesto   P ON E.idPuesto  = P.idPuesto
    WHERE E.status = 'A' AND P.nombre LIKE '%Veterinario%'
    ORDER BY nombreCompleto;
END;
GO

CREATE PROCEDURE CargarHorarioVeterinarioVeterinaria
    @idAcceso INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT
        HL.diaSemana,
        CASE HL.diaSemana
            WHEN 1 THEN 'Lunes'
            WHEN 2 THEN 'Martes'
            WHEN 3 THEN 'Miércoles'
            WHEN 4 THEN 'Jueves'
            WHEN 5 THEN 'Viernes'
            WHEN 6 THEN 'Sábado'
            WHEN 7 THEN 'Domingo'
        END AS nombreDia,
        HL.horaEntrada, HL.horaSalida
    FROM HorarioLaboral HL
    INNER JOIN Empleados E ON HL.idEmpleado = E.idEmpleado
    INNER JOIN Acceso    A ON E.idCliente   = A.idCliente
    WHERE A.idAcceso = @idAcceso
    ORDER BY HL.diaSemana;
END;
GO

CREATE PROCEDURE CargarMascotasPorClienteVeterinaria
    @idCliente INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT M.idMascota, M.nombre, E.nombre AS especie
    FROM Mascotas M
    INNER JOIN Especie E ON M.idEspecie = E.idEspecie
    WHERE M.idCliente = @idCliente AND M.status = 'A'
    ORDER BY M.nombre;
END;
GO

CREATE PROCEDURE CargarCitasPorClienteVeterinaria
    @idCliente INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT CI.idCita,
           M.nombre AS mascota,
           C.nombre + ' ' + C.paterno + ' ' + C.materno AS veterinario,
           H.fecha, H.hora, CI.motivo, CI.estado
    FROM Citas CI
    INNER JOIN Horarios H  ON CI.idHorario = H.idHorario
    INNER JOIN Acceso   A  ON CI.idAcceso  = A.idAcceso
    INNER JOIN Clientes C  ON A.idCliente  = C.idCliente
    LEFT  JOIN Mascotas M  ON CI.idMascota = M.idMascota
    WHERE CI.idCliente = @idCliente AND CI.estado = 'P'
    ORDER BY H.fecha, H.hora;
END;
GO

CREATE PROCEDURE HorasOcupadasVeterinarioVeterinaria
    @idAcceso INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT
        CONVERT(VARCHAR(10), H.fecha, 120) AS fecha,
        CONVERT(VARCHAR(8), H.hora, 108) AS hora,
        H.idHorario
    FROM Citas CI
    INNER JOIN Horarios H ON CI.idHorario = H.idHorario
    WHERE CI.idAcceso = @idAcceso AND CI.estado = 'P'
    ORDER BY H.fecha, H.hora;
END;
GO

CREATE PROCEDURE HorariosDisponibles
    @fecha DATE
AS
BEGIN
    SET NOCOUNT ON;
    SELECT idHorario, hora FROM Horarios WHERE fecha = @fecha AND disponible = 1;
END;
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 10: PROCEDIMIENTOS — HISTORIAL MÉDICO
-- ═══════════════════════════════════════════════════════

CREATE PROCEDURE RegistrarHistorialMedicoVeterinaria
    @idCita        INT,
    @idMascota     INT,
    @idCliente     INT,
    @idAcceso      INT,
    @peso          DECIMAL(8,2),
    @temperatura   DECIMAL(4,1),
    @diagnostico   VARCHAR(300),
    @tratamiento   VARCHAR(300),
    @medicamentos  VARCHAR(300),
    @vacunas       VARCHAR(200),
    @observaciones VARCHAR(300),
    @proximaCita   DATE,
    @respuesta     VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS(SELECT 1 FROM Citas WHERE idCita = @idCita AND estado = 'T')
    BEGIN
        SET @respuesta = 'La cita no está terminada.';
        RETURN;
    END
    IF EXISTS(SELECT 1 FROM HistorialMedico WHERE idCita = @idCita)
    BEGIN
        SET @respuesta = 'Ya existe un historial para esta cita.';
        RETURN;
    END
    INSERT INTO HistorialMedico(idCita, idMascota, idCliente, idAcceso,
        peso, temperatura, diagnostico, tratamiento, medicamentos,
        vacunas, observaciones, proximaCita)
    VALUES(@idCita, @idMascota, @idCliente, @idAcceso,
        @peso, @temperatura, @diagnostico, @tratamiento, @medicamentos,
        @vacunas, @observaciones, @proximaCita);
    SET @respuesta = 'Historial médico registrado correctamente';
END;
GO

CREATE PROCEDURE ModificarHistorialMedicoVeterinaria
    @idHistorial   INT,
    @peso          DECIMAL(8,2),
    @temperatura   DECIMAL(4,1),
    @diagnostico   VARCHAR(300),
    @tratamiento   VARCHAR(300),
    @medicamentos  VARCHAR(300),
    @vacunas       VARCHAR(200),
    @observaciones VARCHAR(300),
    @proximaCita   DATE,
    @respuesta     VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS(SELECT 1 FROM HistorialMedico WHERE idHistorial = @idHistorial)
    BEGIN
        SET @respuesta = 'Historial no encontrado.';
        RETURN;
    END
    UPDATE HistorialMedico
    SET peso = @peso, temperatura = @temperatura,
        diagnostico = @diagnostico, tratamiento = @tratamiento,
        medicamentos = @medicamentos, vacunas = @vacunas,
        observaciones = @observaciones, proximaCita = @proximaCita
    WHERE idHistorial = @idHistorial;
    SET @respuesta = 'Historial médico modificado correctamente';
END;
GO

CREATE PROCEDURE ConsultarHistorialPorMascotaVeterinaria
    @idMascota INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT
        HM.idHistorial,
        CONVERT(VARCHAR(10), HM.fechaRegistro, 120) AS fechaRegistro,
        C2.nombre + ' ' + C2.paterno AS veterinario,
        HM.peso, HM.temperatura, HM.diagnostico, HM.tratamiento,
        HM.medicamentos, HM.vacunas, HM.observaciones,
        CONVERT(VARCHAR(10), HM.proximaCita, 120) AS proximaCita
    FROM HistorialMedico HM
    INNER JOIN Acceso   A  ON HM.idAcceso  = A.idAcceso
    INNER JOIN Clientes C2 ON A.idCliente  = C2.idCliente
    WHERE HM.idMascota = @idMascota
    ORDER BY HM.fechaRegistro DESC;
END;
GO

CREATE PROCEDURE ConsultarHistorialIndividualVeterinaria
    @idHistorial INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT * FROM VistaHistorialMedico WHERE idHistorial = @idHistorial;
END;
GO

CREATE PROCEDURE CargarCitasTerminadasSinHistorialVeterinaria
    @idMascota INT
AS
BEGIN
    SET NOCOUNT ON;
    SELECT
        CI.idCita,
        C2.nombre + ' ' + C2.paterno AS veterinario,
        CONVERT(VARCHAR(10), H.fecha, 120) AS fecha,
        CONVERT(VARCHAR(8), H.hora, 108) AS hora,
        ISNULL(CI.motivo, '') AS motivo
    FROM Citas CI
    INNER JOIN Horarios H  ON CI.idHorario = H.idHorario
    INNER JOIN Acceso   A  ON CI.idAcceso  = A.idAcceso
    INNER JOIN Clientes C2 ON A.idCliente  = C2.idCliente
    WHERE CI.idMascota = @idMascota
      AND CI.estado = 'T'
      AND NOT EXISTS(SELECT 1 FROM HistorialMedico HM WHERE HM.idCita = CI.idCita)
    ORDER BY H.fecha DESC, H.hora;
END;
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 11: DATOS SEMILLA
-- ═══════════════════════════════════════════════════════

INSERT INTO SexoMascota (descripcion) VALUES ('Macho'), ('Hembra');
GO

INSERT INTO MotivoBaja (descripcion) VALUES
('Falleció'),
('Adoptada o vendida a otra persona'),
('Transferida a otro cliente'),
('Cliente se mudó de ciudad'),
('Mascota perdida');
GO

INSERT INTO Puesto (nombre, descripcion) VALUES
('Secretaria',          'Atención al cliente, gestión de citas y tareas administrativas'),
('Veterinario General', 'Médico veterinario encargado de atención clínica general');
GO

INSERT INTO Especie (nombre, status) VALUES
('Perro','A'),('Gato','A'),('Ave','A'),('Conejo','A'),('Hamster','A'),
('Reptil','A'),('Hurón','A'),('Pez','A'),('Cobaya','A'),('Chinchilla','A'),('Otro','A');
GO

-- PERRO
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Labrador','A' FROM Especie WHERE nombre='Perro';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Pastor Alemán','A' FROM Especie WHERE nombre='Perro';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Chihuahua','A' FROM Especie WHERE nombre='Perro';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Pug','A' FROM Especie WHERE nombre='Perro';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Pitbull','A' FROM Especie WHERE nombre='Perro';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Perro';
-- GATO
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Persa','A' FROM Especie WHERE nombre='Gato';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Siamés','A' FROM Especie WHERE nombre='Gato';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Bengala','A' FROM Especie WHERE nombre='Gato';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Gato';
-- AVE
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Perico','A' FROM Especie WHERE nombre='Ave';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Canario','A' FROM Especie WHERE nombre='Ave';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Cacatúa','A' FROM Especie WHERE nombre='Ave';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Ave';
-- CONEJO
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Mini Lop','A' FROM Especie WHERE nombre='Conejo';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Cabeza de León','A' FROM Especie WHERE nombre='Conejo';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Conejo';
-- HAMSTER
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Sirio','A' FROM Especie WHERE nombre='Hamster';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Ruso','A' FROM Especie WHERE nombre='Hamster';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Hamster';
-- REPTIL
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Iguana','A' FROM Especie WHERE nombre='Reptil';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Tortuga','A' FROM Especie WHERE nombre='Reptil';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Gecko','A' FROM Especie WHERE nombre='Reptil';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Reptil';
-- HURÓN
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Estándar','A' FROM Especie WHERE nombre='Hurón';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Hurón';
-- PEZ
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Betta','A' FROM Especie WHERE nombre='Pez';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Goldfish','A' FROM Especie WHERE nombre='Pez';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Pez';
-- COBAYA
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Americana','A' FROM Especie WHERE nombre='Cobaya';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Peruana','A' FROM Especie WHERE nombre='Cobaya';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Cobaya';
-- CHINCHILLA
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Estándar','A' FROM Especie WHERE nombre='Chinchilla';
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Chinchilla';
-- OTRO
INSERT INTO Raza(idEspecie,nombre,status) SELECT idEspecie,'Otro','A' FROM Especie WHERE nombre='Otro';
GO

INSERT INTO Turno (nombre, horaEntrada, horaSalida) VALUES
('Matutino',   '08:00', '14:00'),
('Vespertino', '14:00', '20:00');
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 12: CONSULTAS DE VERIFICACIÓN
-- ═══════════════════════════════════════════════════════

-- Muestra los usuarios registrados con credenciales y tipo
SELECT * FROM Acceso;
GO

-- Muestra los datos personales de todos los clientes
SELECT * FROM Clientes;
GO

-- Muestra los empleados con puesto, turno y salario
SELECT * FROM VistaEmpleados;
GO

-- Muestra las mascotas con dueño, especie y raza
SELECT * FROM VistaMascotas;
GO

-- Muestra las citas con cliente, veterinario y horario
SELECT * FROM VistaCitas;
GO

-- Muestra el historial médico completo
SELECT * FROM VistaHistorialMedico;
GO

SELECT * FROM HorarioLaboral;
GO

SELECT * FROM Horarios;
GO

SELECT * FROM BajaMascota;
GO

---------------------------------------
---SELECTS DE TABLAS COMPLEMENTARIAS---
---------------------------------------
SELECT * FROM Especie;
GO

SELECT * FROM Raza;
GO

SELECT * FROM Puesto;
GO

SELECT * FROM Turno;
GO

SELECT * FROM SexoMascota;
GO

SELECT * FROM MotivoBaja;
GO

-- ═══════════════════════════════════════════════════════
-- SECCIÓN 13: USUARIO ADMINISTRADOR POR DEFECTO
-- ═══════════════════════════════════════════════════════

-- Administrador principal del sistema
INSERT INTO Clientes(nombre, paterno, materno, mail, telefono, tipo, status)
VALUES('Alejandro', 'Guzman', 'Hernandez', 'ale@gmail.com', '5540204694', 'A', 'A');

DECLARE @idAdmin INT;
SET @idAdmin = SCOPE_IDENTITY();

INSERT INTO Acceso(idCliente, usuario, password, tipo)
VALUES(@idAdmin, 'Ale', 'Ale', 'A');
GO

-- Empleado principal
INSERT INTO Clientes(nombre, paterno, materno, mail, telefono, tipo, status)
VALUES('Nora Michelle', 'Santillan', 'Flores', 'nor@gmail.com','1234567899', 'E', 'A');

DECLARE @idAdmin INT;
SET @idAdmin = SCOPE_IDENTITY();

INSERT INTO Acceso(idCliente, usuario, password, tipo)
VALUES(@idAdmin, 'Nor', 'Nor', 'E');
GO

-- Cliente principal
INSERT INTO Clientes(nombre, paterno, materno, mail, telefono, tipo, status)
VALUES('Oswaldo Yae', 'Alonso', 'Guarneros', 'wal@gmail.com', '9087654321', 'C', 'A');

DECLARE @idAdmin INT;
SET @idAdmin = SCOPE_IDENTITY();

INSERT INTO Acceso(idCliente, usuario, password, tipo)
VALUES(@idAdmin, 'Osw', 'Osw', 'C');
GO




