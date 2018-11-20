package com.strandum.interview.repository;

import java.util.List;

import com.strandum.interview.domain.Employee;

public interface EmployeeBatchRepository {

	public List<Employee> persistAllInBatch(List<Employee> employees);
	
}
