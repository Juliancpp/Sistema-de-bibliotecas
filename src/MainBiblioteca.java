import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainBiblioteca {

    // Expresión regular para validar un correo electrónico
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    // incio de sesion
    public static boolean iniciarSesion(String correo, String contrasena) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, correo);
                stmt.setString(2, contrasena);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Sesión iniciada correctamente.");
                    return true;
                } else {
                    System.out.println("Correo o contraseña incorrectos.");
                }
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al iniciar sesión: " + e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {

        // bienvenida a la biblioteca
        System.out.println("Bienvenido a la biblioteca");

        // solicitar si se desea registrar un usuario o iniciar sesion hasta que se
        // ingrese una opcion valida
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while (true) {
            System.out.println("¿Deseas registrarte o iniciar sesión?");
            System.out.println("1. Registrarte");
            System.out.println("2. Iniciar sesión");
            opcion = scanner.nextInt(); // Leer la opción seleccionada
            scanner.nextLine();
            if (opcion == 1 || opcion == 2) {
                break;
            } else {
                System.out.println("Opción no válida.");
            }
        }
        String correo = null;
        String contrasena = null;
        if (opcion == 1) {

            // ingreso de datos del usuario
            System.out.println("Ingresa tu nombre:");
            String nombre = scanner.nextLine();
            System.out.println("Ingresa tu apellido:");
            String apellido = scanner.nextLine();
            // Solicitar el correo hasta que sea válido
            while (true) {
                System.out.println("Ingresa tu correo:");
                correo = scanner.nextLine();
                // Validar el formato del correo
                Matcher matcher = pattern.matcher(correo);
                if (matcher.matches()) {
                    break;
                } else {
                    System.out.println("El correo no tiene un formato válido.");
                }
            }
            System.out.println("Ingresa tu contraseña:");
            contrasena = scanner.nextLine();

            // Verificar si el correo ya esta registrado si ya esta registrado se le pide
            // iniciar sesion y si no se registra
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                try {
                    String sql = "SELECT * FROM usuarios WHERE correo = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, correo);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("El correo ya está registrado.");
                        // Pedir si desea iniciar sesion si el correo ya esta registrado
                        System.out.println("¿Deseas iniciar sesión?");
                        System.out.println("1. Sí");
                        System.out.println("2. No");
                        opcion = scanner.nextInt(); // Leer la opción seleccionada
                        scanner.nextLine();
                        if (opcion == 1) {
                            // Solicitar la contraseña hasta que sea correcta
                            while (true) {
                                System.out.println("Ingresa tu contraseña:");
                                contrasena = scanner.nextLine();
                                if (iniciarSesion(correo, contrasena)) {
                                    break;
                                }
                            }
                        }
                    } else {
                        Usuario.registrarUsuario(nombre, apellido, correo, contrasena);
                    }
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error al verificar el correo: " + e.getMessage());
                }
            }
        } else if (opcion == 2) {
            // Inicio de sesión

            // Ingreso de correo de hasta que sea valido y las credenciales sean correctas
            while (true) {

                // Solicitar el correo hasta que sea válido
                while (true) {
                    System.out.println("Ingresa tu correo:");
                    correo = scanner.nextLine();
                    // Validar el formato del correo
                    Matcher matcher = pattern.matcher(correo);
                    if (matcher.matches()) {
                        break;
                    } else {
                        System.out.println("El correo no tiene un formato válido.");
                    }
                }
                System.out.println("Ingresa tu contraseña:");
                contrasena = scanner.nextLine();

                // Verificar si las credenciales son correctas
                if (iniciarSesion(correo, contrasena)) {
                    break;
                }
            }

        } else {

            System.out.println("Opción no válida.");

        }

        // Obtener el ID del usuario que inició sesión
        int usuarioId = Usuario.obtenerIdUsuario(correo);

        // Solicitar si desea devolver el libro o que se le prestara un libro
        while (true) {
            System.out.println("¿Deseas que se te preste algun libro o devolver un libro?");
            System.out.println("1. Prestar libro");
            System.out.println("2. Devolver libro");
            opcion = scanner.nextInt(); // Leer la opción seleccionada
            scanner.nextLine();
            if (opcion == 1 || opcion == 2) {
                break;
            } else {
                System.out.println("Opción no válida.");
            }
        }

        if (opcion == 1) {

            // Mostrar libros de la base de datos
            System.out.println("Libros disponibles:");
            Libro.mostrarLibros();

            // Solicitar el ID del libro que se desea prestar
            System.out.println("Ingresa el ID del libro que deseas que se te preste:");
            int libroId = scanner.nextInt();
            scanner.nextLine();
            if (Libro.prestarLibro(usuarioId, libroId)) {
            } else {
                System.out.println("No se pudo prestar el libro.");
            }
        } else if (opcion == 2) {

            // verificar si el usuario tiene libros prestados
            if (Usuario.tieneLibroPrestado(usuarioId)) {
                // Mostrar libros prestados al usuario
                System.out.println("Libros prestados:");
                Libro.mostrarLibrosPrestados(usuarioId);

                // Solicitar el ID del libro que se desea devolver
                System.out.println("Ingresa el ID del libro que deseas devolver:");
                int libroId = scanner.nextInt();
                scanner.nextLine();
                if (Libro.devolverLibro(libroId)) {
                    System.out.println("Muchas gracias por usar la biblioteca");
                } else {
                    System.out.println("No se pudo devolver el libro. \nIntente mas tarde");
                }
            } else {
                System.out.println("No tienes libros prestados. \nMuchas gracias por usar la biblioteca");
            }
        }
    }
}