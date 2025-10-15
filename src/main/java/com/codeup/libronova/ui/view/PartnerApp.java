package com.codeup.libronova.ui.view;

import com.codeup.libronova.domain.User;
import com.codeup.libronova.ui.implement.PartnerUIImpl;
import com.codeup.libronova.util.AuthManager;
import com.codeup.libronova.util.Role;

import javax.swing.*;
import java.util.Objects;


public class PartnerApp {
    private final AuthManager authManager = new AuthManager();
    private final PartnerUIImpl partnerUI = new PartnerUIImpl();

    private final MenuMain menuBook = new MenuMain();
    private User currentUser = null;

    public void start() {
        while (currentUser == null) {
            showLogin();
        }
        showMainMenu();
    }

    private void showLogin() {
        String username = JOptionPane.showInputDialog("--- LOGIN ---\nEnter username:");
        if (username == null) {
            JOptionPane.showMessageDialog(null, "Application closed.", "Exit", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        String password = JOptionPane.showInputDialog("Enter password:");
        if (password == null) {
            JOptionPane.showMessageDialog(null, "Login cancelled.", "Exit", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            currentUser = authManager.login(username, password);
            JOptionPane.showMessageDialog(null, "Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Authentication error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMainMenu() {
        String choice = "";
        while (!Objects.equals(choice, "Exit")) {

            String[] options = buildMenuOptions(currentUser.getRole());

            choice = (String) JOptionPane.showInputDialog(
                    null,
                    "Select an option (" + currentUser.getRole() + "):",
                    "MAIN MENU",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == null || choice.equals("Exit")) {
                choice = "Exit";
                continue;
            }

            try {
                processMenuChoice(choice);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error processing action: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        currentUser = null;
        JOptionPane.showMessageDialog(null, "Session closed. Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
    }

    private String[] buildMenuOptions(Role role) {
        if (role == Role.ADMIN) {
            return new String[]{
                    "1. List All Partners (R)",
                    "2. Search Partner by ID (R)",
                    "3. Add New Partner (C)",
                    "4. Update Partner Phone (U)",
                    "5. Delete Partner (D)",
                    "6. Manage Books (ADMIN ONLY)",
                    "Exit"
            };
        } else if (role == Role.PARTNER) {
            return new String[]{
                    "1. List All Partners (R)",
                    "2. Search Partner by ID (R)",
                    "Exit"
            };
        } else {
            return new String[]{"Exit"};
        }
    }

    private void processMenuChoice(String choice) {

        switch (choice) {
            case "1. List All Partners (R)":
                partnerUI.listAllPartner();
                break;
            case "2. Search Partner by ID (R)":
                String idInput = JOptionPane.showInputDialog("Enter partner ID to search:");
                if (idInput != null) {
                    try {
                        int id = Integer.parseInt(idInput);
                        partnerUI.findById(id);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "3. Add New Partner (C)":
                partnerUI.addNewPartner(null);
                break;
            case "4. Update Partner Phone (U)":
                partnerUI.updatePartnerPhone(null);
                break;
            case "5. Delete Partner (D)":
                String deleteIdInput = JOptionPane.showInputDialog("Enter partner ID to delete:");
                if (deleteIdInput != null) {
                    try {
                        int id = Integer.parseInt(deleteIdInput);
                        partnerUI.deletePartner(id);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "6. Manage Books (ADMIN ONLY)":
                menuBook.showMenu();
                break;

            default:

                break;
        }
    }
}