package com.codeup.libronova.ui.view;

import com.codeup.libronova.ui.implement.BookUIImpl;

import javax.swing.*;

public class MenuMain {
    private final BookUIImpl bookUI;
    public MenuMain() {
        this.bookUI = new BookUIImpl();
    }

    public void showMenu() {
        while (true) {
            String[] options = {
                    "Add Product",
                    "Show All Products",
                    "Update Product",
                    "Delete Product",
                    "Search Product by ID",
                    "Import Products from CSV",
                    "Export Products to CSV",
                    "Exit"
            };

            String choice = (String) JOptionPane.showInputDialog(
                    null,
                    "Select an option:",
                    "Product Management Menu",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == null || choice.equals("Exit")) {
                JOptionPane.showMessageDialog(null, "Thank you for using the system!", "Exit", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            switch (choice) {
                case "Add Product":
                    bookUI.addBook();
                    break;
                case "Show All Products":
                    bookUI.seeAllBooks();
                    break;
                case "Update Product":
                    bookUI.updateStock();
                    break;
                case "Delete Product":
                    bookUI.deleteBook();
                    break;
                case "Search Product by ID":
                    bookUI.getById();
                    break;
                case "Import Products from CSV":
                    bookUI.importCSV();
                    break;
                case "Export Products to CSV":
                    bookUI.exportCSV();
                    break;
            }
        }
    }
}
