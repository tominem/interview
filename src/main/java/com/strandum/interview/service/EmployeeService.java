package com.strandum.interview.service;

import java.util.List;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.strandum.interview.domain.Employee;

public interface EmployeeService {
	
	@Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000))
	public List<Employee> consumeAndPersist();

	public List<Employee> findAll();

}
