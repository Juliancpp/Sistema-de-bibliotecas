import java.util.List;
import java.util.Scanner;

import DataAccess.DAO.BookDAO;
import DataAccess.DTO.BookDTO;
import DataAccess.DAO.UserDAO;
import DataAccess.DTO.UserDTO;

public class App {
    public static void main(String[] args) throws Exception {

        // Welcome to the library system
        System.out.println("\tWelcome to the library system");
        int option = 0;
        Scanner scanner = new Scanner(System.in);
        UserDTO user = null;
        String email = null;
        String password = null;
        UserDAO userDAO = new UserDAO();

        while (option != 3) {
            System.out.println("1. Log in");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // Log in
                    while (user == null || user.getEmail() == null) {

                        System.out.print("Enter your email: ");
                        email = scanner.nextLine();

                        System.out.print("Enter your password: ");
                        password = scanner.nextLine();

                        user = userDAO.getByEmailAndPassword(email, password);

                        if (user != null && user.getEmail() != null) {
                            System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName());
                        } else {
                            System.out.println("Incorrect email or password");
                        }
                    }
                    break;
                case 2:
                    // User registration
                    while (user == null || user.getEmail() == null) {

                        System.out.print("Enter your first name: ");
                        String firstName = scanner.nextLine();

                        System.out.print("Enter your last name: ");
                        String lastName = scanner.nextLine();

                        System.out.print("Enter your email: ");
                        email = scanner.nextLine();

                        System.out.print("Enter your password: ");
                        password = scanner.nextLine();

                        user = new UserDTO(firstName, lastName, email, password);
                        userDAO.create(user);

                        System.out.println("User registered successfully");
                    }
                    break;
                case 3:
                    System.out.println("Thank you for using the library system");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        // Create menu to chose between lending a book or returning a book

        while (option != 3) {

            System.out.println("1. Lend a book");
            System.out.println("2. Return a book");
            System.out.println("3. Exit");
            System.out.print("Enter an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // Lend a book
                    BookDAO bookDAO = new BookDAO();
                    List<BookDTO> books = bookDAO.getAll();
                    System.out.println("Books available:");
                    // Listar libros
                    LibroDAO libroDAO = new LibroDAO();
                    List<LibroDTO> libros = libroDAO.getAll();
                    libros.forEach(libro -> System.out.println(libro));
                    System.out.print("Enter the book id: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    BookDTO book = bookDAO.getById(bookId);
                    if (book != null && book.getAvailable()) {
                        book.setAvailable(false);
                        bookDAO.update(book);
                        System.out.println("Book lent successfully");
                    } else {
                        System.out.println("Book not available");
                    }
                    break;
                case 2:
                    // Return a book
                    bookDAO = new BookDAO();
                    books = bookDAO.getAll();
                    System.out.println("Books lent by the user:");
                    for (BookDTO book1 : books) {
                        if (!book1.getAvailable()) {
                            System.out.println(book1.getId() + ". " + book1.getTitle() + " by " + book1.getAuthor());
                        }
                    }
                    System.out.print("Enter the book id: ");
                    bookId = scanner.nextInt();
                    scanner.nextLine();
                    book = bookDAO.getById(bookId);
                    if (book != null && !book.getAvailable()) {
                        book.setAvailable(true);
                        bookDAO.update(book);
                        System.out.println("Book returned successfully");
                    } else {
                        System.out.println("Book not lent by the user");
                    }
                    break;
                case 3:
                    System.out.println("Thank you for using the library system");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }

        }

        scanner.close();
    }
}
