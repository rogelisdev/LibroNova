package com.codeup.libronova.domain;

import java.sql.Timestamp;

public class Loan {
    private int id;
    private int id_partneree;
    private String isbn_book;
    private Timestamp loan_date;
    private Timestamp return_date;
    private String statu_load;

    public Loan(){};
    public Loan(int id_partneree, String isbn_book, Timestamp loan_date, Timestamp return_date, String statu_load) {
        this.id_partneree = id_partneree;
        this.isbn_book = isbn_book;
        this.loan_date = loan_date;
        this.return_date = return_date;
        this.statu_load = statu_load;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_partneree() {
        return id_partneree;
    }

    public void setId_partneree(int id_partneree) {
        this.id_partneree = id_partneree;
    }

    public String getIsbn_book() {
        return isbn_book;
    }

    public void setIsbn_book(String isbn_book) {
        this.isbn_book = isbn_book;
    }

    public Timestamp getLoan_date() {
        return loan_date;
    }

    public void setLoan_date(Timestamp loan_date) {
        this.loan_date = loan_date;
    }

    public Timestamp getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Timestamp return_date) {
        this.return_date = return_date;
    }

    public String getStatu_load() {
        return statu_load;
    }

    public void setStatu_load(String statu_load) {
        this.statu_load = statu_load;
    }
}
