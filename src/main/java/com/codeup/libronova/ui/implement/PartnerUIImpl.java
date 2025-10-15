package com.codeup.libronova.ui.implement;

import com.codeup.libronova.dao.implement.PartnerDAOImpl;
import com.codeup.libronova.domain.Partner;
import com.codeup.libronova.exception.PartnerServiceException;
import com.codeup.libronova.service.implement.PartnerServiceImpl;
import com.codeup.libronova.ui.PartnerUI;
import com.codeup.libronova.validation.implement.PartnerValidationImpl;

import javax.swing.*;
import java.util.List;

import javax.swing.*;
        import java.util.List;
import java.util.Objects;

// Assuming Partner, PartnerDAOImpl, PartnerValidationImpl, PartnerServiceImpl, PartnerUI, and PartnerServiceException are available.

public class PartnerUIImpl implements PartnerUI {
    private final PartnerValidationImpl validation = new PartnerValidationImpl();
    private final PartnerDAOImpl dao = new PartnerDAOImpl();
    private final PartnerServiceImpl service = new PartnerServiceImpl(dao, validation);

    @Override
    public void addNewPartner(Partner partner) {
        try {

            String codePartner = JOptionPane.showInputDialog("Enter partner code: ");
            if (codePartner == null || codePartner.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Input cancelled or code is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String fullname = JOptionPane.showInputDialog("Enter partner full name: ");
            if (fullname == null || fullname.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Input cancelled or name is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String phoneNumber = JOptionPane.showInputDialog("Enter partner phone number: ");
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Input cancelled or phone number is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }


            String statusInput = JOptionPane.showInputDialog("Enter partner status (true/false): ");
            if (statusInput == null || statusInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Input cancelled or status is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean status = Boolean.parseBoolean(statusInput.trim());


            Partner newPartner = new Partner(codePartner, fullname, phoneNumber, status);

            JOptionPane.showMessageDialog(null, "Registering partner, please wait...", "Processing", JOptionPane.INFORMATION_MESSAGE);
            service.addNewPartner(newPartner);

            JOptionPane.showMessageDialog(null, "Partner successfully registered: " + newPartner.getFullname(), "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (PartnerServiceException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An unexpected error occurred while registering the partner: " + e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Partner findById(int id) {

        try {
            if (id <= 0) { // Validating the parameter received
                JOptionPane.showMessageDialog(null, "Invalid ID parameter: must be greater than 0.", "Warning", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            Partner partner = service.findById(id);

            String message = String.format("Partner found:\nID: %d\nCode: %s\nName: %s\nPhone: %s\nStatus: %b",
                    partner.getId(), partner.getCode_partner(), partner.getFullname(), partner.getPhonenumber(), partner.isStatu_partner());
            JOptionPane.showMessageDialog(null, message, "Partner Found", JOptionPane.INFORMATION_MESSAGE);

            return partner;

        } catch (PartnerServiceException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public List<Partner> listAllPartner() {
        try {
            List<Partner> partners = service.listAllPartner();

            if (partners.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are no registered partners.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return partners;
            }

            StringBuilder message = new StringBuilder("Registered Partners List:\n\n");
            for (Partner partner : partners) {
                message.append(String.format("ID: %d, Code: %s, Name: %s, Phone: %s, Status: %b\n",
                        partner.getId(), partner.getCode_partner(), partner.getFullname(), partner.getPhonenumber(), partner.isStatu_partner()));
            }
            JOptionPane.showMessageDialog(null, message.toString(), "Registered Partners", JOptionPane.INFORMATION_MESSAGE);

            return partners;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error listing partners: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return List.of();
        }
    }

    @Override
    public boolean updatePartnerPhone(Partner partner) {

        listAllPartner();

        try {
            String idInput = JOptionPane.showInputDialog("Enter the ID of the partner to update phone number: ");
            if (idInput == null) return false;

            int id = Integer.parseInt(idInput);

            // Get the partner from the database
            Partner partnerToUpdate = service.findById(id);

            String phoneInput = JOptionPane.showInputDialog("Enter the new phone number (Current: " + partnerToUpdate.getPhonenumber() + "): ");
            if (phoneInput == null) return false;

            // Set the new value to the fetched object
            partnerToUpdate.setPhonenumber(phoneInput);

            // Pass the updated object to the service layer
            boolean update = service.updatePartnerPhone(partnerToUpdate);

            if (update) {
                JOptionPane.showMessageDialog(null, "Phone number successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "ERROR updating phone number. Check logs.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ERROR: The ID must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (PartnerServiceException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public void deletePartner(int id) {

        try {
            if (id <= 0) { // Validating the parameter received
                JOptionPane.showMessageDialog(null, "Invalid ID parameter: must be greater than 0.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Partner partner = service.findById(id); // Throws exception if not found

            String message = String.format("Are you sure you want to delete the partner?\nID: %d\nName: %s\nPhone: %s",
                    partner.getId(), partner.getFullname(), partner.getPhonenumber());

            int confirm = JOptionPane.showConfirmDialog(null, message, "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Deletion cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            service.deletePartner(id);
            JOptionPane.showMessageDialog(null, "Partner successfully deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (PartnerServiceException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Deletion Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
