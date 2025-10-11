SET FOREIGN_KEY_CHECKS = 0;

-- VARIABLES DE FECHA PARA MySQL
SET @HOY = CURDATE();
SET @HACE_5_DIAS = DATE_SUB(@HOY, INTERVAL 5 DAY);
SET @HACE_10_DIAS = DATE_SUB(@HOY, INTERVAL 10 DAY);
SET @HACE_15_DIAS = DATE_SUB(@HOY, INTERVAL 15 DAY);
SET @HACE_20_DIAS = DATE_SUB(@HOY, INTERVAL 20 DAY);

-- INSERCIÓN DE SOCIOS (30 Socios) - USANDO INSERT IGNORE (No se duplican en reinicios)
INSERT IGNORE INTO socio (id, nombre, identificacion, telefono, email) VALUES
(1, 'Ana Torres López', '101234567-A', '987654321', 'ana.torres@ejemplo.com'),
(2, 'Carlos Ruiz Pérez', '101234568-B', '987654322', 'carlos.ruiz@ejemplo.com'),
(3, 'Laura Gómez Soto', '101234569-C', '987654323', 'laura.gomez@ejemplo.com'),
(4, 'Miguel Ángel Díaz', '101234570-D', '987654324', 'miguel.diaz@ejemplo.com'),
(5, 'Sofía Ramos Cruz', '101234571-E', '987654325', 'sofia.ramos@ejemplo.com'),
(6, 'Javier Blanco Luna', '101234572-F', '987654326', 'javier.blanco@ejemplo.com'),
(7, 'Elena Castro Gil', '101234573-G', '987654327', 'elena.castro@ejemplo.com'),
(8, 'David Soto Ríos', '101234574-H', '987654328', 'david.soto@ejemplo.com'),
(9, 'Nuria Vidal Roca', '101234575-I', '987654329', 'nuria.vidal@ejemplo.com'),
(10, 'Pablo Sanz Mora', '101234576-J', '987654330', 'pablo.sanz@ejemplo.com'),
(11, 'Andrea Gil Vega', '101234577-K', '987654331', 'andrea.gil@ejemplo.com'),
(12, 'Juan León Salas', '101234578-L', '987654332', 'juan.leon@ejemplo.com'),
(13, 'Isabel Rico Paz', '101234579-M', '987654333', 'isabel.rico@ejemplo.com'),
(14, 'Ricardo Echeverría', '101234580-N', '987654334', 'ricardo.echeverria@ejemplo.com'),
(15, 'Eva Flores Mena', '101234581-O', '987654335', 'eva.flores@ejemplo.com'),
(16, 'Felipe Ruiz Diaz', '101234582-P', '987654336', 'felipe.ruiz@ejemplo.com'),
(17, 'Gema Herrera Cano', '101234583-Q', '987654337', 'gema.herrera@ejemplo.com'),
(18, 'Héctor Marín Sol', '101234584-R', '987654338', 'hector.marin@ejemplo.com'),
(19, 'Irene Navarro Rey', '101234585-S', '987654339', 'irene.navarro@ejemplo.com'),
(20, 'Jorge Peña Bravo', '101234586-T', '987654340', 'jorge.pena@ejemplo.com'),
(21, 'Kira Quintero Ríos', '101234587-U', '987654341', 'kira.quintero@ejemplo.com'),
(22, 'Luis Robles Soto', '101234588-V', '987654342', 'luis.robles@ejemplo.com'),
(23, 'Marta Sierra Vega', '101234589-W', '987654343', 'marta.sierra@ejemplo.com'),
(24, 'Nico Tello Cruz', '101234590-X', '987654344', 'nico.tello@ejemplo.com'),
(25, 'Olga Urrea León', '101234591-Y', '987654345', 'olga.urrea@ejemplo.com'),
(26, 'Pedro Vera Salas', '101234592-Z', '987654346', 'pedro.vera@ejemplo.com'),
(27, 'Quique Rivas Paz', '101234593-0', '987654347', 'quique.rivas@ejemplo.com'),
(28, 'Rosa Sáenz Mora', '101234594-1', '987654348', 'rosa.saenz@ejemplo.com'),
(29, 'Silvia Terrón Gil', '101234595-2', '987654349', 'silvia.terron@ejemplo.com'),
(30, 'Toni Uceda Díaz', '101234596-3', '987654350', 'toni.uceda@ejemplo.com');


-- INSERCIÓN DE LIBROS (61 Libros) - USANDO INSERT IGNORE (No se duplican en reinicios)
INSERT IGNORE INTO libro (id, isbn, titulo, autor, anio_publicacion, cantidad_disponible) VALUES
(1, '978-0743273565', 'El Gran Gatsby', 'F. Scott Fitzgerald', 1925, 5),
(2, '978-0061120084', 'Matar a un Ruiseñor', 'Harper Lee', 1960, 8),
(3, '978-0451524935', '1984', 'George Orwell', 1949, 12),
(4, '978-0385504201', 'El Código Da Vinci', 'Dan Brown', 2003, 10),
(5, '978-0140449195', 'Orgullo y Prejuicio', 'Jane Austen', 1813, 7),
(6, '978-0060850524', 'Cien Años de Soledad', 'Gabriel García Márquez', 1967, 15),
(7, '978-0452284234', 'Los Miserables', 'Victor Hugo', 1862, 3),
(8, '978-8420471830', 'La Sombra del Viento', 'Carlos Ruiz Zafón', 2001, 9),
(9, '978-0743273566', 'Crimen y Castigo', 'Fiódor Dostoyevski', 1866, 6),
(10, '978-0062316097', 'Las Crónicas de Narnia', 'C.S. Lewis', 1950, 11),
(11, '978-0679783268', 'La Odisea', 'Homero', 750, 4),
(12, '978-0345391803', 'El Señor de los Anillos', 'J.R.R. Tolkien', 1954, 18),
(13, '978-0743273567', 'Moby Dick', 'Herman Melville', 1851, 2),
(14, '978-0307474278', 'Ready Player One', 'Ernest Cline', 2011, 10),
(15, '978-0553213101', 'Frankenstein', 'Mary Shelley', 1818, 5),
(16, '977-0385732550', 'Los Juegos del Hambre', 'Suzanne Collins', 2008, 14),
(17, '977-0062316089', 'El Alquimista', 'Paulo Coelho', 1988, 7),
(18, '977-0743273567', 'Harry Potter y la Piedra Filosofal', 'J.K. Rowling', 1997, 20),
(19, '977-0140449187', 'Don Quijote de la Mancha', 'Miguel de Cervantes', 1605, 12),
(20, '977-0393088019', 'Guerra y Paz', 'León Tolstói', 1869, 4),
(21, '977-0061120092', 'Fahrenheit 451', 'Ray Bradbury', 1953, 9),
(22, '977-0451524943', 'Un Mundo Feliz', 'Aldous Huxley', 1932, 8),
(23, '977-0385504219', 'Drácula', 'Bram Stoker', 1897, 6),
(24, '977-0140449201', 'Las Aventuras de Tom Sawyer', 'Mark Twain', 1876, 11),
(25, '977-0060850532', 'Veinte Mil Leguas de Viaje Submarino', 'Julio Verne', 1870, 10),
(26, '977-0452284242', 'Anna Karenina', 'León Tolstói', 1877, 3),
(27, '977-8420471848', 'El Nombre del Viento', 'Patrick Rothfuss', 2007, 13),
(28, '977-0743273568', 'El Retrato de Dorian Gray', 'Oscar Wilde', 1890, 7),
(29, '977-0062316103', 'El Hobbit', 'J.R.R. Tolkien', 1937, 16),
(30, '977-0679783276', 'La Ilíada', 'Homero', 750, 5),
(31, '977-0345391811', 'Juego de Tronos', 'George R.R. Martin', 1996, 15),
(32, '977-0743273569', 'Madame Bovary', 'Gustave Flaubert', 1856, 3),
(33, '977-0307474286', 'It', 'Stephen King', 1986, 8),
(34, '977-0553213119', 'Alicia en el País de las Maravillas', 'Lewis Carroll', 1865, 12),
(35, '977-0385732567', 'El Laberinto de la Soledad', 'Octavio Paz', 1950, 6),
(36, '977-0062316111', 'La Casa de los Espíritus', 'Isabel Allende', 1982, 9),
(37, '977-0743273570', 'Rebelión en la Granja', 'George Orwell', 1945, 10),
(38, '977-0140449219', 'Hamlet', 'William Shakespeare', 1603, 7),
(39, '977-0393088027', 'El Extranjero', 'Albert Camus', 1942, 5),
(40, '977-0061120108', 'Siddhartha', 'Hermann Hesse', 1922, 11),
(41, '977-0451524950', 'El Perfume', 'Patrick Süskind', 1985, 8),
(42, '977-0385504227', 'El Retorno del Rey', 'J.R.R. Tolkien', 1955, 14),
(43, '977-0140449226', 'Lo que el Viento se Llevó', 'Margaret Mitchell', 1936, 6),
(44, '977-0060850540', 'Viaje al Centro de la Tierra', 'Julio Verne', 1864, 10),
(45, '977-0452284259', '1Q84', 'Haruki Murakami', 2009, 9),
(46, '977-8420471855', 'El Traje del Muerto', 'Joe Hill', 2007, 7),
(47, '977-0743273571', 'El Mundo de Sofía', 'Jostein Gaarder', 1991, 12),
(48, '977-0062316129', 'Cementerio de Animales', 'Stephen King', 1983, 5),
(49, '977-0679783283', 'Un Capital', 'Karl Marx', 1867, 3),
(50, '977-0345391828', 'Canción de Hielo y Fuego', 'George R.R. Martin', 2000, 10),
(51, '977-0743273572', 'Crónicas Marcianas', 'Ray Bradbury', 1950, 8),
(52, '977-0307474293', 'Los Pilares de la Tierra', 'Ken Follett', 1989, 11),
(53, '977-0553213127', 'El Nombre de la Rosa', 'Umberto Eco', 1980, 7),
(54, '977-0385732574', 'La Metamorfosis', 'Franz Kafka', 1915, 6),
(55, '977-0062316137', 'El Psicoanalista', 'John Katzenbach', 2002, 9),
(56, '977-0743273573', 'El Principito', 'Antoine de Saint-Exupéry', 1943, 15),
(57, '977-0140449233', 'El Retrato de una Dama', 'Henry James', 1881, 4),
(58, '977-0393088035', 'Rayuela', 'Julio Cortázar', 1963, 10),
(59, '977-0061120116', 'La Cabaña', 'William P. Young', 2007, 5),
(60, '977-0061120117', 'La Llamada de Cthulhu', 'H.P. Lovecraft', 1928, 6),
(61, '977-0061120118', 'Ensayo sobre la ceguera', 'José Saramago', 1995, 7);

-- INSERCIÓN DE PRÉSTAMOS
INSERT INTO prestamo (socio_id, libro_id, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real, monto_multa, pagado) VALUES
-- PRÉSTAMOS ACTIVOS (fecha_devolucion_real y monto_multa = NULL)
(1, 1, @HACE_5_DIAS, DATE_ADD(@HACE_5_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE),
(2, 2, @HACE_5_DIAS, DATE_ADD(@HACE_5_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE),
(3, 3, @HACE_5_DIAS, DATE_ADD(@HACE_5_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE),
(4, 4, @HACE_5_DIAS, DATE_ADD(@HACE_5_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE),
(5, 5, @HACE_5_DIAS, DATE_ADD(@HACE_5_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE),
(16, 6, @HOY, DATE_ADD(@HOY, INTERVAL 7 DAY), NULL, NULL, FALSE), -- Préstamo de Hoy
(17, 7, @HOY, DATE_ADD(@HOY, INTERVAL 7 DAY), NULL, NULL, FALSE), -- Préstamo de Hoy
(18, 8, @HACE_10_DIAS, DATE_ADD(@HACE_10_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE), -- Vencido por 3 días
(19, 9, @HACE_15_DIAS, DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE), -- Vencido por 8 días
(20, 10, @HACE_20_DIAS, DATE_ADD(@HACE_20_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE), -- Vencido por 13 días
(6, 11, @HACE_10_DIAS, DATE_ADD(@HACE_10_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE),
(7, 12, @HACE_10_DIAS, DATE_ADD(@HACE_10_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE),
(8, 13, @HACE_10_DIAS, DATE_ADD(@HACE_10_DIAS, INTERVAL 7 DAY), NULL, NULL, FALSE),

-- PRÉSTAMOS DEVUELTOS SIN MULTA
(21, 21, @HACE_15_DIAS, DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), DATE_ADD(@HACE_15_DIAS, INTERVAL 5 DAY), 0.0, TRUE), -- Devuelto a tiempo
(22, 22, @HACE_15_DIAS, DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), 0.0, TRUE), -- Devuelto justo a tiempo
(23, 23, @HACE_15_DIAS, DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), DATE_ADD(@HACE_15_DIAS, INTERVAL 6 DAY), 0.0, TRUE),

-- PRÉSTAMOS DEVUELTOS CON MULTA (fecha_devolucion_real y monto_multa tienen valor)
(6, 36, @HACE_15_DIAS, DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), DATE_ADD(DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), INTERVAL 1 DAY), 1.0, TRUE), -- 1 día de multa
(7, 37, @HACE_15_DIAS, DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), DATE_ADD(DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), INTERVAL 2 DAY), 2.0, TRUE), -- 2 días de multa
(8, 38, @HACE_15_DIAS, DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), DATE_ADD(DATE_ADD(@HACE_15_DIAS, INTERVAL 7 DAY), INTERVAL 3 DAY), 3.0, TRUE); -- 3 días de multa

SET FOREIGN_KEY_CHECKS = 1;