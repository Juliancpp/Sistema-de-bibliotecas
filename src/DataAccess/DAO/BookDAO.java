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

public class BookDAO extends MySQLDataHelper implements IDAO<BookDTO> {

    @Override
    public BookDTO getBy(Integer id) throws Exception {
        BookDTO book = new BookDTO();
        String query = " SELECT "
                + "  id, "
                + "  title, "
                + "  author, "
                + "  publisher, "
                + "  year, "
                + "  available"
                + " FROM books"
                + " WHERE id = " + id.toString();

        try {
            Connection conn = MySQLDataHelper.openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                book = new BookDTO(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("year"),
                        rs.getBoolean("available"));

            }
            rs.close();
            stmt.close();
            MySQLDataHelper.closeConnection();
        } catch (SQLException e) {
            throw new Exception("Failed to get the book", e);
        }
        return book;
    }

    @Override
    public List<BookDTO> getAll() throws Exception {
        List<BookDTO> books = new ArrayList<>();
        String query = " SELECT "
                + "  id, "
                + "  title, "
                + "  author, "
                + "  publisher, "
                + "  year, "
                + "  available"
                + " FROM books";

        try {
            Connection conn = MySQLDataHelper.openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                BookDTO book = new BookDTO(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("year"),
                        rs.getBoolean("available"));
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

    @Override
    public boolean create(BookDTO entity) throws Exception {
        String query = " INSERT INTO books ("
                + "  title, "
                + "  author, "
                + "  publisher, "
                + "  year, "
                + "  available"
                + ") VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = MySQLDataHelper.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, entity.getTitle());
            pstmt.setString(2, entity.getAuthor());
            pstmt.setString(3, entity.getPublisher());
            pstmt.setInt(4, entity.getYear());
            pstmt.setBoolean(5, entity.getAvailable());
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
            throw new Exception("Failed to create the book", e);
        }
    }

    @Override
    public boolean update(BookDTO entity) throws Exception {

        String query = " UPDATE books SET"
                + "  title = ?, "
                + "  author = ?, "
                + "  publisher = ?, "
                + "  year = ?, "
                + "  available = ?"
                + " WHERE id = ?";

        try {
            Connection conn = MySQLDataHelper.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, entity.getTitle());
            pstmt.setString(2, entity.getAuthor());
            pstmt.setString(3, entity.getPublisher());
            pstmt.setInt(4, entity.getYear());
            pstmt.setBoolean(5, entity.getAvailable());
            pstmt.setInt(6, entity.getId());
            pstmt.executeUpdate();
            pstmt.close();
            MySQLDataHelper.closeConnection();

            return true;
        } catch (SQLException e) {
            throw new Exception("Failed to update the book", e);
        }
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        String query = " DELETE FROM books WHERE id = ?";

        try {
            Connection conn = MySQLDataHelper.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            MySQLDataHelper.closeConnection();

            return true;
        } catch (SQLException e) {
            throw new Exception("Failed to delete the book", e);
        }
    }

    public List<BookDTO> getAllAvailable() throws Exception {
        List<BookDTO> books = new ArrayList<>();
        String query = " SELECT "
                + "  id, "
                + "  title, "
                + "  author, "
                + "  publisher, "
                + "  year, "
                + "  available"
                + " FROM books"
                + " WHERE available = 1";

        try {
            Connection conn = MySQLDataHelper.openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                BookDTO book = new BookDTO(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("year"),
                        rs.getBoolean("available"));
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
}
