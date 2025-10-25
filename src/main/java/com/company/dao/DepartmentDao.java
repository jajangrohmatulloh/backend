package com.company.dao;

import com.company.dto.DepartmentDto;
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
public class DepartmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final class DepartmentRowMapper implements RowMapper<DepartmentDto> {
        @Override
        public DepartmentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            DepartmentDto department = new DepartmentDto();
            department.setDepartmentcode(rs.getString("departmentcode"));
            department.setDepartmentname(rs.getString("departmentname"));
            return department;
        }
    }

    public List<DepartmentDto> findAll() {
        String sql = "SELECT departmentcode, departmentname FROM department ORDER BY departmentcode";
        return jdbcTemplate.query(sql, new DepartmentRowMapper());
    }

    public DepartmentDto findById(String departmentcode) {
        String sql = "SELECT departmentcode, departmentname FROM department WHERE departmentcode = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new DepartmentRowMapper(), departmentcode);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public DepartmentDto save(DepartmentDto department) {
        String sql = "INSERT INTO department (departmentcode, departmentname) VALUES (:departmentcode, :departmentname)";
        Map<String, Object> params = new HashMap<>();
        params.put("departmentcode", department.getDepartmentcode());
        params.put("departmentname", department.getDepartmentname());
        namedParameterJdbcTemplate.update(sql, params);
        return department;
    }

    public DepartmentDto update(String departmentcode, DepartmentDto department) {
        String sql = "UPDATE department SET departmentname = :departmentname WHERE departmentcode = :departmentcode";
        Map<String, Object> params = new HashMap<>();
        params.put("departmentcode", departmentcode);
        params.put("departmentname", department.getDepartmentname());
        namedParameterJdbcTemplate.update(sql, params);
        return findById(departmentcode);
    }

    public void delete(String departmentcode) {
        String sql = "DELETE FROM department WHERE departmentcode = ?";
        jdbcTemplate.update(sql, departmentcode);
    }
}