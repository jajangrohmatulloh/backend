package com.company.dao;

import com.company.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final class EmployeeRowMapper implements RowMapper<EmployeeDto> {
        @Override
        public EmployeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmployeeDto employee = new EmployeeDto();
            employee.setEmpno(rs.getInt("empno"));
            employee.setEmpname(rs.getString("empname"));
            employee.setTiercode(rs.getInt("tiercode"));
            employee.setLocationcode(rs.getString("locationcode"));
            employee.setDepartmentcode(rs.getString("departmentcode"));
            // Handle potential null values for supervisorcode
            if (rs.getObject("supervisorcode") != null) {
                employee.setSupervisorcode(rs.getInt("supervisorcode"));
            }
            employee.setSalary(BigInteger.valueOf(rs.getLong("salary")));
            employee.setEntrydate(rs.getDate("entrydate"));
            return employee;
        }
    }

    public List<EmployeeDto> findAll() {
        String sql = "SELECT empno, empname, tiercode, locationcode, departmentcode, supervisorcode, salary, entrydate FROM employee ORDER BY empno";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    public EmployeeDto findById(Integer empno) {
        String sql = "SELECT empno, empname, tiercode, locationcode, departmentcode, supervisorcode, salary, entrydate FROM employee WHERE empno = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), empno);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public EmployeeDto save(EmployeeDto employee) {
        String sql = "INSERT INTO employee (empno, empname, tiercode, locationcode, departmentcode, supervisorcode, salary, entrydate) VALUES (:empno, :empname, :tiercode, :locationcode, :departmentcode, :supervisorcode, :salary, :entrydate)";
        Map<String, Object> params = new HashMap<>();
        params.put("empno", employee.getEmpno());
        params.put("empname", employee.getEmpname());
        params.put("tiercode", employee.getTiercode());
        params.put("locationcode", employee.getLocationcode());
        params.put("departmentcode", employee.getDepartmentcode());
        params.put("supervisorcode", employee.getSupervisorcode());
        params.put("salary", employee.getSalary().longValue());
        params.put("entrydate", employee.getEntrydate());
        namedParameterJdbcTemplate.update(sql, params);
        return employee;
    }

    public EmployeeDto update(Integer empno, EmployeeDto employee) {
        String sql = "UPDATE employee SET empname = :empname, tiercode = :tiercode, locationcode = :locationcode, departmentcode = :departmentcode, supervisorcode = :supervisorcode, salary = :salary, entrydate = :entrydate WHERE empno = :empno";
        Map<String, Object> params = new HashMap<>();
        params.put("empno", empno);
        params.put("empname", employee.getEmpname());
        params.put("tiercode", employee.getTiercode());
        params.put("locationcode", employee.getLocationcode());
        params.put("departmentcode", employee.getDepartmentcode());
        params.put("supervisorcode", employee.getSupervisorcode());
        params.put("salary", employee.getSalary().longValue());
        params.put("entrydate", employee.getEntrydate());
        namedParameterJdbcTemplate.update(sql, params);
        return findById(empno);
    }

    public void delete(Integer empno) {
        String sql = "DELETE FROM employee WHERE empno = ?";
        jdbcTemplate.update(sql, empno);
    }
    
    public List<EmployeeDto> searchEmployees(String searchTerm, Map<String, Object> filters) {
        StringBuilder sql = new StringBuilder("SELECT empno, empname, tiercode, locationcode, departmentcode, supervisorcode, salary, entrydate FROM employee WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        
        // Add search term condition (search across multiple fields)
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append(" AND (empname ILIKE :searchTerm OR CAST(empno AS TEXT) LIKE :searchTerm OR CAST(tiercode AS TEXT) LIKE :searchTerm OR locationcode ILIKE :searchTerm OR departmentcode ILIKE :searchTerm OR CAST(supervisorcode AS TEXT) LIKE :searchTerm OR CAST(salary AS TEXT) LIKE :searchTerm OR CAST(entrydate AS TEXT) LIKE :searchTerm)");
            params.put("searchTerm", "%" + searchTerm + "%");
        }
        
        // Add filter conditions
        if (filters != null && !filters.isEmpty()) {
            for (Map.Entry<String, Object> filter : filters.entrySet()) {
                String key = filter.getKey();
                Object value = filter.getValue();
                
                switch (key) {
                    case "empname":
                        sql.append(" AND empname ILIKE :").append(key);
                        params.put(key, "%" + value + "%");
                        break;
                    case "empno":
                        sql.append(" AND empno = :").append(key);
                        params.put(key, value);
                        break;
                    case "tiercode":
                        sql.append(" AND tiercode = :").append(key);
                        params.put(key, value);
                        break;
                    case "locationcode":
                        sql.append(" AND locationcode = :").append(key);
                        params.put(key, value);
                        break;
                    case "departmentcode":
                        sql.append(" AND departmentcode = :").append(key);
                        params.put(key, value);
                        break;
                    case "supervisorcode":
                        sql.append(" AND supervisorcode = :").append(key);
                        params.put(key, value);
                        break;
                    case "minSalary":
                        sql.append(" AND salary >= :").append(key);
                        params.put(key, value);
                        break;
                    case "maxSalary":
                        sql.append(" AND salary <= :").append(key);
                        params.put(key, value);
                        break;
                    case "minEntryDate":
                        sql.append(" AND entrydate >= :").append(key);
                        params.put(key, value);
                        break;
                    case "maxEntryDate":
                        sql.append(" AND entrydate <= :").append(key);
                        params.put(key, value);
                        break;
                    default:
                        // Ignore unknown filter parameters
                        break;
                }
            }
        }
        
        sql.append(" ORDER BY empno");
        
        return namedParameterJdbcTemplate.query(sql.toString(), params, new EmployeeRowMapper());
    }
}