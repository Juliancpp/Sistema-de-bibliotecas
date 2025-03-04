DROP DATABASE IF EXISTS biblioteca;
CREATE DATABASE biblioteca;
USE biblioteca;

CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255),
    autor VARCHAR(255),
    editorial VARCHAR(255),
    anio INT,
    disponible BOOLEAN
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    correo VARCHAR(255) UNIQUE,
    contrasena VARCHAR(255)
);

CREATE TABLE prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    libro_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (libro_id) REFERENCES libros(id)
);

INSERT INTO libros (titulo, autor, editorial, anio, disponible) VALUES
('Cien Años de Soledad', 'Gabriel Garcia Marquez', 'Sudamericana', 1967, TRUE),
('Don Quijote de la Mancha', 'Miguel de Cervantes', 'Francisco de Robles', 1605, TRUE),
('La Sombra del Viento', 'Carlos Ruiz Zafón', 'Planeta', 2001, TRUE),
('1984', 'George Orwell', 'Secker & Warburg', 1949, TRUE),
('El Principito', 'Antoine de Saint-Exupéry', 'Reynal & Hitchcock', 1943, TRUE),
('Matar a un Ruiseñor', 'Harper Lee', 'J.B. Lippincott & Co.', 1960, TRUE),
('Orgullo y Prejuicio', 'Jane Austen', 'T. Egerton', 1813, TRUE),
('El Gran Gatsby', 'F. Scott Fitzgerald', 'Charles Scribner\'s Sons', 1925, TRUE),
('Crimen y Castigo', 'Fyodor Dostoevsky', 'The Russian Messenger', 1866, TRUE),
('En Busca del Tiempo Perdido', 'Marcel Proust', 'Grasset', 1913, TRUE),
('Ulises', 'James Joyce', 'Sylvia Beach', 1922, TRUE),
('La Odisea', 'Homero', 'Unknown', -800, TRUE),
('Madame Bovary', 'Gustave Flaubert', 'Revue de Paris', 1856, TRUE),
('El Retrato de Dorian Gray', 'Oscar Wilde', 'Lippincott\'s Monthly Magazine', 1890, TRUE),
('Fahrenheit 451', 'Ray Bradbury', 'Ballantine Books', 1953, TRUE),
('El Hobbit', 'J.R.R. Tolkien', 'George Allen & Unwin', 1937, TRUE),
('Las Aventuras de Huckleberry Finn', 'Mark Twain', 'Chatto & Windus', 1884, TRUE),
('Drácula', 'Bram Stoker', 'Archibald Constable and Company', 1897, TRUE),
('El Nombre de la Rosa', 'Umberto Eco', 'Bompiani', 1980, TRUE),
('Cumbres Borrascosas', 'Emily Brontë', 'Thomas Cautley Newby', 1847, TRUE),
('El Señor de los Anillos', 'J.R.R. Tolkien', 'George Allen & Unwin', 1954, TRUE),
('Los Miserables', 'Victor Hugo', 'A. Lacroix, Verboeckhoven & Cie', 1862, TRUE),
('El Código Da Vinci', 'Dan Brown', 'Doubleday', 2003, TRUE),
('La Metamorfosis', 'Franz Kafka', 'Kurt Wolff Verlag', 1915, TRUE),
('El Alquimista', 'Paulo Coelho', 'HarperTorch', 1988, TRUE);