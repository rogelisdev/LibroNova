package com.codeup.libronova.dao;

import com.codeup.libronova.domain.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
    void addNewBook(Book book);
    Book findById(int id);
    List<Book> listAllBooks();
    boolean updateBookStock(Book book);
    void deleteBook(int id);
    Book findByIsbn(Connection conn, int id) throws SQLException;
}
