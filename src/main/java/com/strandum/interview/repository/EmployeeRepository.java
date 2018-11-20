package com.strandum.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strandum.interview.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeBatchRepository {

	@Query("FROM Employee e JOIN FETCH e.salary salary "
			+ " JOIN FETCH e.jobinfo jobInfo "
			+ " ORDER BY e.id")
	List<Employee> findAllFetch();

}
