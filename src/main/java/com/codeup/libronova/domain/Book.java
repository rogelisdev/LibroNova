package com.codeup.libronova.domain;

public class Book {
    private int id;
    private String isbn;
    private String tittle;
    private String author;
    private int stock;
    private boolean available;

    public Book(){};
    public Book(String isbn, String tittle, String author, int stock, boolean available) {
        this.isbn = isbn;
        this.tittle = tittle;
        this.author = author;
        this.stock = stock;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}