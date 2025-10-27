package com.company.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123";
        String hashedPassword = "$2a$10$8K1p/a0dURXAm7QiTRqNa.E3YPWsKPv1Wxfz9BYF9EXtB3xy05Mym";
        
        boolean matches = encoder.matches(rawPassword, hashedPassword);
        System.out.println("Password matches: " + matches);
        
        String newHash = encoder.encode(rawPassword);
        System.out.println("New hash: " + newHash);
    }
}