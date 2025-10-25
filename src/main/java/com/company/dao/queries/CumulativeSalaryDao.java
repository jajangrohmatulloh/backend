package com.company.dao.queries;

import com.company.dto.queries.CumulativeSalaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CumulativeSalaryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class CumulativeSalaryRowMapper implements RowMapper<CumulativeSalaryDto> {
        @Override
        public CumulativeSalaryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            CumulativeSalaryDto result = new CumulativeSalaryDto();
            result.setDepartmentCode(rs.getString("departmentcode"));
            result.setEmpno(rs.getInt("empno"));
            result.setEmpname(rs.getString("empname"));
            result.setCumulativeSalary(rs.getLong("cumulative_salary"));
            return result;
        }
    }

    public List<CumulativeSalaryDto> getCumulativeSalary() {
        String sql = """
            SELECT 
                e1.departmentcode,
                e1.empno,
                e1.empname,
                (SELECT SUM(e2.salary) 
                 FROM employee e2 
                 WHERE e2.departmentcode = e1.departmentcode 
                   AND e2.empno <= e1.empno) AS cumulative_salary
            FROM employee e1
            ORDER BY e1.departmentcode, e1.empno
            """;
        
        return jdbcTemplate.query(sql, new CumulativeSalaryRowMapper());
    }
}