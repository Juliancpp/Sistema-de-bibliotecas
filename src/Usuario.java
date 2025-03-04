import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Usuario {

    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;

    public Usuario(String nombre, String apellido, String correo, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public static void registrarUsuario(String nombre, String apellido, String correo, String contrasena) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            try {
                String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasena) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nombre);
                stmt.setString(2, apellido);
                stmt.setString(3, correo);
                stmt.setString(4, contrasena);
                stmt.executeUpdate();
                System.out.println("Usuario registrado correctamente.");
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al registrar el usuario: " + e.getMessage());
            }
        }
    }

    public static int obtenerIdUsuario(String correo) {
        int idUsuario = -1; // Valor por defecto si no se encuentra el usuario

        // Obtener conexión
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT id FROM usuarios WHERE correo = ?")) {

            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idUsuario = rs.getInt("id"); // Obtener el ID del usuario
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el ID del usuario: " + e.getMessage());
        }

        return idUsuario; // Devuelve el ID o -1 si no existe
    }

    // Metodo saber si el usuario mediante su id tiene un libro prestado
    public static boolean tieneLibroPrestado(int usuarioId) {
        boolean tieneLibro = false;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM prestamos WHERE usuario_id = ?")) {

            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tieneLibro = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al comprobar si el usuario tiene un libro prestado: " + e.getMessage());
        }

        return tieneLibro;
    }
}
