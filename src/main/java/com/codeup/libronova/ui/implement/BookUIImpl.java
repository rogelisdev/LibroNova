package com.codeup.libronova.ui.implement;

import com.codeup.libronova.dao.implement.BookDAOImpl;
import com.codeup.libronova.domain.Book;
import com.codeup.libronova.exception.BookServiceException;
import com.codeup.libronova.exception.BookUIException;
import com.codeup.libronova.service.implement.BookServiceImpl;
import com.codeup.libronova.ui.BookUI;
import com.codeup.libronova.validation.implement.BookValidationImpl;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.io.*;

public class BookUIImpl implements BookUI {


    private final BookValidationImpl validation = new BookValidationImpl();
    private final BookDAOImpl dao = new BookDAOImpl();
    private final BookServiceImpl service = new BookServiceImpl(dao, validation);

    @Override
    public void addBook() {
        try {
            // 1. ISBN
            String isbn = JOptionPane.showInputDialog("Insert ISBN: ");
            if (isbn == null || isbn.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ISBN entry canceled or is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Title
            String tittle = JOptionPane.showInputDialog("Insert title: ");
            if (tittle == null || tittle.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Title entry canceled or is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Author
            String author = JOptionPane.showInputDialog("Insert author name: ");
            if (author == null || author.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Author entry canceled or is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 4. Stock
            String stockInput = JOptionPane.showInputDialog("Insert stock: ");
            if (stockInput == null || stockInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Stock entry canceled or is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int stock = Integer.parseInt(stockInput);
            if (stock <= 0) {
                JOptionPane.showMessageDialog(null, "Stock must be a positive number.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 5. Availability
            String availableInput = JOptionPane.showInputDialog("Insert 'true' or 'false' for availability: ");
            if (availableInput == null || availableInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Availability entry canceled or is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean available = Boolean.parseBoolean(availableInput.toLowerCase().trim());

            // 6. Registration
            Book book = new Book(isbn, tittle, author, stock, available);

            JOptionPane.showMessageDialog(null, "Registering the book, please wait a moment", "Processing", JOptionPane.INFORMATION_MESSAGE);
            service.addNewBook(book);

            JOptionPane.showMessageDialog(null, "Successfully registered book: " + book.getTittle(), "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Stock must be a valid whole number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An unexpected error occurred during registration: " + e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void getById() {
        try {
            String idInput = JOptionPane.showInputDialog("Enter product ID: ");
            if (idInput == null) {
                JOptionPane.showMessageDialog(null, "Entry cancelled", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(idInput);
            if (id <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid ID: must be greater than 0", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Book book = service.findById(id);
            // CORRECTION: isAvailable() returns a boolean, not a float/double. Format as %b.
            String message = String.format("Product found:\nID: %d\nIsbn: %s\nTitle: %s\nAuthor: %s\nStock: %d\nAvailability: %b",
                    book.getId(), book.getIsbn(), book.getTittle(), book.getAuthor(), book.getStock(), book.isAvailable());
            JOptionPane.showMessageDialog(null, message, "Product Found", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID must be a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (BookServiceException e) {
            // Catch BookServiceException (thrown from service layer)
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateStock() {
        Book book;
        seeAllBooks();

        try {
            String idInput = JOptionPane.showInputDialog("Enter the ID of the product to update its stock: ");
            if (idInput == null) return;

            int id = Integer.parseInt(idInput);

            book = service.findById(id); // service.findById will throw BookServiceException if not found

            String stockInput = JOptionPane.showInputDialog("Enter new stock quantity (current is " + book.getStock() + "): ");
            if (stockInput == null) return;

            int newStock = Integer.parseInt(stockInput);
            if (newStock < 0) {
                JOptionPane.showMessageDialog(null, "Stock quantity cannot be negative.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            book.setStock(newStock);

            boolean update = service.updateBookStock(book);

            if (update) {
                JOptionPane.showMessageDialog(null, "Stock successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // This shouldn't be reached if service returns true/throws exception on error, but kept for safety.
                JOptionPane.showMessageDialog(null, "ERROR updating stock. Check system logs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ERROR: ID and stock must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (BookServiceException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An unexpected system error occurred: " + e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteBook() {
        try {
            String idInput = JOptionPane.showInputDialog("Enter ID of the product to delete: ");
            if (idInput == null) {
                JOptionPane.showMessageDialog(null, "Entry cancelled", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(idInput);
            if (id <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid ID: must be greater than 0", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Confirm deletion
            Book book = service.findById(id); // Throws exception if not found, prevents NullPointerException

            String message = String.format("Are you sure you want to delete the product?\nID: %d\nISBN: %s\nTitle: %s",
                    book.getId(), book.getIsbn(), book.getTittle());
            int confirm = JOptionPane.showConfirmDialog(null, message, "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Deletion Canceled", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            service.deleteBook(id);
            JOptionPane.showMessageDialog(null, "Product successfully removed", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID must be a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (BookServiceException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void seeAllBooks() {
        try {
            List<Book> books = service.listAllBooks();
            if (books.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are no registered products", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder message = new StringBuilder("Product list:\n\n");
            for (Book book : books) {
                // CORRECTION: isAvailable() returns a boolean, not a float/double. Format as %b.
                message.append(String.format("ID: %d, ISBN: %s, Title: %s, Author: %s, Stock: %d, Available: %b\n",
                        book.getId(), book.getIsbn(), book.getTittle(), book.getAuthor(), book.getStock(), book.isAvailable()));
            }
            JOptionPane.showMessageDialog(null, message.toString(), "Registered Books", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error listing books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void importCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select products CSV file");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));

        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "No file selected", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File csvFile = fileChooser.getSelectedFile();
        int importedCount = 0;
        int errorCount = 0;
        List<String> errorMessages = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean firstLine = true;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 5) {
                    errorCount++;
                    errorMessages.add("Line " + lineNumber + ": Invalid format (less than 5 columns)");
                    continue;
                }

                try {
                    String isbn = data[0].trim();
                    String tittle = data[1].trim();
                    String author = data[2].trim();
                    int stock = Integer.parseInt(data[3].trim());
                    boolean available = Boolean.parseBoolean(data[4].trim());

                    if (isbn.isEmpty() || tittle.isEmpty() || author.isEmpty()) {
                        errorCount++;
                        errorMessages.add("Line " + lineNumber + ": Empty ISBN, title, or author");
                        continue;
                    }
                    if (stock <= 0) {
                        errorCount++;
                        errorMessages.add("Line " + lineNumber + ": Stock must be greater than 0");
                        continue;
                    }

                    Book book = new Book(isbn,tittle, author, stock, available);
                    service.addNewBook(book);
                    importedCount++;
                } catch (NumberFormatException e) {
                    errorCount++;
                    errorMessages.add("Line " + lineNumber + ": Stock/Availability is not a valid number/boolean");
                } catch (BookServiceException e) {
                    errorCount++;
                    errorMessages.add("Line " + lineNumber + ": Validation error - " + e.getMessage());
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File I/O Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        StringBuilder message = new StringBuilder();
        message.append(String.format("Import completed: %d products added, %d errors.\n", importedCount, errorCount));
        if (errorCount > 0) {
            message.append("Error details:\n");
            int maxErrorsToShow = Math.min(errorMessages.size(), 10);
            for (int i = 0; i < maxErrorsToShow; i++) {
                message.append(errorMessages.get(i)).append("\n");
            }
            if (errorMessages.size() > maxErrorsToShow) {
                message.append("... and ").append(errorMessages.size() - maxErrorsToShow).append(" more errors.\n");
            }
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Import Result",
                errorCount > 0 ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void exportCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Products CSV File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));
        fileChooser.setSelectedFile(new File("exported_products.csv"));

        int result = fileChooser.showSaveDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "No file selected", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File csvFile = fileChooser.getSelectedFile();
        if (!csvFile.getName().toLowerCase().endsWith(".csv")) {
            csvFile = new File(csvFile.getAbsolutePath() + ".csv");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {

            writer.write("isbn,tittle,author,stock,available");
            writer.newLine();

            List<Book> books= service.listAllBooks();
            int exportedCount = 0;

            for (Book book : books) {
                try {
                    // CORRECTION: isAvailable() returns boolean, formatted as %b.
                    String line = String.format("%s,%s,%s,%d,%b",
                            escapeCSV(book.getIsbn()),
                            escapeCSV(book.getTittle()),
                            escapeCSV(book.getAuthor()),
                            book.getStock(),
                            book.isAvailable());
                    writer.write(line);
                    writer.newLine();
                    exportedCount++;
                } catch (Exception e) {
                    // Log error but continue exporting other products
                    System.err.println("Error exporting product: " + book.getTittle() + " - " + e.getMessage());
                }
            }

            String message = String.format("Export completed: %d products exported.", exportedCount);
            JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }
}