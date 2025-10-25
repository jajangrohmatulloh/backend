package com.company.dao.queries;

import com.company.dto.queries.SalaryRankingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SalaryRankingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class SalaryRankingRowMapper implements RowMapper<SalaryRankingDto> {
        @Override
        public SalaryRankingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            SalaryRankingDto result = new SalaryRankingDto();
            result.setLocationName(rs.getString("locationname"));
            result.setDepartmentName(rs.getString("departmentname"));
            result.setEmpname(rs.getString("empname"));
            result.setTiername(rs.getString("tiername"));
            result.setSalary(rs.getLong("salary"));
            result.setRank(rs.getInt("rank"));
            result.setSalaryGap(rs.getLong("salary_gap"));
            return result;
        }
    }

    public List<SalaryRankingDto> getSalaryRanking() {
        String sql = """
            SELECT 
                l.locationname,
                d.departmentname,
                e.empname,
                t.tiername,
                e.salary,
                (SELECT COUNT(*) 
                 FROM employee e2 
                 JOIN department d2 ON e2.departmentcode = d2.departmentcode
                 JOIN location l2 ON e2.locationcode = l2.locationcode
                 WHERE d2.departmentname = d.departmentname 
                   AND l2.locationname = l.locationname 
                   AND (e2.salary > e.salary OR (e2.salary = e.salary AND e2.empno < e.empno))) + 1 AS rank,
                CASE 
                    WHEN (SELECT COUNT(*) 
                          FROM employee e3 
                          JOIN department d3 ON e3.departmentcode = d3.departmentcode
                          JOIN location l3 ON e3.locationcode = l3.locationcode
                          WHERE d3.departmentname = d.departmentname 
                            AND l3.locationname = l.locationname 
                            AND (e3.salary > e.salary OR (e3.salary = e.salary AND e3.empno < e.empno))) = 0 
                    THEN 0
                    WHEN e.salary = (
                        SELECT e4.salary
                        FROM employee e4
                        JOIN department d4 ON e4.departmentcode = d4.departmentcode
                        JOIN location l4 ON e4.locationcode = l4.locationcode
                        WHERE d4.departmentname = d.departmentname
                          AND l4.locationname = l.locationname
                          AND (e4.salary > e.salary OR (e4.salary = e.salary AND e4.empno < e.empno))
                        ORDER BY e4.salary DESC, e4.empno ASC
                        LIMIT 1
                    )
                    THEN 0
                    ELSE (
                        SELECT e4.salary - e.salary
                        FROM employee e4
                        JOIN department d4 ON e4.departmentcode = d4.departmentcode
                        JOIN location l4 ON e4.locationcode = l4.locationcode
                        WHERE d4.departmentname = d.departmentname
                          AND l4.locationname = l.locationname
                          AND (e4.salary > e.salary OR (e4.salary = e.salary AND e4.empno < e.empno))
                        ORDER BY e4.salary DESC, e4.empno ASC
                        LIMIT 1
                    )
                END AS salary_gap
            FROM employee e
            JOIN department d ON e.departmentcode = d.departmentcode
            JOIN location l ON e.locationcode = l.locationcode
            JOIN tier t ON e.tiercode = t.tiercode
            ORDER BY l.locationname, d.departmentname, e.salary DESC, e.empno
            """;
        
        return jdbcTemplate.query(sql, new SalaryRankingRowMapper());
    }
}