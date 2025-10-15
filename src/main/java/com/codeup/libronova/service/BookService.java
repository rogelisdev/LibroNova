package com.codeup.libronova.service;

import com.codeup.libronova.domain.Book;

import java.util.List;

public interface BookService {
    void addNewBook(Book book);
    Book findById(int id);
    List<Book> listAllBooks();
    boolean updateBookStock(Book book);
    void deleteBook(int id);
}
