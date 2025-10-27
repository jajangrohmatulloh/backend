package com.company.dao.user;

import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            if (rs.getTimestamp("updated_at") != null) {
                user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
            return user;
        }
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, password, role, enabled, created_at, updated_at FROM users WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        String sql = "SELECT id, username, password, role, enabled, created_at, updated_at FROM users ORDER BY id";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public User save(User user) {
        String sql = "INSERT INTO users (username, password, role, enabled, created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            user.getUsername(), 
            user.getPassword(), // Password is already encoded in the controller
            user.getRole(), 
            user.getEnabled(), 
            user.getCreatedAt());
        
        // Retrieve the created user with the generated ID
        return findByUsername(user.getUsername()).orElse(null);
    }

    public User update(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, role = ?, enabled = ?, updated_at = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql,
            user.getUsername(),
            user.getPassword(), // In a real app, this should be encoded
            user.getRole(),
            user.getEnabled(),
            LocalDateTime.now(),
            user.getId());
        
        if (rowsUpdated > 0) {
            return findById(user.getId()).orElse(null);
        }
        return null;
    }

    public Optional<User> findById(Integer id) {
        String sql = "SELECT id, username, password, role, enabled, created_at, updated_at FROM users WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}