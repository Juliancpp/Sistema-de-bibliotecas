package DataAccess.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DataAccess.MySQLDataHelper;
import DataAccess.DTO.BookDTO;
import DataAccess.DTO.UserDTO;

public class UserDAO extends MySQLDataHelper implements IDAO<UserDTO> {

    // Implementation of the abstract methods of the IDAO interface
    @Override
    public UserDTO getBy(Integer id) throws Exception {

        // Create a UserDTO object
        UserDTO user = new UserDTO();
        String query = " SELECT "
                + "  id, "
                + "  first_name, "
                + "  last_name, "
                + "  email, "
                + "  password"
                + " FROM users"
                + " WHERE id = " + id.toString();

        // Connect to the database
        try {

            Connection conn = MySQLDataHelper.openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                user = new UserDTO(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"));

            }

            // Close the connections
            rs.close();
            stmt.close();
            MySQLDataHelper.closeConnection();
        } catch (SQLException e) {
            throw new Exception("Failed to get the user", e);
        }

        return user;
    }

    // Method to get all UserDTO from the database and return them in a list
    @Override
    public List<UserDTO> getAll() throws Exception {

        // Create a list of UserDTO
        List<UserDTO> users = new ArrayList<>();

        // Create the SQL query
        String query = " SELECT "
                + "  id, "
                + "  first_name, "
                + "  last_name, "
                + "  email, "
                + "  password"
                + " FROM users";

        try {

            // Connect to the database
            Connection conn = MySQLDataHelper.openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                UserDTO user = new UserDTO(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"));
                users.add(user);

            }
            rs.close();
            stmt.close();
            MySQLDataHelper.closeConnection();
        } catch (SQLException e) {
            throw new Exception("Failed to get the users", e);

        }
        return users;
    }

    // Method to create a user in the database
    // Receives a UserDTO object
    @Override
    public boolean create(UserDTO entity) throws Exception {

        String query = " INSERT INTO users ("
                + "first_name, "
                + "last_name, "
                + "email, "
                + "password"
                + ") VALUES (?, ?, ?, ?)";

        try {

            Connection conn = MySQLDataHelper.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, entity.getFirstName());
            pstmt.setString(2, entity.getLastName());
            pstmt.setString(3, entity.getEmail());
            pstmt.setString(4, entity.getPassword());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            rs.close();
            pstmt.close();
            MySQLDataHelper.closeConnection();

            return true;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new Exception("The email is already in use. Please use another email.", e);
            }
            throw new Exception("Failed to create the user", e);
        }
    }

    // Method to update a user in the database
    // Receives a UserDTO object
    @Override
    public boolean update(UserDTO entity) throws Exception {

        String query = " UPDATE users SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "email = ?, "
                + "password = ?"
                + " WHERE id = ?";

        try {

            Connection conn = MySQLDataHelper.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, entity.getFirstName());
            pstmt.setString(2, entity.getLastName());
            pstmt.setString(3, entity.getEmail());
            pstmt.setString(4, entity.getPassword());
            pstmt.setInt(5, entity.getId());
            pstmt.executeUpdate();
            pstmt.close();
            MySQLDataHelper.closeConnection();

            return true;

        } catch (SQLException e) {
            throw new Exception("Failed to update the user", e);
        }

    }

    // Method to delete a user from the database
    // Receives an Integer id
    @Override
    public boolean delete(Integer id) throws Exception {

        String query = " DELETE FROM users WHERE id = ?";

        try {

            Connection conn = MySQLDataHelper.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            MySQLDataHelper.closeConnection();
            return true;

        } catch (SQLException e) {
            throw new Exception("Failed to delete the user", e);
        }
    }

    // Method to get a user by their email and password
    // Receives two Strings, email and password
    public UserDTO getByEmailAndPassword(String email, String password) throws Exception {

        // Create a UserDTO object
        UserDTO user = new UserDTO();
        String query = " SELECT "
                + "  id, "
                + "  first_name, "
                + "  last_name, "
                + "  email, "
                + "  password"
                + " FROM users"
                + " WHERE email = ? AND password = ?";

        try {

            Connection conn = MySQLDataHelper.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new UserDTO(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"));

            }
            rs.close();
            pstmt.close();
            MySQLDataHelper.closeConnection();

        } catch (SQLException e) {
            throw new Exception("Failed to get the user", e);
        }

        return user;
    }

    // Method to lend books
    // Receives a user id and a book id
    public boolean lendBooks(Integer userId, Integer bookId) throws Exception {

        // Create the SQL query
        String query = " INSERT INTO loans ("
                + "user_id, "
                + "book_id, "
                + "loan_date"
                + ") VALUES (?, ?, CURRENT_TIMESTAMP)";

        // Connect to the database
        try {

            Connection conn = MySQLDataHelper.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
            pstmt.close();
            MySQLDataHelper.closeConnection();

            return true;
        } catch (SQLException e) {
            throw new Exception("Failed to lend the book", e);
        }
    }

    // Method to get the books borrowed by a user
    // Receives a user id
    public List<BookDTO> getBorrowedBooks(Integer userId) throws Exception {

        // Create a list of BookDTO
        List<BookDTO> books = new ArrayList<>();

        // Create the SQL query
        String query = " SELECT "
                + "  id, "
                + "  title, "
                + "  author, "
                + "  publisher, "
                + "  year"
                + " FROM books";

        // Connect to the database
        try {

            // Connect to the database
            Connection conn = MySQLDataHelper.openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Add the books to the list
            while (rs.next()) {
                BookDTO book = new BookDTO(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("year"));
                books.add(book);
            }

            rs.close();
            stmt.close();
            MySQLDataHelper.closeConnection();

        } catch (SQLException e) {
            throw new Exception("Failed to get the books", e);
        }

        return books;
    }

    // Method to return a book borrowed by a user and update availability
    // Receives a user id and a book id
    public boolean returnBook(Integer userId, Integer bookId) throws Exception {

        // Create the SQL query
        String query = " UPDATE books SET"
                + " available = 1"
                + " WHERE id = ?";

        // Connect to the database
        try {

            Connection conn = MySQLDataHelper.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();

            // Update the loans table to add the return date
            query = " UPDATE loans SET"
                    + " return_date = CURRENT_TIMESTAMP"
                    + " WHERE user_id = ? AND book_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();

            // Close the connections
            pstmt.close();
            MySQLDataHelper.closeConnection();

            return true;
        } catch (SQLException e) {
            throw new Exception("Failed to return the book", e);
        }
    }
}