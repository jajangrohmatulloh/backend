package com.company.service.user;

import com.company.dao.user.UserDao;
import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userDao.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User createUser(String username, String password, String role) {
        // Check if user already exists
        if (userDao.findByUsername(username).isPresent()) {
            return null; // or throw an exception
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(password);
        
        User user = new User(username, encodedPassword, role);
        user.setEnabled(true);
        
        return userDao.save(user);
    }

    public User updateUser(Integer id, String username, String password, String role, Boolean enabled) {
        Optional<User> existingUserOpt = userDao.findById(id);
        
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            
            existingUser.setUsername(username);
            if (password != null && !password.isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(password));
            }
            existingUser.setRole(role);
            existingUser.setEnabled(enabled);
            
            return userDao.update(existingUser);
        }
        
        return null; // or throw an exception
    }

    public boolean deleteUser(Integer id) {
        Optional<User> user = userDao.findById(id);
        if (user.isPresent()) {
            userDao.delete(id);
            return true;
        }
        return false;
    }
}