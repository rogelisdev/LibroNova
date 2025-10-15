package com.codeup.libronova.validation;

public interface PartnerValidation {
    boolean isValidCode(String code);
    boolean isValidFullname(String name);
    boolean isValidPhonenumber(String phone);
    boolean isValidStatus();
}
