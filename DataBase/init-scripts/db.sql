CREATE TABLE persona(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,

    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP,
    fecha_eliminacion TIMESTAMP,
    estado VARCHAR(20) NOT NULL  -- Activo o Inactivo
);

CREATE TABLE usuario(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP,
    fecha_eliminacion TIMESTAMP,
    estado VARCHAR(20) NOT NULL,  -- Activo o Inactivo

    persona_id INT NOT NULL UNIQUE,

    CONSTRAINT fk_persona_p FOREIGN KEY(persona_id) REFERENCES persona(id)
);

CREATE TABLE mascota(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    especie VARCHAR(255) NOT NULL,
    raza VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    peso NUMERIC(5,2) NOT NULL,
    foto VARCHAR(255),

    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP,
    fecha_eliminacion TIMESTAMP,
    estado VARCHAR(20) NOT NULL,  -- Activo o Inactivo

    usuario_id INT NOT NULL,

    CONSTRAINT fK_usuario_mas FOREIGN KEY(usuario_id) REFERENCES usuario(id)
);

CREATE TABLE comida(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,

    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP,
    fecha_eliminacion TIMESTAMP,
    estado VARCHAR(20) NOT NULL  -- Activo o Inactivo
);

-- Horario de alimentacion
CREATE TABLE horario(
    id SERIAL PRIMARY KEY,
    hora TIME NOT NULL,  
    cantidad INT NOT NULL,
    dia_semana INT NOT NULL, -- 1 = Lunes, 2 = Martes, 3 = Miercoles, NULL 
    recordatorio BOOLEAN DEFAULT true,
    
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP,
    fecha_eliminacion TIMESTAMP,
    estado VARCHAR(20) NOT NULL,  -- Activo o Inactivo
    
    mascota_id INT NOT NULL,
    comida_id INT NOT NULL,

    CONSTRAINT fk_mascota_hor FOREIGN KEY(mascota_id) REFERENCES mascota(id),
    CONSTRAINT fk_comida_hor FOREIGN KEY(comida_id) REFERENCES comida(id)
);


CREATE TABLE medicamento(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    tipo VARCHAR(20) CHECK (tipo IN ('VACUNA', 'DESPARASITANTE')) , -- Puede ser: vacuna o desparacitante
    fabricante VARCHAR(255) NOT NULL,
    intervalo_dosis INT NOT NULL,
    descripcion TEXT NOT NULL,

    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP,
    fecha_eliminacion TIMESTAMP,
    estado VARCHAR(20) NOT NULL  -- Activo o Inactivo
    
);

CREATE TABLE evento_salud(
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL, -- 'VACUNACION', 'DESPARACITACION', 'VISITA'
    fecha_realizacion DATE, --cuando se aplico (puede ser NULL si es programada)
    fecha_programada DATE, -- proxima vacuna / cita
    estado_evento VARCHAR(30) NOT NULL, -- 'PROGRAMADO', 'REALIZADO', 'CANCELADO'
    notas VARCHAR(500),
    veterinario VARCHAR(255),

    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP,
    fecha_eliminacion TIMESTAMP,
    estado VARCHAR(20) NOT NULL,  -- Activo o Inactivo

    mascota_id INT NOT NULL,
    medicamento_id INT, -- Puede ser NULL ya que manejamos visita aqui tambien

    CONSTRAINT fk_mascota_es FOREIGN KEY(mascota_id) REFERENCES mascota(id),
    CONSTRAINT fk_medicamente_es FOREIGN KEY(medicamento_id) REFERENCES medicamento(id)
);

CREATE TABLE recordatorio(
    id SERIAL PRIMARY KEY,
    fecha_recordatorio TIMESTAMP NOT NULL,
    canal VARCHAR(20) NOT NULL, -- 'EMAIL', 'IN-APP'
    estado_recordatorio VARCHAR(20) NOT NULL, -- PENDIENTE, ENVIADO, ERROR
    mensaje VARCHAR(500),
    
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP,
    fecha_eliminacion TIMESTAMP,
    estado VARCHAR(20) NOT NULL,  -- Activo o Inactivo

    evento_salud_id INT,
    horario_id INT,
    usuario_id INT NOT NULL,

    CONSTRAINT fk_evento_salud_rec FOREIGN KEY(evento_salud_id) REFERENCES evento_salud(id),
    CONSTRAINT fk_horario_rec FOREIGN KEY(horario_id) REFERENCES horario(id),
    CONSTRAINT fk_usuario_rec FOREIGN KEY(usuario_id) REFERENCES usuario(id)
);

