package com.codeup.libronova.ui;

public interface BookUI {
    void addBook();
    void getById();
    void updateStock();
    void deleteBook();
    void seeAllBooks();
    void importCSV();
    void exportCSV();
    String escapeCSV(String string);
}
