package com.codeup.libronova.validation;

public interface BookValidation {
    boolean isValidIsbn(String isbn);
    boolean isValidTittle(String tittle);
    boolean isValidAuthor(String author);
    boolean isValidStock(int stock);
    boolean isValidAvailable();
}
