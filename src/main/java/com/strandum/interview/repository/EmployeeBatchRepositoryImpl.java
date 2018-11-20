package com.strandum.interview.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.strandum.interview.domain.Employee;
import com.strandum.interview.domain.JobInfo;
import com.strandum.interview.domain.Salary;

@Repository
public class EmployeeBatchRepositoryImpl implements EmployeeBatchRepository {

    private static String SALARY_INSERT = "INSERT INTO Salary(id, salary, hourly_rate) VALUES (?, ?, ?)";
    private static String JOBINFO_INSERT = "INSERT INTO JobInfo(id, contract_type, job_title) VALUES (?, ?, ?)";
    private static String EMPLOYEE_INSERT = "INSERT INTO Employee(id, staff_number, first_name, last_name, id_Salary, id_JobInfo) VALUES (?, ?, ?, ?, ?, ?)";

    @Value("${hibernate.jdbc.batch_size}")
    private Integer batchSize;

	private JdbcTemplate jdbcTemplate;

	@Autowired
    public EmployeeBatchRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    @Transactional
	public List<Employee> persistAllInBatch(List<Employee> employees) {
    	
    	jdbcTemplate.execute("DELETE FROM Employee");
    	jdbcTemplate.execute("DELETE FROM Salary");
    	jdbcTemplate.execute("DELETE FROM JobInfo");
    	
        jdbcTemplate.batchUpdate(
                SALARY_INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                    	
                    	Salary salary = employees.get(i).getSalary();
                    	
                    	ps.setLong(1, Long.valueOf(i));
                        ps.setBigDecimal(2, salary.getSalary());
                        ps.setBigDecimal(3, salary.getHourlyRate());
                    }
                    
                    public int getBatchSize() {
                        return employees.size();
                    }
                } );

        jdbcTemplate.batchUpdate(
        		JOBINFO_INSERT,
        		new BatchPreparedStatementSetter() {
        			public void setValues(PreparedStatement ps, int i) throws SQLException {
        				
        				JobInfo jobInfo = employees.get(i).getJobinfo();
        				
        				ps.setLong(1, Long.valueOf(i));
        				ps.setString(2, jobInfo.getContractType());
        				ps.setString(3, jobInfo.getJobTitle());
        				
        			}
        			
        			public int getBatchSize() {
        				return employees.size();
        			}
        		} );

        jdbcTemplate.batchUpdate(
        		EMPLOYEE_INSERT,
        		new BatchPreparedStatementSetter() {
        			public void setValues(PreparedStatement ps, int i) throws SQLException {
        				
        				Employee employee = employees.get(i);
        				
        				ps.setLong(1, Long.valueOf(i));
        				ps.setString(2, employee.getStaffNumber());
        				ps.setString(3, employee.getFirstName());
        				ps.setString(4, employee.getLastName());
        				ps.setLong(5, Long.valueOf(i));
        				ps.setLong(6, Long.valueOf(i));
        				
        			}
        			
        			public int getBatchSize() {
        				return employees.size();
        			}
        		} );
        
        return employees;
	}

}
