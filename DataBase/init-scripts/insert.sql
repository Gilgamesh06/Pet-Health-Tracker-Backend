
-- Entidad: Persona
INSERT INTO persona (nombre, apellido, fecha_nacimiento, fecha_creacion,
                     fecha_actualizacion, fecha_eliminacion, estado) VALUES
('Carlos', 'Gómez', '1990-05-12', NOW(), NULL, NULL, 'ACTIVO'),
('María', 'López', '1985-09-22', NOW(), NULL, NULL, 'ACTIVO'),
('Juan', 'Martínez', '1998-02-11', NOW(), NULL, NULL, 'ACTIVO');


-- Entidad: Usuario
INSERT INTO usuario (email, password, persona_id, fecha_creacion, fecha_actualizacion,
                     fecha_eliminacion, estado) VALUES
('carlos@gmail.com', 'pass123', 1, NOW(), NULL, NULL, 'ACTIVO'),
('maria@gmail.com', 'pass456', 2, NOW(), NULL, NULL, 'ACTIVO'),
('juan@gmail.com', 'pass789', 3, NOW(), NULL, NULL, 'ACTIVO');

-- Entidad: Mascota
INSERT INTO mascota (nombre, especie, raza, fecha_nacimiento, peso, foto, usuario_id,
                     fecha_creacion, fecha_actualizacion, fecha_eliminacion, estado) VALUES
('Firulais', 'Perro', 'Labrador', '2020-03-10', 25.40, 'firulais.jpg', 1, NOW(), NULL, NULL, 'ACTIVO'),
('Mishi', 'Gato', 'Angora', '2019-07-15', 4.30, 'mishi.png', 2, NOW(), NULL, NULL, 'ACTIVO'),
('Rex', 'Perro', 'Pastor Alemán', '2021-01-05', 30.10, NULL, 1, NOW(), NULL, NULL, 'ACTIVO');


-- Entidad: Comida
INSERT INTO comida (nombre, descripcion, fecha_creacion,
                    fecha_actualizacion, fecha_eliminacion, estado) VALUES
('Dog Chow', 'Alimento balanceado para perro adulto.', NOW(), NULL, NULL, 'ACTIVO'),
('Cat Premium', 'Alimento premium para gatos adultos.', NOW(), NULL, NULL, 'ACTIVO'),
('Dog Puppy', 'Alimento para cachorros en crecimiento.', NOW(), NULL, NULL, 'ACTIVO');


-- Entidad: Horario
INSERT INTO horario (hora, cantidad, dia_semana, recordatorio, mascota_id, comida_id,
                     fecha_creacion, fecha_actualizacion, fecha_eliminacion, estado) VALUES
('08:00', 200, 1, true, 1, 1, NOW(), NULL, NULL, 'ACTIVO'),
('18:00', 200, 1, true, 1, 1, NOW(), NULL, NULL, 'ACTIVO'),
('09:00', 100, 3, true, 2, 2, NOW(), NULL, NULL, 'ACTIVO'),
('07:30', 250, 5, false, 3, 3, NOW(), NULL, NULL, 'ACTIVO');


-- Entidad: Medicamento 
INSERT INTO medicamento (nombre, tipo, fabricante, intervalo_dosis, descripcion,
                         fecha_creacion, fecha_actualizacion, fecha_eliminacion, estado) VALUES
('RabiaVac', 'VACUNA', 'VetLabs', 365, 'Vacuna anual contra la rabia.', NOW(), NULL, NULL, 'ACTIVO'),
('ParasiClean', 'DESPARASITANTE', 'AnimalPharma', 90, 'Elimina parásitos internos.', NOW(), NULL, NULL, 'ACTIVO'),
('PoliVac', 'VACUNA', 'PetHealth', 180, 'Vacuna polivalente para perros.', NOW(), NULL, NULL, 'ACTIVO');


-- Entidad: Evento Salud
INSERT INTO evento_salud (tipo, fecha_realizacion, fecha_programada, estado_evento, notas, veterinario,
                          mascota_id, medicamento_id,
                          fecha_creacion, fecha_actualizacion, fecha_eliminacion, estado) VALUES
('VACUNACION', '2024-01-10', '2025-01-10', 'REALIZADO', 'Aplicada sin novedad', 'Dr. Ramírez',
 1, 1, NOW(), NULL, NULL, 'ACTIVO'),
('DESPARACITACION', '2024-02-15', '2024-05-15', 'REALIZADO', 'Buena respuesta', 'Dra. Muñoz',
 1, 2, NOW(), NULL, NULL, 'ACTIVO'),
('VISITA', NULL, '2024-11-01', 'PROGRAMADO', 'Control general', 'Dr. López',
 2, NULL, NOW(), NULL, NULL, 'ACTIVO'),
('VACUNACION', NULL, '2024-12-12', 'PROGRAMADO', 'Vacuna polivalente', 'Dra. González',
 3, 3, NOW(), NULL, NULL, 'ACTIVO');


-- Entidad: Recordatorio
INSERT INTO recordatorio (fecha_recordatorio, canal, estado_recordatorio, mensaje,
                          evento_salud_id, horario_id, usuario_id,
                          fecha_creacion, fecha_actualizacion, fecha_eliminacion, estado) VALUES
('2024-12-11 08:00:00', 'EMAIL', 'PENDIENTE', 'Recordatorio: vacuna de Rex mañana.',
 4, NULL, 1, NOW(), NULL, NULL, 'ACTIVO'),
('2024-11-01 07:00:00', 'IN-APP', 'PENDIENTE', 'Tienes una cita veterinaria programada.',
 3, NULL, 2, NOW(), NULL, NULL, 'ACTIVO'),
('2024-10-02 07:30:00', 'EMAIL', 'ENVIADO', 'Hora de alimentar a Firulais.',
 NULL, 1, 1, NOW(), NULL, NULL, 'ACTIVO'),
('2024-10-02 18:00:00', 'IN-APP', 'PENDIENTE', 'Alimentación programada para Firulais.',
 NULL, 2, 1, NOW(), NULL, NULL, 'ACTIVO');
