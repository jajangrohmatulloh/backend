package com.company.dao;

import com.company.dto.LocationDto;
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

@Repository
public class LocationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final class LocationRowMapper implements RowMapper<LocationDto> {
        @Override
        public LocationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            LocationDto location = new LocationDto();
            location.setLocationcode(rs.getString("locationcode"));
            location.setLocationname(rs.getString("locationname"));
            location.setLocationaddress(rs.getString("locationaddress"));
            return location;
        }
    }

    public List<LocationDto> findAll() {
        String sql = "SELECT locationcode, locationname, locationaddress FROM location ORDER BY locationcode";
        return jdbcTemplate.query(sql, new LocationRowMapper());
    }

    public LocationDto findById(String locationcode) {
        String sql = "SELECT locationcode, locationname, locationaddress FROM location WHERE locationcode = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new LocationRowMapper(), locationcode);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public LocationDto save(LocationDto location) {
        String sql = "INSERT INTO location (locationcode, locationname, locationaddress) VALUES (:locationcode, :locationname, :locationaddress)";
        Map<String, Object> params = new HashMap<>();
        params.put("locationcode", location.getLocationcode());
        params.put("locationname", location.getLocationname());
        params.put("locationaddress", location.getLocationaddress());
        namedParameterJdbcTemplate.update(sql, params);
        return location;
    }

    public LocationDto update(String locationcode, LocationDto location) {
        String sql = "UPDATE location SET locationname = :locationname, locationaddress = :locationaddress WHERE locationcode = :locationcode";
        Map<String, Object> params = new HashMap<>();
        params.put("locationcode", locationcode);
        params.put("locationname", location.getLocationname());
        params.put("locationaddress", location.getLocationaddress());
        namedParameterJdbcTemplate.update(sql, params);
        return findById(locationcode);
    }

    public void delete(String locationcode) {
        String sql = "DELETE FROM location WHERE locationcode = ?";
        jdbcTemplate.update(sql, locationcode);
    }
}