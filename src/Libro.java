import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Libro {

    // Método para verificar si un libro está disponible
    public static boolean estaDisponible(int libroId) {
        Connection conn = DatabaseConnection.getConnection();
        boolean disponible = false;

        if (conn != null) {
            try {
                String sql = "SELECT disponible FROM libros WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, libroId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    disponible = rs.getBoolean("disponible");
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al verificar disponibilidad del libro: " + e.getMessage());
            }
        }

        return disponible;
    }

    // Método para prestar un libro
    public static boolean prestarLibro(int usuarioId, int libroId) {
        if (!estaDisponible(libroId)) {
            System.out.println("El libro no está disponible.");
            return false;
        }

        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            try {
                // Actualizar disponibilidad del libro
                String updateLibro = "UPDATE libros SET disponible = FALSE WHERE id = ?";
                PreparedStatement stmt1 = conn.prepareStatement(updateLibro);
                stmt1.setInt(1, libroId);
                stmt1.executeUpdate();
                stmt1.close();

                // Registrar el préstamo en la tabla `prestamos`
                String insertPrestamo = "INSERT INTO prestamos (usuario_id, libro_id) VALUES (?, ?)";
                PreparedStatement stmt2 = conn.prepareStatement(insertPrestamo);
                stmt2.setInt(1, usuarioId);
                stmt2.setInt(2, libroId);
                stmt2.executeUpdate();
                stmt2.close();

                conn.close();
                System.out.println("Libro prestado correctamente.");
                return true;
            } catch (SQLException e) {
                System.out.println("Error al prestar el libro: " + e.getMessage());
            }
        }
        return false;
    }

    // Método para devolver un libro
    public static boolean devolverLibro(int libroId) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            try {
                // Actualizar disponibilidad del libro y borra el registro de la tabla
                // `prestamos`
                String deletePrestamo = "DELETE FROM prestamos WHERE libro_id = ?";
                PreparedStatement stmt1 = conn.prepareStatement(deletePrestamo);
                stmt1.setInt(1, libroId);
                String updateLibro = "UPDATE libros SET disponible = TRUE WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(updateLibro);
                stmt.setInt(1, libroId);
                stmt.executeUpdate();
                stmt.close();

                conn.close();
                System.out.println("Libro devuelto correctamente.");
                return true;
            } catch (SQLException e) {
                System.out.println("Error al devolver el libro: " + e.getMessage());
            }
        }
        return false;
    }

    // Mostrar libro disponibles
    public static void mostrarLibros() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM libros WHERE disponible = TRUE";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Título: " + rs.getString("titulo"));
                    System.out.println("Autor: " + rs.getString("autor"));
                    System.out.println("Editorial: " + rs.getString("editorial"));
                    System.out.println("Año de publicación: " + rs.getInt("anio"));
                    System.out.println();
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al mostrar los libros: " + e.getMessage());
            }
        }
    }

    // mostar libros prestados a un usuario
    public static void mostrarLibrosPrestados(int usuarioId) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT libros.id, libros.titulo, libros.autor, libros.editorial, libros.anio FROM libros INNER JOIN prestamos ON libros.id = prestamos.libro_id WHERE prestamos.usuario_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, usuarioId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Título: " + rs.getString("titulo"));
                    System.out.println("Autor: " + rs.getString("autor"));
                    System.out.println("Editorial: " + rs.getString("editorial"));
                    System.out.println("Año de publicación: " + rs.getInt("anio"));
                    System.out.println();
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al mostrar los libros prestados: " + e.getMessage());
            }
        }
    }
}
