package com.codeup.libronova.service.implement;
import com.codeup.libronova.dao.implement.BookDAOImpl;
import com.codeup.libronova.domain.Book;
import com.codeup.libronova.exception.BookServiceException;
import com.codeup.libronova.service.BookService;
import com.codeup.libronova.validation.implement.BookValidationImpl;

import javax.swing.*;
import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDAOImpl dao;
    private final BookValidationImpl validation;

    public BookServiceImpl(BookDAOImpl dao, BookValidationImpl validation) {
        this.validation = validation;
        this.dao = dao;
    }

    @Override
    public void addNewBook(Book book) {
        if (book == null) {
            throw new BookServiceException("Book data cannot be null.");
        }
        if (!validation.isValidIsbn(book.getIsbn())) {
            throw new BookServiceException("Invalid ISBN: The ISBN code cannot be empty.");
        }
        if (!validation.isValidTittle(book.getTittle())) {
            throw new BookServiceException("Invalid title: The book title cannot be empty.");
        }
        if (!validation.isValidAuthor(book.getAuthor())) {
            throw new BookServiceException("Invalid author: The author name cannot be empty.");
        }
        if (!validation.isValidStock(book.getStock())) {
            throw new BookServiceException("Invalid stock: Quantity must be greater than zero.");
        }
        if (!validation.isValidAvailable()) {
            throw new BookServiceException("Invalid availability status.");
        }

        dao.addNewBook(book);
    }

    @Override
    public Book findById(int id) {
        if (id <= 0) {
            throw new BookServiceException("Invalid ID: ID must be a positive number.");
        }

        Book book = dao.findById(id);

        if (book == null) {
            throw new BookServiceException("Book not found for ID: " + id);
        }
        return book;
    }

    @Override
    public List<Book> listAllBooks() {
        return dao.listAllBooks();
    }

    @Override
    public boolean updateBookStock(Book book) {
        if (book == null) {
            throw new BookServiceException("Book data cannot be null for update.");
        }

        Book existingProduct = dao.findById(book.getId());
        if (existingProduct == null) {
            throw new BookServiceException("Update failed: Book with ID " + book.getId() + " does not exist.");
        }

        if (!validation.isValidStock(book.getStock())) {
            throw new BookServiceException("Invalid stock value provided for update.");
        }

        dao.updateBookStock(book);

        return true;
    }

    @Override
    public void deleteBook(int id) {
        if (id <= 0) {
            throw new BookServiceException("Invalid ID: ID must be a positive number.");
        }

        Book bookToDelete = dao.findById(id);
        if (bookToDelete == null) {
            throw new BookServiceException("Deletion failed: Book with ID " + id + " does not exist.");
        }

        dao.deleteBook(id);
    }
}
