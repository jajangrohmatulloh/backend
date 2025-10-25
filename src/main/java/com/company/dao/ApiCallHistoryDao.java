package com.company.dao;

import com.company.dto.ApiCallHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ApiCallHistoryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final class ApiCallHistoryRowMapper implements RowMapper<ApiCallHistoryDto> {
        @Override
        public ApiCallHistoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            ApiCallHistoryDto history = new ApiCallHistoryDto();
            history.setId(rs.getLong("id"));
            history.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            history.setEndpoint(rs.getString("endpoint"));
            history.setHttpMethod(rs.getString("http_method"));
            history.setUserIdentifier(rs.getString("user_identifier"));
            history.setResponseStatus(rs.getInt("response_status"));
            return history;
        }
    }

    public void save(ApiCallHistoryDto history) throws DataAccessException {
        String sql = "INSERT INTO api_call_history (timestamp, endpoint, http_method, user_identifier, response_status) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, 
                java.sql.Timestamp.valueOf(history.getTimestamp()),
                history.getEndpoint(),
                history.getHttpMethod(),
                history.getUserIdentifier(),
                history.getResponseStatus()
            );
        } catch (Exception e) {
            System.err.println("Error inserting into api_call_history: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<ApiCallHistoryDto> findAll() {
        String sql = "SELECT id, timestamp, endpoint, http_method, user_identifier, response_status FROM api_call_history ORDER BY timestamp DESC LIMIT 100";
        return jdbcTemplate.query(sql, new ApiCallHistoryRowMapper());
    }
}