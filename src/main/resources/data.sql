-- data.sql - Datos iniciales para la aplicación
-- Se ejecuta automáticamente al iniciar la aplicación

INSERT INTO students (first_name, last_name, email, birth_date, phone, address, country_code, external_post_id, active, created_at, updated_at)
VALUES
    ('Juan', 'Pérez', 'juan.perez@email.com', '1995-05-15', '+1-555-0101', '123 Main St, Anytown', 'US', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('María', 'González', 'maria.gonzalez@email.com', '1998-08-22', '+1-555-0102', '456 Oak Ave, Somewhere', 'DO', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Carlos', 'Rodríguez', 'carlos.rodriguez@email.com', '1992-11-10', '+34-123-456789', 'Calle Principal 789, Madrid', 'ES', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Ana', 'Martínez', 'ana.martinez@email.com', '1997-03-18', '+1-555-0104', '321 Pine St, Another City', 'US', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Pedro', 'López', 'pedro.lopez@email.com', '1994-07-25', '+1-809-555-0105', 'Av. Libertad 456, Santiago', 'DO', 5, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Laura', 'Fernández', 'laura.fernandez@email.com', '1996-12-03', '+34-987-654321', 'Gran Vía 123, Barcelona', 'ES', 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Miguel', 'Sánchez', 'miguel.sanchez@email.com', '1993-09-14', '+1-555-0107', '654 Elm St, Metro City', 'US', 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Isabella', 'Torres', 'isabella.torres@email.com', '1999-01-30', '+1-809-555-0108', 'Calle Duarte 789, Santo Domingo', 'DO', 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Fernando', 'Morales', 'fernando.morales@email.com', '1991-06-12', '+34-555-123456', 'Plaza Mayor 45, Sevilla', 'ES', 9, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Sofia', 'Jiménez', 'sofia.jimenez@email.com', '2000-04-08', '+1-555-0110', '987 Maple Ave, Big City', 'US', 10, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);