package com.codeup.libronova.validation.implement;

import com.codeup.libronova.validation.BookValidation;

public class BookValidationImpl implements BookValidation {

    @Override
    public boolean isValidIsbn(String isbn) {
        return isbn != null && !isbn.trim().isEmpty();
    }

    @Override
    public boolean isValidTittle(String tittle) {
        return tittle != null && !tittle.trim().isEmpty();
    }

    @Override
    public boolean isValidAuthor(String author) {
        return author != null && !author.trim().isEmpty();
    }

    @Override
    public boolean isValidStock(int stock) {
        return stock > 0;
    }

    @Override
    public boolean isValidAvailable() {
        return true;
    }
}
