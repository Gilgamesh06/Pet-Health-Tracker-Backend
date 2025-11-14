-- Entidad: Persona
INSERT INTO persona (nombre, apellido, fecha_nacimiento) VALUES
('Carlos', 'Gómez', '1990-05-12'),
('María', 'López', '1985-09-22'),
('Juan', 'Martínez', '1998-02-11');

-- Entidad: Usuario
INSERT INTO usuario (email, password, persona_id) VALUES
('carlos@gmail.com', 'pass123', 1),
('maria@gmail.com', 'pass456', 2),
('juan@gmail.com', 'pass789', 3);

-- Entidad: Mascota
INSERT INTO mascota (nombre, especie, raza, fecha_nacimiento, peso, foto, usuario_id) VALUES
('Firulais', 'Perro', 'Labrador', '2020-03-10', 25.40, 'firulais.jpg', 1),
('Mishi', 'Gato', 'Angora', '2019-07-15', 4.30, 'mishi.png', 2),
('Rex', 'Perro', 'Pastor Alemán', '2021-01-05', 30.10, NULL, 1);

-- Entidad: Comida
INSERT INTO comida (nombre, descripcion) VALUES
('Dog Chow', 'Alimento balanceado para perro adulto.'),
('Cat Premium', 'Alimento premium para gatos adultos.'),
('Dog Puppy', 'Alimento para cachorros en crecimiento.');

-- Entidad: Horario
INSERT INTO horario (hora, cantidad, dia_semana, recordatorio, mascota_id, comida_id) VALUES
('08:00', 200, 1, true, 1, 1),  -- Firulais lunes
('18:00', 200, 1, true, 1, 1),  -- Firulais lunes tarde
('09:00', 100, 3, true, 2, 2),  -- Mishi miércoles
('07:30', 250, 5, false, 3, 3); -- Rex viernes

-- Entidad: Medicamento 
INSERT INTO medicamento (nombre, tipo, fabricante, intervalo_dosis, descripcion) VALUES
('RabiaVac', 'VACUNA', 'VetLabs', 365, 'Vacuna anual contra la rabia.'),
('ParasiClean', 'DESPARASITANTE', 'AnimalPharma', 90, 'Elimina parásitos internos.'),
('PoliVac', 'VACUNA', 'PetHealth', 180, 'Vacuna polivalente para perros.');

-- Entidad: Evento Salud
INSERT INTO evento_salud (tipo, fecha_realizacion, fecha_programada, estado, notas, veterinario, mascota_id, medicamento_id)VALUES
('VACUNACION', '2024-01-10', '2025-01-10', 'REALIZADO', 'Aplicada sin novedad', 'Dr. Ramírez', 1, 1),
('DESPARACITACION', '2024-02-15', '2024-05-15', 'REALIZADO', 'Buena respuesta', 'Dra. Muñoz', 1, 2),
('VISITA', NULL, '2024-11-01', 'PROGRAMADO', 'Control general', 'Dr. López', 2, NULL),
('VACUNACION', NULL, '2024-12-12', 'PROGRAMADO', 'Vacuna polivalente', 'Dra. González', 3, 3);

-- Entidad: Recordatorio
INSERT INTO recordatorio (fecha_recordatorio, canal, estado, mensaje, evento_salud_id, horario_id, usuario_id) VALUES
('2024-12-11 08:00:00', 'EMAIL', 'PENDIENTE', 'Recordatorio: vacuna de Rex mañana.', 4, NULL, 1),
('2024-11-01 07:00:00', 'IN-APP', 'PENDIENTE', 'Tienes una cita veterinaria programada.', 3, NULL, 2),
('2024-10-02 07:30:00', 'EMAIL', 'ENVIADO', 'Hora de alimentar a Firulais.', NULL, 1, 1),
('2024-10-02 18:00:00', 'IN-APP', 'PENDIENTE', 'Alimentación programada para Firulais.', NULL, 2, 1);
