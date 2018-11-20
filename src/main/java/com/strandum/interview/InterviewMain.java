package com.strandum.interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.strandum.interview.service.EmployeeService;

/**
 * @author interview
 *
 *	The starting point for the application
 */
@Component
public class InterviewMain {
	
	@Autowired
	private EmployeeService employeeService;
	
	public void run() {
		System.out.println("I am the test Interview application!");
		employeeService.consumeAndPersist();
	}
}
