package com.codeup.libronova.validation.implement;

import com.codeup.libronova.validation.PartnerValidation;

public class PartnerValidationImpl implements PartnerValidation {
    @Override
    public boolean isValidCode(String code) {
        return code != null && !code.trim().isEmpty();
    }

    @Override
    public boolean isValidFullname(String name) {
        return name != null && !name.trim().isEmpty();
    }

    @Override
    public boolean isValidPhonenumber(String phone) {
        return phone != null && !phone.trim().isEmpty();
    }

    @Override
    public boolean isValidStatus() {
        return true;
    }
}
