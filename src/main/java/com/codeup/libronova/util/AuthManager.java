package com.codeup.libronova.util;

import com.codeup.libronova.domain.User;

public class AuthManager {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234";
    private static final String PARTNER_USER = "partner";
    private static final String PARTNER_PASS = "1234";

    public User login(String username, String password) throws Exception {
        if (username.equals(ADMIN_USER) && password.equals(ADMIN_PASS)) {
            return new User(username, Role.ADMIN);
        } else if (username.equals(PARTNER_USER) && password.equals(PARTNER_PASS)) {
            return new User(username, Role.PARTNER);
        } else {
            throw new Exception("Invalid credentials.");
        }
    }
}
