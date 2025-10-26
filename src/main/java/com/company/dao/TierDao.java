package com.company.dao;

import com.company.dto.TierDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TierDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final class TierRowMapper implements RowMapper<TierDto> {
        @Override
        public TierDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            TierDto tier = new TierDto();
            tier.setTiercode(rs.getInt("tiercode"));
            tier.setTiername(rs.getString("tiername"));
            return tier;
        }
    }

    public List<TierDto> findAll() {
        String sql = "SELECT tiercode, tiername FROM tier ORDER BY tiercode";
        return jdbcTemplate.query(sql, new TierRowMapper());
    }

    public TierDto findById(Integer tiercode) {
        String sql = "SELECT tiercode, tiername FROM tier WHERE tiercode = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new TierRowMapper(), tiercode);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public TierDto save(TierDto tier) {
        String sql = "INSERT INTO tier (tiercode, tiername) VALUES (:tiercode, :tiername)";
        Map<String, Object> params = new HashMap<>();
        params.put("tiercode", tier.getTiercode());
        params.put("tiername", tier.getTiername());
        namedParameterJdbcTemplate.update(sql, params);
        return tier;
    }

    public TierDto update(Integer tiercode, TierDto tier) {
        String sql = "UPDATE tier SET tiername = :tiername WHERE tiercode = :tiercode";
        Map<String, Object> params = new HashMap<>();
        params.put("tiercode", tiercode);
        params.put("tiername", tier.getTiername());
        namedParameterJdbcTemplate.update(sql, params);
        return findById(tiercode);
    }

    public void delete(Integer tiercode) {
        String sql = "DELETE FROM tier WHERE tiercode = ?";
        jdbcTemplate.update(sql, tiercode);
    }
    
    public List<TierDto> searchTiers(String searchTerm, Map<String, Object> filters) {
        StringBuilder sql = new StringBuilder("SELECT tiercode, tiername FROM tier WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        
        // Add search term condition
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append(" AND (tiername ILIKE :searchTerm OR CAST(tiercode AS TEXT) LIKE :searchTerm)");
            params.put("searchTerm", "%" + searchTerm + "%");
        }
        
        // Add filter conditions
        if (filters != null && !filters.isEmpty()) {
            for (Map.Entry<String, Object> filter : filters.entrySet()) {
                String key = filter.getKey();
                Object value = filter.getValue();
                
                switch (key) {
                    case "tiername":
                        sql.append(" AND tiername ILIKE :").append(key);
                        params.put(key, "%" + value + "%");
                        break;
                    case "tiercode":
                        sql.append(" AND tiercode = :").append(key);
                        params.put(key, value);
                        break;
                    default:
                        // Ignore unknown filter parameters
                        break;
                }
            }
        }
        
        sql.append(" ORDER BY tiercode");
        
        return namedParameterJdbcTemplate.query(sql.toString(), params, new TierRowMapper());
    }
}