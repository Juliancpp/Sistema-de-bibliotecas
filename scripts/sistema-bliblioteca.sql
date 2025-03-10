DROP DATABASE IF EXISTS library;
CREATE DATABASE library;
USE library;
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    publisher VARCHAR(255),
    year INT,
    available BOOLEAN
);
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);
CREATE TABLE loans (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    loan_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
INSERT INTO books (title, author, publisher, year, available)
VALUES (
        'One Hundred Years of Solitude',
        'Gabriel Garcia Marquez',
        'Sudamericana',
        1967,
        TRUE
    ),
    (
        'Don Quixote',
        'Miguel de Cervantes',
        'Francisco de Robles',
        1605,
        TRUE
    ),
    (
        'The Shadow of the Wind',
        'Carlos Ruiz Zafón',
        'Planeta',
        2001,
        TRUE
    ),
    (
        '1984',
        'George Orwell',
        'Secker & Warburg',
        1949,
        TRUE
    ),
    (
        'The Little Prince',
        'Antoine de Saint-Exupéry',
        'Reynal & Hitchcock',
        1943,
        TRUE
    ),
    (
        'To Kill a Mockingbird',
        'Harper Lee',
        'J.B. Lippincott & Co.',
        1960,
        TRUE
    ),
    (
        'Pride and Prejudice',
        'Jane Austen',
        'T. Egerton',
        1813,
        TRUE
    ),
    (
        'The Great Gatsby',
        'F. Scott Fitzgerald',
        'Charles Scribner\'s Sons',
        1925,
        TRUE
    ),
    (
        'Crime and Punishment',
        'Fyodor Dostoevsky',
        'The Russian Messenger',
        1866,
        TRUE
    ),
    (
        'In Search of Lost Time',
        'Marcel Proust',
        'Grasset',
        1913,
        TRUE
    ),
    (
        'Ulysses',
        'James Joyce',
        'Sylvia Beach',
        1922,
        TRUE
    ),
    ('The Odyssey', 'Homer', 'Unknown', -800, TRUE),
    (
        'Madame Bovary',
        'Gustave Flaubert',
        'Revue de Paris',
        1856,
        TRUE
    ),
    (
        'The Picture of Dorian Gray',
        'Oscar Wilde',
        'Lippincott\'s Monthly Magazine',
        1890,
        TRUE
    ),
    (
        'Fahrenheit 451',
        'Ray Bradbury',
        'Ballantine Books',
        1953,
        TRUE
    ),
    (
        'The Hobbit',
        'J.R.R. Tolkien',
        'George Allen & Unwin',
        1937,
        TRUE
    ),
    (
        'The Adventures of Huckleberry Finn',
        'Mark Twain',
        'Chatto & Windus',
        1884,
        TRUE
    ),
    (
        'Dracula',
        'Bram Stoker',
        'Archibald Constable and Company',
        1897,
        TRUE
    ),
    (
        'The Name of the Rose',
        'Umberto Eco',
        'Bompiani',
        1980,
        TRUE
    ),
    (
        'Wuthering Heights',
        'Emily Brontë',
        'Thomas Cautley Newby',
        1847,
        TRUE
    ),
    (
        'The Lord of the Rings',
        'J.R.R. Tolkien',
        'George Allen & Unwin',
        1954,
        TRUE
    ),
    (
        'Les Misérables',
        'Victor Hugo',
        'A. Lacroix, Verboeckhoven & Cie',
        1862,
        TRUE
    ),
    (
        'The Da Vinci Code',
        'Dan Brown',
        'Doubleday',
        2003,
        TRUE
    ),
    (
        'The Metamorphosis',
        'Franz Kafka',
        'Kurt Wolff Verlag',
        1915,
        TRUE
    ),
    (
        'The Alchemist',
        'Paulo Coelho',
        'HarperTorch',
        1988,
        TRUE
    );