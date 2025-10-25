package com.company.dao.queries;

import com.company.dto.queries.DepartmentAnalysisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentAnalysisDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class DepartmentAnalysisRowMapper implements RowMapper<DepartmentAnalysisDto> {
        @Override
        public DepartmentAnalysisDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            DepartmentAnalysisDto result = new DepartmentAnalysisDto();
            result.setLocationName(rs.getString("locationname"));
            result.setDeptWithMostEmployees(rs.getString("dept_with_most_employees"));
            result.setDeptEmployeeCount(rs.getInt("dept_employee_count"));
            result.setAvgSalaryOfLowestDept(rs.getDouble("avg_salary_of_lowest_dept"));
            return result;
        }
    }

    public List<DepartmentAnalysisDto> getDepartmentAnalysis() {
        String sql = """
            SELECT 
                loc.locationname,
                (SELECT d.departmentname 
                 FROM employee e2 
                 JOIN department d ON e2.departmentcode = d.departmentcode 
                 WHERE e2.locationcode = loc.locationcode 
                 GROUP BY d.departmentname 
                 ORDER BY COUNT(e2.empno) DESC 
                 LIMIT 1) AS dept_with_most_employees,
                (SELECT COUNT(*) 
                 FROM employee e3 
                 JOIN department d3 ON e3.departmentcode = d3.departmentcode 
                 WHERE e3.locationcode = loc.locationcode 
                 GROUP BY d3.departmentname 
                 ORDER BY COUNT(*) DESC 
                 LIMIT 1) AS dept_employee_count,
                (SELECT AVG(e4.salary) 
                 FROM employee e4 
                 JOIN department d4 ON e4.departmentcode = d4.departmentcode 
                 WHERE e4.locationcode = loc.locationcode 
                 AND d4.departmentname = (
                     SELECT d5.departmentname
                     FROM employee e5
                     JOIN department d5 ON e5.departmentcode = d5.departmentcode
                     WHERE e5.locationcode = loc.locationcode
                     GROUP BY d5.departmentname
                     ORDER BY AVG(e5.salary) ASC
                     LIMIT 1
                 )) AS avg_salary_of_lowest_dept
            FROM location loc
            """;
            
        return jdbcTemplate.query(sql, new DepartmentAnalysisRowMapper());
    }
}