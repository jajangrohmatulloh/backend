package com.company.controller;

import com.company.dao.user.UserDao;
import com.company.model.User;
import com.company.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String, String> loginRequest) throws Exception {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // Manually authenticate user
        Optional<User> userOpt = userDao.findByUsername(username);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        User user = userOpt.get();
        if (!user.getEnabled()) {
            return ResponseEntity.status(401).body(Map.of("error", "User is disabled"));
        }

        // Check password
        System.out.println("Raw password: " + password);
        System.out.println("Stored password hash: " + user.getPassword());
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Password matches: " + passwordMatches);
        
        if (!passwordMatches) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        // Create UserDetails from user
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.getEnabled())
                .build();

        final String token = jwtUtil.generateToken(userDetails);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", username);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> registrationRequest) {
        String username = registrationRequest.get("username");
        String password = registrationRequest.get("password");
        String role = registrationRequest.getOrDefault("role", "USER");

        // Check if user already exists
        if (userDao.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        // Create new user
        User user = new User(username, passwordEncoder.encode(password), role);
        User savedUser = userDao.save(user);

        if (savedUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("userId", savedUser.getId());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Registration failed"));
        }
    }
}