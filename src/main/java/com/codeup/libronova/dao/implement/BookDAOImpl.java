package com.codeup.libronova.dao.implement;

import com.codeup.libronova.dao.BookDAO;
import com.codeup.libronova.db.ConnectionDB;
import com.codeup.libronova.domain.Book;
import com.codeup.libronova.exception.BookDAOException;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    @Override
    public void addNewBook(Book book) {
        String sql = "INSERT INTO book (isbn, tittle, author, stock, available) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = ConnectionDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTittle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getStock());
            ps.setBoolean(5, book.isAvailable());

            int fila = ps.executeUpdate();
            if(fila > 0){
                System.out.println("The book has been added correctly " + book.getTittle());
            }
        } catch (SQLException e) {
            throw  new BookDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
    }


    @Override
    public Book findById(int id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        Book book = null;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, id);


            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String tittle = rs.getString("tittle");
                    String author = rs.getString("author");
                    int stock = rs.getInt("stock");
                    boolean available = rs.getBoolean("available");

                    book = new Book(isbn, tittle, author, stock, available);
                    book.setId(rs.getInt("id"));

                    return book;
                }
            }
        } catch (SQLException e) {
            throw new BookDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    @Override
    public List<Book> listAllBooks() {
        String sql = "SELECT * FROM book";
        List<Book> array = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String isbn = rs.getString("isbn");
                String tittle = rs.getString("tittle");
                String author = rs.getString("author");
                int stock = rs.getInt("stock");
                boolean available = rs.getBoolean("available");

                Book book = new Book(isbn, tittle,author,stock,available);
                book.setId(id);
                array.add(book);
            }

        } catch (SQLException e) {
            throw new BookDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
        return array;
    }

    @Override
    public boolean updateBookStock(Book book) {
        String sql = "UPDATE book SET stock = ? WHERE id = ?";
        try(Connection conn = ConnectionDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){

            ps.setInt(1, book.getId());
            ps.setInt(2, book.getId());

            int fila = ps.executeUpdate();
            if(fila > 0){
                System.out.println("The number of books has been updated correctly" + book.getStock());
                return true;
            }
        } catch (SQLException e) {
            throw new BookDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }

    @Override
    public void deleteBook(int id) {
        String sql = "DELETE FROM book WHERE id = ?";
        Book book = null;
        try(Connection conn = ConnectionDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);

            int fila = ps.executeUpdate();
            if(fila > 0){
                System.out.println("The book has been successfully deleted" + book.getTittle());
            }
        } catch (SQLException e) {
            throw new BookDAOException("Check the values recorded in the database, try again." + e.getMessage() + JOptionPane.WARNING_MESSAGE);
        }
    }


    @Override
    public Book findByIsbn(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM book WHERE isbn = ?";
        Book book = null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getIsbn());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setTittle(rs.getString("tittle"));
                    book.setAuthor(rs.getString("author"));
                    book.setStock(rs.getInt("stock"));
                }
            }
        }
        return book;
    }
}
